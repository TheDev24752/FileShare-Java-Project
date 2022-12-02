package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	private JTextField cardNoField;
	private JTextField ccvField;
	private JFormattedTextField expField;
	
	private String userDataPath;
	private Double fileCost;
	private Double balance;
	
	public PurchaseScreen(String userDataPath, Double cost, Double balance, String description) {
		this.userDataPath = userDataPath;
		this.fileCost = cost;
		this.balance = balance;
		
		GridBagConstraints layoutConst = null;
		
		setTitle("FileSharer: Purchase");
		
		descField = new JTextArea(4, 20);
		descField.setEditable(false);
		descField.setText(description);
		
		costLabel = new JLabel("Cost:");
		balanceLabel = new JLabel("Account:");
		remainderLabel = new JLabel("Cost left:");
		cardNoLabel = new JLabel("Card #:");
		ccvLabel = new JLabel("CCV:");
		expLabel = new JLabel("Exp:");
		
		costField = new JTextField("$" + String.format("%.2f",cost));
		costField.setEditable(false);
		
		balanceField = new JTextField("$" + String.format("%.2f",balance));
		balanceField.setEditable(false);
		
		// TODO add the difference
		remainderField = new JTextField(10);
		remainderField.setEditable(false);
		
		cardNoField = new JTextField(10);
		cardNoField.setEditable(true);
		
		ccvField = new JTextField(10);
		ccvField.setEditable(true);
		
		expField = new JFormattedTextField(new SimpleDateFormat("MM/yy"));
		
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
		
		
	}
	
}
