package hdm.ulss.streaming.experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;

// Helper class for reading input files and wirting benchmarks
public class IOHelper {
	final static long BASE_AMOUNT_WORDS = 30000; //constant for amount words
	
	// output method for simple wordcount on console
	public static void printResult(Map<String,Long> map){
		for(Map.Entry<String, Long> entry : map.entrySet()) {
		    String word = entry.getKey();
		    Long count = entry.getValue();
		    System.out.println(word + ": " + count);
		}
	}
		
	// output method for stream wordcount on console
	public static void printResult(DataSet<Tuple2<String, Integer>> dataSet){
		try {
			for(Tuple2<String, Integer> tuple: dataSet.collect()){
				System.out.println(tuple.f0 + ": " + tuple.f1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// writes benchmarks to console
	public static void printBenchmark(int iteration, String method, Date timeStamp1, Date timeStamp2){
		float timeDiff = (timeStamp2.getTime() - timeStamp1.getTime())/1000.0f;
		System.out.println("Iteration:" + iteration  + " words:" + calcAmount(iteration)  + " Method:" + method + "  Time:" + timeDiff + "s");
	}
	
	// writes benchmarks to file
	public static void writeBenchmark(int iteration, String method, Date timeStamp1, Date timeStamp2, String outputPath){
		float timeDiff = (timeStamp2.getTime() - timeStamp1.getTime())/1000.0f;
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputPath, true)))) {
		    out.println("Iteration:" + iteration  + " words:" + calcAmount(iteration) + " Method:" + method + "  Time:" + timeDiff + "s");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// reads and prepares file
	public static String readInput(String filePath){
		String text = "";
		File input = new File(filePath);
		try {
			Scanner sc = new Scanner(input);
			text = sc.useDelimiter("\\Z").next();
			sc.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		return text;
	}
	
	// calculate amount of words
	private static long calcAmount(int iteration){
		return (long) (BASE_AMOUNT_WORDS * Math.pow(2, (iteration - 1)));
	}

}
