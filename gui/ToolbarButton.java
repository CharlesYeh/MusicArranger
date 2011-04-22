package gui;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ToolbarButton implements Drawable {
	int _x, _y, _width, _height;
	
	BufferedImage _icon;
	
	public ToolbarButton(String iconFile) {
		this(iconFile, 0, 0);
	}
	public ToolbarButton(String iconFile, int cx, int cy) {
		try {
			_icon = ImageIO.read(new File(iconFile));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
		
		_x = cx;
		_y = cy;
	}  
	
	public void setX(int cx) {
		_x = cx;
	}
	public void setY(int cy) {
		_y = cy;
	}
	
	public void drawSelf(Graphics g) {
		g.drawImage(_icon, _x, _y, null);
	}
	
}