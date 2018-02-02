/**
 * Subscriber.java
 */
public class Event {
	int id=0;
	String topic;
	String title;
	String content;
	Event(int id,String topic,String title, String content){
		this.id=id;
		this.topic=topic;
		this.title=title;
		this.content=content;
	}
}
