/*

Blake Troutman 2017
SimpleGame in Java (with JavaFX)
CSCI 437 Final Project

Sprite.java - the Sprite class from SimpleGame.

Added features:
	hitboxes,
	new animation mechanism,
	dynamic collision detection (hitbox to hitbox, hitbox to bounding box...)
	maximum speed property (yes, it is enforced)
	additional bound action, BACKGROUND (If you have a big picture that is a background and you dont want it to move off screen)

*/

import javafx.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.lang.Math;
import java.util.ArrayList;

public class Sprite{
	
	//Initialize properties
	protected SpriteSheet spriteSheet = null;
	protected int width = 0;
	protected int height = 0;
	protected SimpleScene scene = null;
	
	//motion and position
	protected long x = 0;
	protected long y = 0;
	protected double dx = 0;
	protected double dy = 0;
	
	protected double speed = 0;
	protected double speedScale = 1;
	protected double maxSpeed = 999999;
	
	protected double imgAngle = 0;//this is stored in degrees
	protected double moveAngle = 0;//this is stored in radians
	
	public String boundAction = "wrap";
	
	//display
	protected boolean visible = true;
	protected boolean animation = false;
	
	//other
	protected String collisionType = "bounding box";
	
	//format of each Double[]: {left-x, right-x, top-y, bottom-y}
	protected ArrayList<Double[]> hitboxes = new ArrayList<Double[]>();
	protected boolean displayHitboxes = false;
	protected double hitboxDisplayWeight = 2.5;
	
	
	//default constructor
	public Sprite(){
		this.scene = new SimpleScene();
		this.spriteSheet = new SpriteSheet();
	}
	
	//overloaded constructor
	public Sprite(SimpleScene scene, String imageName, int imgWidth, int imgHeight){
		
		this.spriteSheet = new SpriteSheet(imageName);
		this.spriteSheet.pause();
		this.width = imgWidth;
		this.height = imgHeight;
		this.scene = scene;
		
	}
	
	//intelligent overloaded constructor
	public Sprite(SimpleScene scene, String imageName){
		
		this.spriteSheet = new SpriteSheet(imageName);
		this.spriteSheet.pause();
		this.width = this.spriteSheet.getWidth();
		this.height = this.spriteSheet.getHeight();
		this.scene = scene;
		
	}
	
	//default SpriteSheet constructor
	public Sprite(SimpleScene scene, SpriteSheet ss){
		this.spriteSheet = ss;
		this.width = this.spriteSheet.getWidth();
		this.height = this.spriteSheet.getHeight();
		this.scene = scene;
	}
	
	//overloaded SpriteSheet constructor (pass in a sprite sheet, but make the sprite bigger or smaller (scale it))
	public Sprite(SimpleScene scene, SpriteSheet ss, int w, int h){
		this.spriteSheet = ss;
		this.width = w;
		this.height = h;
		this.scene = scene;
	}
	
	
	
	
	
	/////////////////////////////////////////////
	//////////////    GETTERS   /////////////////
	/////////////////////////////////////////////
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public long getX(){
		return this.x;
	}
	
	public long getY(){
		return this.y;
	}
	
	public double getDX(){
		return this.dx;
	}
	
	public double getDY(){
		return this.dy;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public double getSpeed(){
		return (Math.sqrt((this.dx * this.dx) + (this.dy * this.dy)));
	}
	
	public double getMaxSpeed(){
		return this.maxSpeed;
	}
	
	public double getImgAngle(){
		return this.imgAngle;
	}
	
	public double getSpeedScale(){
		return this.speedScale;
	}
	
	public String getCollisionType(){
		return this.collisionType;
	}
	
	public boolean getHitboxDisplay(){
		return this.displayHitboxes;
	}
	
	public int getNumHitboxes(){
		return this.hitboxes.size();
	}
	
	public double getHitboxDisplayWeight(){
		return this.hitboxDisplayWeight;
	}
	
	public SpriteSheet getSpriteSheet(){
		return this.spriteSheet;
	}
	
	/////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////
	//////////////    SETTERS   /////////////////
	/////////////////////////////////////////////
	
	public void setWidth(int w){
		this.width = w;
	}
	
	public void setHeight(int h){
		this.height = h;
	}
	
	public void changeImage(String imgFile){
		this.spriteSheet.setImage(new Image(imgFile));;
	}
	
	public void setImage(String imgFile){
		this.changeImage(imgFile);
	}
	
	public void setX(long xpos){
		this.x = xpos;
	}
	
	public void setY(long ypos){
		this.y = ypos;
	}
	
	public void setPosition(long xpos, long ypos){
		this.x = xpos;
		this.y = ypos;
	}
	
	public void setDX(double ndx){
		this.dx = ndx;
	}
	
	public void setDY(double ndy){
		this.dy = ndy;
	}
	
	public void setChangeX(double ndx){
		this.dx = ndx;
	}
	
	public void setChangeY(double ndy){
		this.dy = ndy;
	}
	
	public void changeXby(double tdx){
		this.x += tdx;
	}
	
	public void changeYby(double tdy){
		this.y += tdy;
	}
	
	public void setSpeed(double speed){
		if(speed <= this.maxSpeed){
			this.speed = speed;
			this.calcVector();
		}
	}
	
	public void setMaxSpeed(double speed){
		this.maxSpeed = speed;
	}
	
	public void changeSpeedBy(double diff){
		if(this.speed + diff <= this.maxSpeed){
			this.speed += diff;
			this.calcVector();
		}
	}
	
	public void setImgAngle(double degrees){
		this.imgAngle = degrees;
	}
	
	public void changeImgAngleBy(double degrees){
		this.imgAngle += degrees;
	}
	
	public void setMoveAngle(double degrees){
		degrees = degrees - 90;
		//convert to radians
		this.moveAngle = degrees * Math.PI / 180;
		this.calcVector();
	}
	
	public void changeMoveAngleBy(double degrees){
		double diffRad = degrees * Math.PI / 180;
		//add radian diff to move angle
		this.moveAngle += diffRad;
		this.calcVector();
	}
	
	public void setAngle(double degrees){
		this.setMoveAngle(degrees);
	    this.setImgAngle(degrees);
	}
	
	public void changeAngleBy(double degrees){
		this.changeMoveAngleBy(degrees);
	    this.changeImgAngleBy(degrees);
	}
	
	public void turnBy(double degrees){
		this.changeAngleBy(degrees);
	}
	
	public void hide(){
		this.visible = false;
	}
	
	public void show(){
		this.visible = true;
	}
	
	public void setBoundAction(String action){
		this.boundAction = action;
	}
	
	public void setSpeedScale(double ss){
		this.speedScale = ss;
	}
	
	public void setCollisionType(String type){
		this.collisionType = type;
	}
	
	public void displayHitboxes(){
		this.displayHitboxes = true;
	}
	
	public void hideHitboxes(){
		this.displayHitboxes = false;
	}
	
	public void addHitbox(Double[] hitbox){
		this.hitboxes.add(hitbox);
	}
	
	public void removeHitbox(int i){
		this.hitboxes.remove(i);
	}
	
	public void removeHitbox(Double[] hitbox){
		this.hitboxes.remove(hitbox);
	}
	
	public void setHitboxDisplayWeight(double weight){
		this.hitboxDisplayWeight = weight;
	}
	
	public void setSpriteSheet(SpriteSheet ss){
		this.spriteSheet = ss;
	}
	
	/////////////////////////////////////////////
	
	
	
	
	
	
	/////////////////////////////////////////////
	//////////////   OTHER METHODS   ////////////
	/////////////////////////////////////////////
		
	public void update(double t){
		
		this.updateState(t);
		if(this.visible){
			this.draw();
		}
		
	}//end update
	
	
	
	
	public void updateState(double t){
		
		this.spriteSheet.step(t);
		this.x += Math.round(this.dx * this.speedScale * (100 * t));
		this.y += Math.round(this.dy * this.speedScale * (100 * t));
		this.checkBounds();
		
	}//end updateState
	
	
	
	
	public void draw(){
		
		GraphicsContext ctx = this.scene.canvas.getGraphicsContext2D();
		
		ctx.save();
		
		//transform element
		ctx.translate(this.x, this.y);
		ctx.rotate(this.imgAngle);
		
		//draw image with center on origin
		ctx.drawImage(
						this.spriteSheet.getImage(),
						this.spriteSheet.getCurrentFrame() * this.spriteSheet.getWidth(), //source x
						this.spriteSheet.getCurrentState() * this.spriteSheet.getHeight(), //source y
						this.spriteSheet.getWidth(), //source width
						this.spriteSheet.getHeight(), //source height
						(0 - this.width / 2), //dest x
						(0 - this.height / 2), //dest y
						this.width, //dest width
						this.height //dest height
					);
		
		ctx.restore();
		
		//draw hitboxes (if that option is set) ------------------------------------------------------------
		if(this.displayHitboxes){
			ctx.save();
			ctx.translate(this.x, this.y);
			
			//establish different colors to draw in
			Color[] p = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOWGREEN, Color.PURPLE, Color.ORANGE, Color.ORANGERED};
			
			ctx.setLineWidth(this.hitboxDisplayWeight);
			
			
			//if I don't have a hitbox, give me one for a second
			boolean noHitboxes = false;
			if(this.hitboxes.size() <= 0){
				noHitboxes = true;
				
				//make a hitbox for me
				Double[] hb ={	(double) (0 - this.width / 2), 
								(double) (0 + this.width / 2), 
								(double) (0 - this.height / 2), 
								(double) (0 + this.height / 2)
							};
				this.hitboxes.add(hb);
			}
			
			
			//for every hitbox the sprite has, draw it
			for(int i = 0; i < this.hitboxes.size(); i++){
				ctx.setStroke(p[i % p.length]);
				ctx.strokePolygon(
									new double[]{
													this.hitboxes.get(i)[0], //left
													this.hitboxes.get(i)[0], //left
													this.hitboxes.get(i)[1], //right
													this.hitboxes.get(i)[1]  //right
												},
									new double[]{
													this.hitboxes.get(i)[2], //top
													this.hitboxes.get(i)[3], //bottom
													this.hitboxes.get(i)[3], //bottom
													this.hitboxes.get(i)[2] //top
												}, 
									4 //points that make up polygon
								);//end strokePolygon
			}//end for
			
			
			//if I didn't have a hitbox, remove the temporary one
			if(noHitboxes){
				this.hitboxes.remove(0);
			}
			
			ctx.restore();
		}//end if displayHitboxes -----------------------------------------------------------------------
		
		
	}//end draw
	
	
	
	
	protected void checkBounds(){
		
		//determine where the sprite is leaving the screen
		int right = this.scene.getWidth();
		int left = 0;
		int top = 0;
		int bottom = this.scene.getHeight();
		
		boolean offRight = false;
		boolean offLeft = false;
		boolean offTop = false;
		boolean offBottom = false;
		
		if(this.x > right){
			offRight = true;
		}
		if(this.x < left){
			offLeft = true;
		}
		if(this.y < top){
			offTop = true;
		}
		if(this.y > bottom){
			offBottom = true;
		}
		
		//handle based on boundAction
		if(this.boundAction.toUpperCase().equals("WRAP")){
			
			if(offLeft){
				this.x = right;
			}
			if(offRight){
				this.x = left;
			}
			if(offTop){
				this.y = bottom;
			}
			if(offBottom){
				this.y = top;
			}
			
		}else if(this.boundAction.toUpperCase().equals("BOUNCE")){
			
			if(offTop || offBottom){
				this.dy *= -1;
				this.calcSpeedAngle();
				this.setImgAngle(this.radToDeg(this.moveAngle));
			}
			if(offLeft || offRight){
				this.dx *= -1;
				this.calcSpeedAngle();
				this.setImgAngle(this.radToDeg(this.moveAngle));
			}
			
		}else if(this.boundAction.toUpperCase().equals("STOP")){
			
			if(offLeft || offRight || offTop || offBottom){
				this.setSpeed(0);
			}
			
		}else if(this.boundAction.toUpperCase().equals("DIE")){
			
			if (offLeft || offRight || offTop || offBottom){
		        this.hide();
		        this.setSpeed(0);
			}
			
		}else if(this.boundAction.toUpperCase().equals("BACKGROUND")){
			
			//evaluate X
			long maxXDifferential = (this.width / 2) - this.scene.getWidth();
			
			//going off right side
			if(this.x > (this.scene.getWidth() + maxXDifferential)){
				this.x = this.scene.getWidth() + maxXDifferential;
			}
			//going off left
			if(this.x < -1 * maxXDifferential){
				this.x = -1 * maxXDifferential;
			}
			
			//evaluate y
			long maxYDifferential = (this.height / 2) - this.scene.getHeight();
			
			//going off bottom side
			if(this.y > (this.scene.getHeight() + maxYDifferential)){
				this.y = this.scene.getHeight() + maxYDifferential;
			}
			//going off top
			if(this.y < -1 * maxYDifferential){
				this.y = -1 * maxYDifferential;
			}
			
		}//end handling branch
		
	}//end checkBounds
	
	
	
	
	protected void calcVector(){
		
		this.dx = this.speed * Math.cos(this.moveAngle);
	    this.dy = this.speed * Math.sin(this.moveAngle);
		
	}//end calcVector
	
	
	
	
	protected void calcSpeedAngle(){
		
		this.speed = Math.sqrt((double)(this.dx * this.dx) + (this.dy * this.dy));
		this.moveAngle = Math.atan2(this.dy, this.dx);
		
	}//end calcSpeedAngle
	
	
	
	
	public void addVector(double degrees, double thrust){
		
		//modify current motion vector by adding a new vector to it.
		
		//convert to radians
		degrees -= 90;
		double angle = degrees * Math.PI / 180;
		
		//calculate dx and dy
		double newDX = thrust * Math.cos(angle);
		double newDY = thrust * Math.sin(angle);
		this.dx += newDX;
		this.dy += newDY;
		
		//adjust for the maximum speed
		this.calcSpeedAngle();
		if(this.speed > this.maxSpeed){
			this.speed = this.maxSpeed;
			this.calcVector();
		}
		
	}//end addVector
	
	
	
	
	public boolean collidesWith(Sprite sprite){
		boolean collision = false;
		
		if(this.collisionType.toUpperCase().equals("BOUNDING BOX")){
			
			
			//Bounding box collisions
			if(this.visible){
				if(sprite.visible){
					
					long myLeft = this.x - this.width / 2;
					long myRight = this.x + this.width / 2;
					long myTop = this.y - this.height / 2;
					long myBottom = this.y + this.height / 2;
					long otherLeft = sprite.x - sprite.width / 2;
					long otherRight = sprite.x + sprite.width / 2;
					long otherTop = sprite.y - sprite.height / 2;
					long otherBottom = sprite.y + sprite.height / 2;
					
					//assume collision
					collision = true;
					
					//determine non-colliding states
					if((myBottom < otherTop) ||
					   (myTop > otherBottom) ||
					   (myRight < otherLeft) ||
					   (myLeft > otherRight)){
						collision = false;
					}//end collision determination
					
				}//end sprite visible
			}//end this visible
			
			
		}else if(this.collisionType.toUpperCase().equals("HITBOX")){
			
			
			if(this.visible){
				if(sprite.visible){
					
					//make enemy's hitbox list
					ArrayList<Double[]> otherHitboxes = new ArrayList<Double[]>();
					
					if(sprite.collisionType.toUpperCase().equals("HITBOX") && sprite.hitboxes.size() > 0){
						otherHitboxes = sprite.hitboxes;
					}else{
						Double[] hb ={	(double) (0 - sprite.width / 2), 
										(double) (0 + sprite.width / 2), 
										(double) (0 - sprite.height / 2), 
										(double) (0 + sprite.height / 2)
									};
						otherHitboxes.add(hb);
					}//end if
					
					
					//build my hitbox (if I don't have one)
					boolean noHitboxes = false;
					if(this.hitboxes.size() <= 0){
						noHitboxes = true;
						
						//make a hitbox for me
						Double[] hb ={	(double) (0 - this.width / 2), 
										(double) (0 + this.width / 2), 
										(double) (0 - this.height / 2), 
										(double) (0 + this.height / 2)
									};
						this.hitboxes.add(hb);
						
					}
					
					
					
					//assume collision
					collision = true;
					boolean keepGoing = true; //(break out of loop if a hitbox collides)
					
					//for each combination of hitboxes, perform bounding box tests
					for(int i = 0; i < this.hitboxes.size() && keepGoing; i++){
						for(int j = 0; j < otherHitboxes.size() && keepGoing; j++){
							

							double myLeft = this.hitboxes.get(i)[0] + this.x;
							double myRight = this.hitboxes.get(i)[1] + this.x;
							double myTop = this.hitboxes.get(i)[2] + this.y;
							double myBottom = this.hitboxes.get(i)[3] + this.y;
							double otherLeft = otherHitboxes.get(j)[0] + sprite.x;
							double otherRight = otherHitboxes.get(j)[1] + sprite.x;
							double otherTop = otherHitboxes.get(j)[2] + sprite.y;
							double otherBottom = otherHitboxes.get(j)[3] + sprite.y;
							
							//determine non-colliding states
							if((myBottom < otherTop) ||
							   (myTop > otherBottom) ||
							   (myRight < otherLeft) ||
							   (myLeft > otherRight)){
								collision = false;
							}else{
								//there is a collision
								collision = true;
								keepGoing = false;
							}//end collision determination

							
						}//end inner for
					}//end outer for
					
					
					//if I originally didn't have hitboxes, remove the temp one I made
					if(noHitboxes){
						this.hitboxes.remove(0);
					}
					
					
				}//end if sprite visible
			}//end this visible
			
			
		}//====    end collision types    ====//
		
		return collision;
	}//end collidesWith
	
	
	
	
	public double distanceTo(Sprite sprite){
		//get centers of sprites
		double myX = this.x;
		double myY = this.y;
		double otherX = sprite.x;
		double otherY = sprite.y;
		double diffX = myX - otherX;
		double diffY = myY - otherY;
		double dist = Math.sqrt((diffX * diffX) + (diffY * diffY));
		return dist;
	}//end distanceTo
	
	
	
	
	public double angleTo(Sprite sprite){
		//get centers of sprites
		double myX = this.x + (this.width / 2);
		double myY = this.y + (this.height / 2);
		double otherX = sprite.x + (sprite.width / 2);
		double otherY = sprite.y + (sprite.height / 2);
		
		//calculate difference
		double diffX = otherX - myX;
		double diffY = otherY - myY;
		double radians = Math.atan2(diffY, diffX);
		double degrees = radians * 180 / Math.PI;
		//degrees are offset
		degrees += 90;
		return degrees;
	}//end angleTo
	
	
	
	
	public void report(){
		//used only for debugging.
	    System.out.println("x: " + this.x + ", y: " + this.y
	    		+ ", dx: " + this.dx + ", dy: " + this.dy
	    		+ ", speed: "  + this.speed
	    		+ ", angle: " + this.moveAngle);
	}//end report
	
	
	
	
	//converts radians to degrees (with the 90 degree offset)
	public double radToDeg(double rad){
		return ((rad * 180.0 / Math.PI) + 90);
	}
	
	
	
	
	//converts degrees (with 90 degree offset) to radians
	public double degToRad(double deg){
		return ((deg - 90) * Math.PI / 180.0);
	}
	/////////////////////////////////////////////
	
	
	
	
	
}//end class definition