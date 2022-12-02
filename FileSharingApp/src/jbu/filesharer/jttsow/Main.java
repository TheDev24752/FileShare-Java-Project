package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	// TODO get UI to do the path stuff
	static String filePath;
	
	// Main Class
	public static void main(String[] args) {
		// Creates logInScreen and its components		
		LogInScreen myFrame = new LogInScreen();
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
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