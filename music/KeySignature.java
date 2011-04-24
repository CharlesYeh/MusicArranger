package music;

/* KeySig represents a musical key signature, which specifies the key of the current piece, and
 * the accidentals it contains.
 */
public class KeySignature extends Duration {
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
}