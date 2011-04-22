package gui;

import java.awt.Graphics;

public class PlaybackToolbar extends Toolbar implements Drawable{
	public PlaybackToolbar(DockController dockControl) {
		super(dockControl, Orientation.HORIZONTAL);
	}
	public void drawSelf(Graphics g){
		super.drawSelf(g);
	}
}