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
		EditType editType = editInstr.getElemType();
		
		switch (editType) {
		case STAFF:
			editStaff(editInstr);
			break;
		case MEASURE:
			editMeasure(editInstr);
			break;
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
		case VOICE:
			editVoice(editInstr);
			break;
		case PITCH:
			editPitch(editInstr);
			break;
		case MULTINOTE:
			editMultiNote(editInstr);
			break;
		default:
		}
	}
	private void editStaff(EditInstruction editInstr) {
		int staffNumber = editInstr.getIndex().getStaffNumber();
		ListIterator<Staff> iterator = _piece.getStaffs().listIterator(staffNumber);
		_editor.setStaffIter(iterator);
		
		EditInstructionType instrType = editInstr.getType();
		switch (instrType) {
		// TODO: THESE STAFF INSERTION/REMOVAL FUNCTIONS ARE DUMMIES, HAVE TO CHECK FOR STAFF LENGTH, MAKING KEY SIGNATURES/TIME SIGNATURES CONSISTENT, ETC.
		// ALSO HAVE TO DECIDE WHAT KIND OF INFORMATION NEEDS TO BE PASSED WHEN CREATING A NEW STAFF.
			case INSERT:
				_editor.insertStaff(new Staff());
				break;
			case REMOVE:
				_editor.removeStaff();
				break;
			case REPLACE:
				_editor.replaceStaff(new Staff());
				break;
			default:
				throw new RuntimeException("Instruction of unrecognized EditInstructionType");
		}
		
	}
	private void editChordSymbol(EditInstruction editInstr) {
		int staffNumber = editInstr.getIndex().getStaffNumber();
		int measureNumber = editInstr.getIndex().getMeasureNumber();
		Measure measure = _piece.getStaffs().get(staffNumber).getMeasures().get(measureNumber);
		List<ChordSymbol> chordSymbolList = measure.getChordSymbols();
		Rational measureOffset = editInstr.getIndex().getMeasureOffset();

		// calculate iterator and offset
		IteratorAndOffset iterAndOffset = calcIterAndOffset(chordSymbolList, measureOffset);
		ListIterator<ChordSymbol> iter = (ListIterator<ChordSymbol>) iterAndOffset.iter;
		Rational offset = iterAndOffset.offset;
		ChordSymbol chordSymbol;
		
		// set the iterator in the editor
		_editor.setChordSymbolIter(iter);
		
		EditInstructionType instrType = editInstr.getType();
		switch (instrType) {
		// offset SHOULD be 0 for insertion and removal functions
			case INSERT:
				chordSymbol = (ChordSymbol) editInstr.getElement();
				_editor.insertChordSymbol(chordSymbol);
				break;
			case REMOVE:
				// TODO, this, and following Edit functions
				break;
			case REPLACE:
				break;
			default:
				throw new RuntimeException("Instruction of unrecognized EditInstructionType");
		}
	}
	private void editMeasure(EditInstruction editInstr) {
	}
	private void editKeySignature(EditInstruction editInstr) {
	}
	private void editTimeSignature(EditInstruction editInstr) {
	}
	private void editClef(EditInstruction editInstr) {
	}
	private void editVoice(EditInstruction editInstr) {
	}
	private void editMultiNote(EditInstruction editInstr) {
		int staffNumber = editInstr.getIndex().getStaffNumber();
		int measureNumber = editInstr.getIndex().getMeasureNumber();
		int voiceNumber = editInstr.getIndex().getVoiceNumber();
		Measure measure = _piece.getStaffs().get(staffNumber).getMeasures().get(measureNumber);
		List<MultiNote> multiNoteList = measure.getVoices().get(voiceNumber).getMultiNotes();
		Rational measureOffset = editInstr.getIndex().getMeasureOffset();

		// calculate iterator and offset
		IteratorAndOffset iterAndOffset = calcIterAndOffset(multiNoteList, measureOffset);
		ListIterator<MultiNote> iter = (ListIterator<MultiNote>) iterAndOffset.iter;
		Rational offset = iterAndOffset.offset;
		MultiNote multiNote;
		
		// set the iterator in the editor
		_editor.setMultiNoteIter(iter);
		
		EditInstructionType instrType = editInstr.getType();
		switch (instrType) {
		// offset SHOULD be 0 for insertion and removal functions
			case INSERT:
				multiNote = (MultiNote) editInstr.getElement();
				_editor.insertMultiNote(multiNote);
				break;
			case REMOVE:
				_editor.removeMultiNote();
				break;
			case REPLACE:
				// check if replacement note runs over end of measure
				Rational measureLength = measure.getTimeSignatures().get(0).getMeasureDuration();
				Rational remainingSpace = measureLength.minus(measureOffset);
				multiNote = (MultiNote) editInstr.getElement();
				if (multiNote.getDuration().compareTo(remainingSpace) == 1) {
					// if the replaced note is bigger than the remaining space in the measure
					return;
				}
				else {
					_editor.replaceMultiNote(multiNote);
				}
				break;
			default:
				throw new RuntimeException("Instruction of unrecognized EditInstructionType");
		}
	}
	private void editPitch(EditInstruction editInstr) {
	}
	
	public void interpretPlaybackInstr(PlaybackInstruction playbackInstr) {
	}
	
	// Used for finding the location of an edit, given a measure and an offset into that measure.
	class IteratorAndOffset {
		ListIterator<? extends Timestep> iter;
		Rational offset;
		public IteratorAndOffset(ListIterator<? extends Timestep> iter, Rational offset) {
			this.iter = iter;
			this.offset = offset;
		}
		public ListIterator<? extends Timestep> getIter() {
			return this.iter;
		}
		public Rational getOffset() {
			return this.offset;
		}
	}
	
	private IteratorAndOffset calcIterAndOffset(List<? extends Timestep> list, Rational offset) {
		ListIterator<? extends Timestep> iter;
		Rational offsetFromIter;
		
		iter = list.listIterator();
		offsetFromIter = offset;
		
		Rational rationalZero = new Rational(0,1);
		while (iter.hasNext()) {
			// subtract duration of next element from the current offset
			Rational newOffset = offsetFromIter.minus(iter.next().getDuration());
			if (newOffset.compareTo(rationalZero) == -1) {
				// if the next element is longer than the current offset
				iter.previous();
				break;
			}
			else {
				offsetFromIter = newOffset;
			}
		}
		return new IteratorAndOffset(iter, offsetFromIter);
	}
}
