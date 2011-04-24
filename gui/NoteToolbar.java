package gui;

import java.awt.Graphics;

public class NoteToolbar extends Toolbar {
	ToolbarButton _activeButton;
	
	public NoteToolbar(DockController dockControl) {
		super(dockControl, Orientation.HORIZONTAL);
		
		dockControl.dockToTop(this);
	}
	
	protected void createButtons(){
		// add mode buttons (note, selection, zoom)
		ToolbarButton noteQuar 	= new ToolbarButton("images/note/note_quarter.png");
		ToolbarButton noteHalf 	= new ToolbarButton("images/note/note_whole.png");
		ToolbarButton noteWhole = new ToolbarButton("images/note/note_eighth.png");
		ToolbarButton noteSixth	= new ToolbarButton("images/note/note_sixteenth.png");
		
		ToolbarButton noteModHalf 	= new ToolbarButton("images/note/note_onehalf.png");
		ToolbarButton noteModThird = new ToolbarButton("images/note/note_third.png");
		ToolbarButton noteModDot 	= new ToolbarButton("images/note/note_dot.png");
		ToolbarButton noteModFlat 	= new ToolbarButton("images/note/note_flat.png");
		ToolbarButton noteModSharp = new ToolbarButton("images/note/note_sharp.png");
		ToolbarButton noteModRest 	= new ToolbarButton("images/note/note_rest.png");
		
		_buttons = new ToolbarButton[]{noteQuar, noteHalf, noteWhole, noteSixth, noteModHalf,
								noteModThird, noteModDot, noteModFlat, noteModSharp, noteModRest};
	}
}