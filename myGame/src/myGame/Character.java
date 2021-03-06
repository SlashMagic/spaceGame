package myGame;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character{
	
	int forward = Keyboard.KEY_W;
	int backward = Keyboard.KEY_S;
	int rotateLeft = Keyboard.KEY_A;
	int rotateRight = Keyboard.KEY_D;
	int shoot = Keyboard.KEY_SPACE;
	
	float x = 0;
	float y = 0;
	
	int health = 20000;
	
	int doEngines = 0;
	
	float xVel = 0;
	float yVel = 0;
	
	float rotational_speed = 0.1f;
	
	int index = 0;
	
	int attackTimer = 0;
	
	float speed = 0.000f;

	double currentAngle = 180;
	double shipAngle = 180;
	double velocityAngle = 0;
	double totalVelocity = 0;
	 
	private  Texture textureSpaceShip;
	
	Random rnd = new Random();
	
	
	int enemiesToSpawn = 0;
	
	private Texture[] flames = new Texture[4];
	private Texture pew;
	
	File shootSound;
	File engineSound;
	File hitSound;
	File deathSound;
	
	
	
	float getX(){
		return x;
	}
	
	float getY(){
		return y;
	}
	
	 float getxVel(){
		return xVel;
	}
	
	 float getyVel(){
		return yVel;
	}
	
	World gameWorld;
	
	public Character(World newWorld, int newX, int newY) {
		gameWorld = newWorld;
		x = newX;
		y = newY;
		
		
		loadData();
		
	}
	
	void loadData(){
		try{
			deathSound = new File("res/sound/Explosion.wav");
			hitSound = new File("res/sound/Hit.wav");
			engineSound = new File("res/sound/Enemy_Engines.wav");
			shootSound = new File("res/sound/Laser_Shoot.wav");
			textureSpaceShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/spaceShip.png"), GL11.GL_NEAREST);
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
	
	public void update(int delta){
		
		
		
		for(int i = 0; i < gameWorld.enemyProjectiles.size(); i++){
			if(gameWorld.enemyProjectiles.get(i).x > x && gameWorld.enemyProjectiles.get(i).x < x + 22 && gameWorld.enemyProjectiles.get(i).y > y && gameWorld.enemyProjectiles.get(i).y < y + 32){
				health -= 10;
				playSound(hitSound);
				gameWorld.enemyProjectiles.remove(i);
				
			}
			
		}
		
		if(speed <= 0.075){
			rotational_speed = 0.175f;
		}
		
		if(speed > 0.075){
			rotational_speed = 0.175f;
		}
		
		currentAngle = shipAngle;
		
		if(attackTimer < 500){
			attackTimer += delta;
		}
		
		velocityAngle = Math.atan2(yVel, xVel);
		
		totalVelocity = Math.sqrt(Math.pow((xVel * Math.cos(velocityAngle)), 2) +  Math.pow((yVel * Math.sin(velocityAngle)), 2));
		
		if(totalVelocity > 1){
			if(xVel > 0){
				
				xVel -= 0.0001 * delta * 0.15;
			}
			if(xVel < 0){
				
				xVel += 0.0001 * delta * 0.15;
			}
			if(yVel > 0){
				
				yVel -= 0.0001 * delta * 0.15;
			}
			if(yVel < 0){
				
				yVel += 0.0001 * delta * 0.15;
			}
		}
		
		if(Keyboard.isKeyDown(rotateLeft)){
			
			
			
			shipAngle += delta*rotational_speed;
			
			if(currentAngle > 180){
				
				shipAngle = -180;
			}
		}

		if(Keyboard.isKeyDown(rotateRight)){
			
			
			
			shipAngle -= delta*rotational_speed;
		
		if(currentAngle < -180){
			
				shipAngle = 180;
				}
		}
		
		if(Keyboard.isKeyDown(forward) && speed <= 0.175){
			
			
			
			speed += 0.002;
			
		}
		
		if(Keyboard.isKeyDown(backward) && speed > 0){
			
			
			
			speed -= 0.002;
			
		}
		
		if(Keyboard.isKeyDown(shoot) && attackTimer >= 500){
			
			playSound(shootSound);
			
			attackTimer = 0;
			
			float newX = 0;
			float newY = 0;

			switch(index){
			case 0:
				
				if(currentAngle > 135 || currentAngle < -135){
					newX = x + 4;
					newY = y + 20;
				}
				if(currentAngle <= 135 && currentAngle >= 45){
					newX = x + 8;
					newY = y + 10;
				}
				if(currentAngle < 45 && currentAngle > - 45 ){
					newX = x + 18;
					newY = y + 12;
				}
				if(currentAngle < -45 && currentAngle > -135){
					newX = x + 14;
					newY = y + 24;
				}
				
				index = 1;
				
				break;
				
			case 1:
				
				if(currentAngle > 135 || currentAngle < -135){
					newX = x + 23;
					newY = y + 20;
				}
				if(currentAngle <= 135 && currentAngle >= 45){
					newX = x + 8;
					newY = y + 29;
				}
				if(currentAngle < 45 && currentAngle > - 45 ){
					newX = x - 1;
					newY = y + 12;
				}
				if(currentAngle < -45 && currentAngle > -135){
					newX = x + 14;
					newY = y + 5;
				}
				index = 0;
				
				break;
			}
			
			double newXVel = 0;
			double newYVel = 0;
			
			double velocity = 2f;
			
			newXVel =  (velocity * Math.cos((currentAngle - 90) * (Math.PI / 180)));
			newYVel =  (velocity * Math.sin((currentAngle - 90) * (Math.PI / 180)));
			
			gameWorld.createProjectile(new Projectile(0, newX, newY, newXVel, newYVel, currentAngle) , newX, newY, newXVel, newYVel, currentAngle);
			
		}
		
		if(gameWorld.enemies_1.size() + gameWorld.enemies_2.size() + gameWorld.enemies_3.size() == 0){
			enemiesToSpawn = rnd.nextInt(5) + 3;
			
			
			for(int i = 0; i < enemiesToSpawn; i++){
				gameWorld.createEnemy(this);
		}
		
		}
		
		if(x < 10 ){
			xVel = 0;
			x = 10;
		}
		
		if(x > 960 - 24){
			xVel = 0;
			x = 960 - 24;
		}	
			
		if(y < 5){
			yVel = 0;
			y = 5;
		}
		
		if(y > 540 - 34){
			yVel = 0;
			y = 540 - 34;
		}
		//if(doEngines == 0){
		
			//playSound(engineSound);
			//doEngines = 30;
		//}
		//if(doEngines > 0){
			//doEngines--;
		//}
		yVel = (float)(1.5*(delta*speed) * ((Math.cos((currentAngle) * (Math.PI / 180)))));
		xVel = (float)(1.5*(delta*speed) * ((Math.sin((currentAngle) * (Math.PI / 180)))));
		
		x += xVel;
		y += yVel;
		
		if(health <= 0 ){
			playSound(deathSound);
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
	
	public  void draw() {

		if(Keyboard.isKeyDown(forward)){
			drawTexture(flames[0], x + 11, y + 30);
			drawTexture(flames[0], x + 6, y + 30);
		}

		if(Keyboard.isKeyDown(backward) && speed > 0){
			drawTexture(flames[2], x + 1.8f, y + 17);
			drawTexture(flames[2], x + 15.2f, y + 17);
		}
		
		if(Keyboard.isKeyDown(rotateLeft)){
			if(speed > 0){
				drawTexture(flames[0], x + 11, y + 30);
				drawTexture(flames[0], x + 6, y + 30);
				
			}
			drawTexture(flames[3], x + 13, y + 12);
			if(speed < 0){
				
			}
		}
		
		if(Keyboard.isKeyDown(rotateRight)){
			if(speed > 0){
				drawTexture(flames[0], x + 11, y + 30);
				drawTexture(flames[0], x + 6, y + 30);
			}
			drawTexture(flames[1], x + 3, y + 12);
			if(speed < 0){
				
			}
		}
		
		if(Keyboard.isKeyDown(shoot) && attackTimer < 50){
			if(index == 1){
				drawTexture(pew, x - 3, y + 14);
			}
			if(index == 0){
				drawTexture(pew, x + 16, y + 15);
			}
		}
		
		drawTexture(textureSpaceShip);
		
	}
	
	 void drawTexture(Texture newTexture){
		
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 11, y + 17, 0);
		
		GL11.glRotated(-shipAngle + 180, 0, 0, 1);
		
		GL11.glTranslated(-(x + 11), -(y + 17), 0);
		
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
	 
	void drawTexture(Texture newTexture, float newX, float newY){
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 11, y + (17), 0);
		
		GL11.glRotated(-shipAngle + 180, 0, 0, 1);
		
		GL11.glTranslated(-(x + 11), -(y + 17), 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(newX,newY);
	   		GL11.glTexCoord2f(1,0);
    		GL11.glVertex2f(newX + newTexture.getTextureWidth(),newY);
    		GL11.glTexCoord2f(1,1);
    		GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY+newTexture.getTextureHeight());
    		GL11.glTexCoord2f(0,1);
    		GL11.glVertex2f(newX,newY+newTexture.getTextureHeight());	
    	GL11.glEnd();
    	
    	GL11.glPopMatrix();
   
	}
	
}
