package music;

/* Clef represents a musical clef within the piece, used for specifying the locations of specific
 * pitches within the staff.
 */
public class Clef extends Duration {
	ClefName _clefName;			// Specifies what type of clef to draw
	int _centerLine;			// Specifies line of the staff the clef centers upon; center line is 0, negative transposes down, positive transposes up
	
	// constructor must take in the duration, name, and center line of the clef
	public Clef(Rational duration, ClefName clefName, int centerLine) {
		super(duration);
		_clefName = clefName;
		_centerLine = centerLine;
	}
	
	public ClefName getClefName() {
		return _clefName;
	}
	
	public int getCenterLine() {
		return _centerLine;
	}
	
}

