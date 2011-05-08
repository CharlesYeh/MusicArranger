package music;
/*
 * Represents a scale degree and an accidental if it has one (bIII, i, #IV, etc.)
 */
public class ScaleDegree {
	int _degreeNumber;
	Accidental _accidental;
	
	public ScaleDegree(int degreeNumber, Accidental accidental) {
		_degreeNumber = degreeNumber;
		_accidental = accidental;
	}
	
	public int getDegreeNumber() {
		return _degreeNumber;
	}
	
	public Accidental getAccidental(){
		return _accidental;
	}
	
	// Gives the offset from the root of the key as an integer, number of half steps
	public int getPitchOffset(boolean isMajor) {
		int pitchOffset;
		switch (_degreeNumber) {
			case 1:
				pitchOffset = 0;
				break;
			case 2:
				pitchOffset = 2;
				break;
			case 3:
				if (isMajor) {
					pitchOffset = 4;
				}
				else {
					pitchOffset = 3;
				}
				break;
			case 4:
				pitchOffset = 5;
				break;
			case 5:
				pitchOffset = 7;
				break;
			case 6:
				if (isMajor) {
					pitchOffset = 9;
				}
				else {
					pitchOffset = 8;
				}
				break;
			case 7:
				if (isMajor) {
					pitchOffset = 11;
				}
				else {
					pitchOffset = 10;
				}
				break;
			default:
				throw new RuntimeException("Scale degree outside of 1-7");
		}
		pitchOffset += _accidental.intValue();
		
		return pitchOffset;
	}
}
