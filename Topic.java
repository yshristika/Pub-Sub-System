/**
 * Topic.java
 */
import java.util.*;

public class Topic {
	
	int id;
	/*
	 * arraylist which already consistes of some topics.
	 */
	static List<String> list=new ArrayList<String>(){{
		add("News");
		add("Movies");
		add("Quiz");
		add("Animals");
	}};
	
	String data;
	
	/*
	 * Constructor using which more topics can be added
	 */
	Topic(String topic){
		list.add(topic);
		this.data=topic;
	}
}

