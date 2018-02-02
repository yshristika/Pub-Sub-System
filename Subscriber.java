

import java.io.*;
import java.net.InetAddress;
import java.util.*;

public class Subscriber {
	int id,port_no;
	static int notify_event=0;
	PrintStream out;
	List<String> topics_list=new ArrayList<String>();
	InetAddress ip1;
	BufferedReader br;
	String usr_name;
	/*
	 * Constructor in which new subscribers are added into the arraylist
	 */
	Subscriber(String usr_name,InetAddress ip1,int port_no,int id,PrintStream out,BufferedReader br,List<String> list){
		this.usr_name=usr_name;
		this.ip1=ip1;
		this.port_no=port_no;
		this.id=id;
		this.out=out;
		this.br=br;
		this.topics_list.addAll(list);
	}
	
}
