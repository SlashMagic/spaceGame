package myGame;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class UserInterface {

	Texture healthTexture = null;
	Texture button = null;
	Texture buttonClicked = null;
	Texture mouseCursor = null;
	Texture ship = null;
	Texture enemyShip = null;
	
	boolean forward = false;
	boolean backward = false;
	boolean rotateLeft = false;
	boolean rotateRight = false;
	boolean shoot = false;
	
	float mouseX = 400;
	float mouseY = 300;
	
	float mouseXStep = 0;
	float mouseYStep = 0;
	
	Character newCharacter;
	
	Font newFont;
	
	World gameWorld;
	
	public UserInterface(Font font, Character character, World newWorld) {
		
		newCharacter = character;
		newFont = font;
		gameWorld = newWorld;
		
		Mouse.setGrabbed(true);
		
		loadData();
	}
	
	public void loadData(){
		try{
			healthTexture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/healthBar.png"), GL11.GL_NEAREST);
			button = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/button.png"), GL11.GL_NEAREST);
			mouseCursor = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/cursor.png"), GL11.GL_NEAREST);
			buttonClicked = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/UI/buttonClicked.png"), GL11.GL_NEAREST);
			ship = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/spaceShip.png"), GL11.GL_NEAREST);
			enemyShip = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/enemyShip_0.png"), GL11.GL_NEAREST);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		
		
		if(!gameWorld.mainMenu && !gameWorld.tutorial && !gameWorld.options){
			drawUI();
			
		}
		if(gameWorld.mainMenu){
			drawMainMenu();
			drawMouse();
		}
		
		if(gameWorld.tutorial){
			drawTutorial();
			drawMouse();
		}
		
		if(gameWorld.options){
			drawOptions();
			drawMouse();
		}
	}
	
	public void drawUI(){
		
		drawTexture(button, 8, 8, 408, 48);
		drawTexture(healthTexture, 10, 10, newCharacter.health * 2, 40);
		newFont.drawString("Health", 10, 10, 6);
		drawTexture(button, 696, 8, 200, 48);
		newFont.drawString("Score:" + String.valueOf(gameWorld.score), 696, 8, 7);
	}
	
	public void drawMouse(){
		
		mouseXStep = Mouse.getDX() * 0.3f;
		mouseYStep = Mouse.getDY() * 0.3f;
		
		mouseX += mouseXStep;
		mouseY += -mouseYStep;
		
		if(mouseX < 0){
			mouseX = 0;
		}
		if(mouseY < 0){
			mouseY = 0;
		}
		if(mouseX > 803){
			mouseX = 803;
		}
		if(mouseY > 603){
			mouseY = 603;
		}
		
		drawTexture(mouseCursor, mouseX, mouseY, 12, 12);
		
	}
	
	public void drawMainMenu(){
		
		drawTexture(button, 200, 20, 800, 200);
		newFont.drawString("Space Game", 200, 20, 28);
		
		drawTexture(button, 150, 300, 400, 100);
		newFont.drawString(" New Game", 150, 300, 14);
		
		drawTexture(button, 450, 300, 400, 100);
		newFont.drawString(" Tutorial", 450, 300, 14);
		
		drawTexture(button, 300, 400, 400, 100);
		newFont.drawString(" Options", 307, 400, 14);
		
		drawTexture(button, 300, 500, 400, 100);
		newFont.drawString("   Exit", 300, 500, 14);
		
		drawTexture(ship, 375, 190, 72, 136);
		
		if(Mouse.isButtonDown(0) && mouseX > 300 && mouseY > 400 && mouseX < 500 && mouseY < 450){
			gameWorld.mainMenu = false;
			gameWorld.options = true;
		}
		
		if(Mouse.isButtonDown(0) && mouseX > 150 && mouseY > 300 && mouseX < 350 && mouseY < 350){
			//drawTexture(buttonClicked, 150, 400, 400, 100);
			newCharacter.health = 200;
			gameWorld.score = 0;
			newCharacter.xVel = 0;
			newCharacter.yVel = 0;
			newCharacter.x = 390;
			newCharacter.y = 285;
			newCharacter.speed = 0;
			newCharacter.shipAngle = 179;
			gameWorld.enemies_1.clear();
			gameWorld.enemies_2.clear();
			gameWorld.enemies_3.clear();
			gameWorld.enemyProjectiles.clear();
			gameWorld.projectiles.clear();
			gameWorld.mainMenu = false;
		}
		
		if(Mouse.isButtonDown(0) && mouseX > 450 && mouseY > 300 && mouseX < 650 && mouseY < 350){
			gameWorld.tutorial = true;
			gameWorld.mainMenu = false;
		}
		
		if(Mouse.isButtonDown(0) && mouseX > 300 && mouseY > 500 && mouseX < 500 && mouseY < 550){
			System.exit(0);
		}
	}
	
	public void drawTutorial(){
		
		drawTexture(button, 100, 500, 400, 100);
		newFont.drawString("Main Menu", 110, 500, 14);
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 500 && mouseX < 300 && mouseY < 550){
			gameWorld.tutorial = false;
			gameWorld.mainMenu = true;
		}
		
		drawTexture(button, 100, 100, 1200, 700);
		
		newFont.drawString("this is your space ship.", 130, 110, 7);
		drawTexture(ship, 100, 100, 40, 68);
		
		newFont.drawString("use ^w^ and ^s^ to increase and decrease your speed.", 100, 140, 7);
		
		newFont.drawString("use ^A^ and ^D^ to turn your ship left and right.", 100, 160, 7);
		
		newFont.drawString("These are enemies, they will try to shoot you.", 130, 190, 7);
		drawTexture(enemyShip, 100, 187, 40, 44);
		
		newFont.drawString("Their shots will damage your health bar.", 100, 220, 7);
		
		newFont.drawString("When your health reaches zero, you will lose the game.", 100, 240, 7);
		
		newFont.drawString("you can shoot back by using ^space^.", 100, 280, 7);
		
		newFont.drawString("Killing enemies will increase your score.", 100, 300, 7);
		
		newFont.drawString("Use ^Escape^ to return to the main menu.", 100, 340, 7);
		
		newFont.drawString("All Keys can be rebound in the option menu.", 100, 360, 7);
		
		newFont.drawString("Try to survive as long as possible, good luck!", 100, 400, 7);
	}
	
	public void drawOptions(){
		
		drawTexture(button, 100, 500, 400, 100);
		newFont.drawString("Main Menu", 110, 500, 14);
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 500 && mouseX < 300 && mouseY < 550){
			gameWorld.options = false;
			gameWorld.mainMenu = true;
		}
		
		drawTexture(button, 100, 50, 500, 100);
		newFont.drawString("Forward", 100, 50, 14);
		
		drawTexture(button, 100, 125, 500, 100);
		newFont.drawString("backward", 100, 125, 14);
		
		drawTexture(button, 100, 200, 500, 100);
		newFont.drawString("Rotate Left", 100, 200, 14);
		
		drawTexture(button, 100, 275, 500, 100);
		newFont.drawString("Rotate Right", 100, 275, 14);
		
		drawTexture(button, 100, 350, 500, 100);
		newFont.drawString("Shoot", 100, 350, 14);
		
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 50 && mouseX < 350 && mouseY < 150){
			forward = true;
		}
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 125 && mouseX < 350 && mouseY < 175){
			backward = true;
		}
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 200 && mouseX < 350 && mouseY < 250){
			rotateLeft = true;
		}
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 275 && mouseX < 350 && mouseY < 325){
			rotateRight = true;
		}
		if(Mouse.isButtonDown(0) && mouseX > 100 && mouseY > 350 && mouseX < 350 && mouseY < 400){
			shoot  = true;
		}
		
		if(forward){
			drawTexture(button, 375, 50, 800, 1000);
			newFont.drawString("press a key to rebind forward:", 375, 50, 7);
			for(int i = 0; i < Keyboard.getKeyCount(); i++){
				if(Keyboard.isKeyDown(i)){
					newCharacter.forward = i;
					System.out.println(i);
					forward = false;
				}
			}
		}
		if(backward){
			drawTexture(button, 375, 50, 800, 1000);
			newFont.drawString("press a key to rebind backward:", 375, 50, 7);
			for(int i = 0; i < Keyboard.getKeyCount(); i++){
				if(Keyboard.isKeyDown(i)){
					newCharacter.backward = i;
					System.out.println(i);
					backward = false;
				}
			}
		}
		if(rotateLeft){
			drawTexture(button, 375, 50, 800, 1000);
			newFont.drawString("press a key to rebind rotate left:", 375, 50, 7);
			for(int i = 0; i < Keyboard.getKeyCount(); i++){
				if(Keyboard.isKeyDown(i)){
					newCharacter.rotateLeft = i;
					System.out.println(i);
					rotateLeft = false;
				}
			}
		}
		if(rotateRight){
			drawTexture(button, 375, 50, 800, 1000);
			newFont.drawString("press a key to rebind rotate right:", 375, 50, 7);
			for(int i = 0; i < Keyboard.getKeyCount(); i++){
				if(Keyboard.isKeyDown(i)){
					newCharacter.rotateRight = i;
					System.out.println(i);
					rotateRight = false;
				}
			}
		}
		if(shoot){
			drawTexture(button, 375, 50, 800, 1000);
			newFont.drawString("press a key to rebind shoot:", 375, 50, 7);
			for(int i = 0; i < Keyboard.getKeyCount(); i++){
				if(Keyboard.isKeyDown(i)){
					newCharacter.shoot = i;
					System.out.println(i);
					shoot = false;
				}
			}
		}
	}
	
	public void drawTexture(Texture newTexture, float newX, float newY, int width, int height){
		
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
