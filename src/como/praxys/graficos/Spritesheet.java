package como.praxys.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spriteSheet;
	
	public Spritesheet(String path) {
		try {
			spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width,height);
	}
	
}
