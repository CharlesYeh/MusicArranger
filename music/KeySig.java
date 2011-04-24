package music;

/* KeySig represents a musical key signature, which specifies the key of the current piece, and
 * the accidentals it contains.
 */
public class KeySig {
	Duration _duration;				// Specifies the number of beats the key signature lasts for
	int _accidentalNumber;			// Specifies the number of accidentals 
	Accidental _accidentalType;		// Specifies type of accidental (sharp or flat)
	boolean _isMajor;				// If true, indicates that the section of the piece is in the major mode, if false, minor.	
	
	public KeySig() {
		
	}
	
	public int getAccidentalNumber() {
		return _accidentalNumber;
	}
	
	public Accidental getAccidentalType() {
		return _accidentalType;
	}
	
	public boolean getIsMajor() {
		return _isMajor;
	}
	
	public Duration getDuration() {
		return _duration;
	}
}