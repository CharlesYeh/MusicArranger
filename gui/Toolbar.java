package gui;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;

public abstract class Toolbar implements Drawable{
	
	int _x, _y, _width, _height;
	
	boolean _drag;
	int _dragX, _dragY;
	
	public Toolbar(){
		_x = 20;
		_y = 20;
		_width = 100;
		_height = 200;
		
		_drag = false;
		_dragX = _dragY = 0;
	}
	
	public void drawSelf(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(_x, _y, _width, _height);
	}
	
	public boolean hitTestPoint(int cx, int cy) {
		return _x < cx && cx < _x + _width &&
				 _y < cy && cy < _y + _height;
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		_drag = true;
		
		_dragX = e.getX() - _x;
		_dragY = e.getY() - _y;
	}
	
	public void mouseReleased(MouseEvent e) {
		_drag = false;
	}
	
	public void mouseDragged(MouseEvent e) {
		if (!_drag) return;
		
		// if being dragged, move toolbar
		_x = e.getX() - _dragX;
		_y = e.getY() - _dragY;
	}
}