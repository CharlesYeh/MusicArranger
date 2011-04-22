package gui;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ToolbarButton implements Drawable {
	BufferedImage icon;
	
	public ToolbarButton(String iconFile) {
		try {
			icon = ImageIO.read(new File(iconFile));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
	}
	
	public void drawSelf(Graphics g) {
		
	}
	
}