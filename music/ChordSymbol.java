package music;

/* ChordSymbol is a class that defines the roman numeral chord analysis for a particular selection
 * within the piece.
 */
public class ChordSymbol{
	int _scaleDegree;			// which scale degree the chord starts on (I, ii, V, etc.) represented as an integer from 1-7
	ChordType _chordType;		// quality of the chord (major, minor, etc.)
	
	public ChordSymbol(){
		
	}
	
	public int getScaleDegree() {
		return _scaleDegree;
	}
	
	public ChordType getChordType() {
		return _chordType;
	}
}