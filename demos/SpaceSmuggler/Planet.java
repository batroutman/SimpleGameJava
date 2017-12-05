
public class Planet extends Sprite{

	//constructor
	public Planet(SimpleScene scene, String imageName, int imgWidth, int imgHeight){
		super(scene, imageName, imgWidth, imgHeight);
	}
	
	@Override
	public boolean collidesWith(Sprite sprite){
		boolean result = false;
		
		if(this.distanceTo(sprite) < this.height / 2){
			result = true;
		}
		
		return result;
	}
	
}
