package gui;

import java.awt.Graphics;

public interface Drawable{
	 public void drawSelf(Graphics g);
	 public boolean hitTestPoint(int cx, int cy);
}
