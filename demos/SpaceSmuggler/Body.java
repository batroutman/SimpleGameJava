import java.util.ArrayList;


public class Body extends Sprite{

	public int mass = 300;
	public Game myGame = null;
	
	//constructor
	public Body(SimpleScene scene, String image, int width, int height, Game game){
		super(scene, image, width, height);
		this.myGame = game;
	}
	
	
	public void gravitate(){
		
		double distance = this.distanceTo(this.myGame.mainSprite);
		
		if(distance < 1000){
			int shipMass = this.myGame.mainSprite.mass;
			double dir = this.angleTo(this.myGame.mainSprite);
			double force = (this.mass * shipMass) / (distance * distance);
			Double[] vect = new Double[2];
			vect[0] = dir;
			vect[1] = force;
			this.myGame.gravity.add(vect);
		}
		
	}
	
	@Override
	public boolean collidesWith(Sprite sprite){
		boolean result = false;
		
		if(this.distanceTo(sprite) < this.height / 2){
			result = true;
		}
		
		return result;
		
	}
	
}//end Body definition
