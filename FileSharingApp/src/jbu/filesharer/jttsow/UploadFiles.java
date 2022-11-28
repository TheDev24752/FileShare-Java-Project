package jbu.filesharer.jttsow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class UploadFile { 
	final static String fileDBPath = "M:\\FA2022\\Java\\Project\\ServerSideDatabase\\Files";
	private String fileName;
	private long id;
	private String uploader;
	
	public UploadFile(String uploader, String path) throws IOException {
		this.uploader = uploader;
		String[] fileNameParts = path.split("\\\\");
		this.fileName = fileNameParts[fileNameParts.length - 1];
		
		File toUpload = new File(path);
		File uploaded = new File(fileDBPath + "\\" + fileName);
		System.out.println(uploaded.getAbsolutePath());
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(toUpload);
	        os = new FileOutputStream(uploaded);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    } 
		
	}
	
}  

class UploadScreen extends JFrame implements ActionListener {
	private JButton selectButton;
	private JButton uploadButton;
	private JLabel nameLabel;
	private JLabel sizeLabel;
	private JLabel descLabel;
	private JTextField fileField;
	private JTextField nameField;
	private JTextField sizeField;
	private JTextArea descField;
	private File selectedFile;
	private String uploaderName;
	
	public UploadScreen(String uploaderName) {
		this.uploaderName = uploaderName;
		GridBagConstraints layoutConst = null;
		
		setTitle("FileSharer: Upload");
		
		selectButton = new JButton("Select file");
		selectButton.addActionListener(this);
		
		uploadButton = new JButton("Upload");
		uploadButton.setEnabled(false);
		uploadButton.addActionListener(this);
		
		nameLabel = new JLabel("File name:");
		sizeLabel = new JLabel("File size:");
		descLabel = new JLabel("File description:");
		
		fileField = new JTextField(20);
		fileField.setEditable(false);
		
		nameField = new JTextField(20);
		nameField.setEditable(true);
		
		sizeField = new JTextField(20);
		sizeField.setEditable(false);
		
		descField = new JTextArea(4, 20);
		descField.setEditable(true);
		
		setLayout(new GridBagLayout());
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(selectButton, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;
		add(fileField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(nameLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		add(nameField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		add(sizeLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(sizeField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 0;
		layoutConst.gridy = 3;
		add(descLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;
		add(descField, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 1);
		layoutConst.gridx = 1;
		layoutConst.gridy = 4;
		add(uploadButton, layoutConst);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(selectButton)) {
			JFileChooser j = new JFileChooser();
			int response = j.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION)
            {
                // set the label to the path of the selected file
				selectedFile = j.getSelectedFile();
                fileField.setText(selectedFile.getAbsolutePath());
                
                long size = selectedFile.length();
                sizeField.setText(Long.toString(size));
                
                nameField.setText(selectedFile.getName());
                
                uploadButton.setEnabled(true);
            }
			
		} else if (source.equals(uploadButton)) {
			try {
				UploadFile upFile = new UploadFile(uploaderName, selectedFile.getAbsolutePath());
				// TODO reload main screen
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
}