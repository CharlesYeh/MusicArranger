package instructions;

import music.*;
import java.util.*;

public class FileInstructionNew extends FileInstruction {
	List<Clef> _clefs;
	int _numMeasures;
	int _timeSigNumer;
	int _timeSigDenom;
	int _accidentals;
	boolean _isMajor;
	
	/* When creating a new score, the following information is necessary
	 * 		- number of staffs
	 * 		- number of measures
	 * 		- key signature, represented by numerator and denominator
	 * 		- time signature, represented by the number of accidentals
	 */
	public FileInstructionNew(List<Clef> clefs, int numMeasures,
			int timeSigNumer, int timeSigDenom, int accidentals, boolean isMajor) {
		_clefs = clefs;
		_numMeasures = numMeasures;
		_timeSigNumer = timeSigNumer;
		_timeSigDenom = timeSigDenom;
		_accidentals = accidentals;
		_isMajor = isMajor;
	}

	public List<Clef> getClefs() {
		return _clefs;
	}
	public int getNumMeasures() {
		return _numMeasures;
	}
	public int getAccidentals() {
		return _accidentals;
	}
	public int getTimeSigNumer() {
		return _timeSigNumer;
	}
	public int getTimeSigDenom() {
		return _timeSigDenom;
	}
	public boolean getIsMajor() {
		return _isMajor;
	}
}
