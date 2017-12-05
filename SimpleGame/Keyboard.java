/*

Blake Troutman 2017
SimpleGame in Java (with JavaFX)
CSCI 437 Final Project

Keyboard.java - this class records the user input. Use this class for reading the keyboard
and mouse.

NOTE: feel free to add keyboard constants in the "additional keyboard constants" section

*/
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Keyboard{

	protected final int numKeys = 256;
	
	//keyboard constants
	public final int A = 65, B = 66, C = 67, D = 68, E = 69, F = 70, G = 71,
	H = 72, I = 73, J = 74, K = 75, L = 76, M = 77, N = 78,
	O = 79, P = 80, Q = 81, R = 82, S = 83, T = 84, U = 85,
	V = 86, W = 87, X = 88, Y = 89, Z = 90,
	LEFT = 37, RIGHT = 39, UP = 38, DOWN = 40, SPACE = 32,
	ESC = 27, PGUP = 33, PGDOWN = 34, HOME = 36, END = 35,
	K0 = 48, K1 = 49, K2 = 50, K3 = 51, K4 = 52, K5 = 53,
	K6 = 54, K7 = 55, K8 = 56, K9 = 57;
	
	//additional keyboard constants
	public final int SHIFT = 15, CTRL = 17;
	
	public boolean[] keysDown = new boolean[this.numKeys];
	
	
	//mouse data
	public double mouseX = 0;
	public double mouseY = 0;
	public boolean clicked = false;
	
	//default constructor
	public Keyboard(){
		//initialize the keysDown array
		for(int i = 0; i < this.numKeys; i++){
			this.keysDown[i] = false;
		}
	}//end constructor
	
	
	
	public void press(String s){
		this.keysDown[this.getKeyConst(s)] = true;
	}
	
	
	
	public void release(String s){
		this.keysDown[this.getKeyConst(s)] = false;
	}
	
	
	
	//map from KeyCode to ascii
	public int getKeyConst(String s){
		int result = 0;
		
		if(s.length() == 1){
			//it is a letter
			result = s.charAt(0);
		}else if(s.toUpperCase().equals("LEFT")){
			result = LEFT;
		}else if(s.toUpperCase().equals("RIGHT")){
			result = RIGHT;
		}else if(s.toUpperCase().equals("UP")){
			result = UP;
		}else if(s.toUpperCase().equals("DOWN")){
			result = DOWN;
		}else if(s.toUpperCase().equals("SPACE")){
			result = SPACE;
		}else if(s.toUpperCase().equals("SHIFT")){
			result = SHIFT;
		}else if(s.toUpperCase().equals("ESCAPE")){
			result = ESC;
		}else if(s.toUpperCase().equals("PAGE_UP")){
			result = PGUP;
		}else if(s.toUpperCase().equals("PAGE_DOWN")){
			result = PGDOWN;
		}else if(s.toUpperCase().equals("HOME")){
			result = HOME;
		}else if(s.toUpperCase().equals("END")){
			result = END;
		}else if(s.toUpperCase().equals("CONTROL")){
			result = CTRL;
		}else if(s.toUpperCase().startsWith("DIGIT", 0)){
			int num = Integer.parseInt(Character.toString(s.charAt(5)));
			result = num + 48;
		}
		
		return result;
	}//end getKeyConst
	
	
	public void onPress(){
		/*
		System.out.println("Pressed");
		System.out.println("x: " + this.mouseX);
		System.out.println("y: " + this.mouseY);
		*/
	}
	
	public void onRelease(){
		/*
		System.out.println("Released");
		System.out.println("x: " + this.mouseX);
		System.out.println("y: " + this.mouseY);
		*/
	}
	
	
	
}//end class definition
