
public class Chest extends Sprite{

	public boolean collected = false;
	
	//constructor
	public Chest(SimpleScene scene, String image, int width, int height){
		super(scene, image, width, height);
		this.collected = false;
	}
	

}
