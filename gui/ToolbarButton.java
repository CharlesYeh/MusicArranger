package gui;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import instructions.*;

public class ToolbarButton extends Drawable {
	final static int PADDING = 1;
	
	static BufferedImage IMG_OUT, IMG_OVER;
	
	BufferedImage _icon;
	Instruction _instruction;
	
	public static void init(String btnOut, String btnOver) {
		try{
			IMG_OUT = ImageIO.read(new File(btnOut));
			IMG_OVER = ImageIO.read(new File(btnOver));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
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
	
	public void setInstruction(Instruction instr) {
		_instruction = instr;
	}
	
	public Instruction getInstruction() {
		return _instruction;
	}
	
	public void drawSelf(Graphics g) {
		//g.drawImage(IMG_OUT, _x, _y, null);
		g.drawImage(_icon, _x, _y, null);
	}
	
}