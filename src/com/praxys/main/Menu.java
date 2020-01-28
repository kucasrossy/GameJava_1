package com.praxys.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	private String[] options = {"Novo Jogo", "Carregar Jogo", "Fechar"};
	private String option;
	private int currentOption = 0;
	private int maxOption = options.length - 1;
	
	public boolean up,down,action,isOn;
	
	public Menu() {
		Soud.musicBack.loop();
	}
	
	public void tick() {
		if(up) {
			up = false;
			this.currentOption--;
			if(currentOption<0) {
				currentOption = maxOption;
			}
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption>maxOption) {
				currentOption = 0;
			}
		}
		
		if(action) {
			action = false;
			option = options[currentOption];
			switch(option) {
				case "Novo Jogo":
				case "Carregar Jogo":
					Game.gameState = "Normal";
					Soud.musicBack.stop();
				break;
				case "Fechar":
					System.exit(1);
				break;
			}
		}
	}
	
	public void render(Graphics g) {
	    if(!isOn)
		g.setColor(Color.RED);
	    else
		g.setColor(new Color(0,50,0,100));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("arial",Font.BOLD,35));
		g.drawString(">>StarShooting<<",225, 80);
		g.setFont(new Font("arial",Font.BOLD,30));
		if(!isOn)
		g.drawString("Novo Jogo", 275, 175);
		else
		g.drawString("Continuar", 275, 175);	
		g.drawString("Carregar Jogo", 255, 275);
		g.drawString("Sair", 322, 375);
		
		if(options[currentOption] == "Novo Jogo") {
			g.drawString(">", 250, 175);
		}else if(options[currentOption] == "Carregar Jogo") {
			g.drawString(">", 230, 275);
		}else if(options[currentOption] == "Fechar") {
			g.drawString("> ", 300, 375);
		}
	}
}
