package gui;

import java.util.Arrays;
import java.util.HashSet;

import java.awt.Graphics;

import instructions.ModeInstruction;
import instructions.ModeInstructionType;
import instructions.GenerateInstruction;
import instructions.GenerateInstructionType;

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
		ToolbarButton modeNote = new ToolbarButton("images/btns/note_quarter.png", true);
		ToolbarButton modeSelection = new ToolbarButton("images/btns/mode_chord.png", true);
		ToolbarButton modeClef = new ToolbarButton("images/btns/mode_clef.png", true);
		ToolbarButton modeTime = new ToolbarButton("images/btns/mode_time.png", true);
		ToolbarButton modeKey = new ToolbarButton("images/btns/note_sharp.png", true);
		//ToolbarButton modeZoom = new ToolbarButton("images/btns/mode_zoom.png");
		
		ToolbarButton genChords = new ToolbarButton("images/btns/gen_chords.png", false);
		ToolbarButton genVoices = new ToolbarButton("images/btns/gen_voices.png", false);

		modeNote.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.NOTE));
		modeSelection.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.SELECTION));
		modeClef.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.CLEF));
		modeTime.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.TIME));
		modeKey.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.KEY));
		//modeZoom.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.ZOOM));
		
		genChords.setInstruction(new ModeInstruction(ModeInstructionType.GENERATE, GenerateInstructionType.CHORDS));
		genVoices.setInstruction(new ModeInstruction(ModeInstructionType.GENERATE, GenerateInstructionType.VOICES));

		_buttons = Arrays.asList(modeNote, /*modeSelection,*/ /*modeZoom, */modeClef, modeTime, modeKey, genChords, genVoices);
		_buttonGroups.add(new HashSet<Integer>(Arrays.asList(0, 1, 2, 3)));
		_buttonGroups.add(new HashSet<Integer>(Arrays.asList(4, 5)));
		
		modeNote.setPressed(true);
	}
}
