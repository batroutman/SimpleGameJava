import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;


public class Game{
	
	/////////////////////////////////////////////////////
	//////////      MANDATORY PROPERTIES     ////////////
	/////////////////////////////////////////////////////
	
	public String title = "Hitboxes";
	public SimpleScene scene = new SimpleScene(1280, 680);
	public Keyboard k = new Keyboard();
	/////////////////////////////////////////////////////
	

	
	Sprite mySprite = new Sprite(this.scene, "Mario_Sprite.jpg", 100, 150);
	Sprite mySprite2 = new Sprite(this.scene, "Mario_Sprite.jpg", 100, 150);

	
	Sound mySound = new Sound("default.wav");
	
	//implementation for init() function [this MUST be implemented].
	//this function runs once at the beginning of the program.
	public void init(){
	
		mySprite.setPosition(200, 200);
		mySprite.setCollisionType("hitbox");
		mySprite.displayHitboxes();
		
		mySprite.addHitbox(new Double[]{-30.0, 22.0, -70.0, -15.0});//face
		mySprite.addHitbox(new Double[]{-25.0, 18.0, -10.0, 45.0});//torso
		mySprite.addHitbox(new Double[]{-50.0, -30.0, 0.0, 45.0});//left arm
		mySprite.addHitbox(new Double[]{23.0, 45.0, -45.0, -20.0});//nose
		mySprite.addHitbox(new Double[]{-41.0, -30.0, -45.0, -20.0});//nose
		mySprite.addHitbox(new Double[]{20.0, 45.0, 0.0, 45.0});//right arm
		mySprite.addHitbox(new Double[]{-45.0, -15.0, 60.0, 75.0});//left foot
		mySprite.addHitbox(new Double[]{15.0, 50.0, 60.0, 75.0});//left foot
		
		mySprite.setHitboxDisplayWeight(4);

		
		
		mySprite2.setPosition(500, 400);
		mySprite2.setCollisionType("hitbox");
		mySprite2.displayHitboxes();
		mySprite2.setMaxSpeed(5);
		
		mySprite2.addHitbox(new Double[]{-30.0, 22.0, -70.0, -15.0});//face
		mySprite2.addHitbox(new Double[]{-25.0, 18.0, -10.0, 45.0});//torso
		mySprite2.addHitbox(new Double[]{-50.0, -30.0, 0.0, 45.0});//left arm
		mySprite2.addHitbox(new Double[]{23.0, 45.0, -45.0, -20.0});//nose
		mySprite2.addHitbox(new Double[]{-41.0, -30.0, -45.0, -20.0});//nose
		mySprite2.addHitbox(new Double[]{20.0, 45.0, 0.0, 45.0});//right arm
		mySprite2.addHitbox(new Double[]{-45.0, -15.0, 60.0, 75.0});//left foot
		mySprite2.addHitbox(new Double[]{15.0, 50.0, 60.0, 75.0});//left foot


		this.scene.start();
		
	}//end init
	
	
	
	
	
	//implementation for update() function [this MUST be implemented].
	//this function run once every frame. t is the value of the current 
	//frametime and should be passed to every Sprite's update() function.
	public void update(double t){
		
		//begin by clearing the scene
		this.scene.clear();
		
		//mario1 with hitboxes
		if(k.keysDown[k.W]){
			this.mySprite.changeYby(-2);
		}
		if(k.keysDown[k.A]){
			this.mySprite.changeXby(-2);
		}
		if(k.keysDown[k.S]){
			this.mySprite.changeYby(2);
		}
		if(k.keysDown[k.D]){
			this.mySprite.changeXby(2);
		}
		
		//mario2
		if(k.keysDown[k.UP]){
			this.mySprite2.addVector(0, 1);
		}
		if(k.keysDown[k.LEFT]){
			this.mySprite2.addVector(270, 1);
		}
		if(k.keysDown[k.DOWN]){
			this.mySprite2.addVector(180, 1);
		}
		if(k.keysDown[k.RIGHT]){
			this.mySprite2.addVector(90, 1);
		}

		
		if(mySprite.collidesWith(mySprite2)){
			//mySprite2.hide();
			mySound.play();
		}
		
		
		//drag
		mySprite2.setSpeed(mySprite2.getSpeed() * 0.99);
		
		mySprite.update(t);
		mySprite2.update(t);

		
	}//end update
	

	
	
	
	
}//end Game class definition