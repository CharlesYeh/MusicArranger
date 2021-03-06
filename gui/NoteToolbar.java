package gui;

import java.util.Arrays;
import java.util.HashSet;
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
		ToolbarButton noteQuar 	= new ToolbarButton("images/btns/note_quarter.png", true);
		ToolbarButton noteHalf 	= new ToolbarButton("images/btns/note_half.png", true);
		ToolbarButton noteWhole	= new ToolbarButton("images/btns/note_whole.png", true);
		ToolbarButton noteEighth = new ToolbarButton("images/btns/note_eighth.png", true);
		ToolbarButton noteSixth	= new ToolbarButton("images/btns/note_sixteenth.png", true);
		
		ToolbarButton noteModHalf 	= new ToolbarButton("images/btns/note_onehalf.png", true);
		ToolbarButton noteModThird = new ToolbarButton("images/btns/note_third.png", true);
		ToolbarButton noteModDot 	= new ToolbarButton("images/btns/note_dot.png", true);
		
		ToolbarButton noteModFlat 	= new ToolbarButton("images/btns/note_flat.png", true);
		ToolbarButton noteModSharp = new ToolbarButton("images/btns/note_sharp.png", true);
		ToolbarButton noteModNatural = new ToolbarButton("images/btns/note_natural.png", true);
		ToolbarButton noteModRest 	= new ToolbarButton("images/btns/note_rest.png", false);
		
		noteQuar.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.QUARTER));
		noteHalf.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.HALF));
		noteWhole.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.WHOLE));
		noteEighth.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.EIGHTH));
		noteSixth.setInstruction(new ModeInstruction(ModeInstructionType.DURATION, EditDuration.SIXTEENTH));
		
		/*noteModHalf.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.HALF));
		noteModThird.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.THIRD));*/
		noteModFlat.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.FLAT));
		noteModNatural.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.NATURAL));
		noteModSharp.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.SHARP));
		noteModRest.setInstruction(new ModeInstruction(ModeInstructionType.MODIFIER, EditModifier.REST));
		
		_buttons = Arrays.asList(noteQuar, noteHalf, noteWhole, noteEighth, noteSixth, /*noteModHalf,
								noteModThird, noteModDot, */noteModFlat, noteModNatural, noteModSharp, noteModRest);
		
		noteModFlat.setToToggle(true);
		noteModNatural.setToToggle(true);
		noteModSharp.setToToggle(true);
		noteModRest.setToToggle(true);
		
		_buttonGroups.add(new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4)));
		_buttonGroups.add(new HashSet<Integer>(Arrays.asList(5, 6, 7)));
		_buttonGroups.add(new HashSet<Integer>(Arrays.asList(8)));
		
		// set initialized state
		noteQuar.setPressed(true);
	}
}