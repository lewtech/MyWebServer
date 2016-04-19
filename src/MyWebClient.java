/*--------------------------------------------------------

1. Name: Llewellyn Flauta / Date: 04/03/2016

2. Java version used, if not the official version for the class:
Java(TM) SE Runtime Environment (build 1.8.0_60-b27)

3. Precise command-line compilation examples / instructions:

e.g.:
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

public class MyWebClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 1888;
		String serverName;
		if (args.length < 1) serverName = "localhost";
		else serverName = args[0];
		//processes input from keyboard.. goes to ip address if entered, quits if "quit", 
		System.out.println("Clark Elliott's Inet Client, 1.8. \n");
		System.out.println("Using server: " + serverName + ", Port: " + port);
		//reads the input stream
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		try {
			String name;
			do {
				System.out.println("Enter a hostname or an IP Address, (quit) to end: ");
				System.out.flush();
				name = in.readLine();
				if (name.indexOf("quit")<0) getRemoteAddress(name,serverName, port);
			} while (name.indexOf("quit")<0); 
			System.out.println("Cancelled by user request.");
		}catch (IOException x) {x.printStackTrace();}
	}

	static void getRemoteAddress(String name, String serverName, int port) {
		// initialize variables
		Socket sock;
		BufferedReader fromServer;
		PrintStream toServer;
		String textFromServer;
		try {
			//open our connection to server port, choose your own port number
			sock = new Socket(serverName, port);
			//create filter I/O streams for the socket:
			fromServer= new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//send machine name or ip address to server:
			toServer = new PrintStream(sock.getOutputStream());
			toServer.println(name); toServer.flush();
			//read two or three lines of response from the server
			//and bolock while syncronously waiting:
			for (int i = 1; i<=3; i++){
				textFromServer= fromServer.readLine();
				if (textFromServer != null) System.out.println(textFromServer);
			}
			sock.close(); //closes connections
		}catch (IOException x) { //prints error
			System.out.println("Socket error.");
			x.printStackTrace();
			}
	}
	
	
		
	
	
	static String toText (byte ip[]) { //make portable for 128 bit format
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < ip.length; ++i){
			if (i>0) result.append(".");
			result.append (0xff & ip[i]);
		}
		return result.toString();
		}
		
		
	}


