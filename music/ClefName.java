package music;
/* ClefName is an enum type whose fields represent the possible types of clefs that a staff can
 * use.
 */
public enum ClefName {
	GCLEF(32, new Pitch(NoteLetter.G, 4, Accidental.NATURAL)), 
	FCLEF(24, new Pitch(NoteLetter.F, 3, Accidental.NATURAL)), 
	CCLEF(28, new Pitch(NoteLetter.C, 4, Accidental.NATURAL)); 
	// Corresponding to the line values of G4, F3, and C4, respectively.
	
	private int _centerValue;
	private Pitch _pitch;
	
	ClefName(int centerValue, Pitch pitch) {
		_centerValue = centerValue;
		_pitch = pitch;
	}
	
	public int centerValue() {
		return _centerValue;
	}
	
	public Pitch centerPitch() {
		return _pitch;
	}
}