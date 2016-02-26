package hdm.ulss.streaming.experiment;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SimpleWordCount {
	
	// empty constructor
	public SimpleWordCount() {
		
	}
	
	public Map<String, Long> count(String text) {
		Map<String, Long> result = new HashMap<>();
		Stream<String> input = Stream.of(text.toLowerCase().split("\\W+"));
		result = input.collect(groupingBy(name -> name, counting()));
		return result;
	}

}
