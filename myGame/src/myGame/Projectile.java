package myGame;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Projectile {

	 float x = -10;
	 float y = -10;
	
	 float xVel = 0;
	 float yVel = 0;
	
	
	private  Texture textureProjectile;
	
	public float getY(){
		return y;
	}
	
	public Projectile(float newX, float newY, float xVelShip, float yVelShip) {
		
		
		x = newX;
		y = newY + 17;
		
		xVel = xVelShip;
		yVel = 2f;
		
		loadData();
	}
	
	 void loadData(){
		try{
			
			textureProjectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectileLaser.png"), GL11.GL_NEAREST);
			
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
