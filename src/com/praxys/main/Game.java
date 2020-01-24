package com.praxys.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.praxys.entities.BulletShoot;
import com.praxys.entities.Enemy_test;
import com.praxys.entities.Entity;
import com.praxys.entities.Player;
import com.praxys.word.Word;

import como.praxys.graficos.Spritesheet;
import como.praxys.graficos.UI;

public class Game extends Canvas implements Runnable, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public boolean isRunning;
	private Thread thread;
	public final static int WIDTH = 240;
	public final static int HEIGHT = 160;
	private final int SCALE = 3;
	
	private BufferedImage image;
	
	public static List<Entity> entitys;
	public static List<BulletShoot> bullets;
	public static List<Enemy_test> enemys;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static Word word;
	
	public static Random rand;
	
	public static String gameState = "GameOver";
	
	public UI ui;
	
	private  int cur_Level = 1, max_Level = 2;
	
	public Game() {
		rand = new Random();
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		requestFocus();
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entitys = new ArrayList<Entity>();
		enemys = new ArrayList<Enemy_test>();
		bullets = new ArrayList<BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,16,16,spritesheet.getImage(32, 0, 16, 16));
		this.addKeyListener(this);
		entitys.add(player);
		word = new Word("/level1.png");
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		
		if(gameState == "Normal") {
			for(int i = 0; i<entitys.size();i++) {
				Entity e = entitys.get(i);
				e.tick();
			}
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			
			if(enemys.size() == 0) {
				//Passando de Nivel
				cur_Level++;
				if(cur_Level > max_Level) {
					cur_Level = 1;
				}
				
				String newWorld = "level"+cur_Level+".png";
				Word.restarGame(newWorld);
			}
		}else if(gameState == "GameOver") {
			//Game Over;
			System.out.println("Game Over!");
		}
	
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Renderização do Jogo
		//Graphics2D g2 = (Graphics2D) g;
		word.render(g);
		for(int i = 0 ;i < entitys.size();i++) {
			Entity e = entitys.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image,0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("ARIAL",Font.BOLD,20));
		g.setColor(Color.WHITE);
		g.drawString("Munição: " + player.ammo, 50, 50);
		if(gameState == "GameOver") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("ARIAL",Font.BOLD,50));
			g2.setColor(Color.RED);
			g.drawString("Você Perdeu",235, HEIGHT*SCALE/2);
			
		}
		bs.show();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.direita = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.esquerda = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.cima = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.baixo = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_J && player.hasGun) {
			player.shooting = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.direita = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.esquerda = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP|| e.getKeyCode() == KeyEvent.VK_W) {
			player.cima = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.baixo = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
