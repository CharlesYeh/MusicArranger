package music;
/* ClefName is an enum type whose fields represent the possible types of clefs that a staff can
 * use.
 */
public enum ClefName {
	GCLEF(32), FCLEF(24), CCLEF(28); // Corresponding to the line values of G4, F3, and C4, respectively.
	
	private int _centerValue;
	
	ClefName(int centerValue) {
		_centerValue = centerValue;
	}
	
	public int centerValue() {
		return _centerValue;
	}
}