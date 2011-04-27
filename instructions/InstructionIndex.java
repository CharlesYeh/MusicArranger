package instructions;

import music.Rational;

public class InstructionIndex {
	int _staffNumber;		// -1 if staff is irrelevant
	int _voiceNumber;		// -1 if voice is irrelevant
	Rational _timestamp;	// rhythmic position of element in song
	EditType _editedType;	// type of element
}
