/**
 * EventManager.java
 * author - Shristika yadav
 */
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class EventManager extends Thread {
	ServerSocket listen;
	
	Topic topic;
	Event event;
	static int event_id=0;
	static int sub_port_no;
	static InetAddress clientAddr;
	static String usr_name="nope";
	
	static List<Subscriber> subscribers_list=new ArrayList<Subscriber>();
	static List<Event> events_list=new ArrayList<Event>();
	static List<Object> notify_list=new ArrayList<Object>();
	static Hashtable<String, ArrayList> queue=new Hashtable<String,ArrayList>();
	int port=4200;
	static int id=0;
	
	public EventManager(int port){
		try {
			listen=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()	{
        try {
            Socket clientConnection = listen.accept();
            clientAddr=clientConnection.getInetAddress();
            sub_port_no=clientConnection.getPort();
            BufferedReader br=new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            PrintStream out = new PrintStream (clientConnection.getOutputStream (), true);
            DataOutputStream dos=new DataOutputStream(clientConnection.getOutputStream());
            String str="yes",topc = null,title,content;
			String str1;
			int i;
			int flag=0;
			Subscriber sc = null;
			
			
			// When a subscriber connects again than to show him his notification.
			out.println("Enter username");
    		usr_name=br.readLine();
        	for(i=0;i<subscribers_list.size();i++){
        		sc=subscribers_list.get(i);

        		if(sc.usr_name.equals(usr_name) ){
            		out.println("Press 1 to view notification or  2 to view menu");
                	str=br.readLine(); 
                	if(str.equals("1")){
                		notifySubscribers(out,br,sc);	
                	}
        		}
        		else{
        			sc=null;
        		}
        	}
        	
        		
            	/*
            	 * Publisher menu
            	 */
        		out.println("Press 1 to publish and 2 to subscribe");
            	str=br.readLine();
            	if(str.equals("1")){
    				while(!str.equalsIgnoreCase("NO")){

    	        		out.print("[0]. if want to add new topic ");
    	        		for(i=0;i<topic.list.size();i++)
    	        			out.print(":"+"["+(i+1)+"]. "+topic.list.get(i));
    	        		out.println(":Choose the topic by entering the number.");
    	                str1=br.readLine();
    	                if(str1.equals("0")){
    	                	flag=0;
    	                	out.println("Enter the topic under which you want to publish");
    	                	topc=br.readLine();
    	                	for(i=0;i<topic.list.size();i++){
    	                		if(str1.equalsIgnoreCase(topic.list.get(i))){
    	                			out.println("Topic already present in the topics list.:This topic chosen for you.");
    	                			flag=1;
    	                		}
    	                		else
    	                			str1=String.valueOf(topic.list.size()+1);
    	                	}
    	                	if(flag!=1){
    	                		topic=new Topic(topc);
    	                		for(String key:queue.keySet()){
    	                			notify_list=queue.get(key);
    	                			notify_list.add(topic);
    	               
    	                		}
    	                		
    	                	}
    	                }
    	 
    	                topc=topic.list.get((Integer.parseInt(str1))-1);
    	                out.println("Enter the title of the Article");
    	                title=br.readLine();
    	                out.println("Enter the content of the Article.");
    	                content=br.readLine();
    	                event=new Event(++event_id,topc,title,content);
    	                events_list.add(event);
    	                for(String key:queue.keySet()){
    	                	for(i=0;i<subscribers_list.size();i++){
    	                		Subscriber ps=subscribers_list.get(i);
    	                		if(ps.usr_name.equals(key) && ps.topics_list.contains(topc)){
    	                			notify_list=queue.get(key);
    	                			notify_list.add(event);
    	                			
    	                		}
    	                	}
                		}
    	                out.println("Do you want to publish another article?:Press yes or no");
    	                str=br.readLine();
    	                
    				}

    			}
    			/*
    			 * Subscribers menu
    			 */
            	else if(str.equals("2")){
            		notifySubscribers(out,br,sc);

            		Event event;
            		int flagly=0;
            		List<String> list=new ArrayList<String>();
            		String option="Yes",unsubs,choice;
            		boolean main_choice=true;
            		
            		//SUBSCRIBE
            		while(main_choice==true){
                		out.print("Choose the number - :");
                		out.print("[1] To subscribe to the topics:[2] To unsubscribe:[3] View the Articles:");
                		out.println("[4] Exit");
                		choice=br.readLine();
                		switch(choice){
                		case "1":
                			out.print("You are a new subscriber :Choose the topics you want to subscribe to?:");
                    		while(!option.equalsIgnoreCase("no")){
                    			out.print("TYPE NO TO STOP SUBSCRIBING:");
                    			for(i=0;i<topic.list.size();i++)
                        			out.print(":"+"["+(i+1)+"]. "+topic.list.get(i));
                    			out.println();
                    			option=br.readLine();
                    			if(!option.equalsIgnoreCase("no") && list.contains(topic.list.get(Integer.parseInt(option)-1))){
                    				out.print("Already subscribed to the topic:");
                    			}
                    			else if((!option.equalsIgnoreCase("no")) && (flagly!=1))
                    				list.add(topic.list.get(Integer.parseInt(option)-1));
                    			flagly=0;
                    		}
                    		if(subscribers_list.contains(sc)){
                    			sc.topics_list.addAll(list);
                    		}
                    		else{
                        		sc=new Subscriber(usr_name,clientAddr,sub_port_no,id++,out,br,list);
                        		queue.put(usr_name, new ArrayList<Object>());
                        		subscribers_list.add(sc);
                    		}
                    		out.print("You have subscribed to :");
                    		for(i=0;i<sc.topics_list.size();i++)
                    			out.print(sc.topics_list.get(i)+":");
                    		break;
                    	//Unsubscribe
                		case "2":
                			
                			if(sc==null){
                				out.print("list is empty:Nothing to unsubscribe:");
                			}
                			else{
                    			if(sc.topics_list.size()==0)
                    				out.println("list is empty:Nothing to unsubscribe:Press ENTER to continue");
                    			else{
                    				out.print(": To unsubscribe type the index of the topic");
                            		for(i=0;i<sc.topics_list.size();i++)
                            			out.print(":["+(i+1)+"] "+sc.topics_list.get(i));
                            		out.println();
                            		unsubs=br.readLine();
                            		sc.topics_list.remove(Integer.parseInt(unsubs)-1);
                    			}
                			}
                			
                    		break;
                    	//VIEW
                		case "3":
                			String view="yes";
                			while(!view.equalsIgnoreCase("no")){
                    			List<String> title_list=new ArrayList<String>();
                    			int id=0,article_no=0;
                    			String sub_topic;
                    			for(int index=0;index<sc.topics_list.size();index++){
                    				sub_topic=sc.topics_list.get(index);
                    				out.print("topic is = "+sub_topic+":");
                    				if(events_list.size()==0)
                    					out.print("Nothing to show");
                    				else{
                        				for(i=0;i<events_list.size();i++){
                        					event=events_list.get(i);
                        					if(sub_topic.equalsIgnoreCase(event.topic)){
                        						title_list.add(event.title);
                        						out.print("["+(event.id)+"] "+event.title+":");
                            				}	
                            			}	
                    				}

                    			}
                    			out.println("Enter the number for the article you want to view");
                    			article_no=Integer.parseInt(br.readLine());
                    			event=events_list.get(article_no-1);
                    			out.print(event.topic+":"+event.title+":"+event.content+":");
                    			out.println("Do you want to look at more atricles - (type yes or no)");
                    			view=br.readLine();
                			}
                			
                			
                			break;
                		case "4":
                			main_choice=false;
                			break;
                		default:
                			System.out.println("Wrong input");
                		}
            			
            		}
            	}
            	else{
            		System.out.println("Wrong input");
            	}

        	
            } catch(Exception e) {
            	System.out.println(e);
            	e.printStackTrace();
            }   
   }

	/*
	 * This method is used to notify subscriber about all the new topics or 
	 * the new articles which came up while the subscriber was offline. 
	 */
	private void notifySubscribers(PrintStream out,BufferedReader br, Subscriber s) throws IOException{
		
		String str;

		if(!(s==null)){
			Event e=null;
			Topic tc=null;
			String user_ans;
			List<Object> notification_list=new ArrayList<Object>();
			
			notification_list=queue.get(usr_name);
				for(int index=0;index<notification_list.size();index++){
					Object obj=notification_list.get(index);
					if((obj.getClass().getName()).equals("Topic")){
						tc=(Topic) obj;
						out.print(":NOTIFICATION - :");
						out.print(": Adevrtise");
						out.print(":New topic has been advertised -  "+tc.data+":");
						out.println("If you want to subscribe to it press yes or no");
						user_ans=br.readLine();
						if(user_ans.equalsIgnoreCase("yes")){
							s.topics_list.add(tc.data);
							
						}
				}
					else if(obj.getClass().getName().equals("Event")){
						e=(Event) obj;
						out.print(":NOTIFICATION - :");
						out.print(":Article related to the topic you subscribed which is "+e.topic+":");
						out.print("title = "+e.title+": content = "+e.content+":");
					}
				
			}
			notification_list.clear();
    		out.println(":press 1 to view the menu");
    		str=br.readLine();
		}
		
	}

	public EventManager(int port, int id)	{
		this(port);
		this.id = id;
	}
	
	//gets the localport of the serverSocket.
	public int getLocalPort ()	{
        return listen.getLocalPort();
   }
	
	/*
	 * Connects on the main port, generates random, new, free port and connects the client on that port
	 */
	public void listenToPort(){
		try{
			int id=0;
			for(;;){
				Socket clientConnection=listen.accept();
				EventManager aServer=new EventManager(0,id++);
				sub_port_no=aServer.getLocalPort();
				
				aServer.start();
				PrintWriter out = new PrintWriter (clientConnection.getOutputStream (), true);
                out.println(aServer.getLocalPort());
                clientConnection.close();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void main(String args[])
	{
		new EventManager(4200).listenToPort();
	}
}