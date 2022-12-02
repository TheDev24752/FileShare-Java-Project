package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Credentials {
	final static String credentialsDB = "..\\ServerSideDatabase\\Credentials.txt";
	String name;
	String password;
	String email;
	public static Credentials user;

	// Default Constructor
	public Credentials() {
		this.name = "";
		this.password = "";
		this.email = "";
	}

	// Constructor
	public Credentials(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

  //Log in function
	static boolean loadCredentials(String name, String password) {
		try {
			// Opening and Reading the file that contains the members' data
			//File myObj = new File(credentialsDBSalome);
			File myObj = new File(credentialsDB);
			
			Scanner myReader = new Scanner(myObj);
			
			// Reading each line and splitting the data and storing it into an array
			while (myReader.hasNextLine()) {
				// itemize the data
				String data = myReader.nextLine();
				String[] arrOfStr = data.split("/", 3);
				
				// check if the user's input matches an entry in the database; will skip if the data is wrong
				//int acct_pwd = Integer.parseInt(arrOfStr[1]);
				if (!arrOfStr[0].equals(name) || !arrOfStr[1].equals(password)) {
					System.out.println("Hang on.");
				} else {
					// build the Credentials object; user found
					user = new Credentials(arrOfStr[0], arrOfStr[1], arrOfStr[2]);
					myReader.close();
				
					return true;
				}
				
			}
			myReader.close();
			return false;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Sign up function
	static boolean signUp(String name, String password) {
		String email;
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please, enter your email address:");
		email = scnr.next();
		try{
			FileWriter fw = new FileWriter(credentialsDB, true);
			fw.write('\n' +name + "/" + password + "/" + email);
			fw.close();
			
			System.out.println("You're all set!!");
			user = new Credentials(name, password, email);
			return true;
		} catch (IOException i) {
			System.out.println("An error occured.");
			return false;
		}
		
	}
}

class LogInScreen extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2394667733742689013L;
	private JButton logInButton;
	private JButton signUpButton;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JPasswordField  passwordField;
	
	public LogInScreen() {
		GridBagConstraints layoutConst = null;
		
		// set frame's title
		setTitle("FileSharer: Log-in");
		
		// Create labels
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		
		// Create Buttons and adding action listeners
		logInButton = new JButton("log in");
		logInButton.addActionListener(this);
		signUpButton = new JButton("sign up");
		signUpButton.addActionListener(this);
		
		// create username field
		usernameField = new JTextField(15);
		usernameField.setEditable(true);
		
		// create password field
		passwordField = new JPasswordField(15);
		passwordField.setEditable(true);
		
		// Use a GridBagLayout
		setLayout(new GridBagLayout());
		
		//Specifying components' locations
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 10, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(usernameLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 1, 10, 10);
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;
		add(usernameField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 10, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(passwordLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 1, 10, 10);
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		add(passwordField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 5, 10, 10);
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		add(logInButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 5, 10, 10);
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(signUpButton, layoutConst);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		boolean loggedIn = false;
		String username;
		String password;
		
		username = usernameField.getText();
		password = String.valueOf(passwordField.getPassword());
		if (source.equals(logInButton)) {
			loggedIn = Credentials.loadCredentials(username, password);
			if (loggedIn) {
				MainScreen wScreen = new MainScreen(username);
				wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				wScreen.pack();
				wScreen.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "The username or password is incorrect.", "login", JOptionPane.WARNING_MESSAGE);
			}
		} else if (source.equals(signUpButton)) {
			loggedIn = Credentials.signUp(username, password);
			
		}
		
	}
	
	public static void main(String[] args) {
		// Creates logInScreen and its components		
		LogInScreen myFrame = new LogInScreen();
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		
	}
	
}