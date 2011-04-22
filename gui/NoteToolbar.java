package gui;

import java.awt.Graphics;

public class NoteToolbar extends Toolbar implements Drawable{
	public NoteToolbar(DockController dockControl) {
		super(dockControl, Orientation.HORIZONTAL);
		
		
	}
	public void drawSelf(Graphics g){
		super.drawSelf(g);
	}
}