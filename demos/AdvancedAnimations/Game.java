import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;


public class Game{
	
	/////////////////////////////////////////////////////
	//////////      MANDATORY PROPERTIES     ////////////
	/////////////////////////////////////////////////////
	
	public String title = "Advanced Animations";
	public SimpleScene scene = new SimpleScene(1280, 680);
	public Keyboard k = new Keyboard();
	/////////////////////////////////////////////////////
	
	SpriteSheet ss = new SpriteSheet("LPC.png", 21, 13);
	Sprite sprite = new Sprite(this.scene, ss, 75, 75);
	
	//implementation for init() function [this MUST be implemented].
	//this function runs once at the beginning of the program.
	public void init(){
	
		sprite.setPosition(100, 100);
		
		//intelligently read the LPC format
		sprite.getSpriteSheet().setPreset("LPC");
		
		sprite.getSpriteSheet().setGlobalSpeed(35);
		
		//start standing still facing forward
		sprite.getSpriteSheet().pause();
		sprite.getSpriteSheet().setState(2);
		sprite.getSpriteSheet().setFrame(0);
		this.scene.start();
		
	}//end init
	
	
	
	
	
	//implementation for update() function [this MUST be implemented].
	//this function run once every frame. t is the value of the current 
	//frametime and should be passed to every Sprite's update() function.
	public void update(double t){
		
		//begin by clearing the scene
		this.scene.clear();
		
		if(k.keysDown[k.W]){
			sprite.getSpriteSheet().setState("backward walk");
			sprite.getSpriteSheet().play();
			sprite.changeYby(-5);
		}else if(k.keysDown[k.A]){
			sprite.getSpriteSheet().setState("left walk");
			sprite.getSpriteSheet().play();
			sprite.changeXby(-5);
		}else if(k.keysDown[k.S]){
			sprite.getSpriteSheet().setState("forward walk");
			sprite.getSpriteSheet().play();
			sprite.changeYby(5);
		}else if(k.keysDown[k.D]){
			sprite.getSpriteSheet().setState("right walk");
			sprite.getSpriteSheet().play();
			sprite.changeXby(5);
		}else{
			sprite.getSpriteSheet().pause();
			sprite.getSpriteSheet().setFrame(0);
		}
		
		sprite.update(t);
		
	}//end update
	

	
	
	
	
}//end Game class definition