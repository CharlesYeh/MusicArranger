package gui;

import java.awt.Graphics;

public class ModeToolbar extends Toolbar implements Drawable{
	public ModeToolbar(DockController dockControl) {
		super(dockControl, Orientation.VERTICAL);
		
		// add mode buttons
	}
	public void drawSelf(Graphics g){
		super.drawSelf(g);
	}
}