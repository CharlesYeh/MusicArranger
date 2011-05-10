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
	
	public Pitch(NoteLetter noteLetter, Accidental accidental) {
		this(noteLetter, 0, accidental, false);
	}
	
	public Pitch copy() {
		return new Pitch(_noteLetter, _octave, _accidental, false);
	}
	
	public NoteLetter getNoteLetter() {
		return _noteLetter;
	}
	
	public int getOctave() {
		return _octave;
	}
	
	public int getLineNumber() {
		return _noteLetter.intValue() + _octave * 7;
	}
	
	public Accidental getAccidental() {
		return _accidental;
	}
	
	public boolean getIsTiedToNext() {
		return _isTiedToNext;
	}
	
	public boolean equals(Pitch pitch) {
		return equalsName(pitch) && (_octave == pitch.getOctave());
	}
	
	// Checks if two pitches are the same, EXCLUDING THE OCTAVE
	public boolean equalsName(Pitch pitch) {
		return (_noteLetter == pitch.getNoteLetter()) && (_accidental == pitch.getAccidental());
	}
	
	public String toString() {
		return " " + _octave + _noteLetter + _accidental;
	}
	
	// adds an Interval to a Pitch and returns that pitch, NOT CONSIDERING THE OCTAVE
	public Pitch addInterval(Interval interval) {
		int pitchLetterNumber = _noteLetter.intValue();
		int newLetterNumber = (pitchLetterNumber + interval.getSize() - 1) % 7;
		int pitchPitchNumber = _noteLetter.pitchValue() + _accidental.intValue();
		int newPitchNumber = (pitchPitchNumber + interval.getHalfSteps()) % 12;
		
		int accidentalNumber = newPitchNumber - NoteLetter.getNoteLetter(newLetterNumber).pitchValue();
		if (accidentalNumber < -6) accidentalNumber += 12;
		if (accidentalNumber > 6) accidentalNumber -= 12;
		
		NoteLetter noteLetter = NoteLetter.getNoteLetter(newLetterNumber);
		Accidental accidental = Accidental.getAccidental(accidentalNumber);
		Pitch newPitch = new Pitch(noteLetter, 0, accidental);
		
		return newPitch;
	}
	
	// Calculates the interval from one pitch up to another, DOES NOT CONSIDER
	// OCTAVES
	public Interval calcIntervalTo(Pitch upperPitch) {
		Pitch lowerPitch = this;

		int pitchUpNoteValue = upperPitch.getNoteLetter().intValue();
		int pitchLowNoteValue = lowerPitch.getNoteLetter().intValue();
		int pitchUpPitchValue = upperPitch.getNoteLetter().pitchValue() + upperPitch.getAccidental().intValue();
		int pitchLowPitchValue = lowerPitch.getNoteLetter().pitchValue() + lowerPitch.getAccidental().intValue();

		int letterDif = (pitchUpNoteValue - pitchLowNoteValue) % 7;
		if (letterDif < 0) letterDif += 7;
		int pitchDif = (pitchUpPitchValue - pitchLowPitchValue) % 12;
		if (pitchDif < 0) pitchDif += 12;
		
		int accidentalNum = pitchDif - NoteLetter.getNoteLetter(letterDif).pitchValue();
		
		return new Interval(accidentalNum, letterDif + 1);
	}

	public void setOctave(int newOctave) {
		
		if(newOctave < 12 && newOctave >= 0)
			_octave = newOctave;
	}
	
	// Returns -1, 0, 1 for p is lower than, equal to, or higher than this pitch.
	public int compareTo(Pitch p) {
		
		if(this.equals(p)){
			
			return 0;
		}
		else {
			if(_octave < p.getOctave()) {
				
				return -1;
			}
			else if(_octave > p.getOctave()){
				return 1;
			}
			else {
				int thisPitch = _noteLetter.pitchValue() + _accidental.intValue();
				int pPitch = p.getNoteLetter().pitchValue() + p.getAccidental().intValue();
				
				if(thisPitch < pPitch) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
	}
}