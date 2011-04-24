package music;

/* Clef represents a musical clef within the piece, used for specifying the locations of specific
 * pitches within the staff.
 */
public class Clef {
	Duration _duration;		// Specifies the number of beats the clef lasts for
	ClefName _clefName;		// Specifies what type of clef to draw
	int centerLine;			// Specifies line of the staff the clef centers upon
	
	public Clef(){
	}
	
	public ClefName getClefName() {
		return _clefName;
	}
	
	public int getCenterLine() {
		return _centerLine;
	}
	
	public Duration getDuration() {
		return _duration;
	}
}