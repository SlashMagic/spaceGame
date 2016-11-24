package myGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {

	 Texture textureBackground;
	
	 List<Projectile> projectiles = new ArrayList<Projectile>();
	 
	 List<Enemy> enemies = new ArrayList<Enemy>();
	 
	 float enemyX = 0;
	 float enemyY = 0;
	 
	 
	 
	public World() {
		
		loadData();
		
	}
	
	
	 void loadData(){
		try{
			
			textureBackground = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/spaceBackground.png"), GL11.GL_NEAREST);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	 
	public void createEnemy(){
		
		Random rnd = new Random();
		
		enemyX = rnd.nextInt(Display.getWidth() - 50) + 20;
		enemyY = rnd.nextInt(Display.getHeight() - 50) + 20;
		
		enemies.add(new Enemy(enemyX, enemyY));
	}
	 
	 public  void createProjectile(Projectile newProjectile, float newX, float newY, double newXVel, double newYVel, double newAngle){
		 	
			projectiles.add(new Projectile(newX, newY, newXVel, newYVel , newAngle));
		}
	
	public  void draw( int delta){
		
		drawTexture(textureBackground, 0, 0);
		
		for(int i = 0; i < projectiles.size(); i++){
			 	
				projectiles.get(i).update(delta);
				projectiles.get(i).draw();
		 }
		
		for(int i = 0; i < enemies.size(); i++){
			
			enemies.get(i).update();
			enemies.get(i).draw();                  
			
			for(int j = 0; j < projectiles.size(); j++){
				
				if(projectiles.get(i).x > enemies.get(i).x && projectiles.get(i).x + 5 < enemies.get(i).x + 22 && projectiles.get(i).y > enemies.get(i).y && projectiles.get(i).y + 6 < enemies.get(i).y + 22){
					
					projectiles.remove(i);
					enemies.remove(i);
					
				}
			}
			
			
		}
	}
	
	 void drawTexture(Texture newTexture, int newX, int newY){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(0,0);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(0 + newTexture.getTextureWidth(),0);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(0+newTexture.getTextureWidth(),0+newTexture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(0,0+newTexture.getTextureHeight());	
    		
    	GL11.glEnd();
		
	}

}
