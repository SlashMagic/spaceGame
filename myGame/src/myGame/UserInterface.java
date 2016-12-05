package myGame;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class UserInterface {

	Texture healthTexture = null;
	Texture button = null;
	
	Character newCharacter;
	
	Font newFont;
	
	World gameWorld;
	
	public UserInterface(Font font, Character character, World newWorld) {
		
		newCharacter = character;
		newFont = font;
		gameWorld = newWorld;
		
		loadData();
	}
	
	public void loadData(){
		try{
			healthTexture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/healthBar.png"), GL11.GL_NEAREST);
			button = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/button.png"), GL11.GL_NEAREST);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		
		
		if(!gameWorld.mainMenu){
			drawUI();
		}
		if(gameWorld.mainMenu){
			drawMainMenu();
		}
	}
	
	public void drawUI(){
		
		drawTexture(button, 8, 8, 408, 48);
		drawTexture(healthTexture, 10, 10, newCharacter.health * 2, 40);
		newFont.drawString("Health", 10, 10, 6);
	}
	
	public void drawMainMenu(){
		
		drawTexture(button, 200, 20, 800, 200);
		newFont.drawString("Space Game", 200, 20, 28);
		
		drawTexture(button, 150, 400, 400, 100);
		newFont.drawString(" New Game", 150, 400, 14);
		
		drawTexture(button, 450, 400, 400, 100);
		newFont.drawString(" Tutorial", 450, 400, 14);
		
		drawTexture(button, 300, 500, 400, 100);
		newFont.drawString("   Exit", 300, 500, 14);
		
		if(Mouse.isButtonDown(0) && Mouse.getX() > 360 && Mouse.getX() < 840 && Mouse.getY() > 265  && Mouse.getY() < 360){
			gameWorld.mainMenu = false;
		}
		
		if(Mouse.isButtonDown(0) && Mouse.getX() > 715 && Mouse.getX() < 1205 && Mouse.getY() > 88 && Mouse.getY() < 180){
			System.exit(0);
		}
	}
	
	public void drawTexture(Texture newTexture, int newX, int newY, int width, int height){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(newX,newY);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(newX + width, newY);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(newX + width, newY + height);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(newX, newY + height);	
	GL11.glEnd();
		
	}

}
