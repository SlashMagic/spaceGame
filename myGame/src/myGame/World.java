package myGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {

	 Texture textureBackground;
	
	 List<Projectile> projectiles = new ArrayList<Projectile>();
	 
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
	 
	 public void update(){
		 for(int i = 0; i < projectiles.size(); i++){
			 System.out.println("NO!");
				projectiles.get(i).update();
				projectiles.get(i).draw();
		 }
	 }
	 
	 
	 public  void createProjectile(Projectile newProjectile){
		 	
			projectiles.add(new Projectile(100, 500, 1, 1));
		}
	
	public  void draw(){
		
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
