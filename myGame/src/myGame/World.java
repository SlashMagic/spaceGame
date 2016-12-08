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
	 
	 List<Projectile> enemyProjectiles = new ArrayList<Projectile>();
	 
	 List<Enemy> enemies = new ArrayList<Enemy>();
	 
	 World gameWorld;
	 
	 float enemyX = 0;
	 float enemyY = 0;
	 
	 Font newFont;
	 
	 int enemyQuad = 0;
	 
	 int enemiesToSpawn = 0;
	 
	 int score = 0;
	 
	 boolean mainMenu = true;
	 
	 boolean tutorial = false;
	 
	 boolean options = false;
	 
	public World(Font font) {
		newFont = font;
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
	
	public void createEnemy(Character newCharacter){
		
		Random rnd = new Random();
		
		enemyQuad = rnd.nextInt(4) + 1;
		if(enemyQuad == 1){
			enemyX = -40;
			enemyY = rnd.nextInt(550) + 20;
		}
		if(enemyQuad == 2){
			enemyX = rnd.nextInt(750) + 20;
			enemyY = -40;
		}
		if(enemyQuad == 3){
			enemyX = 840;
			enemyY = rnd.nextInt(550) + 20;
		}
		if(enemyQuad == 4){
			enemyX = rnd.nextInt(750) + 20;
			enemyY = 640;
		}
		
		enemies.add(new Enemy(this, newCharacter , enemyX, enemyY));
	}
	 
	 public  void createProjectile(Projectile newProjectile, float newX, float newY, double newXVel, double newYVel, double newAngle){
		 	
			projectiles.add(new Projectile(0, newX, newY, newXVel, newYVel , newAngle));
		}
	 
	 public void createEnemyProjectile(Projectile newProjectile, float newX, float newY, double newXVel, double newYVel, double newAngle){
		 
		 enemyProjectiles.add(new Projectile(1, newX, newY, newXVel, newYVel, newAngle));
		 
	 }
	
	public  void update( int delta){
		
		for(int i = 0; i < projectiles.size(); i++){
				
				projectiles.get(i).update(delta);
				
				projectiles.get(i).draw();
				
				if(projectiles.get(i).x < 0 || projectiles.get(i).y < 0 || projectiles.get(i).x > Display.getWidth() || projectiles.get(i).y > Display.getHeight()){
					projectiles.remove(i);
				}
		 }
		
		for(int i = 0; i < enemyProjectiles.size(); i++){
			
			enemyProjectiles.get(i).update(delta);
			
			enemyProjectiles.get(i).draw();
			
			if(enemyProjectiles.get(i).x < 0 || enemyProjectiles.get(i).x > Display.getWidth() || enemyProjectiles.get(i).y < 0 || enemyProjectiles.get(i).y > Display.getHeight()){
				enemyProjectiles.remove(i);
			}
			
		}
		
		for(int i = 0; i < enemies.size(); i++){
			
			enemies.get(i).update();
				                  
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(); 
		}
		
	}
	
	public void draw(){
		drawTexture(textureBackground, 0, 0);
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
