import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class SimpleScene {
	
	/////////////////////////////////////////////////
	//////////////////  PROPERTIES  /////////////////
	/////////////////////////////////////////////////
	
	public Canvas canvas = null;
	public Color BGColor = Color.WHITE;
	protected boolean visible = true;
	protected boolean running = false;
	
	/////////////////////////////////////////////////
	
	
	
	
	
	//default constructor
	public SimpleScene(){
		this.canvas = new Canvas(640, 480);
	}
	
	//overloaded constructor
	public SimpleScene(int width, int height){
		this.canvas = new Canvas(width, height);
	}
	
	
	//getters and setters
	public int getWidth(){
		return (int)this.canvas.getWidth();
	}
	
	
	public int getHeight(){
		return (int)this.canvas.getHeight();
	}
	
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public void show(){
		this.visible = true;
	}
	
	public void hide(){
		this.visible = false;
	}
	
	public void start(){
		this.running = true;
	}
	
	public void stop(){
		this.running = false;
	}
	
	public boolean getState(){
		return this.running;
	}
	
	
	
	//other methods
	public void clear(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(BGColor);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	
	

	
	
	
	
	
	
}//end class definition
