package logic;

import music.*;
import instructions.*;

/*
 * LogicManager is the highest level class in the logic package, which is instantiated
 * the arranger package.  Primarily, it receives Instructions from the arranger and gui
 * packages, interprets those Instructions, and delegates the execution of those
 * Instructions to the classes contained within the package.
 */

public class LogicManager {
	Piece _piece;
	Editor _editor;
	ArrangerXMLParser _arrangerXMLParser;
	ArrangerXMLWriter _arrangerXMLWriter;

	public LogicManager(Piece piece) {
		_piece = piece;
		_editor = makeEditor();
		_arrangerXMLParser = makeArrangerXMLParser();
		_arrangerXMLWriter = makeArrangerXMLWriter();
	}

	protected Editor makeEditor() {
		Editor editor = new Editor();
		editor.setPiece(_piece);
		return editor;
	}
	protected ArrangerXMLParser makeArrangerXMLParser() {

		return new ArrangerXMLParser(_editor);
	}
	protected ArrangerXMLWriter makeArrangerXMLWriter() {
		return new ArrangerXMLWriter();
	}
	/*
	 * Finds the Instruction's class type and calls the cooresponding method to interpret
	 * the Instruction, after casting it appropriately.
	 */
	public LogicManager interpretInstr(Instruction instr) {
		Class<?> instructionClass = instr.getClass();
		
		if (instructionClass == FileInstruction.class){
			FileInstruction fileInstr = (FileInstruction) instr;
			interpretFileInstr(fileInstr);
		}
		else if (instructionClass == EditInstruction.class) {
			EditInstruction editInstr = (EditInstruction) instr;
			interpretEditInstr(editInstr);
		}
		else if (instructionClass == PlaybackInstruction.class) {
			PlaybackInstruction playbackInstr = (PlaybackInstruction) instr;
			interpretPlaybackInstr(playbackInstr);
		}
		else {
			throw new RuntimeException("Instruction of unrecognized InstructionType");
		}
		
		return this;
	}
	
	public LogicManager interpretFileInstr(FileInstruction fileInstr) {
		return this;
	}
	
	public LogicManager interpretEditInstr(EditInstruction editInstr) {
		return this;
	}
	
	public LogicManager interpretPlaybackInstr(PlaybackInstruction playbackInstr) {
		return this;
	}
}
