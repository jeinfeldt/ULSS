package hdm.ulss.streaming.experiment;

import java.util.Date;

public class App{

	public static void main(String[] args) {
		// init
		Date pre;
		Date post;
		SimpleWordCount simple = new SimpleWordCount();
		StreamWordCount stream = new StreamWordCount();
	
		// guard clause - reading arguments
		if(args.length < 3){
			System.out.println("Invalid number of command line parameters, you need:");
			System.out.println("- path to the input file");
			System.out.println("- number of iterations for the experiment");
			System.out.println("- flag whether the input should be increased (1,0)");
			System.out.println("- optional: path for outputfile of benchmarks");
			System.exit(0);
		}
		
		// fetching information
		String filePath = args[0];
		int iterations = Integer.parseInt(args[1]);
		int increaseFlag = Integer.parseInt(args[2]);
		String outputPath = null;
		String text = IOHelper.readInput(filePath);
		
		// if a path for output is specified, write to file
		if(args.length == 4){
			outputPath = args[3];
		}
		System.out.println("--- starting experiment ---");
		// we want multiple iterations
		for(int i=0; i<iterations; i+=1){
			
			// testing simple wordcount
			pre = new Date();
			simple.count(text);
			post = new Date();
			if(outputPath != null){
				IOHelper.writeBenchmark(i+1, "simple", pre, post, outputPath);				
			} else {
				IOHelper.printBenchmark(i+1, "simple", pre, post);
			}
			
			// testing stream wordcount
			pre = new Date();
			stream.count(text);
			post = new Date();
			if(outputPath != null){
				IOHelper.writeBenchmark(i+1, "flink", pre, post, outputPath);				
			} else {
				IOHelper.printBenchmark(i+1, "flink", pre, post);
			}
		
			// increasing textamount
			if(increaseFlag == 1){
				text = text.concat(" " + text);
			}
		}
		System.out.println("--- finished experiment ---");
	}
}
