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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class FileData {
	private String fileName;
	private String uploader;
	private String size;
	private String description;
	private final static String fileDBPath = "..\\ServerSideDatabase\\Files";
	
	public FileData(String fileName, String uploader, String size, String description) {
		this.fileName = fileName;
		this.uploader = uploader;
		this.size = size;
		this.description = description;
	}
	
	public int sizeVal() {
		int val = Integer.parseInt(size);
		return val;
	}
	
	public String getPath() { return fileDBPath + "\\" + getID(); }
	
	public String getSize() { return size; }
	
	public String getID() { return uploader + "-" + fileName; }
	
	public String getName() { return fileName; }
	
	public String metaName() { return getID() + ".meta"; }
	
	public String getUploader()	{ return uploader; }
	
	public String getDesc() { return description; }
	
	public Double cost() { return cost(sizeVal()); }
	
	public static Double cost(int size) {
		double cost = size * .00003;
		if (cost > 3.0)
			return 3.0;
		else {
			return cost;
		}
	}
}

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
	private FileData[] files;
	private File userData;
	private Double userBalance;
	
	final static String fileDBPath = "..\\ServerSideDatabase\\Files";
	final static String userDBPath = "..\\ServerSideDatabase\\Users";
	
	private boolean userOwnsFile(String selectedFile) throws FileNotFoundException {
		Scanner scn = new Scanner(userData);
		// check the user's data for the file ID
		while (scn.hasNextLine()) {
			// check the element for the file ID
			if (scn.nextLine().equals(selectedFile)) {
				scn.close();
				return true;
				
			}
			
		}
		// the user's data contains no record of purchase
		scn.close();
		return false;
		
	}
	
	private void download(int selectedRow) {
		FileData file = files[selectedRow];
		String selectedFileID = file.getID();
		
		try {
			if (userOwnsFile(selectedFileID)) {
				downloadAlreadyOwnedFile(file);
			} else {
				PurchaseScreen pScreen = new PurchaseScreen(userName,
						userDBPath + "\\" + userName + ".DAT",
						userBalance,
						file);
				pScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pScreen.pack();
				pScreen.setVisible(true);
				dispose();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}

	private void downloadAlreadyOwnedFile(FileData file) {
		int a = JOptionPane.showConfirmDialog(this, 
				"You already own this file. "
				+ "Are you sure you want to download?");
		if (a == JOptionPane.YES_OPTION) { 
			String usrHome = System.getProperty("user.home");
			PurchaseScreen.download(new File(file.getPath()), new File(usrHome + "\\Downloads\\" + file.getName()));
			
			MainScreen wScreen = new MainScreen(userName);
			wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wScreen.pack();
			wScreen.setVisible(true);
			dispose();
		}
	}

	public DownloadScreen(String userName) {
		this.userName = userName;
		this.userData = new File(userDBPath + "\\" + userName + ".DAT");

		GridBagConstraints layoutConst = null;
		
		setTitle("FileSharer: Download");
		
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		
		selectLabel = new JLabel("Select a file:");
		balanceLabel = new JLabel("Balance:");
		
		try {
			userBalance = Double.parseDouble(getUserBalance()) * 0.01;
			balanceField = new JTextField("$" + String.format("%.2f", userBalance));
		} catch (Exception e) {
			balanceField = new JTextField("0.00");
		}
		balanceField.setEditable(false);
		
		String[] columnNames = {"Name", "Uploader", "Cost"};
		fileTable = new JTable(getFileList(), columnNames);
		fileTable.setDefaultEditor(Object.class, null);
		fileTable.setSize(480, 120);
		
		// Double click support for more tech-y users
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
		add(selectLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
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

	private String getUserBalance() throws FileNotFoundException {
		Scanner scn = new Scanner(userData);
		String balance = scn.nextLine();
		scn.close();
		
		return balance;
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
		files = new FileData[fileList.length];
		String[][] fileSheet = new String[fileList.length][3];
		
		try {
			for (int i = 0; i < fileList.length; i++) {
				Scanner scr = new Scanner(fileList[i]);
				String desc = new String();
				int j = 0;
				Integer size = 1;
				while (scr.hasNextLine()) {
					// get each piece of info from the file and store it
					String dataPoint = scr.nextLine();
					if (j < 2) {
						fileSheet[i][j] = dataPoint;
					} else if (j == 2) {
						// cap the cost to $3 @ .3 cents a kB; also, convert it to dollars
						size = Integer.parseInt(dataPoint);
						double cost = FileData.cost(size);
						dataPoint = "$" + String.format("%.2f", cost);
						fileSheet[i][j] = dataPoint;
					} else {
						desc += dataPoint;
					}
					
					j++;
				}
				files[i] = new FileData(fileSheet[i][0], fileSheet[i][1], size.toString(), desc);
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
