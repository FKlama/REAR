package de.uni.goettingen.REARClient.Net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import de.uni.goettingen.REARClient.SignalObject;

public class DataConnection implements Runnable {
	private File			uploadPath;
	private	File			file;
	private SignalObject	signal;
	private String			studentID;
	private String			examID;
	
	public DataConnection(File path) {
		uploadPath = path;
		signal  = null;
		
	}
	
	public void addSignal(SignalObject s) {
		signal = s;
	}
	
	private void log(String message) {
		if(signal != null)
			signal.log(message);
		else
			System.out.println(message);
	}
	
	public void pushFile(File f, String sID, String eID) {
		file		= f;
<<<<<<< HEAD
		studentID	= sID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-");
		examID		= eID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-");
		signal.log("push " + f.getAbsolutePath());
=======
		studentID	= sID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-") + "\n";
		examID		= eID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-") + "\n";
		log("push " + f.getAbsolutePath());
>>>>>>> branch 'ProductionVersion' of ssh://git@github.com/FKlama/REAR.git
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		try {
<<<<<<< HEAD
			signal.log("  inside Thread");
			String				filename    = examID + "_" + studentID + ".flac";
			File 				outFile		= new File(uploadPath, filename);
			signal.log("  copy: " + file.getAbsolutePath() + " => " + outFile.getAbsolutePath());
			copyFile(file, outFile);
			
=======
			log("  inside Thread");
			ssh.ensureConnection();
			log("  connecting to "+host+":"+port);
			Socket 				sock		= new Socket(host, port);
			DataOutputStream	out			= new DataOutputStream(sock.getOutputStream());
			FileInputStream		fStr		= new FileInputStream(file);
			byte[] 				buff 		= new byte[1024];
			int    				rsize;
			int					total = 0;
			out.write(studentID.getBytes(), 0, studentID.length());
			out.write(examID.getBytes(), 0, examID.length());
			while((rsize = fStr.read(buff, 0, 1024)) > 0) {
//				log("  rsize = " + rsize);
				out.write(buff, 0, rsize);
				total += rsize;
			}
			fStr.close();
			log("   size " + total);
>>>>>>> branch 'ProductionVersion' of ssh://git@github.com/FKlama/REAR.git
			Thread.sleep(3000);
			
			signal.finishedDownload();
			
<<<<<<< HEAD
			signal.log("  closing Thread");
=======
			log("  closing Thread");
>>>>>>> branch 'ProductionVersion' of ssh://git@github.com/FKlama/REAR.git
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	private void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}
