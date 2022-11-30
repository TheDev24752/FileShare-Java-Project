package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class DownloadScreen extends JFrame implements ActionListener {
	private static final long serialVersionUID = 9199623328253410778L;
	private JButton downloadButton;
	private JButton backButton;
	private JLabel selectLabel;
	private JLabel balanceLabel;
	private JTextField balanceField;
	private JTable fileTable;
	private JScrollPane filePane;
	private String userName;
	
	public DownloadScreen(String userName) {
		GridBagConstraints layoutConst = null;
		this.userName = userName;
		
		setTitle("FileSharer: Download");
		
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		
		selectLabel = new JLabel("Select a file:");
		balanceLabel = new JLabel("Balance:");
		
		balanceField = new JTextField();
		balanceField.setEditable(false);
		
		//TODO get data to populate table
		fileTable = new JTable();
		fileTable.setSize(480, 120);
		filePane = new JScrollPane(fileTable);
		
		setLayout(new GridBagLayout());
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		layoutConst.gridheight = 4;
		add(filePane, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;
		add(balanceLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		add(balanceField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(downloadButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;
		add(backButton, layoutConst);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(downloadButton)) {
			
		} else if (source.equals(backButton)) {
			MainScreen wScreen = new MainScreen(userName);
			wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wScreen.pack();
			wScreen.setVisible(true);
			dispose();
		}
	}
	
}
