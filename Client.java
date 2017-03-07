import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	private Socket socket = null;
	private InputStream input = null;
	private OutputStream output = null;

	public void chatApp(){
		try{
			socket = new Socket("LocalHost", 6066);
			System.out.println("You are now connected to the chat");
			input = socket.getInputStream();
			output = socket.getOutputStream();
			reader();
			writer();
		} catch (UnknownHostException h){
			h.printStackTrace();
		} catch (IOException io){
			io.printStackTrace();
		}
	}

	public void reader(){
		Thread reads = new Thread(){
			public void run(){
				while(socket.isConnected()){
					try{
						byte[] readBuffer = new byte[200];
						int number = input.read(readBuffer);

						if(number > 0){
							byte[] arrayByte = new byte[number];
							System.arraycopy(readBuffer, 0, arrayByte, 0, number);
							String recievedMessages = new String(arrayByte, "UTF-8");
							System.out.println("Messages Recieved" + recievedMessages);
							/* else{
							 * notify();
							 * }
							 */
						}
					}catch(SocketException se){
						System.exit(0);
					} catch(IOException ioe){
						ioe.printStackTrace();
					}
				}
			}
		};
		reads.setPriority(Thread.MAX_PRIORITY);
		reads.start();
	}

	public void writer(){
		Thread write = new Thread(){
			public void run(){
				while(socket.isConnected()){
					try{
						BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
						sleep(100);
						String typedMessages = inputReader.readLine();
						if(typedMessages != null && typedMessages.length()>0){
							synchronized(socket){
								output.write(typedMessages.getBytes("UTF-8"));
								sleep(100);
							}
						};
					}catch(IOException ioe){
						ioe.printStackTrace();
					} catch (InterruptedException ie){
						ie.printStackTrace();
					}
				}
			}
		};
		write.setPriority(Thread.MAX_PRIORITY);
		write.start();
	}

	public static void main(String[]args) throws Exception{
		Client chatClient = new Client();
		chatClient.chatApp();
	}

}
