/*

Blake Troutman
Low Level Game
CSCI 437
12-1-2017

All of these components were built straight from the ground up,
based off of Andy's game engine.

All of the user's code goes in Game.java (that's where init and update are)

*/


import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.animation.*;

public class Display extends Application{
	
	public Game myGame = new Game();
	public Keyboard k = myGame.k;
	public Canvas canvas = null;
	
	public void start(Stage stage) {
		
		//initialize application
		stage.setTitle(this.myGame.title);
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		//event handler for keyboard presses
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				k.press(event.getCode().getName());
			}
		});
		
		//event handler for keyboard releases
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				k.release(event.getCode().getName());
			}
		});
		

		
		//add the canvas from the Game class
		this.canvas = myGame.scene.canvas;
		root.getChildren().add(canvas);
		
		//call the init method of Game class
		myGame.init();
		
		//create time start point
		final long[] startNanoTime = {System.nanoTime()};
		final long[] lastNanoTime = startNanoTime;
		
		//this is the major update method
		new AnimationTimer(){
			public void handle(long currentNanoTime){
				//create time differential (converting to seconds)
				double t = (currentNanoTime - lastNanoTime[0]) / 1000000000.0; 
				
				//lock frametime (1 / framerate)
				if(t >= 0.017){
					//update last frame time
					lastNanoTime[0] = currentNanoTime;
					
					//make sure that the Display still displays whatever scene is at myGame.scene
					//canvas = myGame.scene.canvas;
					
					if(myGame.scene.getState()){
						//run the user-defined update function
						myGame.update(t);
					}
					
				}//end if for each game frame
	 
			}//end handle
		}.start();
	 
		stage.show();
	}//end start
	
	
	
	//main
	public static void main(String[] args){
		
		launch(args);
		
	}
	
}