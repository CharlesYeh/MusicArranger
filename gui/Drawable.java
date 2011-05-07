package gui;

import java.awt.Point;
import java.awt.Graphics;

public abstract class Drawable{
	int _x, _y, _width, _height;
	
	public abstract void drawSelf(Graphics g);
	
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
	
	/*
	 * Returns whether a given point is within the toolbar
	 */
	public boolean hitTestPoint(int cx, int cy) {
		return _x < cx && cx < _x + _width &&
				 _y < cy && cy < _y + _height;
	}
	
	/*
	 * Returns whether a given point is within the toolbar
	 */
	public boolean hitTestPoint(double cx, double cy) {
		return hitTestPoint((int) cx, (int) cy);
	}
	
	public boolean hitTestPoint(Point p) {
		return hitTestPoint((int) p.getX(), (int) p.getY());
	}
}
