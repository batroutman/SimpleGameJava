import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;


public class Game{
	
	/////////////////////////////////////////////////////
	//////////      MANDATORY PROPERTIES     ////////////
	/////////////////////////////////////////////////////
	
	public String title = "Simple Animations";
	public SimpleScene scene = new SimpleScene(1280, 680);
	public Keyboard k = new Keyboard();
	/////////////////////////////////////////////////////
	
	SpriteSheet batSS = new SpriteSheet("bat.png", 4, 4);
	Sprite bat = new Sprite(this.scene, batSS, 75, 75);
	
	//implementation for init() function [this MUST be implemented].
	//this function runs once at the beginning of the program.
	public void init(){
		
		bat.setPosition(100,100);
		bat.getSpriteSheet().setGlobalSpeed(30);
		
		this.scene.start();
		
	}//end init
	
	
	
	
	
	//implementation for update() function [this MUST be implemented].
	//this function run once every frame. t is the value of the current 
	//frametime and should be passed to every Sprite's update() function.
	public void update(double t){
		
		//begin by clearing the scene
		this.scene.clear();
		
		if(k.keysDown[k.W]){
			bat.getSpriteSheet().setState(2);
			bat.changeYby(-2);
		}else if(k.keysDown[k.A]){
			bat.getSpriteSheet().setState(3);
			bat.changeXby(-2);
		}else if(k.keysDown[k.S]){
			bat.getSpriteSheet().setState(0);
			bat.changeYby(2);
		}else if(k.keysDown[k.D]){
			bat.getSpriteSheet().setState(1);
			bat.changeXby(2);
		}
		
		bat.update(t);
		
	}//end update
	

	
	
	
	
}//end Game class definition