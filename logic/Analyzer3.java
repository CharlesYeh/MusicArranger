package logic;

import java.util.List;
import music.*;
import util.Graph;
import util.Node;
import java.util.ArrayList;
import util.Edge;
import java.util.ListIterator;

public class Analyzer3 extends Analyzer{
	
	/*
	 * Takes a list of Pitches, a ChordSymbol, and a keysignature, and returns a list of Pitches where harmonized melodies should be placed
	 */
	public List<Pitch> harmonizeMelodyInstance(List<Pitch> melodyInstance, ChordSymbol chordsym, KeySignature keySig) {
		
		List<Pitch> chordTones = getChordPitches(chordsym, keySig);
		int numberOfChordTones = chordTones.size();
		int numberOfChordTonesPresent = melodyInstance.size(); 
		int[] chordToneCheckList = new int[numberOfChordTones]; // Each element represents a chord tone. 0 means that chord tone is not present, 1 means it is.
		int lowestExistingNoteOctave = 12; //lowest Octave of the notes that already exist
		
		List<Pitch> returnList = new ArrayList<Pitch>();
		
		
		// Find out which chord tones are already present within the Melody
		for(int i = 0; i < chordTones.size(); i++) {
			
			Pitch chordPitch = chordTones.get(i);
			for(Pitch melodyPitch : melodyInstance) {
				
				if(melodyPitch.getNoteLetter() == chordPitch.getNoteLetter() && melodyPitch.getAccidental() == chordPitch.getAccidental()) {
					// The melody contains this chord pitch, but maybe in a different Octave
					
					chordToneCheckList[i] = 1;
					break;
				}
			}
		}
		
		for(Pitch melodyPitch : melodyInstance) { // find lowest octave and 
			if(melodyPitch.getOctave() < lowestExistingNoteOctave) {
				
				lowestExistingNoteOctave = melodyPitch.getOctave();
			}
			returnList.add(melodyPitch);
		}
		
//		int absoluteLowestOctave = lowestExistingNoteOctave;
		int remainingPitchesToEnter = 4 - numberOfChordTonesPresent;
		while(remainingPitchesToEnter > 0) {
			
			if(chordToneCheckList[0] == 0) { //root of chord is not present yet, so add root
				
				Pitch root = chordTones.get(0);
				// set octave of root to be the lowest melody note
				root.setOctave(lowestExistingNoteOctave); 
				chordToneCheckList[0] = 1;
				returnList.add(root);
			}
			else if(chordToneCheckList[1] == 0) { //3rd of chord is missing, add 3rd
				
				Pitch third = chordTones.get(1);
				// set octave of root to be 1 below the lowest melody note
				third.setOctave(lowestExistingNoteOctave - 1); 
				chordToneCheckList[1] = 1;
				returnList.add(third);
			}
			else if(chordToneCheckList.length > 3 && chordToneCheckList[3] == 0) { //7th of chord is missing, add 7th
				
				Pitch seventh = chordTones.get(3);
				// set octave of root to be 1 below the lowest melody note
				seventh.setOctave(lowestExistingNoteOctave - 1); 
				chordToneCheckList[3] = 1;
				returnList.add(seventh);
			}
			else if(chordToneCheckList[2] == 0) { // enter 5th
				
				Pitch fifth = chordTones.get(2);
				// set octave of root to be 1 below the lowest melody note
				fifth.setOctave(lowestExistingNoteOctave - 1); 
				chordToneCheckList[2] = 1;
				returnList.add(fifth);
			}
			else { // all chord tones already exist, repeat octave
				
				Pitch root = chordTones.get(0).copy();
				// set octave of root to be 1 below the lowest melody note
				root.setOctave(lowestExistingNoteOctave - 1); 
				chordToneCheckList[0] = 1;
				returnList.add(root);
			}
			
			remainingPitchesToEnter--;
		}
		
		return returnList;
	}

}
