package com.praxys.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.praxys.main.Game;
import com.praxys.main.Soud;
import com.praxys.word.Camera;
import com.praxys.word.Word;

public class Enemy_test extends Entity {

	private int vel = 1;
	private BufferedImage[] sprites;
	private int frame = 0, maxFrame = 5, index = 0, maxIndex = 1;
	
	private int vida = 5;
	
	public boolean isDamaged;
	
	public Enemy_test(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getImage(7*16, 16, 16, 16);
		sprites[1] = Game.spritesheet.getImage(8*16, 16, 16, 16);
	}
	
	public void tick() {
		isDamaged = false;
		
		if(!isColiddingPlayer()) {
			if(Game.rand.nextInt(100) < 50) {
				if( x > Game.player.getX() && Word.isFree(getX() - vel, getY())) {
					x-= vel;
				}else if(x<Game.player.getX() && Word.isFree(getX() + vel, getY())) {
					x+= vel;
				}
				
				if(y < Game.player.getY() && Word.isFree(getX(), getY() + vel)) {
					y+= vel;
				}else if( y > Game.player.getY() && Word.isFree(getX(), getY() - vel)) {
					y-= vel;
				}
			}
		}else {
			//Colisão com o player
			if(Game.rand.nextInt(100)  < 5) {
				Soud.musicHurt.play();
				Game.player.vida-= Game.rand.nextInt(5);
				Game.player.isDamaged = true;
			}
		}
		
		frame++;	
		if(frame > maxFrame) {
			frame = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		isColiddingBullets();
		
		if(vida<=0) {
			Game.entitys.remove(this);
			Game.enemys.remove(this);
			return;
		}
	}
	
	public void isColiddingBullets() {
		for(int i = 0; i<Game.bullets.size();i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(isColidding(this, e)) {
					vida-= Game.player.dano;
					this.isDamaged = true;
					Game.bullets.remove(e);
					return;
				}
			}
		}
	}
	
	public boolean isColiddingPlayer() {
		Rectangle enemy = new Rectangle(this.getX(), this.getY(), 16,16);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		if(enemy.intersects(player) && this.z == Game.player.z) {
			return true;
		}
		
		
		return false;
	}
	
	public void render(Graphics g) {
		//super.render(g);
		if(!isDamaged) {
			g.drawImage(sprites[index],getX() - Camera.x,getY() - Camera.y,16,16,null);
		}else {
			g.drawImage(Entity.enemy_Dano,getX() - Camera.x, getY() - Camera.y,null);
		}
		
	}

}
