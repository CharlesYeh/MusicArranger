package instructions;

import music.Rational;
import music.Accidental;

public class InstructionIndex {
	int _staffNumber;		// which staff to modify, not specified if irrelevant
	int _measureNumber;		// which measure to modify, not specified if irrelevant
	int _voiceNumber;		// which voice to modify, not specified if irrelevant
	Rational _measureOffset;// rhythmic position of element, relative to start of measure
	
	//int _pitchNumber;		// which pitch to modify in the multinote, not specified if irrelevant
	int _lineNumber;
	Accidental _accidental;
	
	// constructors
	
	/*
	 * Editing staff data, only a staff number is needed
	 */
	public InstructionIndex(int staffNumber) {
		_staffNumber = staffNumber;
	}
	
	/*
	 * Editing measure data, specify staff and measure number
	 */
	public InstructionIndex(int staffNumber, int measureNumber) {
		this(staffNumber);
		_measureNumber = measureNumber;
	}
	
	/* 
	 * Editing an element of a measure, specify staff, measure number, and location
	 * in measure
	 */
	public InstructionIndex(int staffNumber, int measureNumber, Rational measureOffset) {
		this(staffNumber, measureNumber);
		_measureOffset = measureOffset;
	}
	
	/*
	 * Editing a note, specify staff, measure, voice number, and location in measure
	 */
	public InstructionIndex(int staffNumber, int measureNumber, int voiceNumber, 
			Rational measureOffset) {
		this(staffNumber, measureNumber, measureOffset);
		_voiceNumber = voiceNumber;
	}
	
	/*
	 * Editing a pitch, specify staff, measure, voice number, location in measure, and
	 * index of pitch in the MultiNote
	 */
	public InstructionIndex(int staffNumber, int measureNumber, int voiceNumber, 
			Rational measureOffset, int lineNumber) {
		this(staffNumber, measureNumber, voiceNumber, measureOffset);
		//_pitchNumber = pitchNumber;
		_lineNumber = lineNumber;
	}
	
	// getters and setters
	public int getStaffNumber() {
		return _staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this._staffNumber = staffNumber;
	}

	public int getVoiceNumber() {
		return _voiceNumber;
	}

	public void setVoiceNumber(int voiceNumber) {
		this._voiceNumber = voiceNumber;
	}

	public int getMeasureNumber() {
		return _measureNumber;
	}

	public void setMeasureNumber(int measureNumber) {
		this._measureNumber = measureNumber;
	}

	public Rational getMeasureOffset() {
		return _measureOffset;
	}

	public void setMeasureOffset(Rational measureOffset) {
		this._measureOffset = measureOffset;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this._lineNumber = lineNumber;
	}

	public Accidental getAccidental() {
		return _accidental;
	}

	public void setAccidental(Accidental accidental) {
		this._accidental = accidental;
	}
	
	public Accidental getAccidental() {
		return _accidental;
	}
	
	public void setAccidental(Accidental accid) {
		_accidental = accid;
	}
}
