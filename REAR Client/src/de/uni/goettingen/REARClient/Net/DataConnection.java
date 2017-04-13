package de.uni.goettingen.REARClient.Net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import de.uni.goettingen.REARClient.SignalObject;
import de.uni.goettingen.REARClient.Net.SSH.SSHconnection;

public class DataConnection implements Runnable {
	private String			host;
	private int				port;
	private	File			file;
	private SSHconnection	ssh;
	private SignalObject	signal;
	private String			studentID;
	private String			examID;
	
	public DataConnection(String h, int p, SSHconnection s) {
		host 	= h;
		port 	= p;
		ssh  	= s;
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
		studentID	= sID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-") + "\n";
		examID		= eID.trim().replaceAll("[/\"\'|\\\\:\\*\\?<>]", "-") + "\n";
		log("push " + f.getAbsolutePath());
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		try {
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
			Thread.sleep(3000);
			out.close();
			sock.close();
			
			signal.finishedDownload();
			
			log("  closing Thread");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
