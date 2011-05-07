package gui;

import java.awt.Graphics;

import instructions.ModeInstruction;
import instructions.ModeInstructionType;

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
		ToolbarButton noteEighth = new ToolbarButton("images/note/note_eighth.png");
		ToolbarButton noteSixth	= new ToolbarButton("images/note/note_sixteenth.png");
		
		ToolbarButton noteModHalf 	= new ToolbarButton("images/note/note_onehalf.png");
		ToolbarButton noteModThird = new ToolbarButton("images/note/note_third.png");
		ToolbarButton noteModDot 	= new ToolbarButton("images/note/note_dot.png");
		ToolbarButton noteModFlat 	= new ToolbarButton("images/note/note_flat.png");
		ToolbarButton noteModSharp = new ToolbarButton("images/note/note_sharp.png");
		ToolbarButton noteModRest 	= new ToolbarButton("images/note/note_rest.png");
		
		noteQuar.setInstruction(new ModeInstruction(this, ModeInstructionType.DURATION, EditDuration.QUARTER));
		noteHalf.setInstruction(new ModeInstruction(this, ModeInstructionType.DURATION, EditDuration.HALF));
		noteEighth.setInstruction(new ModeInstruction(this, ModeInstructionType.DURATION, EditDuration.EIGHTH));
		noteSixth.setInstruction(new ModeInstruction(this, ModeInstructionType.DURATION, EditDuration.SIXTEENTH));
		
		noteModHalf.setInstruction(new ModeInstruction(this, ModeInstructionType.MODIFIER, EditModifier.HALF));
		noteModThird.setInstruction(new ModeInstruction(this, ModeInstructionType.MODIFIER, EditModifier.THIRD));
		noteModFlat.setInstruction(new ModeInstruction(this, ModeInstructionType.MODIFIER, EditModifier.FLAT));
		noteModSharp.setInstruction(new ModeInstruction(this, ModeInstructionType.MODIFIER, EditModifier.SHARP));
		noteModRest.setInstruction(new ModeInstruction(this, ModeInstructionType.MODIFIER, EditModifier.REST));
		
		_buttons = new ToolbarButton[]{noteQuar, noteHalf, noteEighth, noteSixth, noteModHalf,
								noteModThird, noteModDot, noteModFlat, noteModSharp, noteModRest};
	}
}