package gui;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;

public abstract class Toolbar implements Drawable{
	
	DockController _dockControl;
	Orientation _orientation;
	
	int _x, _y, _width, _height;
	
	boolean _drag;
	int _dragX, _dragY;
	
	Color myColor;
	
	public Toolbar(DockController dockControl, Orientation orient) {
		_dockControl = dockControl;
		_orientation = orient;
		
		_x = (int) (Math.random() * 300);
		_y = 20;
		_width = 100;
		_height = 30;
		
		_drag = false;
		_dragX = _dragY = 0;
		
		// JUST FOR TESTING
		myColor = new Color((int)(Math.random() * 0xFFFFFF));
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public int getWidth() {
		return _width;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setX(int ox) {
		_x = ox;
	}
	
	public void setY(int oy) {
		_y = oy;
	}
	
	public void setOrientation(Orientation or) {
		_orientation = or;
		if (_orientation == Orientation.HORIZONTAL) {
			_width = 300;
			_height = 30;
		}
		else{
			_width = 30;
			_height = 300;
		}
	}
	
	public void drawSelf(Graphics g) {
		g.setColor(myColor);
		g.fillRect(_x, _y, _width, _height);
	}
	
	/*
	 * Returns whether a given point is within the toolbar
	 */
	public boolean hitTestPoint(int cx, int cy) {
		return _x < cx && cx < _x + _width &&
				 _y < cy && cy < _y + _height;
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	/* Begin dragging and undock
	 *
	 */
	public void mousePressed(MouseEvent e) {
		_drag = true;
		
		_dragX = e.getX() - _x;
		_dragY = e.getY() - _y;
		
		_dockControl.unDock(this);
	}
	
	/* Stop dragging and check dockings
	 *
	 */
	public void mouseReleased(MouseEvent e) {
		_drag = false;
		
		// check docking to a side
		if (_y < 0) {
			_dockControl.dockToTop(this);
		}
		else if (_x < 0) {
			_dockControl.dockToLeft(this);
		}
	}
	
	/* Drag this toolbar
	 *
	 */
	public void mouseDragged(MouseEvent e) {
		if (!_drag) return;
		
		// if being dragged, move toolbar
		_x = e.getX() - _dragX;
		_y = e.getY() - _dragY;
	}
}