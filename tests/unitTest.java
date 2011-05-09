package tests;

import java.util.*;
import music.*;
import logic.Analyzer;
import logic.temporary.*;
import tests.MidiAPITest;

public class unitTest{
        public static void main(String args[]) {

                KeySignature CMajor = new KeySignature(new Rational(4, 4), 0, true);
                KeySignature DMajor = new KeySignature(new Rational(4, 4), 2, true);
                Pitch C = MidiAPITest.createPitchFromMidiPitch(60);
                Pitch D = MidiAPITest.createPitchFromMidiPitch(62);
                Pitch E = MidiAPITest.createPitchFromMidiPitch(64);
                Pitch F = MidiAPITest.createPitchFromMidiPitch(65);
                Pitch G = MidiAPITest.createPitchFromMidiPitch(67);
                
                AnalyzeMethod analyzer = new AnalyzeMethod();
                List<List<Pitch>> melody = new ArrayList<List<Pitch>>();
                
                ArrayList<Pitch> melodyInstance1 = new ArrayList<Pitch>();
                melody.add(melodyInstance1);
                melodyInstance1.add(C);
                melodyInstance1.add(E);
                
                ArrayList<Pitch> melodyInstance2 = new ArrayList<Pitch>();
                melody.add(melodyInstance2);
                melodyInstance2.add(F);
                
                ArrayList<Pitch> melodyInstance3 = new ArrayList<Pitch>();
                melody.add(melodyInstance3);
                melodyInstance3.add(G);
                
                ArrayList<Pitch> melodyInstance4 = new ArrayList<Pitch>();
                melody.add(melodyInstance4);
                melodyInstance4.add(C);
                melodyInstance4.add(E);
                
                
                List<List<ChordSymbol>> allPossibleChords = analyzer.analyzeMelody(melody, CMajor);
                
                //print out all possible chords
                int melodyNo = 0;
                for(List<ChordSymbol> melodyInstance : allPossibleChords) {
                	
                	System.out.println("melodyInstance " + melodyNo);
                	for(ChordSymbol chordsym : melodyInstance) {
                		
                		System.out.println(chordsym.toString());
                	}
                	
                	melodyNo++;
                }
                
                
        }
}