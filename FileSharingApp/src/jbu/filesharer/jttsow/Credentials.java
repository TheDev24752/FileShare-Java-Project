package jbu.filesharer.jttsow;

import java.util.Scanner;
import java.io.*;

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