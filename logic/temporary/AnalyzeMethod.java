package logic.temporary;
import java.util.*;

import util.Graph;
import util.Node;

import music.*;

// Takes in a melody (represented as a list of lists of pitches) and
// returns the possible chords that could exist at each part of the melody
public class AnalyzeMethod extends logic.Analyzer{
	public Graph<ChordSymbol> _chordPreferencesGraph;
	
	public AnalyzeMethod() {}
	
	
	//Takes in a List of ChordSymbol objects, a Pitch object, and a KeySignature object,
	//and removes all ChordSymbol objects in the list that represent chords that do not contain the given pitch
	public void filterChords(List<ChordSymbol> chords, Pitch pitch,
			KeySignature keySig) {
		int numChords = chords.size();
		ListIterator<ChordSymbol> iter = chords.listIterator();
		
		for (int i = 0; i < numChords; i++) {
			ChordSymbol nextChord = iter.next();
			List<Pitch> assocPitches = getChordPitches(nextChord, keySig);
			boolean containsPitch = false;
			
			for (Pitch assocPitch : assocPitches) {
				containsPitch = containsPitch || pitch.equalsName(assocPitch);
			}
			
			if (!containsPitch) {
				iter.remove();
			}
		}
	}
	
	// Each list of pitches is turned into a List<ChordSymbol>, which represent the possible
	// chords it could belong to.
	public List<List<ChordSymbol>> analyzeMelody(List<List<Pitch>> melodyLine,
			KeySignature keySig) {
		
		if (keySig.getIsMajor()) {
			initMajorKeyGraph();
		}
		else {
			initMinorKeyGraph();
		}
		
		ArrayList<List<ChordSymbol>> output = new ArrayList<List<ChordSymbol>>();
		for(List<Pitch> melodyInstance : melodyLine) { //for each instance in the melody
			
			//get all the nodes that are possible within the _chordPreferencesGraph
			List<Node<ChordSymbol>> chordNodes = _chordPreferencesGraph.getNodes();
			List<ChordSymbol> possibleChords = new LinkedList<ChordSymbol>();
			for (Node<ChordSymbol> chordNode : chordNodes) {
				possibleChords.add(chordNode.getValue());
			}
			
			//filter out the chords by going through each of the pitches in that melody instance 
			for(Pitch pitch : melodyInstance) {
				
				filterChords(possibleChords, pitch, keySig);
			}
			
			//add this List of ChordSymbol objects to the output list
			output.add(possibleChords);
		}
		
		return output;

	}
	
	// Given a pitch and the key it belongs to, returns the scale degree of that pitch.
	public ScaleDegree calcScaleDegree(Pitch pitch, Pitch key, boolean isMajor) {
		int letterDif = letterDifference(pitch, key);
		int pitchDif = pitchDifference(pitch, key);
		
		int scaleDegreeNumber = letterDif;
		
		int dfaultPitchDif;
		// pitch difference if the letter difference were to have no accidental
		
		if (isMajor) {
			dfaultPitchDif = NoteLetter.getNoteLetter(letterDif).pitchValue();
		}
		else { // if minor
			letterDif  = (letterDif - 2) % 7;
			if (letterDif < 0) letterDif += 7;
			dfaultPitchDif = (NoteLetter.getNoteLetter(letterDif).pitchValue() -
				NoteLetter.A.pitchValue()) % 12;
			if (dfaultPitchDif < 0) dfaultPitchDif += 12;
		}
		int accidentalValue = pitchDif - dfaultPitchDif;
		Accidental accidental = Accidental.getAccidental(accidentalValue);

		return new ScaleDegree(scaleDegreeNumber, accidental);
	}
	
	// returns the Note associated with a key signature
	public Pitch getKeySigPitch(KeySignature keySig) {
		int accidentalNumber = keySig.getAccidentalNumber();
		
		int noteLetterValue = (accidentalNumber * 4) % 7;
		if (noteLetterValue < 0) noteLetterValue += 7;
		NoteLetter noteLetter = NoteLetter.getNoteLetter(noteLetterValue);
		
		int pitchValue = (accidentalNumber * 7) % 12;
		if (pitchValue < 0) pitchValue += 12;
		int noteLetterPitchValue = noteLetter.pitchValue();
		int accidentalValue = pitchValue - noteLetterPitchValue;
		Accidental accidental = Accidental.getAccidental(accidentalValue);
		
		if (keySig.getIsMajor()) {
			return new Pitch(noteLetter, accidental);
		}
		else {
			return addInterval(new Pitch(noteLetter, accidental), new Interval(IntervalType.MAJOR, 6));
		}
	}
	
	// Returns the letter difference between two pitches
	// For example, letterDifference between F# and Bb is 4 (four letters to get from F to B)
	public int letterDifference(Pitch pitch1, Pitch pitch2) {
		int pitch1NoteValue = pitch1.getNoteLetter().intValue();
		int pitch2NoteValue = pitch2.getNoteLetter().intValue();

		int letterDif = (pitch1NoteValue - pitch2NoteValue) % 7;
		if (letterDif < 0) letterDif += 7;
		
		return letterDif;
	}
	
	// Returns the pitch difference between two pitches (in half steps), DISREGARDING OCTAVES
	// For example, pitchDifference between F# and Bb is 8
	public int pitchDifference(Pitch pitch1, Pitch pitch2) {
		int pitch1PitchValue = pitch1.getNoteLetter().pitchValue() + pitch1.getAccidental().intValue();
		int pitch2PitchValue = pitch2.getNoteLetter().pitchValue() + pitch2.getAccidental().intValue();
		
		int pitchDif = (pitch1PitchValue - pitch2PitchValue) % 12;
		if (pitchDif < 0) pitchDif += 12;
		
		return pitchDif;
	}
	
	// Takes in a pitch and an interval, finds the pitch at that interval above the pitch,
	// and returns it, NOT CONSIDERING OCTAVES
	public Pitch addInterval(Pitch pitch, Interval interval) {
		int pitchLetterNumber = pitch.getNoteLetter().intValue();
		int newLetterNumber = (pitchLetterNumber + interval.getSize() - 1) % 7;
		int pitchPitchNumber = pitch.getNoteLetter().pitchValue() + pitch.getAccidental().intValue();
		int newPitchNumber = (pitchPitchNumber + interval.getHalfSteps()) % 12;
		
		int accidentalNumber = newPitchNumber - NoteLetter.getNoteLetter(newLetterNumber).pitchValue();
		if (accidentalNumber < -6) accidentalNumber += 12;
		if (accidentalNumber > 6) accidentalNumber -= 12;
		
		NoteLetter noteLetter = NoteLetter.getNoteLetter(newLetterNumber);
		Accidental accidental = Accidental.getAccidental(accidentalNumber);
		Pitch newPitch = new Pitch(noteLetter, 0, accidental);
		
		return newPitch;
	}
	public List<Pitch> getChordPitches(ChordSymbol chordSymbol, KeySignature keySig){
		boolean isMajor = keySig.getIsMajor();
		List<Pitch> chordPitches = new ArrayList<Pitch>();
		
		ChordType chordType = chordSymbol.getChordType();
		Pitch keySigPitch = getKeySigPitch(keySig);
		Pitch rootNote = addInterval(keySigPitch, chordSymbol.getScaleDegree().getIntervalFromRoot(isMajor));
		
		
		switch (chordType) {
			case MAJOR:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				break;

			case MINOR:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				break;

			case DIMIN:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 5)));
				break;

			case MAJOR7:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 7)));
				break;

			case MAJORMINOR7:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 7)));
				break;

			case MINOR7:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 7)));
				break;

			case DIMIN7:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 7)));
				break;
				
			case NEAPOLITAN:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				break;

			case HDIMIN7:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.DIMINISHED, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MINOR, 7)));
				break;

			case ITAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.AUGMENTED, 6)));
				break;

			case FRAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.AUGMENTED, 4)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.AUGMENTED, 6)));
				break;

			case GERAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(addInterval(rootNote, new Interval(IntervalType.AUGMENTED, 6)));
				break;

		}

		return chordPitches;
	}
	
	
}
