package gui;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Toolbar implements Drawable{
	
	int _x, _y, _width, _height;
	
	public Toolbar(){
		_x = 20;
		_y = 20;
		_width = 100;
		_height = 200;
	}
	
	public void drawSelf(Graphics g){
		System.out.println("HI");
		g.setColor(Color.RED);
		g.fillRect(_x, _y, _width, _height);
	}
}