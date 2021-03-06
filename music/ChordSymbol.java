package music;

import java.util.ArrayList;

/* ChordSymbol is a class that defines the roman numeral chord analysis for a particular selection
 * within the piece.
 */
public class ChordSymbol extends Timestep {
	ScaleDegree _scaleDegree;			// which scale degree the chord starts on (I, ii, V, etc.) represented as an integer from 1-7
								// set to 0 if scale degree is inapplicable
	ChordType _chordType;		// quality of the chord (major, minor, etc.)

	int _inversion;				// 0 signifies no inversion or unspecified


	public ChordSymbol(ScaleDegree scaleDegree, ChordType chordType) {
		this(new Rational(), scaleDegree, chordType, 0);
	}

	public ChordSymbol(ScaleDegree scaleDegree, ChordType chordType, int inv) {
		this(new Rational(), scaleDegree, chordType, inv);
	}

	public ChordSymbol(Rational duration, ScaleDegree scaleDegree, ChordType chordType){
		this(duration, scaleDegree, chordType, 0);
	}

	public ChordSymbol(Rational duration, ScaleDegree scaleDegree, ChordType chordType, int inv){
		super(duration);
		_scaleDegree = scaleDegree;
		_chordType = chordType;
		_inversion = inv;
	}

	public ScaleDegree getScaleDegree() {
		return _scaleDegree;
	}

	public ChordType getChordType() {
		return _chordType;
	}

	public int getInversion(){
		return _inversion;
	}

	public String getSymbolText() {
		switch (_chordType){
			case NEAPOLITAN:
				return "N";
			case ITAUG6:
				return "It";
			case FRAUG6:
				return "Fr";
			case GERAUG6:
				return "Ger";
			case UNSPECIFIED:
			case BLANK:
				return "*";
		}
		
		String chordName = "";
		switch (_scaleDegree.getDegreeNumber()) {
			case 1:
				chordName = "I";
				break;
			case 2:
				chordName = "II";
				break;
			case 3:
				chordName = "III";
				break;
			case 4:
				chordName = "IV";
				break;
			case 5:
				chordName = "V";
				break;
			case 6:
				chordName = "VI";
				break;
			case 7:
				chordName = "VII";
				break;
		}
		
		if (_chordType == ChordType.MINOR || _chordType == ChordType.DIMIN) {
			chordName = chordName.toLowerCase();
		}
		
		return chordName;
	}

	public String getTopInversionText() {
		switch (_chordType) {
			case MAJOR:
			case MINOR:
			case DIMIN:
			case NEAPOLITAN:
				// not a 7th chord
				switch (_inversion) {
					case 0: return "";
					case 1: return "6";
					case 2: return "6";
				}
				break;

			case MAJOR7:
			case MAJORMINOR7:
			case MINOR7:
			case DIMIN7:
			case HDIMIN7:
				// 7th chord
				switch (_inversion) {
					case 0: return "7";
					case 1: return "6";
					case 2: return "4";
					case 3: return "4";
				}

				break;

			case ITAUG6:
			case FRAUG6:
			case GERAUG6:
				return "+6";
		}

		return "";
	}

	public String getBotInversionText() {
		switch (_chordType) {
			case MAJOR:
			case MINOR:
			case DIMIN:
			case NEAPOLITAN:
				// not a 7th chord
				switch (_inversion) {
					case 0: return "";
					case 1: return "";
					case 2: return "4";
				}
				break;

			case MAJOR7:
			case MAJORMINOR7:
			case MINOR7:
			case DIMIN7:
			case HDIMIN7:
				// 7th chord
				switch (_inversion) {
					case 0: return "";
					case 1: return "5";
					case 2: return "3";
					case 3: return "2";
				}

				break;
		}

		return "";
	}
	
	public boolean equals(Object chordsym){
		if (chordsym == null)
			return false;
		
		ChordSymbol toCompare = (ChordSymbol) chordsym;
		if (toCompare.getInversion() == _inversion && toCompare.getScaleDegree().equals(_scaleDegree) && toCompare.getChordType() == _chordType)
			return true;
		else
			return false;
	}
	
	public ChordSymbol copy() {
		return new ChordSymbol(_duration, _scaleDegree, _chordType, _inversion);
	}
	
	public String toString() {
		return " Duration: " + _duration + " " + _scaleDegree + " " + _chordType + " Inversion:" + _inversion;
	}
}