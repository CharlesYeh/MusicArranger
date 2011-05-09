package gui;

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
		//ToolbarButton modeZoom = new ToolbarButton("images/btns/mode_zoom.png");
		
		ToolbarButton genChords = new ToolbarButton("images/btns/gen_chords.png", true);
		ToolbarButton genVoices = new ToolbarButton("images/btns/gen_voices.png", true);

		modeNote.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.NOTE));
		modeSelection.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.SELECTION));
		//modeZoom.setInstruction(new ModeInstruction(ModeInstructionType.MODE, EditMode.ZOOM));
		
		genChords.setInstruction(new GenerateInstruction(GenerateInstructionType.CHORDS));
		genVoices.setInstruction(new GenerateInstruction(GenerateInstructionType.VOICES));

		_buttons = new ToolbarButton[]{modeNote, modeSelection, /*modeZoom, */genChords, genVoices};
	}
}
