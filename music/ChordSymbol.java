package music;

import java.util.ArrayList;

/* ChordSymbol is a class that defines the roman numeral chord analysis for a particular selection
 * within the piece.
 */
public class ChordSymbol extends Timestep {
	int _scaleDegree;			// which scale degree the chord starts on (I, ii, V, etc.) represented as an integer from 1-7
	ChordType _chordType;		// quality of the chord (major, minor, etc.)

	int _inversion;


	public ChordSymbol(int scaleDegree, ChordType chordType) {
		this(new Rational(), scaleDegree, chordType);
	}

	public ChordSymbol(int scaleDegree, ChordType chordType, int inv) {
		this(new Rational(), scaleDegree, chordType);

		_inversion = inv;
	}

	public ChordSymbol(Rational duration, int scaleDegree, ChordType chordType){
		super(duration);
		_scaleDegree = scaleDegree;
		_chordType = chordType;
	}

	public int getScaleDegree() {
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
		}

		switch (_scaleDegree) {
			case 1:
				return "I";
			case 2:
				return "ii";
			case 3:
				return "iii";
			case 4:
				return "IV";
			case 5:
				return "V";
			case 6:
				return "vi";
			case 7:
				return "vii";
	//		case 8:
	//			return "I";
		}

		return "";
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
					case 0: return "";
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

	//returns a list of integers that each represent one of the current chord's non-root notes as a certain pitches (half steps) above the root.
	public ArrayList<Integer> getNonRootNotes(){

		ArrayList<Integer> nonRootNotes = new ArrayList<Integer>();
		switch (_chordType) {
			case MAJOR:
				nonRootNotes.add(4);
				nonRootNotes.add(7);
				break;

			case MINOR:
				nonRootNotes.add(3);
				nonRootNotes.add(7);
				break;

			case DIMIN:
				nonRootNotes.add(3);
				nonRootNotes.add(6);
				break;

			case MAJOR7:
				nonRootNotes.add(4);
				nonRootNotes.add(7);
				nonRootNotes.add(11);
				break;

			case MAJORMINOR7:
				nonRootNotes.add(4);
				nonRootNotes.add(7);
				nonRootNotes.add(10);
				break;

			case MINOR7:
				nonRootNotes.add(3);
				nonRootNotes.add(7);
				nonRootNotes.add(10);
				break;

			case DIMIN7:
				nonRootNotes.add(3);
				nonRootNotes.add(6);
				nonRootNotes.add(9);
				break;

			case HDIMIN7:
				nonRootNotes.add(3);
				nonRootNotes.add(6);
				nonRootNotes.add(10);
				break;

			case ITAUG6:
				nonRootNotes.add(4);
				nonRootNotes.add(10);
				break;

			case FRAUG6:
				nonRootNotes.add(4);
				nonRootNotes.add(6);
				nonRootNotes.add(10);
				break;

			case GERAUG6:
				nonRootNotes.add(4);
				nonRootNotes.add(7);
				nonRootNotes.add(10);
				break;

		}

		return nonRootNotes;
	}

	//returns true if current ChordSymbol is equal to the given ChordSymbol, otherwise false
	public boolean equals(Object cs){

		ChordSymbol chordsym = (ChordSymbol) cs;
		if(chordsym.getInversion() == _inversion && chordsym.getScaleDegree() == _scaleDegree && chordsym.getChordType() == _chordType)
			return true;
		else
			return false;
	}
}