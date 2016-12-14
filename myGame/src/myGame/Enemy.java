package myGame;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

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
	
	int type = 0;

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
	
	private Texture enemySpaceShip;
	
	private Texture[] flames = new Texture[4];
	
	private Texture pew;
	
	public Enemy(World newWorld, Character newCharacter, float newX, float newY) {
		Random rnd = new Random();
		type = rnd.nextInt(1);
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
			enemySpaceShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/enemyShip_0.png"), GL11.GL_NEAREST);
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
	
	public void update(int delta){
		desiredAngle = Math.toDegrees(Math.atan2((x + 11) - (character.getX() + 11), (y + 11) - (character.getY() + 17)));
		
		/*if(gameWorld.enemies.size() != 1){
			for(int i = 0; i < gameWorld.enemies.size(); i++){
				
				double rise = 0;
				double run = 0;

				if(this.y != gameWorld.enemies.get(i).y){
				
					rise = y - gameWorld.enemies.get(i).y;
					run = x - gameWorld.enemies.get(i).x;
				
					distance = Math.sqrt(Math.pow(rise, 2) + Math.pow(run,  2));
				
					if(distance < 50 && previousDistance < distance && enemyAngle !=  gameWorld.enemies.get(i).enemyAngle){
						
						if((enemyAngle < -90 && gameWorld.enemies.get(i).enemyAngle > 90) || (enemyAngle > 90 || gameWorld.enemies.get(i).enemyAngle < -90)){
							if(enemyAngle > gameWorld.enemies.get(i).enemyAngle){
								enemyAngle += 0.5;
								if(enemyAngle > 180){
									enemyAngle = -180;
								}
							}
							if(gameWorld.enemies.get(i).enemyAngle > enemyAngle){
								enemyAngle -= 0.5;
								if(enemyAngle < -180){
									enemyAngle = 180;
								}
							}
						}
						else{
							if(enemyAngle > gameWorld.enemies.get(i).enemyAngle){
								enemyAngle -= 0.5;
								if(enemyAngle < -180){
									enemyAngle = 180;
								}
							}
							if(gameWorld.enemies.get(i).enemyAngle > enemyAngle){
								enemyAngle += 0.5;
								if(enemyAngle > 180){
									enemyAngle = -180;
								}
							}
						}
						
						isEvading = true;
						break;
					}	
					else if(distance >= 100){
					
						isEvading = false;
					
					}
				}
			}
		}
		
		previousDistance = distance;
		*/
		if(!isEvading){
			if(!(enemyAngle < desiredAngle+ 1 && enemyAngle>desiredAngle- 1) ){
				if(desiredAngle > enemyAngle){
					if(Math.abs(desiredAngle - enemyAngle) < 180){
						enemyAngle += 0.5 * delta * 0.15;
						if(enemyAngle > 180){
							enemyAngle = -180;
						}
						
					}
					
					else{
						enemyAngle -= 0.5 * delta * 0.15;
						if(enemyAngle < -180){
							enemyAngle = 180;
						}
					}
				}
				
				else{
					if(Math.abs(enemyAngle - desiredAngle) < 180){
						enemyAngle -= 0.5 * delta * 0.15;
						if(enemyAngle < -180){
							enemyAngle = 180;
						}
					}
					else{
						enemyAngle += 0.5 * delta * 0.15;
						if(enemyAngle > 180){
							enemyAngle = -180;
						}
					
					}
				}
			}else{
			
				if(attackTimer == 0){
					
					attackTimer = 144;
					
					float newProjectileX = 0;
					float newProjectileY = 0;
					
					
					switch(index){
					case 0:
					
						if(enemyAngle > 135 || enemyAngle < -135){
							newProjectileX = x + 20;
							newProjectileY = y + 20;
						}
						if(enemyAngle <= 135 && enemyAngle >= 45){
							newProjectileX = x + 2;
							newProjectileY = y + 20;
						}
						if(enemyAngle < 45 && enemyAngle > - 45 ){
							newProjectileX = x + 0;
							newProjectileY = y + 2;
						}
						if(enemyAngle < -45 && enemyAngle > -135){
							newProjectileX = x + 14;
							newProjectileY = y + 24;
						}
						
						
					
						index = 1;
					
						break;
					
					case 1:
					
						if(enemyAngle > 135 || enemyAngle < -135){
							newProjectileX = x + 4;
							newProjectileY = y + 20;
						}
						if(enemyAngle <= 135 && enemyAngle >= 45){
							newProjectileX = x + 0;
							newProjectileY = y + 1;
						}
						if(enemyAngle < 45 && enemyAngle > - 45 ){
							newProjectileX = x + 18;
							newProjectileY = y + 2;
						}
						if(enemyAngle < -45 && enemyAngle > -135){
							newProjectileX = x + 14;
							newProjectileY = y + 5;
						}
						index = 0;
					
						break;
					}
				
					double newProjectileXVel = 0;
					double newProjectileYVel = 0;
				
					double velocity = 1.5f;

					newProjectileXVel =  -(velocity * Math.cos((enemyAngle - 90) * (Math.PI / 180)));
					newProjectileYVel =  -(velocity * Math.sin((enemyAngle - 90) * (Math.PI / 180)));
					
					playSound(shoot);
				
					gameWorld.createEnemyProjectile(new Projectile(1, newProjectileX, newProjectileY, newProjectileXVel, newProjectileYVel, enemyAngle) , newProjectileX, newProjectileY, newProjectileXVel, newProjectileYVel, enemyAngle);
				}
			}
		}	
		
		else if(isEvading){
			
		}
		yVel = -(float)((speed) * ((Math.cos((enemyAngle) * (Math.PI / 180)))));
		xVel = -(float)((speed) * ((Math.sin((enemyAngle) * (Math.PI / 180)))));
			
		x += xVel * delta * 0.15;
		y += yVel * delta * 0.15;                
		
			for(int j = 0; j < gameWorld.projectiles.size(); j++){
				
				if(gameWorld.projectiles.get(j).x > x && gameWorld.projectiles.get(j).x + 1 < x + 22 && gameWorld.projectiles.get(j).y > y && gameWorld.projectiles.get(j).y + 1 < y + 22){
					gameWorld.enemies.remove(this);
					playSound(hitSound);
					gameWorld.projectiles.remove(j);
					gameWorld.score += 10;
				}
			
		}
			if(attackTimer != 0){
				attackTimer -= 1;
			}
			
	}
	
	public void playSound(File sound){
		
		try{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
			
			AudioFormat format = audioIn.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	

	public void draw(){
	
		if(attackTimer > 120){
			if(index == 1){
				drawTexture(pew, x - 1, y - 2);
			}
			else if(index == 0){
				drawTexture(pew, x + 16, y - 2);
			}
		}
		drawTexture(flames[0], x + 5, y + 17);
		drawTexture(flames[0], x + 10, y + 17);
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
