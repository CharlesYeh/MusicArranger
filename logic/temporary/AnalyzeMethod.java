package logic.temporary;
import java.util.*;

import util.Graph;
import util.Node;

import music.*;

// Takes in a melody (represented as a list of lists of pitches) and
// returns the possible chords that could exist at each part of the melody
public class AnalyzeMethod {
	public Graph<ChordSymbol> _chordPreferencesGraph;
	
	public AnalyzeMethod() {}
	
	
	
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
		// TODO
		
		
		List<List<ChordSymbol>> output = new ArrayList<List<ChordSymbol>>();
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
	
	public void initMajorKeyGraph() {
		_chordPreferencesGraph = new Graph<ChordSymbol>();

		// create chords
		Node<ChordSymbol> chordI		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordii		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordiii		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordIV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordvi		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordviio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN));

		// V seven chords
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 0));
		Node<ChordSymbol> chordV65		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 1));
		Node<ChordSymbol> chordV43		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 2));
		Node<ChordSymbol> chordV42		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.FLAT), ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.GERAUG6));

		// I -> I ii III iv V VI VIio N It6 Fr6 Ger6
		// ii -> V VIio N It6 Fr6 Ger6
		// III -> VI iv
		// iv -> I V VIio ii N It6 Fr6 Ger6
		// V -> I VI
		// VI -> ii iv
		// VIi -> I V ii N It6 Fr6 Ger6

		///Adding edges for chordI
		_chordPreferencesGraph.addEdge(chordI, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordiii, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordIV, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordvi, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordI, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordI, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordI, chordGer6, 	2);

		//Adding edges for chordii
		_chordPreferencesGraph.addEdge(chordii, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordii, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordii, chordN, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordGer6,	3);

		//Adding edges for chordIII
		_chordPreferencesGraph.addEdge(chordiii, chordvi, 	1);
		_chordPreferencesGraph.addEdge(chordiii, chordIV, 	1);

		//Adding edges for chordiv
		_chordPreferencesGraph.addEdge(chordIV, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordI, 	2); //plagal cadence
		_chordPreferencesGraph.addEdge(chordIV, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordGer6,	2);

		//Adding edges for chordV
		_chordPreferencesGraph.addEdge(chordV, chordI, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV, chordvi, 	2); //Deceptive cadence

		//Adding edges for chordVI
		_chordPreferencesGraph.addEdge(chordvi, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordIV, 	1);

		//Adding edges for chordVIio
		_chordPreferencesGraph.addEdge(chordviio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordI, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviio, chordii,	1);
		_chordPreferencesGraph.addEdge(chordviio, chordN, 	2);

	}

	public void initMinorKeyGraph() {
		_chordPreferencesGraph = new Graph<ChordSymbol>();

		// create chords
		Node<ChordSymbol> chordi		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordiio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.DIMIN));
		Node<ChordSymbol> chordIII		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordiv		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordVI		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordviio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN));

		// V seven chords
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 0));
		Node<ChordSymbol> chordV65		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 1));
		Node<ChordSymbol> chordV43		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 2));
		Node<ChordSymbol> chordV42		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.FLAT), ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.GERAUG6));

		// i -> iio III iv V VI viio N It6 Fr6 Ger6
		// iio -> V viio N It6 Fr6 Ger6
		// III -> VI iv
		// iv -> I V viio iio N It6 Fr6 Ger6
		// V -> I VI
		// VI -> iio iv
		// VIi -> I V iio N It6 Fr6 Ger6

		///Adding edges for chordi
		_chordPreferencesGraph.addEdge(chordi, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordIII, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordiv, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordVI, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordi, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordi, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordi, chordGer6, 	2);

		//Adding edges for chordiio
		_chordPreferencesGraph.addEdge(chordiio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordiio, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordiio, chordN, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordGer6,	3);

		//Adding edges for chordIII
		_chordPreferencesGraph.addEdge(chordIII, chordVI, 	1);
		_chordPreferencesGraph.addEdge(chordIII, chordiv, 	1);

		//Adding edges for chordiv
		_chordPreferencesGraph.addEdge(chordiv, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordviio,	1);
		_chordPreferencesGraph.addEdge(chordiv, chordi, 	2); //plagal cadence
		_chordPreferencesGraph.addEdge(chordiv, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordGer6,	2);

		//Adding edges for chordV
		_chordPreferencesGraph.addEdge(chordV, chordi, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV, chordVI, 	2); //Deceptive cadence

		//Adding edges for chordVI
		_chordPreferencesGraph.addEdge(chordVI, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordVI, chordi, 	1);

		//Adding edges for chordviio
		_chordPreferencesGraph.addEdge(chordviio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordi, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviio, chordiio,1);
		_chordPreferencesGraph.addEdge(chordviio, chordN, 	2);

	}
}
