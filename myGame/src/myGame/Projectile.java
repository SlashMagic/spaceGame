package myGame;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Projectile {

	 float x = -10;
	 float y = -10;
	
	 int type = 0;
	 
	 double xVel = 0;
	 double yVel = 0;
	
	double projectileAngle = 0;
	 
	private  Texture textureProjectile;
	
	public float getY(){
		return y;
	}
	
	public Projectile(int newType, float newX, float newY, double newXVel, double newYVel, double newAngle) {
		type = newType;
		
		x = newX;
		y = newY;
		
		xVel = newXVel;
		yVel = newYVel;
		
		projectileAngle = newAngle;
		
		loadData();
	}
	
	 void loadData(){
		try{
			if(type == 0){
				textureProjectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectileLaser.png"), GL11.GL_NEAREST);
			}
			if(type == 1){
				textureProjectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectileBlueLaser.png"), GL11.GL_NEAREST);
			}
			else{
				textureProjectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectileLaser.png"), GL11.GL_NEAREST);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public  void update(int delta){
		x += xVel;
		y -= yVel;
	}
	
	public  void draw() {
		
		drawTexture(textureProjectile);
	}
	
	 void drawTexture(Texture newTexture){
		
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, 0);
		
		GL11.glRotated(-projectileAngle, 0, 0, 1);
		
		GL11.glTranslated(-(x), -(y), 0);
		
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
    	
    	GL11.glPopMatrix();
	}

}
