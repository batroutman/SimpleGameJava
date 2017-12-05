
public class Ship extends Sprite{

	public Keyboard k = null;
	public int mass = 1;
	public boolean keyListen = false;
	
	//constructor
	public Ship(SimpleScene scene, String image, int width, int height, Keyboard keyboard){
		super(scene, image, width, height);
		this.k = keyboard;
		this.mass = 1;
		this.keyListen = false;
	}
	
	
	
	//checkKeys
	public void checkKeys(){
		
		if(this.keyListen){

			if(k.keysDown[k.A]){
				//rotate to left
				this.changeImage("cannon_fire.png");
				this.setAngle(270);
			}
			if(k.keysDown[k.D]){
				//rotate to right
				this.setImage("cannon_fire.png");
				this.setAngle(90);
			}
			if(k.keysDown[k.W]){
				//rotate to up
				this.setImage("cannon_fire.png");
				this.setAngle(0);
			}
			if(k.keysDown[k.S]){
				//rotate to down
				this.setImage("cannon_fire.png");
				this.setAngle(180);
			}
			if(!(k.keysDown[k.A] || k.keysDown[k.D] || k.keysDown[k.W] || k.keysDown[k.S])){
				this.setImage("cannon.png");
			}
			
		}//end if
		
	}
	
}
