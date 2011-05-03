package instructions;

import music.*;

public class FileInstructionNew extends FileInstruction {
	int _numStaffs;
	int _numMeasures;
	int _keySigNumer;
	int _keySigDenom;
	int _accidentals;
	boolean _isMajor;
	
	/* When creating a new score, the following information is necessary
	 * 		- number of staffs
	 * 		- number of measures
	 * 		- key signature, represented by numerator and denominator
	 * 		- time signature, represented by the number of accidentals
	 */
	public FileInstructionNew(Object src, int numStaffs, int numMeasures,
			int keySigNumer, int keySigDenom, int accidentals, boolean isMajor) {
		super(src);
		_numStaffs = numStaffs;
		_numMeasures = numMeasures;
		_keySigNumer = keySigNumer;
		_keySigDenom = keySigDenom;
		_accidentals = accidentals;
		_isMajor = isMajor;
	}

	public int getNumStaffs() {
		return _numStaffs;
	}
	public int getNumMeasures() {
		return _numMeasures;
	}
	public int getAccidentals() {
		return _accidentals;
	}
	public int getKeySigNumer() {
		return _keySigNumer;
	}
	public int getKeySigDenom() {
		return _keySigDenom;
	}
	public boolean getIsMajor() {
		return _isMajor;
	}
}
