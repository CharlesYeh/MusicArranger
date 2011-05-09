package logic;

import java.lang.Thread;
import music.ChordSymbol;
import music.ChordType;
import music.Interval;
import music.IntervalType;
import music.NoteLetter;
import music.Piece;
import music.KeySignature;
import music.Pitch;
import music.ScaleDegree;
import java.util.ArrayList;
import util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import music.Accidental;

public class Analyzer extends Thread {

	Graph<ChordSymbol> _chordPreferencesGraph;


	public Analyzer() {
		initMajorKeyGraph();
	}

	public void addChordSymbols(Piece p) {
		// go through piece matching notes to chords

	}
	
	public Graph<ChordSymbol> getChordPreferencesGraph() {
		
		return _chordPreferencesGraph;
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

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.GERAUG6));

		// I -> I ii III iv V VI VIio N It6 Fr6 Ger6
		// ii -> V VIio N It6 Fr6 Ger6
		// III -> VI iv
		// iv -> I V VIio ii N It6 Fr6 Ger6
		// V -> I VI
		// VI -> ii iv
		// VIi -> I V ii N It6 Fr6 Ger6

		//Default startingNode to be ChordI (only for testing purposes)
		_chordPreferencesGraph.addStartingNode(chordI, 1);
		
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

		//Adding edges for chordIV
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

		//Adding edges for chordvi
		_chordPreferencesGraph.addEdge(chordvi, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordIV, 	1);

		//Adding edges for chordviio
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

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.NEAPOLITAN));
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

		//Default startingNode to be ChordI (only for testing purposes)
		_chordPreferencesGraph.addStartingNode(chordi, 1);
		
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
//	public void listPossibleChords(KeySignature key, ChordSymbol chord){
//
//
//	}

	/*
	 *this function takes a pitch, and a key signature, so that it could compare the pitch with the chords within the _chordPreferencesGraph,
	 *and return a list of ChordSymbols which correspond to chords that contain that pitch.
	 *
	 */
	/*public ArrayList<ChordSymbol> findMatchingChords(int pitch, KeySignature keysig) {

		int halfStepsFromC = pitch % 12;
		int key = keysig.halfStepsFromC();
		int pitchDegree = halfStepsFromC - key; //pitch degree is the number of half steps above the tonic 0-11
		if (pitchDegree < 0)
			pitchDegree = pitchDegree + 12;
		System.out.println("pitchDegree = " + pitchDegree);

		if(keysig.getIsMajor())
			initMajorKeyGraph();
		else
			initMinorKeyGraph();

		ArrayList<ChordSymbol> matchingChords = new ArrayList<ChordSymbol>();
		for(Node node : _chordPreferencesGraph.getNodes()) {

			ChordSymbol chordsym = (ChordSymbol) node.getValue();
//			System.out.println("=========Current chord is: " + chordsym.getSymbolText() + "==========");

			//Assigns the a pitch degree to the chord
			int	chordNotePitchDegree;
			if(chordsym.getChordType() == ChordType.NEAPOLITAN) {

				chordNotePitchDegree = 1;
			}
			else if(chordsym.getChordType() == ChordType.ITAUG6 || chordsym.getChordType() == ChordType.FRAUG6 || chordsym.getChordType() == ChordType.GERAUG6) {

				chordNotePitchDegree = 8;
			}
			else {

				chordNotePitchDegree = scaleDegreeToPitchDegree(chordsym.getScaleDegree());
			}

			//checking if the root of the chord matces the given pitch
			if(pitchDegree == chordNotePitchDegree) {

				matchingChords.add(chordsym);
			}
			else {
				//checking if the non-root notes of the chord match the given pitch
				List<Integer> nonRootNotes = chordsym.getNonRootNotes();
				for(int halfStepsFromRoot : nonRootNotes) {

					int nonrootChordPitchDegree = ((chordNotePitchDegree + halfStepsFromRoot) % 12);
//					System.out.println("nonrootChordPitchDegree = " + nonrootChordPitchDegree);
					if(nonrootChordPitchDegree == pitchDegree) {

						matchingChords.add(chordsym);
						break;
					}
				}
			}
		}

		return matchingChords;
	}
*/

	/*
	 * Takes a list of pitches and finds chord progressions that match that list of pitches. Each chord progression will be returned as a list of pitches.
	 * The more commonly used chord progressions will be considered, depending on the weight of the edges that link the chords in the chord graph.
	 * A list of best progressions will be returned, in the form of an ArrayList<Arraylist<ChordSymbol>>
	 *
	 */
//	public ArrayList<ArrayList<ChordSymbol>> matchPitchesToChordProgressions(ArrayList<Integer> pitchList, KeySignature keysig) {
//
//		ArrayList<ArrayList<ChordSymbol>> allPossibleChords = new ArrayList<ArrayList<ChordSymbol>>();
//		for(int pitch : pitchList) {
//
//			allPossibleChords.add(findMatchingChords(pitch, keysig));
//		}
//
//		ArrayList<ArrayList<ChordSymbol>> matchingChordProgressions;
////		ListIterator pitchItr = pitchList.listIterator();
//
//		if(pitchList.size() != 0)
//			matchingChordProgressions = matchPitchesToChordProgressionsHelper(pitchList, 0
//
//				, allPossibleChords, matchingChordProgressions);
//
//		return matchingChordProgressions;
//	}


	/*
	 *Helper function for matchPitchesToChordProgressions() that implements recursion
	 *
	 */
//	public ArrayList<ArrayList<ChordSymbol>> matchPitchesToChordProgressionsHelper(ListIterator<ArrayList<ChordSymbol>> allPossibleChordsItr, Node currentChord, ArrayList<ArrayList<ChordSymbol>> matchingChordProgressions) {
//
//		if(!allPossibleChordsItr.hasNext()) {
//
//
//		}
//		else {
//
//
//		}
//	}


	public Graph<ChordSymbol> createPossibleProgressionsGraph(List<List<ChordSymbol>> matchingChordsList){

		Graph<ChordSymbol> matchingProgressionsGraph = new Graph<ChordSymbol>();

		if(matchingChordsList.size() == 0) {

			return null;
		}

		//convert matchingChordList to a list of equal dimension with each ChordSymbol encased in a Node structure
		List<List<Node<ChordSymbol>>> matchingNodesList = new ArrayList<List<Node<ChordSymbol>>>();
		for(List<ChordSymbol> matchingChords : matchingChordsList) {

			List<Node<ChordSymbol>> matchingNodes = new ArrayList<Node<ChordSymbol>>();
			for(ChordSymbol chordsym : matchingChords) {

				Node<ChordSymbol> node = new Node<ChordSymbol>(chordsym);
				matchingNodes.add(node);

			}

			matchingNodesList.add(matchingNodes);
		}


		//use the new list of possible chord nodes to create the the matchingProgressions graph

		//first add the nodes of the list of chords that match the first note into the Graph.
		//These are the beggining chords for all of the chord progressions that can be potentially generated.

		List<Node<ChordSymbol>> firstChordNodes = matchingNodesList.get(0);
		for(Node<ChordSymbol> node : firstChordNodes) {

			matchingProgressionsGraph.addStartingNode(node, 1);
		}

		if(matchingNodesList.size() > 0) {

			for(Node<ChordSymbol> node : firstChordNodes) {

				createPossibleProgressionsGraphHelper(node, matchingNodesList, 1, matchingProgressionsGraph);
			}
		}

		return matchingProgressionsGraph;
	}

	//helper function for createPossibleProgressionsGraph that implements recursion to add the edges that complete the chord progression graph
	private void createPossibleProgressionsGraphHelper(Node<ChordSymbol> currentNode,
								List<List<Node<ChordSymbol>>> matchingNodesList, int nextNodesListIdx, Graph<ChordSymbol> progressionsGraph) {

		//if the currentNode does not belong to the last set of Node objects in the nextNodesList
		if(nextNodesListIdx < matchingNodesList.size()) {

			List<Node<ChordSymbol>> nextNodes = matchingNodesList.get(nextNodesListIdx);

			//get the Node in the chordPreferencesGraph that contains currentNode's ChordSymbol, so as to know what chords can follow the current one
			Node<ChordSymbol> chordGraphNode = _chordPreferencesGraph.findNode(currentNode.getValue());

			//generate list of ChordSymbols that the current chord can conventionally go to
			List<Edge<ChordSymbol>> followingEdges = chordGraphNode.getFollowing();
			List<ChordSymbol> followingChords = new ArrayList<ChordSymbol>();

			for(Edge<ChordSymbol> edge : followingEdges) {

				followingChords.add((ChordSymbol) edge.getBack().getValue());
			}

			boolean hasNext = false; //boolean determining if the current node leads to any other node at all

			//for each of the nodes following the current one one
			for(Node<ChordSymbol> nextNode : nextNodes) {


				if(followingChords.contains(nextNode)) { //determine if the chord progression from the current chord to the next is conventional

					progressionsGraph.addEdge(currentNode, nextNode, 1); //valid progression, add edge to the return graph
					hasNext = true;
					createPossibleProgressionsGraphHelper(nextNode, matchingNodesList, nextNodesListIdx, progressionsGraph); //recur
				}
			}

			if(!hasNext) {

				removeFromProgression(currentNode, matchingNodesList, nextNodesListIdx - 1, progressionsGraph);
			}
		}
	}

	//removes the Node toRemove from the Graph and removes the relevant Edges,
	//if the node that is removed is the only node that one of its previous nodes lead to, then that previous node is removed as well
	private void removeFromProgression(Node<ChordSymbol> toRemove, List<List<Node<ChordSymbol>>> matchingNodesList,
															int matchingNodesListIdx, Graph progressionsGraph) {

		//get the list of Nodes from which to remove the Node toRemove
		List<Node<ChordSymbol>> currentNodesList = matchingNodesList.get(matchingNodesListIdx);
		currentNodesList.remove(toRemove);

		if(matchingNodesListIdx > 0) {


			List<Edge<ChordSymbol>> previousEdges = toRemove.getPreceding();

			//check to see if the any previous Nodes only leads to the current Node that was just deleted
			for(Edge<ChordSymbol> previousEdge : previousEdges) {

				Node<ChordSymbol> previousNode = previousEdge.getFront();
				if(previousNode.getFollowing().isEmpty()) {//if the previous node only leads to the current node, it will be deleted as well

					removeFromProgression(previousNode, matchingNodesList, matchingNodesListIdx - 1, progressionsGraph);
					progressionsGraph.removeEdge(previousNode, toRemove);
				}
			}
		}
		else if(matchingNodesListIdx == 0) {//if toRemove belongs to the first set of Nodes in matchingNodesList


		}


	}


	/*
	 * Takes a integer which indicates the scale degree of a note (1-7), and converts that to the pitch degree (0-11, one half step for each pitch degree).
	 */
	public int scaleDegreeToPitchDegree(int scaleDegree) {

		if(scaleDegree < 1 || scaleDegree > 7){

//			System.out.println("erroroneous scaleDegree input");
			return 0;
		}
		else if(scaleDegree < 4)
			return ((scaleDegree - 1) * 2);
		else
			return (5 + (scaleDegree - 4) *2);
	}

	public void run() {
		// start analysis process here

	}
}