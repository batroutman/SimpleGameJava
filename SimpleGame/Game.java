/*

Blake Troutman 2017
SimpleGame in Java (with JavaFX)
CSCI 437 Final Project

Game.java - the entry point for users of this library.
Mandatory properties are mandatory to have and then you just implement
init() and update().

NOTE: 	the t parameter in update is the frametime. Just pass this to 
		the update() method of each sprite and they will use it properly

*/



public class Game{
	
	/////////////////////////////////////////////////////
	//////////      MANDATORY PROPERTIES     ////////////
	/////////////////////////////////////////////////////
	
	public String title = "This is my Game!";
	public SimpleScene scene = new SimpleScene(1280, 680);
	public Keyboard k = new Keyboard();
	/////////////////////////////////////////////////////
	

	
	//declare your variables here, if you want

	
	
	
	
	//implementation for init() function [this MUST be implemented].
	//this function runs once at the beginning of the program.
	public void init(){
	
		//your code goes here
		
		
		//end this method with this.scene.start()
		this.scene.start();
		
	}//end init
	
	
	
	

	
	
	//implementation for update() function [this MUST be implemented].
	//this function run once every frame. t is the value of the current 
	//frametime and should be passed to every Sprite's update() function.
	public void update(double t){
		
		//begin by clearing the scene
		this.scene.clear();
		
		//your code goes here 
		//(don't forget to call the update(t) method for each Sprite)
		
		
	}//end update
	

	
	
	
	
}//end Game class definition