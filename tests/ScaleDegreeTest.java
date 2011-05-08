package tests;
import logic.*;
import logic.temporary.AnalyzeMethod;
import music.*;
import java.util.*;

public class ScaleDegreeTest {
	public static void main(String[] args) {
		AnalyzeMethod test = new AnalyzeMethod();
		Pitch pitch1 = new Pitch(NoteLetter.E, Accidental.NATURAL);
		Interval interval1 = new Interval(IntervalType.DIMINISHED, 6);
		ChordSymbol chordSymbol = new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.FRAUG6);
		KeySignature keySig = new KeySignature(1, false);
		System.out.println(test.addInterval(pitch1, interval1).toString());
		
		System.out.println("Key Signature: " + test.getKeySigPitch(keySig));
		
		List<Pitch> pitchList = test.getChordPitches(chordSymbol, keySig);
		
		for (Pitch pitch : pitchList) {
			System.out.println(pitch.toString());
		}
	}
}
