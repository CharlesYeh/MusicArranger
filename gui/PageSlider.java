package gui;

import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

import java.awt.Color;
import java.awt.Graphics;
import arranger.ArrangerConstants;

public class PageSlider extends Drawable {
	final int SLIDER_WIDTH = 20;
	final int SLIDER_HEIGHT = 40;
	
	final static String SLIDER_IMG = "images/gui/slider.png";
	Image _icon;
	
	public PageSlider() {
		try {
			_icon = ImageIO.read(new File(SLIDER_IMG));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
		
		_x = ArrangerConstants.WINDOW_WIDTH - _width;
		_y = 0;
		_width = SLIDER_WIDTH;
		_height = SLIDER_HEIGHT;
	}
	
	public void drawSelf(Graphics g) {
		// draw slider on top
		_x = ArrangerConstants.WINDOW_WIDTH - _width;
		
		g.setColor(Color.GRAY);
		g.fillRect(_x, 0, _width, ArrangerConstants.WINDOW_HEIGHT);
		//g.setColor(Color.BLUE);
		//g.fillRect(_x, _y, _width, _height);
		g.drawImage(_icon, _x, _y, null);
	}
	
	public void setY(int oy) {
		// bound checking
		oy = Math.min(Math.max(oy, 0), ArrangerConstants.WINDOW_HEIGHT - _height);
		
		super.setY(oy);
	}
	
	public double getSlidePercent() {
		return (double) _y / (ArrangerConstants.WINDOW_HEIGHT - SLIDER_HEIGHT);
	}
}
