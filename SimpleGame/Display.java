/*

Blake Troutman 2017
SimpleGame in Java (with JavaFX)
CSCI 437 Final Project

Display.java - the application class to jump start the game
(this is the main class)

*/
import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
		
		//event handlers for mouse functionality (in prototype stage)
		scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				k.mouseX = event.getX();
				k.mouseY = event.getY();
			}
		});
		
		//event handlers for mouse functionality (in prototype stage)
		scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				k.mouseX = event.getX();
				k.mouseY = event.getY();
			}
		});
		
		//event handlers for mouse functionality (in prototype stage)
		scene.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
				k.clicked = true;
				k.onPress();
			}
		});
		
		//event handlers for mouse functionality (in prototype stage)
		scene.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
				k.clicked = false;
				k.onRelease();
			}
		});

		
		//add the canvas from the Game class
		this.canvas = myGame.scene.canvas;
		root.getChildren().add(canvas);
		
		//call the init method of Game class
		myGame.init();
		
		
		
		//create time start point and framerate
		final long[] startNanoTime = {System.nanoTime()};
		final long[] lastNanoTime = startNanoTime;
		int framerate = 45;
		final double frametime = 1.0 / ( 2 * framerate );
		//(upon investigation, the framerate averages to be only half of what you input for "framerate")
		//(this is likely a result of JavaFX running the AnimationTimer as fast as possible,)
		//(making if difficult to accurately control framerate)
		//(so, the framerate is multiplied by 2 here in order to achieve results closer to the desired framerate)
		
		
		
		//this is the major update method
		new AnimationTimer(){
			public void handle(long currentNanoTime){
				
				//create time differential (converting to seconds)
				double t = (currentNanoTime - lastNanoTime[0]) / 1000000000.0; 
				
				//lock framerate
				if(t >= frametime){
					//update last frame time
					lastNanoTime[0] = currentNanoTime;
					
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
	
}//end class definition