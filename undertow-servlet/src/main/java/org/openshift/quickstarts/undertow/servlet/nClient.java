import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class nClient extends Thread {
	private Socket s;
	private String userInput = "";
	private String inputBuffer = "";
	private boolean sendMsgToServer = false;
	public nClient(Socket socket) {
		
		s = socket;
		System.out.println("constructor");
		
		
	}
	
	public void run() {
		
		InputStreamReader r;
		try {
			r = new InputStreamReader(s.getInputStream());
			BufferedReader clientIn = new BufferedReader(r);
		
			while(true) {
				try {
				userInput = clientIn.readLine();
				System.out.println("MESSAGE INPUT" + userInput);
				inputBuffer = userInput;
				sendMsgToServer = false;
				}catch(SocketException e) {
					
				}
		
			}	
		} catch (IOException e) {
			
		//	e.printStackTrace();
		}
		
	}
	
	public String getClientMsg() {
	
		if(!inputBuffer.equals("")  && sendMsgToServer == false) {
			
			sendMsgToServer = true;
			return inputBuffer;
			
		}
		return "";
	}
	public void sendMsg(String msg) {

		System.out.println("PRINTING USER MSG");
		
		try {
	
			PrintWriter clientOut = new PrintWriter(s.getOutputStream(), true);
			
			clientOut.println(msg);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
