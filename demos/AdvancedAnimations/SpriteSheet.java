import java.util.ArrayList;
import javafx.scene.image.Image;


public class SpriteSheet {

	//properties
	protected String filename = "";
	protected Image image = null;
	protected double fullWidth = 0;
	protected double fullHeight = 0;
	protected int width = 0;
	protected int height = 0;
	protected int numStates = 0;
	protected int maxFrames = 0;
	
	protected boolean paused = false;
	
	protected int currentState = 0;
	protected int currentFrame = 0;
	protected double timeSinceLastUpdate = 0.0;
	protected ArrayList<Double> speed = new ArrayList<Double>();
	protected ArrayList<Integer> framesPerState = new ArrayList<Integer>();
	protected ArrayList<String> map = new ArrayList<String>();
	
	
	
	//default constructor
	public SpriteSheet(){
		
		this.filename = "default.png";
		this.image = new Image(this.filename);
		
		this.numStates = 1;
		this.maxFrames = 1;
		
		this.fullWidth = this.image.getWidth();
		this.fullHeight = this.image.getHeight();
		
		this.calcFrameDimensions();
		
		this.initSpeed();
		this.initFramesPerState();
		
	}//end default constructor
	
	
	
	//Non-animated constructor
	public SpriteSheet(String file){
		
		this.filename = file;
		this.image = new Image(this.filename);
		
		this.numStates = 1;
		this.maxFrames = 1;
		
		this.fullWidth = this.image.getWidth();
		this.fullHeight = this.image.getHeight();
		
		this.calcFrameDimensions();
		
		this.initSpeed();
		this.initFramesPerState();
		
	}//end default constructor
	
	
	
	//overloaded constructor
	public SpriteSheet(String file, int rows, int maxCols){
		
		this.filename = file;
		this.image = new Image(this.filename);
		
		this.numStates = rows;
		this.maxFrames = maxCols;
		
		this.fullWidth = this.image.getWidth();
		this.fullHeight = this.image.getHeight();
		
		this.calcFrameDimensions();
		
		this.initSpeed();
		this.initFramesPerState();
		
	}//end overloaded constructor
	
	
	//single sprite constructor
	public SpriteSheet(String file, int w, int h, int rows, int maxCols){
			
			this.filename = file;
			this.image = new Image(this.filename);
			
			this.numStates = rows;
			this.maxFrames = maxCols;
			
			this.fullWidth = w;
			this.fullHeight = h;
			
			this.calcFrameDimensions();
			
			this.initSpeed();
			this.initFramesPerState();
			
	}//end single sprite constructor
	
	
	
	
	//use the width and height of the image with the states and frames to calculate width and height for each frame
	protected void calcFrameDimensions() {
		this.width = (int) Math.round(this.fullWidth / this.maxFrames);
		this.height = (int)Math.round(this.fullHeight / this.numStates);
	}
	
	
	
	public void step(double t){
		
		if(!this.paused){
			this.timeSinceLastUpdate += t;
			
			if(this.timeSinceLastUpdate > 1.0 / this.speed.get(currentState)){
				//update the frame
				this.timeSinceLastUpdate = 0.0;

				this.currentFrame++;
				
				if(this.currentFrame >= this.framesPerState.get(this.currentState)){
					this.currentFrame = 0;
				}
			}
		}//end if !this.paused
		
	}
	
	
	protected void initFramesPerState(){
		//establish framesPerState
		for(int i = 0; i < this.numStates; i++){
			this.framesPerState.add(this.maxFrames);
		}
	}
	
	
	protected void initSpeed(){
		//establish speeds
		for(int i = 0; i < this.numStates; i++){
			this.speed.add(20.0);
		}
	}
	
	
	//sets properties based on given preset
	public void setPreset(String preset){
		
		if(preset.toUpperCase().equals("LPC")){
			this.maxFrames = 13;
			this.numStates = 21;
			
			this.map = new ArrayList<String>();
			this.map.add("backward spell");
			this.map.add("left spell");
			this.map.add("forward spell");
			this.map.add("right spell");
			this.map.add("backward thrust");
			this.map.add("left thrust");
			this.map.add("forward thrust");
			this.map.add("right thrust");
			this.map.add("backward walk");
			this.map.add("left walk");
			this.map.add("forward walk");
			this.map.add("right walk");
			this.map.add("backward sword");
			this.map.add("left sword");
			this.map.add("forward sword");
			this.map.add("rigth sword");
			this.map.add("backward arrow");
			this.map.add("left arrow");
			this.map.add("forward arrow");
			this.map.add("right arrow");
			this.map.add("hurt");
			
			this.setFramesPerState(0, 7);
			this.setFramesPerState(1, 7);
			this.setFramesPerState(2, 7);
			this.setFramesPerState(3, 7);
			this.setFramesPerState(4, 8);
			this.setFramesPerState(5, 8);
			this.setFramesPerState(6, 8);
			this.setFramesPerState(7, 8);
			this.setFramesPerState(8, 9);
			this.setFramesPerState(9, 9);
			this.setFramesPerState(10, 9);
			this.setFramesPerState(11, 9);
			this.setFramesPerState(12, 6);
			this.setFramesPerState(13, 6);
			this.setFramesPerState(14, 6);
			this.setFramesPerState(15, 6);
			this.setFramesPerState(16, 13);
			this.setFramesPerState(17, 13);
			this.setFramesPerState(18, 13);
			this.setFramesPerState(19, 13);
			this.setFramesPerState(20, 6);
		}else{
			System.out.println("Preset not installed.");
		}
		
	}//end setPreset
	
	
	
	
	///////////////////////////////////////////////////////////////////
	//////////////////////     GETTERS      ///////////////////////////
	///////////////////////////////////////////////////////////////////
	
	public Image getImage(){
		return this.image;
	}
	
	public double getFullWidth() {
		return this.fullWidth;
	}
	
	public double getFullHeight() {
		return this.fullHeight;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getNumStates() {
		return this.numStates;
	}
	
	public int getMaxFrames() {
		return this.maxFrames;
	}

	public int getCurrentState() {
		return this.currentState;
	}
	
	public int getCurrentFrame() {
		return this.currentFrame;
	}
	
	public double getSpeed(int state) {
		return this.speed.get(state);
	}
	
	public double getSpeed(){
		return this.speed.get(this.currentState);
	}
	
	public boolean isPaused(){
		return this.paused;
	}
	
	public int getFramesPerState(int state){
		return this.framesPerState.get(state);
	}
	
	public ArrayList<String> getMap(){
		return this.map;
	}
	
	///////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////
	//////////////////////     SETTERS      ///////////////////////////
	///////////////////////////////////////////////////////////////////
	
	public void setImage(Image img){
		this.image = img;
	}
	
	public void setWidth(int w){
		this.width = w;
	}
	
	public void setHeight(int h){
		this.height = h;
	}
	
	public void setNumStates(int num){
		this.numStates = num;
		this.calcFrameDimensions();
	}
	
	public void setMaxFrames(int frames){
		this.maxFrames = frames;
		this.calcFrameDimensions();
	}
	
	public void setState(int s){
		this.currentState = s;
	}
	
	public void setState(String s){
		int i = 0;
		boolean keepGoing = true;
		while(keepGoing){
			if(this.map.get(i).toUpperCase().equals(s.toUpperCase())){
				keepGoing = false;
				this.setState(i);
			}else{
				i++;
			}
			
			if(i >= this.map.size()){
				keepGoing = false;
				System.out.println("ERROR: Name not in Map.");
			}
		}//end while
	}
	
	public void setFrame(int f){
		this.currentFrame = f;
	}
	
	public void setSpeed(double s){
		this.speed.set(this.currentState, s);
	}
	
	public void setSpeed(int state, double s){
		this.speed.set(state, s);
	}
	
	public void setGlobalSpeed(double s){
		for(int i = 0; i < this.speed.size(); i++){
			this.speed.set(i, s);
		}
	}
	
	public void pause(){
		this.paused = true;
	}
	
	public void play(){
		this.paused = false;
	}
	
	public void setFramesPerState(int state, int frames){
		this.framesPerState.set(state, frames);
	}
	
	public void setMap(ArrayList<String> names){
		this.map = names;
	}
	
	///////////////////////////////////////////////////////////////////
	
	
	
	
}//end class definition
