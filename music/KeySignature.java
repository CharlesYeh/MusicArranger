package music;

/* KeySig represents a musical key signature, which specifies the key of the current piece, and
 * the accidentals it contains.
 */
public class KeySignature extends Timestep {
	int _accidentalNumber;			// Specifies the number of accidentals (negative numbers are flats, positives for sharps)
	boolean _isMajor;				// If true, indicates that the section of the piece is in the major mode, if false, minor.

	public KeySignature(Rational duration, int accidentalNumber, boolean isMajor) {
		super(duration);
		_accidentalNumber = accidentalNumber;
		_isMajor = isMajor;
	}

	public int getAccidentalNumber() {
		return _accidentalNumber;
	}

	public boolean getIsMajor() {
		return _isMajor;
	}

	public boolean equals(Object o) {
		KeySignature keysig = (KeySignature) o;
		return _isMajor == keysig.getIsMajor() && _accidentalNumber == keysig.getAccidentalNumber();
	}

	public int halfStepsFromC(){

		int returnVal = (_accidentalNumber * 7) % 12;
		if(returnVal < 0)
			returnVal = 12 + returnVal;

		return returnVal;
	}
	
	// returns the Note associated with a key signature
	public Pitch getKeySigPitch() {
		int noteLetterValue = (_accidentalNumber * 4) % 7;
		if (noteLetterValue < 0) noteLetterValue += 7;
		NoteLetter noteLetter = NoteLetter.getNoteLetter(noteLetterValue);
		
		int pitchValue = (_accidentalNumber * 7) % 12;
		if (pitchValue < 0) pitchValue += 12;
		int noteLetterPitchValue = noteLetter.pitchValue();
		int accidentalValue = noteLetterPitchValue - pitchValue;
		Accidental accidental = Accidental.getAccidental(accidentalValue);
		
		return new Pitch(noteLetter, 0, accidental);
	}
}