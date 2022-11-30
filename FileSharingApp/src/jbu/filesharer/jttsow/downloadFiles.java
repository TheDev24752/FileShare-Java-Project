package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;

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
	final static String fileDBPath = "..\\ServerSideDatabase\\Files";
	final static String userDBPath = "..\\ServerSideDatabase\\Users";
	
	private void download(int selectedRow) {
		System.out.println(selectedRow);
		
	}
	
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
		String[] columnNames = {"Name", "Uploader", "Cost"};
		fileTable = new JTable(getFileList(), columnNames);
		fileTable.setDefaultEditor(Object.class, null);
		fileTable.setSize(480, 120);
		fileTable.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent event){
		        if(event.getClickCount()==2){
		            download(fileTable.getSelectedRow());
		        }
		    }
		});
		
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

	private String[][] getFileList() {
		// get the directory of the file database
		File fileDB = new File(fileDBPath);

		// create a filter to get just the .meta files
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".meta");
			}
		};
		
		// get list of .meta files
		File[] fileList = fileDB.listFiles(filter);
		String[][] fileSheet = new String[fileList.length][3];
		
		try {
			for (int i = 0; i < fileList.length; i++) {
				Scanner scr = new Scanner(fileList[i]);
				for (int j = 0; j < 3; j++) {
					// get each piece of info from the file and store it in fileSheet
					String dataPoint = scr.nextLine();
					if (j == 2) {
						// cap the cost to $3 @ .3 cents a kB; also, convert it to dollars
						double cost = Math.min(Integer.parseInt(dataPoint) * .00003, 3.0);
						dataPoint = String.format("%.2f", cost);
					}
					fileSheet[i][j] = dataPoint;
				}
				scr.close();
			} 
			
		} catch (Exception e) {
			String[][] error = {{"error", "connection to file database failed", ""}};
			return error;
		}
		
		return fileSheet;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(downloadButton)) {
			download(fileTable.getSelectedRow());
		} else if (source.equals(backButton)) {
			MainScreen wScreen = new MainScreen(userName);
			wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wScreen.pack();
			wScreen.setVisible(true);
			dispose();
		}
	}
	
}
