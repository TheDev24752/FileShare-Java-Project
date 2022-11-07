package jbu.filesharer.jttsow;

import java.io.*;

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