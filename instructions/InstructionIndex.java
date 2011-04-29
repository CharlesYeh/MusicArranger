package instructions;

import music.Rational;

public class InstructionIndex {
	int _staffNumber;		// which staff to modify, not specified if irrelevant
	int _voiceNumber;		// which voice to modify, not specified if irrelevant
	Rational _timestamp;	// rhythmic position of element in song
	int _pitchNumber;		// which pitch to modify in the multinote, not specified if irrelevant
	
	// constructors
	public InstructionIndex(Rational timestamp, int staffNumber) {
		this(timestamp);
		_staffNumber = staffNumber;
	}
	
	public InstructionIndex(Rational timestamp, int staffNumber, int voiceNumber) {
		this(timestamp, staffNumber);
		_voiceNumber = voiceNumber;
	}
	
	public InstructionIndex(Rational timestamp, int staffNumber, int voiceNumber,
			int pitchNumber) {
		this(timestamp, staffNumber, voiceNumber);
		_pitchNumber = pitchNumber;
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

	public Rational getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(Rational timestamp) {
		this._timestamp = timestamp;
	}

	public int getPitchNumber() {
		return _pitchNumber;
	}

	public void setPitchNumber(int pitchNumber) {
		this._pitchNumber = pitchNumber;
	}

	public InstructionIndex(Rational timestamp) {
		_timestamp = timestamp;
	}
	
}
