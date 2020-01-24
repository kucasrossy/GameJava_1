package com.praxys.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.praxys.entities.Bullet;
import com.praxys.entities.Enemy_test;
import com.praxys.entities.Entity;
import com.praxys.entities.LifePack;
import com.praxys.entities.Player;
import com.praxys.entities.Weapon;
import com.praxys.main.Game;

import como.praxys.graficos.Spritesheet;

public class Word {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static int Tile_Size = 16;
	
	public Word(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
				
					switch(pixelAtual) {
						case 0xFF000000:
							//Chão
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
						break;
						case 0xFFFFFFFF:
							//Parede
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.tile_Wall);
						break;
						case 0xFF0026FF:
							//Player
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
							Game.player.setX(xx * 16);
							Game.player.setY(yy * 16);
						break;
						case 0xFFFF0000:
							//Enemy
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
							Enemy_test en = new Enemy_test(xx*16,yy*16,16,16,Entity.enemy_En);
							Game.enemys.add(en);
							Game.entitys.add(en);
						break;
						case 0xFFFF6A00:
							//Weapon
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
							Game.entitys.add(new Weapon(xx*16,yy*16,16,16,Entity.weapon_En));
						break;
						case 0XFFFF7F7F:
							//Life Pack
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
							LifePack pack = new LifePack(xx*16,yy*16,16,16,Entity.life_Pack);
							pack.setMask(4, 4, 8, 8);
							Game.entitys.add(pack);
						break;
						case 0xFFFFD800:
						//Bullet
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
							Bullet b = new Bullet(xx*16,yy*16,16,16,Entity.bullet_en);
							b.setMask(6, 4, 4, 12);
							Game.entitys.add(b);
						default:
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.tile_Florr);
						break;
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		
		int x1 = xNext/Tile_Size;
		int y1 = yNext/Tile_Size;
		
		int x2 = (xNext + Tile_Size-1)/Tile_Size;
		int y2 = yNext/Tile_Size;
		
		int x3 = xNext/Tile_Size;
		int y3 = (yNext+Tile_Size-1)/Tile_Size;
		
		int x4 = (xNext + Tile_Size-1)/Tile_Size;
		int y4 = (yNext+Tile_Size-1)/Tile_Size;
		
		return !(tiles[x1 + (y1 * Word.WIDTH)] instanceof WallTile ||
				 tiles[x2 + (y2 * Word.WIDTH)] instanceof WallTile ||
				 tiles[x3 + (y3 * Word.WIDTH)] instanceof WallTile ||
				 tiles[x4 + (y4 * Word.WIDTH)] instanceof WallTile);
	}
	
	public static void restarGame(String word) {
		Game.entitys.clear();
		Game.enemys.clear();
		Game.entitys = new ArrayList<Entity>();
		Game.enemys = new ArrayList<Enemy_test>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getImage(32, 0,16,16));
		Game.entitys.add(Game.player);
		Game.word = new Word("/"+word);
		return;
	}
	
	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int xx = xStart; xx<=xFinal; xx++) {
			for(int yy = yStart; yy<=yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * HEIGHT)];
				tile.render(g);
			}
		}
	}
}
