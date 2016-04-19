/*--------------------------------------------------------

1. Name: Llewellyn Flauta / Date: 04/03/2016

2. Java version used, if not the official version for the class:
Java(TM) SE Runtime Environment (build 1.8.0_60-b27)

3. Precise command-line compilation examples / instructions:

> javac InetServer
> javac InetClient

4. Precise examples / instructions to run this program:

In separate shell windows:

> java InetServer
> java InetClient

All acceptable commands are displayed on the various consoles.

5. List of files needed for running the program.

InetServer.java
InetClient.java

6. Notes:

I had some syntax errors when typing out the programs. It was great to have the Professor’s bytecode to be able to troubleshoot which portion of my program was broken.

----------------------------------------------------------*/
import java.io.*; //get the input output libraries
import java.net.*; //get the java networking libraries

import org.omg.CORBA.portable.UnknownException; //i used corbortura in another class with eclipse

class Worker extends Thread {		//class definition
	Socket sock;					//class member, socket, local to worker
	Worker (Socket s) {sock = s;}	//constructor, assign arg s to local sock
	
	public void run(){
		//get i/o streams in/out from the socket:
		PrintStream out = null;
		BufferedReader in = null;
		
		//try to read stream, if not print error message
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream (sock.getOutputStream());
			//note this branch may not execute when expected
			try {
				String name;
				name = in.readLine();
				System.out.println("Looking up " + name);
				printRemoteAddress(name, out);
			} catch (IOException x){
				System.out.println("Server read error");
				x.printStackTrace();
			}
			sock.close(); //close this conection, but not the server
			} catch (IOException ioe) {System.out.println(ioe);}
			}
		

	static void printRemoteAddress(String name, PrintStream out) {
		// prints the remote address, throws an error if there is an issue
		try {
			out.println("Looking up " + name + "...");
			InetAddress machine = InetAddress.getByName(name);
			out.println("Host name : " + machine.getHostName()); //To client...
			out.println("Host IP : " + toText (machine.getAddress()));
			
		}catch(UnknownException | UnknownHostException ex){
			out.println("Failed in attempt to look up " + name);
		} 
		}
		
	

	//not interesting to us:
	static String toText(byte ip[] ) { //make portable for 128 bit format
		StringBuffer result = new StringBuffer ();
		for (int i = 0; i < ip.length; ++i){
			if (i>0) result.append (".");
			result.append (0xff & ip[i]);
		}
		// TODO Auto-generated method stub
		return result.toString();
	}

}

/*
 * sets up the port that we are working from.
 */
public class MyWebServer {
	public static void main (String a[]) throws IOException{
		int q_len = 6; //not interesting. number of requests for opsys to queue
		int port = 1888;
		Socket sock;
		
		ServerSocket servsock = new ServerSocket (port, q_len);
		
		
		System.out.println("Clark Elliott's Inet Server 1.8 starting up, listening at port:" + port);
		while (true){ //run loop for the server
			sock = servsock.accept(); //wait for the next client connection
			new Worker(sock).start(); //spawn worker to handle it
			
		}
	}
}