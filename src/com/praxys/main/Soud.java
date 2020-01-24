package com.praxys.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Soud {

	private AudioClip clip;
	
	public static Soud musicBack = new Soud("/music.wav");
	public static Soud musicHurt = new Soud("/hurt.wav");
	
	private Soud(String name) {
		try {
			clip = Applet.newAudioClip(Soud.class.getResource(name));
		}catch(Throwable w) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void stop() {
		clip.stop();
	}
	
}
