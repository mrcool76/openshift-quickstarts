


	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.ServerSocket;
	import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
	
	public class ChatServer extends Thread{

		// accept a connection
		// respond to the request 
		
		private ServerSocket in;
		private boolean exit = false;
		private static List<nClient> chatClients = new ArrayList<nClient>();
		public ChatServer() {
			/*
			System.out.println("Running on port " +port);
			try {
				in = new ServerSocket(port);
				System.out.println(in.getLocalSocketAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
		}
		public void setPort(int port) {
			System.out.println("Running on port " +port);
			try {
				in = new ServerSocket(port);
				System.out.println(in.getLocalSocketAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			String userMsg = "";
		
			checkServerInput.start();
			
			while(true) {
				
				try {
					Thread.sleep(100);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
		
				for(int x = 0; x< chatClients.size();x++){
					userMsg = chatClients.get(x).getClientMsg();
					if(!userMsg.equals("")) {
						for(int y = 0; y< chatClients.size();y++){
							if(y!= x) {
								chatClients.get(y).sendMsg(userMsg);	
							}		
						}
					}	
				}
			}
			
	
			
		}
		public int getNumClients() {
			
			return chatClients.size();
		}
		public void go() {
			//List<nClient> chatClients = new ArrayList<>();
			
			while(!exit) {
			try {	
				System.out.println("Server listening");
				try {
					Socket s = in.accept();
					
					System.out.println("Server accepted connection on " + in.getLocalPort() + " ; " + s.getPort() );
					
					nClient newPerson = new nClient(s);
					
					
					newPerson.start();
					chatClients.add(newPerson);
					
					System.out.println(chatClients.size());
				} catch(SocketException se) {
				//	se.printStackTrace();
				}
			}catch (IOException e) {
				//e.printStackTrace();
			}
		}	
		try {
			System.out.println("Socket closed");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		}
		

		public boolean checkGUI(String[] args) {
			for(int x = 0; x<args.length;x++) {
				if(args[x].equals("-gui")){
					return true;
				}
			}
			return false;
		}
		public String checkPort(String[] args) {
			String port = "14001";
			for(int x = 0; x<args.length;x++) {
				if(args[x].equals("-csp")){
					try {
						port = args[x+1];
					}catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("No port specified");
						return null;
					}
				}
				
			}
			return port;
		}
		Thread checkServerInput = new Thread() {
		    public void run() {
		    	System.out.println("CHECKING INPUT");
		    	String input = "";
		    	while(true) {
		    		
		        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		        	try{
		        		input = reader.readLine();
		        		System.out.println(input);
		        	}catch(IOException e){
		        		
		        	}
		        	if(input.equals("EXIT")) {
		        		exit = true;
		        		
		        		try {
							in.close();
							System.out.println("Server closed");
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		    	}
		    }
		};

}

