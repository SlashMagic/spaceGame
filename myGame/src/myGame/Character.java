package myGame;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Character {

	
	static float x = 0;
	static float y = 0;
	
	static float xVel = 0;
	static float yVel = 0;
	
	static int attackTimer = 0;
	
	static int index = 0;
	
	private static Texture textureSpaceShip;
	
	static float getX(){
		return x;
	}
	
	static float getY(){
		return y;
	}
	
	static float getxVel(){
		return xVel;
	}
	
	static float getyVel(){
		return yVel;
	}
	
	static int getAttackTimer(){
		int timer = attackTimer;
		return timer;
	}
	
	static void setAttackTimer(int newTime){
		attackTimer = newTime;
	}
	
	public Character(int newX, int newY) {
		
		x = newX;
		y = newY;
		
		loadData();
		
	}
	
	public static void createProjectile(float newX, float newY, float newXVel, float newYVel){
		
		Projectile newProjectile = new Projectile(newX, newY, newXVel, newYVel);
		Main.createProjectile(newProjectile);
	}
	
	
	void loadData(){
		try{
			
			textureSpaceShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/spaceShip.png"), GL11.GL_NEAREST);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void update(){
		if(Keyboard.isKeyDown(Keyboard.KEY_A) && xVel > -1){
			xVel -= 0.01;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && xVel < 1){
			xVel += 0.01;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && yVel > -1){
			yVel -= 0.01;
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && yVel < 1){
			yVel += 0.01;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			if(xVel > 0){
				xVel -= 0.01;
			}
			if(xVel < 0){
				xVel += 0.01;
			}
			if(yVel > 0){
				yVel -= 0.01;
			}
			if(yVel < 0){
				yVel += 0.01;
			}
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackTimer == 0){
			
			attackTimer = 72;
			
			float newX = 0;
			
			switch(index){
			case 0:
				newX = Character.getX();
				index = 1;
				break;
				
			case 1:
				newX = Character.getX() + 21;
				index = 0;
				break;
		}
			createProjectile(newX, y, xVel, yVel);
		}
		
		if(x < 0 ){
			xVel = 0;
			x = 0;
		}
		
		if(x > Main.getWidth() - 24){
			xVel = 0;
			x = Main.getWidth() - 24;
		}	
			
		if(y < 0){
			yVel = 0;
			y = 0;
		}
		
		if(y > Main.getHeight() - 34){
			yVel = 0;
			y = Main.getHeight() - 34;
		}
		
		x += xVel;
		y += yVel;
	}
	
	/*static float getAngle(){
		float angle = 0;
		
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		
		angle = (float) Math.atan2(mouseY-y, mouseX-x);
		angle = (float) (angle*(180/Math.PI));
		System.out.println(angle);
		return angle;
	}*/
	
	public static void draw() {
		drawTexture(textureSpaceShip);
	}
	
	static void drawTexture(Texture newTexture){
		
		
		
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
