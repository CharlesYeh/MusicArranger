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
		ToolbarButton noteQuar 	= new ToolbarButton("images/btns/note_quarter.png");
		ToolbarButton noteHalf 	= new ToolbarButton("images/btns/note_half.png");
		ToolbarButton noteWhole	= new ToolbarButton("images/btns/note_whole.png");
		ToolbarButton noteEighth = new ToolbarButton("images/btns/note_eighth.png");
		ToolbarButton noteSixth	= new ToolbarButton("images/btns/note_sixteenth.png");
		
		ToolbarButton noteModHalf 	= new ToolbarButton("images/btns/note_onehalf.png");
		ToolbarButton noteModThird = new ToolbarButton("images/btns/note_third.png");
		ToolbarButton noteModDot 	= new ToolbarButton("images/btns/note_dot.png");
		
		ToolbarButton noteModFlat 	= new ToolbarButton("images/btns/note_flat.png");
		ToolbarButton noteModSharp = new ToolbarButton("images/btns/note_sharp.png");
		ToolbarButton noteModRest 	= new ToolbarButton("images/btns/note_rest.png");
		
		noteQuar.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.QUARTER));
		noteHalf.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.HALF));
		noteWhole.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.WHOLE));
		noteEighth.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.EIGHTH));
		noteSixth.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.SIXTEENTH));
		
		/*noteModHalf.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.HALF));
		noteModThird.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.THIRD));*/
		noteModFlat.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.FLAT));
		noteModSharp.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.SHARP));
		noteModRest.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.REST));
		
		_buttons = new ToolbarButton[]{noteQuar, noteHalf, noteWhole, noteEighth, noteSixth, /*noteModHalf,
								noteModThird, noteModDot, */noteModFlat, noteModSharp, noteModRest};
	}
}