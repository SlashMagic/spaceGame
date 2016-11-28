package myGame;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character{
	
	float x = 0;
	float y = 0;
	
	float xVel = 0;
	float yVel = 0;
	
	int index = 0;
	
	int attackTimer = 0;
	
	float speed = 0.001f;
	float maxSpeed = 0.1f;
	
	double desiredAngle = 0;
	double currentAngle = 180;
	double shipAngle = 180;
	double velocityAngle = 0;
	double totalVelocity = 0;
	 
	private  Texture textureSpaceShip;
	
	Random rnd = new Random();
	
	int enemiesToSpawn = 0;
	
	private Texture[] flames = new Texture[4];
	private Texture pew;
	
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
		
		desiredAngle = Math.atan2(Mouse.getX() - (x + (textureSpaceShip.getWidth() / 2)), (Display.getHeight() - Mouse.getY()) - (y + (textureSpaceShip.getTextureHeight() / 2)));
		
		desiredAngle = desiredAngle * (180/Math.PI);
		
		currentAngle = shipAngle;
		
		float rotational_speed = 0.1f;
		
	
		
		
		/*if(!(currentAngle < desiredAngle+1 && currentAngle>desiredAngle-1) ){
			if(desiredAngle > currentAngle){
				if(Math.abs(desiredAngle - currentAngle) < 180){
					shipAngle += rotational_speed;
					if(shipAngle > 180){
						shipAngle = -180;
					}
					
				}
			
				else{
					shipAngle -= rotational_speed;
					if(shipAngle < -180){
						shipAngle = 180;
					}

				}
		}
				
			
			else{
				if(Math.abs(currentAngle - desiredAngle) < 180){
					shipAngle -= rotational_speed;
					if(shipAngle < -180){
						shipAngle = 180;
					}
				}
				else{
					shipAngle += rotational_speed;
					if(shipAngle > 180){
						shipAngle = -180;
					}
				}
			}
		}	*/
		
		
		if(attackTimer != 0){
			attackTimer--;
		}
		
		if(delta == 17){
			delta = 16;
		}
		
		if(delta == 6){
			delta = 7;
		}
		
		velocityAngle = Math.atan2(yVel, xVel);
		
		totalVelocity = Math.sqrt(Math.pow((xVel * Math.cos(velocityAngle)), 2) +  Math.pow((yVel * Math.sin(velocityAngle)), 2));
		
		
		
		if(totalVelocity > 1){
			if(xVel > 0){
				xVel -= 0.0001;
			}
			if(xVel < 0){
				xVel += 0.0001;
			}
			if(yVel > 0){
				yVel -= 0.0001;
			}
			if(yVel < 0){
				yVel += 0.0001;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			shipAngle += delta*rotational_speed;
			if(currentAngle > 180){
				shipAngle = -180;
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			shipAngle -= delta*rotational_speed;
			if(currentAngle < -180){
				shipAngle = 180;
			}
			
		}
		
		/*if(Keyboard.isKeyDown(Keyboard.KEY_A) && totalVelocity < 1){
			yVel += (float)((delta*speed) * ((Math.cos((currentAngle + 90) * (Math.PI / 180)))));
			xVel += (float)((delta*speed) * ((Math.sin((currentAngle + 90) * (Math.PI / 180)))));
			
		}*/
		
		/*if(Keyboard.isKeyDown(Keyboard.KEY_D) && totalVelocity < 1){
			yVel -= (float)((delta*speed) * ((Math.cos((currentAngle + 90) * (Math.PI / 180)))));
			xVel -= (float)((delta*speed) * ((Math.sin((currentAngle + 90) * (Math.PI / 180)))));
		}*/
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && totalVelocity < 1){
			yVel += (float)(1.5*(delta*speed) * ((Math.cos((currentAngle) * (Math.PI / 180)))));
			xVel += (float)(1.5*(delta*speed) * ((Math.sin((currentAngle) * (Math.PI / 180)))));
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && totalVelocity >= 0){
			yVel -= (float)((delta*speed) * ((Math.cos((currentAngle) * (Math.PI / 180)))));
			xVel -= (float)((delta*speed) * ((Math.sin((currentAngle) * (Math.PI / 180)))));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			
			if(xVel > 0){
				xVel -= (float)delta*speed;
			}
			
			if(xVel < 0){
				xVel += (float)delta*speed;
			}
			
			if(yVel > 0){
				yVel -= (float)delta*speed;
				
			}
			if(yVel < 0){
				yVel += (float)delta*speed;
			}
			
			if(totalVelocity < 0.01){
				yVel = 0;
				xVel = 0;
			}
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackTimer == 0){
			
			
			
			attackTimer = 72;
			
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
			
			double velocity = 1.5f;
			
			
			
			newXVel =  (velocity * Math.cos((currentAngle - 90) * (Math.PI / 180)));
			newYVel =  (velocity * Math.sin((currentAngle - 90) * (Math.PI / 180)));
			
			
			gameWorld.createProjectile(new Projectile(newX, newY, newXVel, newYVel, currentAngle) , newX, newY, newXVel, newYVel, currentAngle);
			
		}
		
		if(gameWorld.enemies.size() == 0){
			enemiesToSpawn = rnd.nextInt(4) + 1;
			for(int i = 0; i < enemiesToSpawn; i++){
				gameWorld.createEnemy(this);
		}
		
		}
		
		if(x < 10 ){
			xVel = 0;
			x = 10;
		}
		
		if(x > 800 - 24){
			xVel = 0;
			x = 800 - 24;
		}	
			
		if(y < 5){
			yVel = 0;
			y = 5;
		}
		
		if(y > 600 - 34){
			yVel = 0;
			y = 600 - 34;
		}
		
		x += xVel;
		y += yVel;
		
	}
	
	public  void draw() {
		drawTexture(textureSpaceShip);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			drawTexture(flames[0], x + 11, y + 30);
			drawTexture(flames[0], x + 6, y + 30);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			drawTexture(flames[3],x + 14, y + 11);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			drawTexture(flames[2], x + 1.8f, y + 16);
			drawTexture(flames[2], x + 15.2f, y + 16);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			drawTexture(flames[1], x + 2.5f, y + 11);
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			if(xVel > 0.1){
				drawTexture(flames[3], x + 14, y + 11);
			}
			if(xVel < -0.1){
				drawTexture(flames[1], x + 1.5f, y + 11);
			}
			if(yVel > 0.1){
				drawTexture(flames[0], x + 11, y + 30);
				drawTexture(flames[0], x + 6,y + 30);
			}
			if(yVel < -0.1){
				drawTexture(flames[2], x + 1.8f, y + 16);
				drawTexture(flames[2], x + 15.2f, y + 16);
			}
			
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackTimer > 62){
			if(index == 1){
				drawTexture(pew, x - 3, y + 14);
			}
			if(index == 0){
				drawTexture(pew, x + 16, y + 15);
			}
		}
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
