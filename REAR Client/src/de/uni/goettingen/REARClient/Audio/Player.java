package de.uni.goettingen.REARClient.Audio;

import java.io.File;

import de.uni.goettingen.REARClient.SignalObject;

public class Player {
	private Thread					t;
	private PlayerThread			pt;
	private Boolean					playing;
	private Recorder				rec;
	private SignalObject			sig;

	public Player(File inFile, Recorder recorder, SignalObject s) {
		sig		= s;
		playing	= false;
		rec		= recorder;
		pt		= new PlayerThread(inFile, this);
		t		= new Thread(pt);
		System.out.println("New player thread");
		t.start();
	}
	
	public synchronized void log(String message) {
		sig.log(message);
	}
	
	public synchronized void setPlaying(Boolean p) {
		playing = p;
	}
	
	public synchronized Boolean getPlaying(Boolean p) {
		return playing;
	}
	
	public synchronized void stop() {
		pt.stop();
	}
	
	public synchronized Boolean isDone() {
		return pt.isDone();
	}
	
	public synchronized void stopRecording() {
		if(rec != null)
			rec.stopRecording();
	}
}
