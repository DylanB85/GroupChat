import java.io.BufferedReader;
import java.io.PrintWriter;

public class User {
	public PrintWriter out;
	public BufferedReader in;
	
	
	public User(PrintWriter out,BufferedReader in){
		this.out = out;
		this.in = in;
	}
}
