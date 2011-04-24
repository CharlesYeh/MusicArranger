package gui;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import logic.Instruction;

public class ToolbarButton extends Drawable {
	final static int PADDING = 1;
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
		_width = _icon.getWidth();
		_height = _icon.getHeight();
	}  
	
	public Instruction getInstruction() {
		return new Instruction(this);
	}
	
	public void drawSelf(Graphics g) {
		g.drawImage(_icon, _x, _y, null);
	}
	
}