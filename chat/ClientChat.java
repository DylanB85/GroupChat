
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientChat implements Runnable {

	// The client socket
	static Socket socket = null;
	// The output stream
	static PrintStream print = null;
	// The input stream
	static DataInputStream input = null;
	static BufferedReader inputLine = null;
	static boolean closed = false;
	OutputStream output;

	public static void main(String[] args) {

		// The default port.
		int portNumber = 6067;
		// The default host.
		String host = "localhost";

		if (args.length < 2) {
			System.out.println("Now using host =" + host
					+ ", port number =" + portNumber);
		} else {
			host = args[0];
			portNumber = Integer.valueOf(args[1]).intValue();
		}

		try {
			socket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			print = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("IOException");
		}

		if (socket != null && print != null && input != null) {
			try {
				new Thread(new ClientChat()).start();
				while (!closed) {
					print.println(inputLine.readLine());
				}
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
	}

	 public void send(String text) {
         try {
        	 output.write((text).getBytes());
         } catch (IOException e) {
			System.out.println("IOException");
         }
     }

	public void run() {
		String responseLine;
		try {
			while ((responseLine = input.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf(" Bye") != -1)
					break;
			}
			closed = true;
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

}
