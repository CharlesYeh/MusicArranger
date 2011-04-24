package music;

public class Pitch {
	NoteLetter _noteLetter;		// Letter representation of note
	int _octave;				// Specifies the octave range of a note
	Accidental _accidental;		// Tells what accidentals are applied to the note
	boolean _isTiedToNext;		// If true, specifies that a note in the next MultiNote is tied to this note

	public Pitch(){
		
	}
}