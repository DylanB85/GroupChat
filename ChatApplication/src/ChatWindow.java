import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	static int WIDTH=640;
	static int HEIGHT = WIDTH/16*9;
	
	static final JPanel panel = new JPanel();
	static final JTextArea textarea = new JTextArea();
	static final JScrollPane scroll = new JScrollPane(textarea);
	static final JTextField textField = new JTextField();
	static final JButton button = new JButton("Send");
	
	public ChatWindow(){
		this.setSize(WIDTH,HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		
		scroll.setPreferredSize(new Dimension(WIDTH -32, HEIGHT -100));
		textField.setPreferredSize(new Dimension (WIDTH -32, 32));
	
		panel.add(scroll);
		panel.add(textField);
		panel.add(button);
		this.add(panel);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = textField.getText();
				Client.out.println(Client.username + ":" + text);
				textField.setText("");
				
			}
		});
		
	}
	
	public static void writeToChat(String text){
		textarea.append(text+"\n");
	}

}
