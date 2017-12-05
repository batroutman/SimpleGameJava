/*

Blake Troutman
Low Level Game
CSCI 437
12-1-2017

Game.java

This is where the user of all of these components puts their code to make the videogame.
"Mandatory Properties" are properties that the other components depend on,
everything else (besides init and update) is optional

*/

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;


public class Game{
	
	/////////////////////////////////////////////////////
	//////////      MANDATORY PROPERTIES     ////////////
	/////////////////////////////////////////////////////
	
	public String title = "This is my game!";
	public SimpleScene scene = new SimpleScene(1280, 720);
	public Keyboard k = new Keyboard();
	/////////////////////////////////////////////////////
	
	
	
	
	
	
	
	//custom variables (sprites, scenes, etc.)
	public ArrayList<Chest> chests = new ArrayList<Chest>();
	public ArrayList<Body> asteroids = new ArrayList<Body>();
	public Ship mainSprite = null;
	public Sprite background = null;
	public Sprite earth = null;
	public int MAX_SPEED = 20;
	public ArrayList<Double[]> gravity = new ArrayList<Double[]>();
	public int numChests = 5;
	public int collected = 0;
	public Sprite compass = null;
	public ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	
	
	
	//implementation for init() function [this MUST be implemented].
	//this function runs once at the beginning of the program.
	public void init(){
	
		//background init
		this.background = new Sprite(this.scene, "space-backdrop.png", 1280 * 5, 800 * 5);
		this.background.setBoundAction("background");
		this.background.setSpeedScale(0.2);
		this.background.setSpeed(0);
		this.sprites.add(this.background);
		
		//compass init
		this.compass = new Sprite(this.scene, "compass.png", 100, 100);
		this.compass.setSpeed(0);
		this.compass.setPosition(this.compass.getWidth() / 2, this.scene.getHeight() - this.compass.getHeight() / 2);
		
		//earth init
		this.earth = new Planet(this.scene, "earth.png", 4800, 4800);
		this.earth.setSpeed(0);
		this.earth.setPosition(0, 7000);
		this.earth.setBoundAction("continue");
		this.sprites.add(this.earth);
		
		//ship init
		this.mainSprite = new Ship(this.scene, "cannon.png", 50, 50, this.k);
		this.mainSprite.keyListen = true;
		this.mainSprite.setSpeed(0);
		this.mainSprite.setPosition(this.scene.getWidth() / 2, this.scene.getHeight() / 2);
		
		//asteroids init
		this.asteroids.add(new Body(this.scene, "asteroid.png", 250, 250, this));
		this.asteroids.get(0).setBoundAction("continue");
		this.asteroids.get(0).setSpeed(0);
		this.asteroids.get(0).setPosition(0, 0);
		this.sprites.add(this.asteroids.get(0));
		
		for(int i = 1; i < 200; i++){
			
			int size = (int) Math.round(Math.random() * 400 + 50);
			long x = Math.round(Math.random() * this.background.getWidth() - (this.background.getWidth() / 2));
			long y = Math.round(Math.random() * this.background.getHeight() - (this.background.getHeight() / 2));
			
			while(x > 0 && x < this.scene.getWidth()){
				x = Math.round(Math.random() * this.background.getWidth() - (this.background.getWidth() / 2));
			}
			
			while(y > 0 && y < this.scene.getHeight()){
				y = Math.round(Math.random() * this.background.getHeight() - (this.background.getHeight() / 2));
			}
			
			this.asteroids.add(new Body(this.scene, "asteroid.png", size, size, this));
			this.asteroids.get(i).setBoundAction("continue");
			this.asteroids.get(i).setSpeed(0);
			this.asteroids.get(i).setPosition(x, y);
			
			this.sprites.add(this.asteroids.get(i));
		}
		
		
		//chest init
		this.chests.add(new Chest(this.scene, "chest.png", 100, 50));
		this.chests.get(0).setBoundAction("continue");
		this.chests.get(0).setSpeed(0);
		this.chests.get(0).setPosition(500, 500);
		this.sprites.add(this.chests.get(0));
		
		for(int i = 1; i < this.numChests; i++){
			
			this.chests.add(new Chest(this.scene, "chest.png", 100, 50));
			this.chests.get(i).setBoundAction("continue");
			this.chests.get(i).setSpeed(0);
			
			boolean keepGoing = true;
			do{
				
				long x = Math.round(Math.random() * this.background.getWidth() - (this.background.getWidth() / 2));
				long y = Math.round(Math.random() * this.background.getHeight() - (this.background.getHeight() / 2));
				
				while(x > 0 && x < this.scene.getWidth()){
					x = Math.round(Math.random() * this.background.getWidth() - (this.background.getWidth() / 2));
				}
				
				while(y > 0 && y < this.scene.getHeight()){
					y = Math.round(Math.random() * this.background.getHeight() - (this.background.getHeight() / 2));
				}
				
				this.chests.get(i).setPosition(x, y);
				
				//check for collisions with asteroids
				boolean colliding = false;
				for(int j = 0; j < this.asteroids.size() && colliding == false; j++){
					if(this.asteroids.get(j).collidesWith(this.chests.get(i))){
						colliding = true;
					}
				}
				
				keepGoing = colliding;
				
			}while(keepGoing);
			
			this.sprites.add(this.chests.get(i));
			
		}//end for
	
		this.scene.start();
		
		System.out.println("====  Welcome to Space Smuggler!  ====");
		System.out.println("(WASD to move)");
		System.out.println("Follow your compass in the bottom left corner to find treasure.");
		System.out.println("Collect all of the treasure, then follow your compass to Earth!");
		System.out.println("Watch out for the asteroids though, their gravitational pull is strong!");
		System.out.println("Crashing into one will certainly kill you! D:");
		System.out.println("Good Luck!");
		
	}//end init
	
	
	
	
	
	//implementation for update() function [this MUST be implemented].
	//this function run once every frame. t is the value of the current 
	//frametime and should be passed to every Sprite's update() function.
	public void update(double t){
		
		this.scene.clear();
		
		//update each sprite
		for(int i = 0; i < this.sprites.size(); i++){
			
			//manage keys for background
			if(k.keysDown[k.A]){
				this.sprites.get(i).addVector(90, 0.4);
			}
			if(k.keysDown[k.D]){
				this.sprites.get(i).addVector(270, 0.4);
			}
			if(k.keysDown[k.W]){
				this.sprites.get(i).addVector(180, 0.4);
			}
			if(k.keysDown[k.S]){
				this.sprites.get(i).addVector(0, 0.4);
			}
			
			//apply gravity
			for(int j = 0; j < this.gravity.size(); j++){
				this.sprites.get(i).addVector(this.gravity.get(j)[0], this.gravity.get(j)[1]);
			}
			
			this.sprites.get(i).update(t);
		}//end for
		
		//add gravity vectors and check collision
		for(int i = 0; i < this.asteroids.size(); i++){
			this.asteroids.get(i).gravitate();
			
			if(this.asteroids.get(i).collidesWith(this.mainSprite)){
				this.mainSprite.setImage("explosion.gif");
				this.scene.stop();
				System.out.println("YOU LOSE!");
			}
		}
		
		//clean gravity
		while(this.gravity.size() > 250){
			this.gravity.remove(0);
		}
		
		//rotate chests and check chest collision
		for(int i = 0; i < this.chests.size(); i++){
			//rotate chests
			this.chests.get(i).changeImgAngleBy(1);
			
			//check collisions
			if(this.chests.get(i).collidesWith(this.mainSprite)){
				this.chests.get(i).hide();
				this.chests.get(i).collected = true;
				this.collected++;
				System.out.println("You collected a chest!");
				System.out.println(this.collected + " / " + this.chests.size());
				
				if(this.collected >= this.chests.size()){
					System.out.println("All chests collected. Return to Earth.");
				}
			}
		}
		
		//update main sprite
		this.mainSprite.checkKeys();
		this.mainSprite.update(t);
		
		//update compass
		if(this.collected < this.chests.size()){
			//point to chest
			int chest = 0;
			for(int i = 1; i < this.chests.size() && this.chests.get(chest).collected == true; i++){
				chest = i;
			}
			
			this.compass.setAngle(this.mainSprite.angleTo(this.chests.get(chest)));
		}else{
			//point to earth
			this.compass.setAngle(this.mainSprite.angleTo(this.earth) - 90);
		}
		this.compass.update(t);
		
		//check Earth message
		if(this.earth.collidesWith(this.mainSprite)){
			
			if(this.collected >= this.chests.size()){
				this.scene.stop();
				System.out.println("YOU WIN!");
			}else{
				System.out.println("You must collect all treasure before returning to Earth.");
			}
			
		}
		
	}//end update
	

	
	
	
	
}//end Game class definition