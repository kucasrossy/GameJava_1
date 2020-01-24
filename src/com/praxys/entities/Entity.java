package com.praxys.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.praxys.main.Game;
import com.praxys.word.Camera;

public class Entity {
	
	public static BufferedImage life_Pack = Game.spritesheet.getImage(6*16, 0, 16, 16);
	public static BufferedImage weapon_En = Game.spritesheet.getImage(7*16, 0, 16, 16);
	public static BufferedImage bullet_en = Game.spritesheet.getImage(6*16, 16, 16, 16);
	public static BufferedImage enemy_En = Game.spritesheet.getImage(7*16, 16, 16, 16);
	public static BufferedImage enemy_Dano = Game.spritesheet.getImage(9*16, 16, 16, 16);
	public static BufferedImage gun_Left = Game.spritesheet.getImage(9*16, 0, 16, 16);
	public static BufferedImage gun_Right = Game.spritesheet.getImage(8*16, 0, 16, 16);
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	private int maskX,maskY,maskW,maskH;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.maskW = width;
		this.maskH = height;
	}
	
	public void setMask(int newX, int newY, int newW, int newH) {
		this.maskX = newX;
		this.maskY = newY;
		this.maskW = newW;
		this.maskH = newH;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	protected int getX() {
		return x;
	}
	
	protected int getY() {
		return y;
	}
	
	protected int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
	
	public boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX,e1.getY()+e1.maskY,e1.maskW,e1.maskH);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX,e2.getY()+e2.maskY,e2.maskW,e2.maskH);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		/*g.setColor(Color.RED);
		g.fillRect(getX() + maskX - Camera.x, getY() + maskY - Camera.y,maskW,maskH);*/
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
	}
}
