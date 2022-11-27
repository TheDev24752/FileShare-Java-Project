package jbu.filesharer.jttsow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Main {
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
			int pwdInt;
			
			try {
				pwdInt = Integer.parseInt(password);
			} catch (Exception e) {
				System.out.println("please enter a number.");
				continue;
			}
			
			loggedIn = Credentials.loadCredentials(firstName, lastName, pwdInt);
		} while (!loggedIn);
	    System.out.println("Yay!!! You're logged in.");
		System.out.println("your email is: " + Credentials.user.email);
		
	    while (true) {
			String upDown = ask("What do you want to do? (up/down/exit)", scnr);
			if (upDown.equals("up")) {
				filePath = ask("Enter the path of the file:", scnr);
				try {
					UploadFile upFile = new UploadFile(firstName, filePath);
				} catch (Exception e) {
					System.out.println(e);
				}
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

class LogInScreen extends JFrame implements ActionListener{
	private JButton attemptLogIn;
	private JButton signUp;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JTextField passwordField;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("hi");
	}
	
}