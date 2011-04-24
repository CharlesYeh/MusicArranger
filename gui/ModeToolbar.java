package gui;

import java.awt.Graphics;

public class ModeToolbar extends Toolbar {
	final static int BUTTONS = 3;
	
	ToolbarButton _activeButton;
	
	public ModeToolbar(DockController dockControl) {
		super(dockControl, Orientation.VERTICAL);
		
		createButtons();
		dockControl.dockToLeft(this);
	}
	
	protected void createButtons(){
		// add mode buttons (note, selection, zoom)
		ToolbarButton modeNote = new ToolbarButton("images/mode/mode_note.png");
		ToolbarButton modeChord = new ToolbarButton("images/mode/mode_chord.png");
		ToolbarButton modeZoom = new ToolbarButton("images/mode/mode_zoom.png");
		
		_buttons = new ToolbarButton[]{modeNote, modeChord, modeZoom};
	}
}
