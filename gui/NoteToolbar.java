package gui;

import java.awt.Graphics;

public class NoteToolbar extends Toolbar implements Drawable{
	ToolbarButton _activeButton;
	
	public NoteToolbar(DockController dockControl) {
		super(dockControl, Orientation.HORIZONTAL);
		
		
	}
	
	protected void createButtons(){
		_buttons = new ToolbarButton[]{};
	}
	
	public void drawSelf(Graphics g){
		super.drawSelf(g);
	}
}