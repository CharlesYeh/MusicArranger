package gui;

import java.awt.Graphics;

public class ModeToolbar extends Toolbar implements Drawable{
	final static int BUTTONS = 3;
	
	ToolbarButton[] _buttons
	
	public ModeToolbar(DockController dockControl) {
		super(dockControl, Orientation.VERTICAL);
		
		
		// add mode buttons (note, selection, zoom)
		ToolbarButton modeNote = new ToolbarButton("images/mode_note.png");
		ToolbarButton modeChord = new ToolbarButton("images/mode_chord.png");
		ToolbarButton modeZoom = new ToolbarButton("images/mode_zoom.png");
		
		buttons = {modeNote, modeChord, modeZoom};
	}
	public void drawSelf(Graphics g) {
		super.drawSelf(g);
		
		
	}
}
