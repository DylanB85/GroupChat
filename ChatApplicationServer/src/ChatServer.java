import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	
	ServerSocket sSocket;
	Socket socket;
	
	PrintWriter out;
	BufferedReader in;
	
	static Console console;
	
	static ArrayList<User> users = new ArrayList<User>();
	
	public ChatServer(){
		try{
			sSocket = new ServerSocket(6006);
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					while(true){
						try{
							socket =  sSocket.accept();
							
							
							out = new PrintWriter(socket.getOutputStream(),true);
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							
							users.add(new User(out,in));
							
							new Thread(new Runnable(){

								@Override
								public void run() {
									PrintWriter o = out;
									BufferedReader i = in;
									
									o.println("Welcome to the chat");
									while(true){
										try {
											String text = i.readLine();
											broadcast(text);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
								}
								
							}).start();
							
							
						} catch(IOException e){
							e.printStackTrace();
						}
					}
					
				}
				
			}).start();
			
			
		} catch(IOException e){
			e.printStackTrace();
		}
		
		console = new Console();
	}
	
	public static void main(String[]args){
		new ChatServer();
		console.writeToConsole("Server Started");
	}
	
	public static void broadcast(String text){
		for(int i=0; i < users.size(); i++){
			User user = users.get(i);
			if(user == null || user.out ==null){
				users.remove(user);
			}
			user.out.println(text);
		}
	}
}
