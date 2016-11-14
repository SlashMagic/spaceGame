package myGame;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;


public class Main {
	
	int width = 800;
	int height = 600;
	
	boolean fullscreen = false;
	
	long lastFPS;
	
	int fps;
	
	long lastFrame;
	
	boolean exit = false;
	
	static int index = 0;
	static int indexProjectile = 0;
	
	
	
	static List<Projectile> projectiles = new ArrayList<Projectile>();
	
	static int getWidth(){
		int width = Display.getWidth();
		return width;
	}
	
	static int getHeight(){
		int height = Display.getHeight();
		return height;
	}
	
	
	
	static int getIndexProjectile(){
		int newIndex = index;
		return newIndex;
	}
	
	public static void createProjectile(Projectile newProjectile){
		projectiles.add(newProjectile);
	}
	
	
	
	public void start(){
		
		
		
		
		try{
			
			if(fullscreen){
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
		
		new Character(100, 100);
		
		new Projectile(-100, -100, 0, 0);
		
		new World();
		
		new Effects();
		
		while(!Display.isCloseRequested() && !exit){
			
			
			
			updateTimer();
			
			updateFPS();
			
			Keyboard.poll();
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				exit = true;
			}
			
		
			
			//int delta = getDelta();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Color.white.bind();
			
			Character.update();
			
			for(int i = 0; i < projectiles.size(); i++){
				
			}
			
			System.out.println(projectiles.size());
			
			
			World.draw();
			Character.draw();
			Projectile.draw();
			Effects.draw();
			
			Display.update();
			
			Display.sync(144);
			
		}
		
		Display.destroy();
		
	}
	
	public int getDelta(){
		long time = getTime();
		int delta = (int)(time - lastFrame);
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
	
	public void updateTimer(){
		if(Character.getAttackTimer() != 0){
			Character.setAttackTimer(Character.getAttackTimer() - 1);
		}
	}
	
	public static void main(String[] args){
		Main myMain = new Main();
		myMain.start();
	}
	
	
}
