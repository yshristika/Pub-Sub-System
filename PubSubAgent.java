/**
 * PubSubAgent.java
 * author - Shristika yadav
 */
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class PubSubAgent extends Thread {
	ServerSocket client_listen;
	public PubSubAgent(int port) {
		try{
			client_listen=new ServerSocket(port);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//server side coding
	public void run(){
		try {
			
			for(;;){ 
				Socket clientConnect=client_listen.accept();
				
				BufferedReader br=new BufferedReader(new InputStreamReader(clientConnect.getInputStream()));
				PrintStream out=new PrintStream(clientConnect.getOutputStream(),true);
				DataOutputStream dos=new DataOutputStream(clientConnect.getOutputStream());
				
				String str,str1;
				
				str=br.readLine();
				if(str.equalsIgnoreCase("advertise")){
					BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
					str=br.readLine();
					String[] str5=str.split(":");
					for(int i=0;i<str5.length;i++)
						System.out.println(str5[i]);
					str1=kb.readLine();
					out.println(str1);
				}
				else{
					String[] str5=str.split(":");
					for(int i=0;i<str5.length;i++)
						System.out.println(str5[i]);
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]) throws UnknownHostException
	{
		String hostName="localhost";
		//byte[] ipAddr=new byte[]{(byte) 129,21,85,95};
		InetAddress ip=InetAddress.getByName("localhost");//getByAddress(ipAddr);
		int port=4200;
		try{
			Socket socket=new Socket(ip,port);
			BufferedReader din = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
			String newPort = din.readLine ();
			socket.close();
			din.close();
			
			int portToConnect=new Integer(newPort).intValue();
			socket = new Socket(ip, portToConnect);
			din = new BufferedReader ( new InputStreamReader (socket.getInputStream()));
			DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
			String str,str1;
			
			//AGian
			str=din.readLine();
			System.out.println(str);
			str1=kb.readLine();
			dos.writeBytes(str1+"\n");
			
			//Taking data for pub or sub
			str=din.readLine();
			System.out.println(str);
			str1=kb.readLine();
			dos.writeBytes(str1+"\n");
			if(str1.equals("1")){
				while(!((str=din.readLine()).equalsIgnoreCase("EXIT"))){ // "exit"
					String[] str5=str.split(":");
					for(int i=0;i<str5.length;i++)
						System.out.println(str5[i]);
					str1=kb.readLine();
					dos.writeBytes(str1+"\n"); //sends data
				}
				socket.close();
				din.close();
			}
			else if(str1.equals("2")){
				//System.out.println("2 Enter in it");
				while(!((str=din.readLine()).equalsIgnoreCase("EXIT"))){ // "exit"
					String[] str5=str.split(":");
					for(int i=0;i<str5.length;i++)
						System.out.println(str5[i]);
					str1=kb.readLine();
					dos.writeBytes(str1+"\n"); //sends data
				}
				socket.close();
				din.close();
				PubSubAgent aClient=new PubSubAgent(portToConnect);
				aClient.start();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}