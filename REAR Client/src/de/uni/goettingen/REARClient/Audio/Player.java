package de.uni.goettingen.REARClient.Audio;

import java.io.File;

import de.uni.goettingen.REARClient.SignalObject;

public class Player {
	private Thread					t;
	private PlayerThread			pt;
	private Boolean					playing;
	private Recorder				rec;
<<<<<<< HEAD
	private SignalObject			signal;
=======
	private SignalObject			sig;
>>>>>>> branch 'ProductionVersion' of ssh://git@github.com/FKlama/REAR.git

<<<<<<< HEAD
	public Player(SignalObject sig, File inFile, Recorder recorder) {
		signal  = sig;
=======
	public Player(File inFile, Recorder recorder, SignalObject s) {
		sig		= s;
>>>>>>> branch 'ProductionVersion' of ssh://git@github.com/FKlama/REAR.git
		playing	= false;
		rec		= recorder;
		pt		= new PlayerThread(signal, inFile, this);
		t		= new Thread(pt);
		signal.log("New player thread");
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
