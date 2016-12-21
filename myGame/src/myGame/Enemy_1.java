package myGame;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy_1 extends Enemy {

	public Enemy_1(World newWorld, Character newCharacter, float newX, float newY) {
		super(newWorld, newCharacter, newX, newY);
		loadDData();
		loadData();
	}
	
	public void loadDData(){
		try{
			enemySpaceShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/enemyShip_0.png"), GL11.GL_NEAREST);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(int delta){
		
		desiredAngle = Math.toDegrees(Math.atan2((x + 11) - (character.getX() + 11), (y + 11) - (character.getY() + 17)));
		
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
			
				if(attackTimer >= 1000){
					
					attackTimer = 0;
					
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
					gameWorld.enemies_1.remove(this);
					playSound(hitSound);
					
					gameWorld.projectiles.remove(j);
					gameWorld.score += 10;
				}
			
		}
			if(attackTimer < 1000){
				attackTimer += delta;
			}
			
	}
	
	public void draw(){
		
		if(attackTimer < 50){
			if(index == 0){
				drawTexture(pew, x - 1, y - 2);
			}
			else if(index == 1){
				drawTexture(pew, x + 16, y - 2);
			}
		}
		drawTexture(flames[0], x + 5, y + 17);
		drawTexture(flames[0], x + 10, y + 17);
		drawTexture(enemySpaceShip);
		
	}

}
