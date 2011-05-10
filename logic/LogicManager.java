package logic;

import music.*;
import instructions.*;

import java.util.*;
import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.print.attribute.*;

/*
 * LogicManager is the highest level class in the logic package, which is instantiated
 * the arranger package.  Primarily, it receives Instructions from the arranger and gui
 * packages, interprets those Instructions, and delegates the execution of those
 * Instructions to the classes contained within the package.
 */

public class LogicManager implements Printable {
	Piece _piece;
	Editor _editor;
	ArrangerXMLParser _arrangerXMLParser;
	ArrangerXMLWriter _arrangerXMLWriter;
	Analyzer _analyzer;

	MidiAPI _api;

	Image _toPrint;

	public LogicManager(Piece piece) {
		_piece = piece;
		_editor = makeEditor();
		_arrangerXMLParser = makeArrangerXMLParser();
		_arrangerXMLWriter = makeArrangerXMLWriter();
		_analyzer = makeAnalyzer();

		_api = new MidiAPI();
	}

	public Editor getEditor() {
		return _editor;
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
	protected Analyzer makeAnalyzer() {
		return new Analyzer();
	}

	public void interpretInstrBlock(InstructionBlock instrBlock) {
		List<Instruction> allInstructions = instrBlock.getInstructions();
		for (Instruction instr : allInstructions) {
			// instructions return true if they succeed, false if they do not (cancelling the
			// instruction block)
			if (!interpretInstr(instr)) {
				return;
			}
		}
	}

	/*
	 * Finds the Instruction's class type and calls the cooresponding method to interpret
	 * the Instruction, after casting it appropriately.
	 */
	private boolean interpretInstr(Instruction instr) {
		
		if (instr instanceof FileInstruction){
			FileInstruction fileInstr = (FileInstruction) instr;
			return interpretFileInstr(fileInstr);
		}
		else if (instr instanceof EditInstruction) {
			EditInstruction editInstr = (EditInstruction) instr;
			return interpretEditInstr(editInstr);
		}
		else if (instr instanceof PlaybackInstruction) {
			PlaybackInstruction playbackInstr = (PlaybackInstruction) instr;
			return interpretPlaybackInstr(playbackInstr);
		}
		else if (instr instanceof GenerateInstruction) {
			GenerateInstruction genInstr = (GenerateInstruction) instr;
			return interpretGenerateInstr(genInstr);
		}
		else {
			throw new RuntimeException("Instruction of unrecognized InstructionType");
		}
	}

	private boolean interpretFileInstr(FileInstruction fileInstr) {
		if (fileInstr instanceof FileInstructionIO) {
			FileInstructionIO fileInstrIO = (FileInstructionIO) fileInstr;
			return interpretFileInstrIO(fileInstrIO);
		}
		else if (fileInstr instanceof FileInstructionNew) {
			FileInstructionNew fileInstrNew = (FileInstructionNew) fileInstr;
			return interpretFileInstrNew(fileInstrNew);
		}
		else if (fileInstr instanceof FileInstructionPrint) {
			FileInstructionPrint fileInstrPrint = (FileInstructionPrint) fileInstr;
			return interpretFileInstrPrint(fileInstrPrint);
		}
		else {
			System.out.println("FileInstruction not recognized.");
			return false;
		}
	}

	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		//FileInstructionPrint
		g.drawImage(_toPrint, 0, 0, null);

		return PAGE_EXISTS;
	}
	private boolean interpretFileInstrPrint(FileInstructionPrint fileInstrPrint) {
		//fileInstrPrint
		_toPrint = fileInstrPrint.getImage();
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		PageFormat pf = job.pageDialog(aset);
		job.setPrintable(this, pf);
		boolean ok = job.printDialog(aset);
		if (ok) {
			try {
				job.print(aset);
			}
			catch (PrinterException ex) {
				System.out.println("Print failed");
			}
		}
		// TODO: LEARN HOW PRINT WORKS
		return true;

	}
	private boolean interpretFileInstrNew(FileInstructionNew fileInstrNew) {
		System.out.println("start new file");

		List<Clef> clefs = fileInstrNew.getClefs();
		int numStaffs = clefs.size();
		int numMeasures = fileInstrNew.getNumMeasures();
		int timeSigNumer = fileInstrNew.getTimeSigNumer();
		int timeSigDenom = fileInstrNew.getTimeSigDenom();
		int accidentals = fileInstrNew.getAccidentals();
		boolean isMajor = fileInstrNew.getIsMajor();

		_editor.clearScore();

		// loop through staffs
		for (int stfnm = 0; stfnm < numStaffs; stfnm++) {
			Staff staff = new Staff();
			_editor.insertStaff(staff);

			// loop through measures
			for (int msrnm = 0; msrnm < numMeasures; msrnm++) {
				Measure measure = new Measure();
				_editor.insertMeasure(measure);

				// Instantiate key signature, time signature
				Rational duration = new Rational(timeSigNumer, timeSigDenom);
				TimeSignature timeSig = new TimeSignature(duration, timeSigNumer, timeSigDenom);
				KeySignature keySig = new KeySignature(duration, accidentals, isMajor);
				_editor.insertKeySig(keySig);
				_editor.insertTimeSig(timeSig);

				// Instantiate clef
				ClefName clefName = clefs.get(stfnm).getClefName();
				int centerLine = clefs.get(stfnm).getCenterLine();
				Clef clef = new Clef(duration, clefName, centerLine);
				_editor.insertClef(clef);

				// Instantiate single voice with a rest;
				Voice voice = new Voice();
				MultiNote rest = new MultiNote(duration);
				_editor.insertVoice(voice);
				_editor.insertMultiNote(rest);

				// Add blank placeholder chordsymbols
				ChordSymbol chordSymbol = new ChordSymbol(duration, new ScaleDegree(0, Accidental.NATURAL), ChordType.BLANK);
				_editor.insertChordSymbol(chordSymbol);
			}
		}

		System.out.println("created new piece");
		
		return true;
	}
	private boolean interpretFileInstrIO(FileInstructionIO fileInstrIO) {
		FileInstructionType fileInstrType = fileInstrIO.getType();
		String fileName = fileInstrIO.getFileName();

		switch (fileInstrType) {
		case OPEN:
			try {
				_arrangerXMLParser.parse(fileName);
			} catch (Exception e) {
				System.out.println("Load failed");
				return false;
			}
			break;
		case SAVE:
			try {
				_arrangerXMLWriter.write(_piece, fileName);
			} catch (Exception e) {
				System.out.println("Save failed");
				return false;
			}
			break;
		}
		return true;
	}

	private boolean interpretGenerateInstr(GenerateInstruction genInstr) {
		GenerateInstructionType genType = genInstr.getType();

		switch (genType) {
		case CHORDS:
			GenerateInstructionAnalyzeChords genInstrAnalyzeChords = 
				(GenerateInstructionAnalyzeChords) genInstr;
			return interpretGenerateInstrAnalyzeChords(genInstrAnalyzeChords);
		case VOICES:

			break;
		}
		
		return true;
	}
	
	private boolean interpretGenerateInstrAnalyzeChords(GenerateInstructionAnalyzeChords genInstrAnalyzeChords) {
		// TODO: ONLY ANALYZES BASED ON ONE VOICE, CURRENTLY
		
		InstructionIndex startIndex = genInstrAnalyzeChords.getStartIndex();
		InstructionIndex endIndex = genInstrAnalyzeChords.getEndIndex();
		Rational spacing = genInstrAnalyzeChords.getSpacing();
		
		List<InstructionIndex> indices = generateInstructionIndices(spacing, startIndex, endIndex);
		List<List<Pitch>> melodyLine = getMelodyLine(indices, spacing);
		
		return true;
	}

	private boolean interpretEditInstr(EditInstruction editInstr) {
		EditType editType = editInstr.getElemType();

		switch (editType) {
		case STAFF:
			return editStaff(editInstr);
		case MEASURE:
			return editMeasure(editInstr);
		case CHORD_SYMBOL:
			return editChordSymbol(editInstr);
		case KEY_SIGNATURE:
			return editKeySignature(editInstr);
		case TIME_SIGNATURE:
			return editTimeSignature(editInstr);
		case CLEF:
			return editClef(editInstr);
		case VOICE:
			return editVoice(editInstr);
		case PITCH:
			return editPitch(editInstr);
		case MULTINOTE:
			return editMultiNote(editInstr);
		default:
			System.out.println("Unrecognized EditType");
			return false;
		}
	}

	private boolean editStaff(EditInstruction editInstr) {
		InstructionIndex index = editInstr.getIndex();

		int staffNumber = index.getStaffNumber();
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
				System.out.println("Instruction of unrecognized EditInstructionType");
				return false;
		}
		return true;
	}

	private boolean editChordSymbol(EditInstruction editInstr) {
		InstructionIndex index = editInstr.getIndex();

		for (Staff staff : _piece.getStaffs()) {
				
			int measureNumber = index.getMeasureNumber();
			Rational measureOffset = index.getMeasureOffset();
			Measure measure = staff.getMeasures().get(measureNumber);
			List<ChordSymbol> chordSymbolList = measure.getChordSymbols();
	
			// calculate iterator and offset
			IteratorAndOffset iterAndOffset = calcIterAndOffset(chordSymbolList, measureOffset);
			ListIterator<ChordSymbol> iter = (ListIterator<ChordSymbol>) iterAndOffset.getIter();
			Rational offset = iterAndOffset.getOffset();
	
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
					_editor.removeChordSymbol();
					break;
				case REPLACE:
					// check if replacement note runs over end of measure
					Rational measureLength = measure.getTimeSignatures().get(0).getMeasureDuration();
					chordSymbol = (ChordSymbol) editInstr.getElement();
					if (measureOffset.compareTo(measureLength) > 0) {
						// Error, measureOffset is longer than the actual measure
						return false;
					}
					else {
						_editor.replaceChordSymbol(chordSymbol, offset);
					}
					break;
				default:
					System.out.println("Instruction of unrecognized EditInstructionType");
					return false;
			}
		}
		return true;
	}

	private boolean editMeasure(EditInstruction editInstr) {
		return true;
	}

	private boolean editKeySignature(EditInstruction editInstr) {
		return true;
	}

	private boolean editTimeSignature(EditInstruction editInstr) {
		return true;
	}

	private boolean editClef(EditInstruction editInstr) {
		return true;
	}

	private boolean editVoice(EditInstruction editInstr) {
		return true;
	}

	private boolean editMultiNote(EditInstruction editInstr) {
		InstructionIndex index = editInstr.getIndex();

		int staffNumber = index.getStaffNumber();
		int measureNumber = index.getMeasureNumber();
		int voiceNumber = index.getVoiceNumber();
		Rational measureOffset = index.getMeasureOffset();
		Measure measure = _piece.getStaffs().get(staffNumber).getMeasures().get(measureNumber);
		List<MultiNote> multiNoteList = measure.getVoices().get(voiceNumber).getMultiNotes();

		// calculate iterator and offset
		IteratorAndOffset iterAndOffset = calcIterAndOffset(multiNoteList, measureOffset);
		ListIterator<MultiNote> iter = (ListIterator<MultiNote>) iterAndOffset.getIter();
		Rational offset = iterAndOffset.getOffset();

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
				if (multiNote.getDuration().compareTo(remainingSpace) > 0) {
					// if the replaced note is bigger than the remaining space in the measure
					System.out.println("New note is too big");
					return false;
				}
				else {
					_editor.replaceMultiNote(multiNote);
				}
				break;
			default:
				System.out.println("Instruction of unrecognized EditInstructionType");
		}
		return true;
	}

	private boolean editPitch(EditInstruction editInstr) {
		InstructionIndex index = editInstr.getIndex();
		
		// get Measure
		int staffNumber = index.getStaffNumber();
		int measureNumber = index.getMeasureNumber();
		int voiceNumber = index.getVoiceNumber();
		int lineNumber = index.getLineNumber();
		Rational measureOffset = index.getMeasureOffset();
		Measure measure = _piece.getStaffs().get(staffNumber).getMeasures().get(measureNumber);
		List<MultiNote> multiNoteList = measure.getVoices().get(voiceNumber).getMultiNotes();

		// find clef and keysig
		Clef clef = (Clef) getElementAt(measureOffset, measure.getClefs());
		KeySignature keySig = (KeySignature) getElementAt(measureOffset, measure.getKeySignatures());
		
		// find MultiNote
		MultiNote multiNote = (MultiNote) getElementAt(measureOffset, multiNoteList);
		
		Pitch pitch = calcPitch(lineNumber, clef, keySig);
		// IF AN ACCIDENTAL IS SPECIFIED
		if (index.getAccidental() != null) {
			pitch = new Pitch(pitch.getNoteLetter(), pitch.getOctave(), index.getAccidental());
		}
		
		EditInstructionType editInstrType = editInstr.getType();
		ListIterator<Pitch> iter = multiNote.getPitches().listIterator();
		
		switch (editInstrType) {
		case INSERT:
			_editor.setPitchIter(iter);
			_editor.insertPitch(pitch);
			break;
		case REMOVE:
			// search pitches for a pitch that matches the pitch sent by the instruction
			while (iter.hasNext()) {
				if (iter.next().equals(pitch)) {
					iter.previous();
					break;
				}
			}
			_editor.setPitchIter(iter);
			_editor.removePitch();
			break;
		case REPLACE:
			System.out.println("LogicManager does not support REPLACE for pitch edits.");
			return false;
		}
		return true;
	}

	private boolean interpretPlaybackInstr(PlaybackInstruction playbackInstr) {
		switch (playbackInstr.getType()) {
		case START:
			_api.playPiece(_piece);
			break;
		case STOP:
			_api.stopPlayback();
			break;
		}
		return true;
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
			if (newOffset.compareTo(rationalZero) < 0) {
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
	private Timestep getElementAt(Rational offset, List<? extends Timestep> list) {
		IteratorAndOffset iterAndOff = calcIterAndOffset(list, offset);
		return iterAndOff.getIter().next();
	}
	
	private List<Timestep> getElementsAtUntil(Rational start, Rational finish, List<? extends Timestep> list) {
		IteratorAndOffset iterAndOff = calcIterAndOffset(list, start);
		ListIterator<? extends Timestep> iterator = iterAndOff.iter;
		Rational offset = iterAndOff.offset;
		Rational timeLeft = finish.minus(start).minus(offset);
		List<Timestep> output = new ArrayList<Timestep>();

		Timestep firstElem = iterator.next();
		output.add(firstElem);
		timeLeft.minus(firstElem.getDuration());
		
		while (timeLeft.getNumerator() > 0) {
			Timestep nextElem = iterator.next();
			output.add(nextElem);
			
			timeLeft.minus(nextElem.getDuration());
		}
		
		return output;
	}
	
	// Calculates a pitch, given a line number, the clef, and the key signature
	public Pitch calcPitch(int lineNum, Clef clef, KeySignature keySig) {
		Pitch clefPitch = clef.getClefName().centerPitch();
		Pitch keySigPitch = keySig.getKeySigPitch();
		
		int clefLine = clef.getCenterLine();
		boolean isMajor = keySig.getIsMajor();
		
		int clefSclDgr = (clefPitch.getNoteLetter().intValue() - keySigPitch.getNoteLetter().intValue() + 1) % 7;
		if (clefSclDgr < 0) clefSclDgr += 7;
		
		int lineNumSclDgr = (lineNum + (clefSclDgr - 1 - clefLine)) % 7;
		if (lineNumSclDgr < 0) lineNumSclDgr += 7;
		
		lineNumSclDgr++;
		ScaleDegree sclDgr = new ScaleDegree(lineNumSclDgr, Accidental.NATURAL);
		Interval sclDgrInterval = sclDgr.getIntervalFromRoot(isMajor);
		Pitch pitchLetter = keySigPitch.addInterval(sclDgrInterval);
		
		int octave = (clef.getCenterValue() + lineNum - clefLine) / 7;
		Pitch pitch = new Pitch(pitchLetter.getNoteLetter(), octave, pitchLetter.getAccidental());
		return pitch;
	}

	// Given a start, an end, and the spacing between indices, returns all indices pointing
	// to the indices at those locations
	public List<InstructionIndex> generateInstructionIndices(Rational spacing, InstructionIndex start,
			InstructionIndex end) {
		List<InstructionIndex> output = new ArrayList<InstructionIndex>();
		
		int currentMeasure = start.getMeasureNumber();
		Rational currentOffset = start.getMeasureOffset();
		
		int endMeasure = end.getMeasureNumber();
		Rational endOffset = end.getMeasureOffset();
		
		while (currentMeasure < endMeasure || 
				currentMeasure == endMeasure && currentOffset.compareTo(endOffset) == -1) {
			Rational measureLength = _piece.getStaffs().get(0).getMeasures().get(currentMeasure).getTimeSignatures().get(0).getMeasureDuration();
			
			while (currentOffset.compareTo(measureLength) == -1) {
				// Make instruction indices for the measure
				InstructionIndex nextIndex = new InstructionIndex(currentMeasure, currentOffset);
				output.add(nextIndex);
				currentOffset = currentOffset.plus(spacing);
			}
			currentOffset = new Rational(0, 1);
			currentMeasure++;
		}
		
		return output;
	}
	
	// Returns the lists of pitch-lists corresponding to a "melody" based on the voices specified,
	// the spacing between points, and start/end indices
	public List<List<Pitch>> getMelodyLine(List<InstructionIndex> indices, 
			Rational spacing) {
		List<List<Pitch>> output = new ArrayList<List<Pitch>>();
		
		for (InstructionIndex index : indices) {
			List<Pitch> pitchList = new ArrayList<Pitch>();

			// get the start and end indices
			Rational start = index.getMeasureOffset();
			Rational end = start.plus(spacing);
			for (Staff staff : _piece.getStaffs()) {
				int measureNumber = index.getMeasureNumber();
				Measure measure = staff.getMeasures().get(measureNumber);
				for (Voice voice : measure.getVoices()) {
					List<Timestep> timesteps = getElementsAtUntil(start, end, voice.getMultiNotes());
					for (Timestep timestep : timesteps) {
						// get pitches from each note
						MultiNote note = (MultiNote) timestep;
						for (Pitch pitch : note.getPitches()) {
							// add each pitch to pitchList
							pitchList.add(pitch);
						}
					}
				}
			}
		
		}
		
		return output;
	}
}
