package music;
/* NoteLetter is an enum type whose fields represent the different possible pitches of a note, 
 * excluding accidentals.
 */
public enum NoteLetter {
	C(0, 0), D(1, 2), E(2, 4), F(3, 5), G(4, 7), A(5, 9), B(6, 11);
	
	private int _intValue;
	private int _pitchValue;
	
	NoteLetter(int intValue, int pitchValue) {
		_intValue = intValue;
		_pitchValue = pitchValue;
	}
	
	public static NoteLetter getNoteLetter(int v) {
		// v must be from 0 to 7
		v = v % 8;
		if (v < 0)
			v += 8;
		
		return NoteLetter.values()[v];
	}
	
	public int intValue() {
		return _intValue;
	}
	
	public int pitchValue() {
		return _pitchValue;
	}
}