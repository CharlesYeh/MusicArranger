package gui;

import java.awt.Graphics;
import java.awt.FontMetrics;

public class ToolbarLabel extends Drawable {
	String _label;
	
	public ToolbarLabel(String lbl) {
		_label = lbl;
		
		//FontMetrics fm = Graphics.getFontMetrics();
		
		_x = _y = 0;
		//_width = fm.stringWidth(_label);
	}
	
	public void drawSelf(Graphics g) {
		g.drawString(_label, _x, _y);
	}
}