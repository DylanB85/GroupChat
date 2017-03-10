import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
	Socket socket;
	public static PrintWriter out;
	public static BufferedReader in;
	
	public static String username ="";
	
	public Client(){
		new ChatWindow();
		
		try {
			socket = new Socket("localhost", 6006);
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			while(true){
				
			String message = in.readLine();
			ChatWindow.writeToChat(message);
			
			}
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[]args){
		username = JOptionPane.showInputDialog("What is your name");
		new Client();
	}

}
