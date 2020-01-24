package com.praxys.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.praxys.main.Game;
import com.praxys.word.Camera;
import com.praxys.word.Word;

public class Player extends Entity{

	public boolean direita;
	public boolean esquerda;
	public boolean cima;
	public boolean baixo;
	private int vel = 2;
	private int dir_direita = 1, dir_esquerda = 0;
	private int dir = dir_direita;
	
	private BufferedImage[] spriteDir;
	private BufferedImage[] spriteEsq;
	
	private BufferedImage damage;
	
	private boolean moved;
	private int frame=0,maxFrame = 5, index=0, maxIndex = 3;
	
	public double vida = 100, maxVida = 100;
	
	public int ammo = 0;
	
	public int dano = 1;
	
	public boolean isDamaged;
	public boolean hasGun;
	public boolean shooting;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spriteDir = new BufferedImage[4];
		spriteEsq = new BufferedImage[4];
		
		damage = Game.spritesheet.getImage(0, 16, 16, 16);
		
		for(int i = 0; i<4; i++) {
			spriteDir[i] = Game.spritesheet.getImage(32 + (i*16),0, 16, 16);
			spriteEsq[i] = Game.spritesheet.getImage(32+(i*16),16, 16, 16);
		}
	}
	
	public void tick() {
		moved = false;
		isDamaged = false;
		
		if(direita && Word.isFree(x+vel,y)) {
			moved = true;
			dir = dir_direita;
			x+=vel;
		}
		
		if(esquerda && Word.isFree(x-vel, y)) {
			moved = true;
			dir = dir_esquerda;
			x-=vel;
		}
		
		if(cima && Word.isFree(x, y-vel)) {
			y-=vel;
		}
		
		if(baixo && Word.isFree(x, y+vel)) {
			y+= vel;
		}
		
		if(moved) {
			frame++;
			if(frame > maxFrame) {
				frame = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		if(vida<=0) {
			//Game Over
			//Word.restarGame("level1.png");
			Game.gameState = "GameOver";
		}
		
		if(shooting) {
			//Criar Balas
			shooting = false;
			if(ammo > 0) {
				ammo--;
				int dx = 0;
				int px = 0;
				int py = 0;
				if(dir == this.dir_direita) {
					px = 8;
					py = 7;
					dx = 1;
				}else {
					px = -8;
					py = 7;
					dx = -1;
				}
				BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
				Game.bullets.add(bullet);
				System.out.println("Atirando!");
			}
		}
		
		checkItem();
		
		Camera.x = Camera.clamp(getX() - (Game.WIDTH/2), 0, (Word.WIDTH*16 - Game.WIDTH));
		Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2), 0, (Word.HEIGHT*16 - Game.HEIGHT));
	} 
	
	public void checkItem() {
		for(int i = 0; i<Game.entitys.size();i++) {
			Entity atual = Game.entitys.get(i);
			if(atual instanceof LifePack) {
				if(isColidding(this, atual)) {
					vida += 10;
					if(vida>100) {
						vida = 100;
					}
					Game.entitys.remove(atual);
				}
			}else if(atual instanceof Bullet) {
				if(isColidding(this,atual)) {
					ammo+= 10;
					System.out.println("Balas: " + ammo);
					Game.entitys.remove(atual);
				}
			}else if(atual instanceof Weapon) {
				if(isColidding(this,atual)) {
					if(hasGun) {
						Game.player.dano *= 2;
						Game.entitys.remove(atual);
					}else {
						hasGun = true;
						Game.entitys.remove(atual);
					}
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == dir_direita) {
				g.drawImage(spriteDir[index], getX() - Camera.x, getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.gun_Right,getX()+8 - Camera.x,getY() - Camera.y, null);
				}
			}else if(dir == dir_esquerda) {
				g.drawImage(spriteEsq[index], getX() - Camera.x, getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.gun_Left,getX()-8 - Camera.x,getY() - Camera.y, null);
				}
			}
		}else {
			g.drawImage(damage,getX() - Camera.x,getY() - Camera.y,null);
		}
	}

}
