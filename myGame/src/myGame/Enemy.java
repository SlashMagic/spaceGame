package myGame;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {

	float x;
	float y;
	
	float xVel;
	float yVel;
	
	float speed = 0.5f;
	
	int index = 0;

	int attackTimer = 288;
	
	double enemyAngle;
	double desiredAngle;
	
	double previousDistance = 0;
	double distance = 0;
	
	boolean isEvading = false;
	
	World gameWorld;
	Character character;
	
	File shoot;
	File hitSound;
	
	Texture enemySpaceShip;
	
	Texture[] flames = new Texture[4];
	
	Texture pew;
	
	public Enemy(World newWorld, Character newCharacter, float newX, float newY) {
		gameWorld = newWorld;
		character = newCharacter;
		x = newX;
		y = newY;
		
		enemyAngle = Math.toDegrees(Math.atan2((x + 11) - (character.getX() + 11), (y + 11) - (character.getY() + 17)));
		loadData();
	}
	
	
	void loadData(){
		try{
			hitSound = new File("res/sound/Hit.wav");
			shoot = new File("res/sound/Laser_Shoot.wav");
			flames[0] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames.png"), GL11.GL_NEAREST);
			flames[1] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames1.png"), GL11.GL_NEAREST);
			flames[2] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames2.png"), GL11.GL_NEAREST);
			flames[3] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/flames3.png"), GL11.GL_NEAREST);
			pew = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bluePew.png"), GL11.GL_NEAREST);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	public void playSound(File sound){
		
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
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
	
	public void drawTexture(Texture newTexture, float newX, float newY){
		
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 11, y + 11, 0);
		
		GL11.glRotated(-enemyAngle, 0, 0, 1);
		
		GL11.glTranslated(-(x + 11), -(y + 11), 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(newX,newY);
	   		GL11.glTexCoord2f(1,0);
    		GL11.glVertex2f(newX + newTexture.getImageWidth(), newY);
    		GL11.glTexCoord2f(1,1);
    		GL11.glVertex2f(newX + newTexture.getImageWidth(), newY + newTexture.getImageHeight());
    		GL11.glTexCoord2f(0,1);
    		GL11.glVertex2f(newX , newY + newTexture.getImageHeight());	
    		
    	GL11.glEnd();
    	
    	GL11.glPopMatrix();
		
	}
	
}
