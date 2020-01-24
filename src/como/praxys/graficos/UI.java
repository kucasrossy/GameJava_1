package como.praxys.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.praxys.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(7, 4, 70, 5);
		g.setColor(Color.green);
		g.fillRect(7, 4, (int) ((Game.player.vida/Game.player.maxVida)*70), 5);
		g.setColor(Color.WHITE);
		Font f = new Font("Arial",Font.BOLD,7);
		g.setFont(f);
		g.drawString("Vida: " + Game.player.vida , 7, 9);
	}
}
