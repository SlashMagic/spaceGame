package myGame;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;



public class Main {
	
	int width = 800;
	int height = 600;
	
	boolean fullscreen = false;
	
	long lastFPS;
	
	int fps;
	
	long lastFrame;
	
	boolean exit = false;
	
	int index = 0;
	int indexProjectile = 0;
	
	 int getWidth(){
		int width = Display.getWidth();
		return width;
	}
	
	 int getHeight(){
		int height = Display.getHeight();
		return height;
	}
	
	 int getIndexProjectile(){
		int newIndex = index;
		return newIndex;
	}
	
	
	
	public void start(){
		
		try{
			
			if(fullscreen){
				//width = 1920;
				//height = 1080;
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			}
			if(!fullscreen){
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			
			Display.create();
			Keyboard.create();
			
		} catch (LWJGLException e){
			e.printStackTrace();
			System.exit(0);
		}
		
		lastFPS = getTime();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                    
	 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
	    GL11.glClearDepth(1);                                       
	 
	    
	    
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		World gameWorld = new World();
		
		Character newCharacter = new Character(gameWorld, 100, 100);
		
		
		
		while(!Display.isCloseRequested() && !exit){
			
			updateFPS();
			
			Keyboard.poll();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				exit = true;
			}
			
			float delta = getDelta();
			
			System.out.println(delta);
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Color.white.bind();
			
			newCharacter.update(delta);
			
			gameWorld.draw(delta);
			
			newCharacter.draw();
			
			Display.update();
			
			Display.sync(120);
			
		}
		
		Display.destroy();
		
	}
	
	public float getDelta(){
		long time = getTime();
		float delta = (int)(time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS(){
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	

	
	public static void main(String[] args){
		Main myMain = new Main();
		myMain.start();
	}
	
	
}
