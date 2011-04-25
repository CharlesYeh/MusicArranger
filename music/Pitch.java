package music;

public class Pitch {
	NoteLetter _noteLetter;		// Letter representation of note
	int _octave;				// Specifies the octave range of a note
	Accidental _accidental;		// Tells what accidentals are applied to the note
	boolean _isTiedToNext;		// If true, specifies that a note in the next MultiNote is tied to this note

	public Pitch(NoteLetter noteLetter, int octave, Accidental accidental, boolean isTiedToNext){
		_noteLetter = noteLetter;
		_octave = octave;
		_accidental = accidental;
		_isTiedToNext = isTiedToNext;
	}
	
	public Pitch(NoteLetter noteLetter, int octave, Accidental accidental){
		this(noteLetter, octave, accidental, false);
	}
	
	public NoteLetter getNoteLetter() {
		return _noteLetter;
	}
	
	public int getOctave() {
		return _octave;
	}
	
	public Accidental getAccidental() {
		return _accidental;
	}
	
	public boolean getIsTiedToNext() {
		return _isTiedToNext;
	}
}