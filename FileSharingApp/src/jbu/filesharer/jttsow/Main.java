package jbu.filesharer.jttsow;

import java.util.Scanner;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {
	// TODO get UI to do the path stuff
	static String filePath;
	final static String testFile = "F:\\smile.bmp";
	
	// Main Class
	public static void main(String[] args) {
	    Scanner scnr = new Scanner(System.in); 
		boolean loggedIn = false;
		String firstName;
		
		do {
			firstName = ask("what is your first name?", scnr);
			String lastName = ask("what is your last name?", scnr);
			String password = ask("what is your password?", scnr);
			
			
			loggedIn = Credentials.loadCredentials(firstName, password);
		} while (!loggedIn);
	    System.out.println("Yay!!! You're logged in.");
		System.out.println("your email is: " + Credentials.user.email);
		
		MainScreen wScreen = new MainScreen(firstName);
		wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wScreen.pack();
		wScreen.setVisible(true);
		
	    while (true) {
			String upDown = ask("What do you want to do? (up/down/exit)", scnr);
			if (upDown.equals("up")) {
				UploadScreen uscreen = new UploadScreen(firstName);
				uscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				uscreen.pack();
				uscreen.setVisible(true);
			} else if (upDown.equals("exit")) {
				System.exit(-1);
			}
		}
	}
	
	public static String ask(String q, Scanner scan) {
		System.out.println(q);
		return scan.nextLine();
	}

}

class MainScreen extends JFrame implements ActionListener {
	private JButton upButton;
	private JButton downButton;
	private JButton exitButton;
	private JLabel instructionLabel;
	private String firstName;
	
	public MainScreen(String firstName) {
		GridBagConstraints layoutConst = null;
		this.firstName = firstName;
		
		setTitle("FileSharer: Welcome");
		
		upButton = new JButton("Upload");
		upButton.addActionListener(this);
		
		downButton = new JButton("Download");
		downButton.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		
		instructionLabel = new JLabel("Hello! Are we uploading or downloading?");
		
		setLayout(new GridBagLayout());
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(instructionLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(upButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		add(downButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		add(exitButton, layoutConst);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(upButton)) {
			UploadScreen uscreen = new UploadScreen(firstName);
			uscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			uscreen.pack();
			uscreen.setVisible(true);
			dispose();
		} else if (source.equals(downButton)) {
			
		} else if (source.equals(exitButton)) {
			System.exit(-1);
		}
	}
	
}