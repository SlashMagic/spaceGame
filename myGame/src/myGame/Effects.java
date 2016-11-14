package myGame;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Effects {

	static Texture[] flames = new Texture[4];
	static Texture pew;
	
	public Effects() {
		loadData();
	}
	
	public static Texture getFlames0(){
		return flames[0];
	}
	
	
	static void loadData(){
		try{
			
			flames[0] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames.png"), GL11.GL_NEAREST);
			flames[1] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames1.png"), GL11.GL_NEAREST);
			flames[2] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames2.png"), GL11.GL_NEAREST);
			flames[3] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames3.png"), GL11.GL_NEAREST);
			pew = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/pew.png"), GL11.GL_NEAREST);
		
		}
		
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void draw(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			drawTexture(flames[0], Character.getX() + 11, Character.getY() + 30);
			drawTexture(flames[0], Character.getX() + 6, Character.getY() + 30);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			drawTexture(flames[3], Character.getX() + 14, Character.getY() + 11);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			drawTexture(flames[2], Character.getX() + 1.8f, Character.getY() + 16);
			drawTexture(flames[2], Character.getX() + 15.2f, Character.getY() + 16);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			drawTexture(flames[1], Character.getX() + 2.5f, Character.getY() + 11);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Character.getAttackTimer() > 62){
			
			if(Main.getIndexProjectile() == 1){
				drawTexture(pew, Character.getX() - 3, Character.getY() + 15);
			}
			
			if(Main.getIndexProjectile() == 0){
				drawTexture(pew, Character.getX() + 16, Character.getY() + 15);
			}
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			if(Character.getxVel() > 0.1){
				drawTexture(flames[3], Character.getX() + 14, Character.getY() + 11);
			}
			if(Character.getxVel() < -0.1){
				drawTexture(flames[1], Character.getX() + 1.5f, Character.getY() + 11);
			}
			if(Character.getyVel() > 0.1){
				drawTexture(flames[0], Character.getX() + 11, Character.getY() + 30);
				drawTexture(flames[0], Character.getX() + 6, Character.getY() + 30);
			}
			if(Character.getyVel() < -0.1){
				drawTexture(flames[2], Character.getX() + 1.8f, Character.getY() + 16);
				drawTexture(flames[2], Character.getX() + 15.2f, Character.getY() + 16);
			}
			
		}
	
	}
	
	public static void drawTexture(Texture newTexture, float x, float y){
		
newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(x,y);
	   		GL11.glTexCoord2f(1,0);
    		GL11.glVertex2f(x + newTexture.getTextureWidth(),y);
    		GL11.glTexCoord2f(1,1);
    		GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
    		GL11.glTexCoord2f(0,1);
    		GL11.glVertex2f(x,y+newTexture.getTextureHeight());	
    	GL11.glEnd();
		
	}
	
}
