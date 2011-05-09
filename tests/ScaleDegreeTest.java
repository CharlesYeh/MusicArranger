package tests;
import logic.*;
import music.*;
import java.util.*;
import util.*;

public class ScaleDegreeTest {
	public static void main(String[] args) {
//		Analyzer test = new Analyzer();
		Pitch pitch1 = new Pitch(NoteLetter.A, Accidental.FLAT);
		Pitch pitch2 = new Pitch(NoteLetter.G, Accidental.NATURAL);
		Pitch pitch3 = new Pitch(NoteLetter.E, Accidental.FLAT);
//		Interval interval1 = new Interval(IntervalType.DIMINISHED, 6);
//		ChordSymbol chordSymbol = new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.FRAUG6);
//		KeySignature keySig = new KeySignature(-4, false);
//		String keyMode;
//		if (keySig.getIsMajor()) {keyMode = "MAJOR";} else {keyMode = "MINOR";}
//		
//		System.out.println("Key Signature: " + test.getKeySigPitch(keySig).toString() + keyMode);
//		
//		if (keySig.getIsMajor()) {
//			test.initMajorKeyGraph();
//		}
//		else {
//			test.initMinorKeyGraph();
//		}
//		List<Node<ChordSymbol>> chordNodes = test.getChordPreferencesGraph().getNodes();
//		List<ChordSymbol> possibleChords = new LinkedList<ChordSymbol>();
//		for (Node<ChordSymbol> chordNode : chordNodes) {
//			possibleChords.add(chordNode.getValue());
//		}
//		
//		System.out.println("Initial possible chords:");
//		
//		for (ChordSymbol possibleChord : possibleChords) {
//			System.out.println(possibleChord.toString());
//		}
//		test.filterChords(possibleChords, pitch1, keySig);
//		
//		System.out.println("Filtering out chords not containing " + pitch1.toString());
//
//		for (ChordSymbol possibleChord : possibleChords) {
//			System.out.println(possibleChord.toString());
//		}
//		test.filterChords(possibleChords, pitch2, keySig);
//		System.out.println("Filtering out chords not containing " + pitch2.toString());
//
//		for (ChordSymbol possibleChord : possibleChords) {
//			System.out.println(possibleChord.toString());
//		}
//		test.filterChords(possibleChords, pitch3, keySig);
//		System.out.println("Filtering out chords not containing " + pitch3.toString());
//
//		for (ChordSymbol possibleChord : possibleChords) {
//			System.out.println(possibleChord.toString());
//		}
		
		System.out.println(pitch1.calcIntervalTo(pitch2).toString());
	}
}
