
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ServerChat {

	static ServerSocket serverSocket = null; //server socket
	static Socket socket = null; //client socket

	// server can only accept 'maxClientCount'  (5)
	static int maxClient = 5;
	static final clientThread[] threads = new clientThread[maxClient];

	public static void main(String args[]) {

		int portNumber = 6067;
		if (args.length < 1) {
			System.out.println("Now using port number=" + portNumber);
		}

		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				socket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClient; i++) {
					if (threads[i] == null) {
						(threads[i] = new clientThread(socket, threads)).start();
						break;
					}
				}
				if (i == maxClient) {
					PrintStream os = new PrintStream(socket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					socket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

class clientThread extends Thread {

	DataInputStream input = null;
	PrintStream print = null;
	Socket socket = null;
	final clientThread[] threads;
	int maxClient;

	public clientThread(Socket socket, clientThread[] threads) {
		this.socket = socket;
		this.threads = threads;
		maxClient = threads.length;
	}

	public void run() {
		int maxClient = this.maxClient;
		clientThread[] threads = this.threads;

		try {
			input = new DataInputStream(socket.getInputStream());
			print = new PrintStream(socket.getOutputStream());
			print.println("Enter your name.");
			String name = input.readLine();

			for (int i = 0; i < maxClient; i++) {
				if (threads[i] != null && threads[i] != this) {
					threads[i].print.println("- A new user entered the chat room -");
				}
			}
			while (true) {
				String line = input.readLine();
				if (line.startsWith("/leave")) {
					break;
				}
				for (int i = 0; i < maxClient; i++) {
					if (threads[i] != null) {
						threads[i].print.println(name + ": " + line);
					}
				}
			}
			for (int i = 0; i < maxClient; i++) {
				if (threads[i] != null && threads[i] != this) {
					threads[i].print.println("- A user has left the chat room -");
				}
			}

			socket.close();
		} catch (IOException e) {
		}
	}
}
