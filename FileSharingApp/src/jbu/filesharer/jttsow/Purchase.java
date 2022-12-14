package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

class PurchaseScreen extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2325949346103573554L;
	private JTextArea descField;
	private JButton buyButton;
	private JButton backButton;
	private JLabel costLabel;
	private JLabel balanceLabel;
	private JLabel remainderLabel;
	private JLabel cardNoLabel;
	private JLabel ccvLabel;
	private JLabel expLabel;
	private JTextField costField;
	private JTextField balanceField;
	private JTextField remainderField;
	private JFormattedTextField cardNoField;
	private JFormattedTextField ccvField;
	private JFormattedTextField expField;
	
	private FileData fileData;
	private String userName;
	private String userDataPath;
	private Double rem;
	private Double balance;
	
	public PurchaseScreen(String userName, String userDataPath, Double balance, FileData fileData) {
		this.userName = userName;
		this.userDataPath = userDataPath;
		this.fileData = fileData;
		this.balance = balance;
		
		GridBagConstraints layoutConst = null;
		
		setTitle("FileSharer: Purchase");
		
		descField = new JTextArea(4, 20);
		descField.setEditable(false);
		descField.setText(fileData.getName() + "\r\n" + fileData.getDesc());
		
		costLabel = new JLabel("Cost:");
		balanceLabel = new JLabel("Account:");
		remainderLabel = new JLabel("Cost left:");
		cardNoLabel = new JLabel("Card #:");
		ccvLabel = new JLabel("CCV:");
		expLabel = new JLabel("Exp:");
		
		costField = new JTextField("$" + String.format("%.2f", fileData.cost()));
		costField.setEditable(false);
		
		balanceField = new JTextField("$" + String.format("%.2f", balance));
		balanceField.setEditable(false);
		
		if ((rem = fileData.cost() - balance) < 0.0) {
			rem = 0.0;
		}
		remainderField = new JTextField("$" + String.format("%.2f", rem));
		remainderField.setEditable(false);
		
		cardNoField = new JFormattedTextField(createFormatter("####-####-####-####"));
		cardNoField.setColumns(12);
		cardNoField.setEditable(true);
		
		ccvField = new JFormattedTextField(createFormatter("###"));
		ccvField.setColumns(12);
		ccvField.setEditable(true);
		
		expField = new JFormattedTextField(createFormatter("##/##"));
		expField.setColumns(12);
		expField.setEditable(true);
		
		buyButton = new JButton("Purchase");
		buyButton.addActionListener(this);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		
		setLayout(new GridBagLayout());
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		layoutConst.gridwidth = 2;
		add(descField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(costLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		add(costField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		add(balanceLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(balanceField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 3;
		add(remainderLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;
		add(remainderField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 4;
		add(cardNoLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 4;
		add(cardNoField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 5;
		add(ccvLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 5;
		add(ccvField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 6;
		add(expLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 6;
		add(expField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 7;
		add(backButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 7;
		add(buyButton, layoutConst);
		
	}
	
	private void takeMoney(String dataPath, int decrease, String newFileName) throws IOException {
		BufferedReader dataIn = new BufferedReader(new FileReader(dataPath));
        StringBuffer inputBuffer = new StringBuffer();
        
        // get, increase, and save the users balance
        String line = dataIn.readLine();
        Integer balance = Integer.parseInt(line);
		balance -= decrease;
		line = balance.toString();
		inputBuffer.append(line + "\r\n");

        // save the currently owned files
        while ((line = dataIn.readLine()) != null) { 
            inputBuffer.append(line);
            inputBuffer.append("\r\n");
        }
        
        // append the new file
        inputBuffer.append(newFileName);
        dataIn.close();

        // write the data file
        FileOutputStream fileOut = new FileOutputStream(dataPath);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(backButton)) {
			DownloadScreen dScreen = new DownloadScreen(userName);
			dScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dScreen.pack();
			dScreen.setVisible(true);
			dispose();
		} else if (source.equals(buyButton)) {
			makePurchase();
			String usrHome = System.getProperty("user.home");
			download(new File(fileData.getPath()), new File(usrHome + "\\Downloads\\" + fileData.getName()));
			
			MainScreen wScreen = new MainScreen(userName);
			wScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wScreen.pack();
			wScreen.setVisible(true);
			dispose();
		}
	}
	
	public static void download(File toDownload, File downloaded) {
		InputStream is = null;
	    OutputStream os = null;
	    
	    try {
	        is = new FileInputStream(toDownload);
	        os = new FileOutputStream(downloaded);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	        is.close();
	        os.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

	private void makePurchase() {
		try {
			if (rem <= 0)
				takeMoney(userDataPath, (int)(fileData.cost() * 100), fileData.getID());
			else
				// would normally make purchase with the bank
				takeMoney(userDataPath, (int)(balance * 100), fileData.getID());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
}

