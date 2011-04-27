package instructions;

import music.Rational;

public class InstructionIndex {
	int _staffNumber;		// which staff to modify, not specified if irrelevant
	int _voiceNumber;		// which voice to modify, not specified if irrelevant
	Rational _timestamp;	// rhythmic position of element in song
	int _pitchNumber;		// which pitch to modify in the multinote, not specified if irrelevant
	
	public InstructionIndex(Rational timestamp) {
		_timestamp = timestamp;
	}
	
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
}
