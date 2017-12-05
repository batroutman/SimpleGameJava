import javafx.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.lang.Math;

public class Sprite{
	
	//Initialize properties
	protected String imgName = "";
	protected Image img = null;
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
	
	protected double imgAngle = 0;//this is stored in degrees
	protected double moveAngle = 0;//this is stored in radians
	
	public String boundAction = "wrap";
	
	//display
	protected boolean visible = true;
	protected boolean animation = false;
	
	//other
	protected String collisionType = "bounding box";
	
	
	//default constructor
	public Sprite(){
		this.scene = new SimpleScene();
	}
	
	//overloaded constructor
	public Sprite(SimpleScene scene, String imageName, int imgWidth, int imgHeight){
		
		this.imgName = imageName;
		this.img = new Image(this.imgName);
		this.width = imgWidth;
		this.height = imgHeight;
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
	
	public double getImgAngle(){
		return this.imgAngle;
	}
	
	public double getSpeedScale(){
		return this.speedScale;
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
		this.imgName = imgFile;
		this.img = new Image(imgFile);
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
	
	public void setDX(long ndx){
		this.dx = ndx;
	}
	
	public void setDY(long ndy){
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
		this.speed = speed;
		this.calcVector();
	}
	
	public void changeSpeedBy(double diff){
		this.speed += diff;
		this.calcVector();
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
	/////////////////////////////////////////////
	
	
	
	
	
	
	/////////////////////////////////////////////
	//////////////   OTHER METHODS   ////////////
	/////////////////////////////////////////////
		
	public void update(double t){
		
		this.x += Math.round(this.dx * this.speedScale * (10 * t));
		this.y += Math.round(this.dy * this.speedScale * (10 * t));
		this.checkBounds();
		if(this.visible){
			this.draw();
		}
		
	}//end update
	
	
	
	
	protected void draw(){
		
		GraphicsContext ctx = this.scene.canvas.getGraphicsContext2D();
		
		ctx.save();
		
		//transform element
		ctx.translate(this.x, this.y);
		ctx.rotate(this.imgAngle);
		
		//draw image with center on origin
		if(!this.animation){
			ctx.drawImage(this.img, (0 - this.width / 2), (0 - this.height / 2), this.width, this.height);
		}else{
			//add animation functionality
		}
		
		ctx.restore();
		
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
			
			
		}//end collision types
		
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
	
	
	
	
	
}