/*

Blake Troutman 2017
SimpleGame in Java (with JavaFX)
CSCI 437 Final Project

Sound.java - this is the Sound class. Instantiate one of these when you want noises.

*/

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;


public class Sound {

	protected String filename = "";
	protected Media sound = null;
	protected MediaPlayer player = null;
	protected boolean running = false;
	
	
	
	//default constructor
	public Sound(){
		this.filename = "default.wav";
		this.sound = new Media(new File(this.filename).toURI().toString());
		this.player = new MediaPlayer(this.sound);
		this.setEventHandlers();
	}//end default constructor
	
	
	
	//overloaded constructor
	public Sound(String file){
		this.filename = file;
		this.sound = new Media(new File(this.filename).toURI().toString());
		this.player = new MediaPlayer(this.sound);
		this.setEventHandlers();
	}//end overloaded constructor
	
	
	protected void setEventHandlers(){
		
		//set event handlers for mediaplayer
		this.player.setOnEndOfMedia(new Runnable(){
			@Override
			public void run(){
				running = false;
				player.stop();
				player.seek(new Duration(0));
			}
		});
		
		this.player.setOnPlaying(new Runnable(){
			@Override
			public void run(){
				running = true;
			}
		});
		
		
	}//end setEventHandlers
	
	public void play(){
		
		if(!this.running){

			this.player.play();
				
		}
		
	}//end play
	
	
	public void stop(){
		this.running = false;
		this.player.stop();
	}
	
	
	public void pause(){
		this.running = false;
		this.player.pause();
	}
	
}//end class definition
