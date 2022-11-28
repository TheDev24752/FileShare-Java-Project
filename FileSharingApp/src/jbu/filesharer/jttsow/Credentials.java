package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Credentials {
	final static String credentialsDB = "M:\\FA2022\\Java\\Project\\ServerSideDatabase\\credentials.txt";
	String name;
	String lastName;
	int password;
	String email;
	public static Credentials user;

	// Default Constructor
	public Credentials() {
		this.name = "";
		this.lastName = "";
		this.password = 0;
		this.email = "";
	}

	// Constructor
	public Credentials(String name, String lastName, int password, String email) {
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

  //Log in function
	static boolean loadCredentials(String name, String lastName, int password) {
		try {
			// Opening and Reading the the file that contains the members' data
			File myObj = new File(credentialsDB);
			Scanner myReader = new Scanner(myObj);

			// Reading each line and spliting the data and storing it into an array
			while (myReader.hasNextLine()) {
				// itemize the data
				String data = myReader.nextLine();
				String[] arrOfStr = data.split("/", 4);
				
				// check if the user's input matches an entry in the database; will skip if the data is wrong
				int acct_pwd = Integer.parseInt(arrOfStr[2]);
				if (!arrOfStr[0].equals(name) || !arrOfStr[1].equals(lastName) || acct_pwd != password) {
					continue;
				}
				
				// build the Credentials object; user found
				user = new Credentials(arrOfStr[0], arrOfStr[1], acct_pwd, arrOfStr[3]);
				myReader.close();
				return true;
			}
			System.out.println("The entered username or password is wrong; try again.");
			myReader.close();
			return false;

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}
}

class LogInScreen extends JFrame implements ActionListener{
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
		
		System.out.println("test");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		String username;
		String password;
		
		username = usernameField.getName();
		password = passwordField.getName();
	}
	
	public static void main(String[] args) {
		// Creates logInScreen and its components		
		LogInScreen myFrame = new LogInScreen();
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		
	}
	
}