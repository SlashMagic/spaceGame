package myGame;

import java.io.IOException;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	double enemyAngle;
	double desiredAngle;
	
	
	
	private Texture enemySpaceShip;
	
	private Texture[] flames = new Texture[4];
	
	private Texture pew;
	
	public Enemy(float newX, float newY) {
		x = newX;
		y = newY;
		
	
		
		loadData();
	}
	
	
	void loadData(){
		try{
			
			enemySpaceShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/enemyShip.png"), GL11.GL_NEAREST);
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
	
	public void update(){
		desiredAngle = (Math.atan2((x + 11) - (Character.getX() + 11), (y + 11) - (Character.getY() + 17))) * (180 / Math.PI);
		
		
		if(!(enemyAngle < desiredAngle+1 && enemyAngle>desiredAngle-1) ){
		if(desiredAngle > enemyAngle){
			if(Math.abs(desiredAngle - enemyAngle) < 180){
				enemyAngle += 0.5;
				if(enemyAngle > 180){
					enemyAngle = -180;
				}
				
			}
		
			else{
				enemyAngle -= 0.5;
				if(enemyAngle < -180){
					enemyAngle = 180;
				}

			}
	}
			
		
		else{
			if(Math.abs(enemyAngle - desiredAngle) < 180){
				enemyAngle -= 0.5;
				if(enemyAngle < -180){
					enemyAngle = 180;
				}
			}
			else{
				enemyAngle += 0.5;
				if(enemyAngle > 180){
					enemyAngle = -180;
				}
			}
		}
	}	
	}
	
	public void draw(){
		drawTexture(enemySpaceShip);
	}
	
	public void drawTexture(Texture newTexture){
		
newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 11, y + 11, 0);
		
		GL11.glRotated(-enemyAngle, 0, 0, 1);
		
		GL11.glTranslated(-(x + 11), -(y + 11), 0);
		
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
