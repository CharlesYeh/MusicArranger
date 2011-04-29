package logic;

import music.*;

import java.util.*;

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
	public void interpretInstr(Instruction instr) {
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
		
	}
	
	public void interpretFileInstr(FileInstruction fileInstr) {
	}
	
	public void interpretEditInstr(EditInstruction editInstr) {
		EditType elemType = editInstr.getElemType();
		
		// Determine whether the edit is of a Timestep, or something else in the piece (timesteps behave very differently)
		switch (elemType) {
			case CHORD_SYMBOL:
				editChordSymbol(editInstr);
				break;
			case KEY_SIGNATURE:
				editKeySignature(editInstr);
				break;
			case TIME_SIGNATURE:
				editTimeSignature(editInstr);
				break;
			case CLEF:
				editClef(editInstr);
				break;
			case MULTINOTE:
				editMultiNote(editInstr);
				break;
			case STAFF:
				editStaff(editInstr);
				break;
			case VOICE:
				editVoice(editInstr);
				break;
			case PITCH:
				editPitch(editInstr);
				break;
			default:
				throw new RuntimeException("Passed EditType not recognized by interpretEditInstr.");
		}
	}

	private void editChordSymbol(EditInstruction editInstr) {
		InstructionIndex index;
		EditInstructionType instrType;
		Rational timestamp;
		List<ChordSymbol> chordSymbolList;
		ListIterator<ChordSymbol> iter;
		Rational offset;
		ChordSymbol element;
		
		index = editInstr.getIndex();
		instrType = editInstr.getType();		
		timestamp = index.getTimestamp();
		chordSymbolList = _piece.getChordSymbols();
		IteratorAndOffset iterAndOffset = indexSearch(chordSymbolList, timestamp);
		
		iter = iterAndOffset.iter;
		offset = iterAndOffset.offset;
		
		// Handling different types of edits:
		switch (instrType) {
			case INSERT:
				element = (ChordSymbol) editInstr.getElement();
				break;
			case REMOVE:
				break;
			case REPLACE:
				element = (ChordSymbol) editInstr.getElement();
				break;
			default:
				throw new RuntimeException("Unrecognized EditInstructionType passed.");
		}
	}
	private void editKeySignature(EditInstruction editInstr) {
	}
	private void editTimeSignature(EditInstruction editInstr) {
	}
	private void editClef(EditInstruction editInstr) {
	}
	private void editMultiNote(EditInstruction editInstr) {
	}
	private void editStaff(EditInstruction editInstr) {
	}
	private void editVoice(EditInstruction editInstr) {
	}
	private void editPitch(EditInstruction editInstr) {
	}
	
	public void interpretPlaybackInstr(PlaybackInstruction playbackInstr) {
	}
	

	// defining a return value for indexSearch
	static class IteratorAndOffset {
		ListIterator iter;
		Rational offset;
		
		IteratorAndOffset(ListIterator<? extends Timestep> iter, Rational offset) {
			this.iter = iter;
			this.offset = offset;
		}
	}
	
	// indexSearch is used to to find the corresponding ListIterator of a specified rhythmic location in the song, and the rhythmic offset (if any) of the index from the iterator's position
	private IteratorAndOffset indexSearch(List<? extends Timestep> timestepList, Rational position) {
		Rational remainingBeats = position;
		Rational rationalZero = new Rational(0,1);
		ListIterator<? extends Timestep> iter = timestepList.listIterator();
		
		while (iter.hasNext()) {
			Timestep nextTimestep = iter.next();
			Rational nextDur = nextTimestep.getDuration();
			Rational newRemainingBeats = remainingBeats.minus(nextDur);
			
			// if remainingBeats falls below 0, return the previous iterator and remainingBeats
			if (newRemainingBeats.compareTo(rationalZero) == -1) {
				iter.previous();
				break;
			}
			else {
				remainingBeats = newRemainingBeats;
			}
		}
		return new IteratorAndOffset(iter, remainingBeats);
		
	}
}
