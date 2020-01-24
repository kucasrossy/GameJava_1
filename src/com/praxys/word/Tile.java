package com.praxys.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.praxys.main.Game;

public class Tile {
	
	public static BufferedImage tile_Florr = Game.spritesheet.getImage(0, 0, 16, 16);
	public static BufferedImage tile_Wall = Game.spritesheet.getImage(16, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x,y - Camera.y,null);
		//System.out.println("Testando");
	}
}
