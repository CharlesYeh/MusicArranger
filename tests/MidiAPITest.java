package tests;

/**
 * @(#)midiAPI_test.java
 *
 *
 * @author
 * @version 1.00 2011/4/24
 */
import music.*;
import logic.MidiAPI;
import java.util.*;


public class MidiAPITest {

    public MidiAPITest() {

    }

    public static void main(String args[]){
    	//testVoice1
    	int[] doMiSo = {60, 64, 67};
		int[] doMiSo2 = {64, 67, 72};
		ArrayList<int[]> voice1 = new ArrayList<int[]>();
		voice1.add(doMiSo);
		voice1.add(doMiSo2);

		//testVoice2
		int[] mary_had_a_little_lamb = {66, 64, 62, 64, 66, 66, 66, 64, 64, 64, 66, 69, 69, 66, 64, 62, 64, 66, 66, 66, 64, 64, 66, 64, 62};
        int[] mary_had_a_little_lamb2 = {69, 67, 66, 67, 69, 69, 69, 67, 67, 67, 69, 73, 73, 69, 67, 66, 67, 69, 69, 69, 67, 67, 69, 67, 66};
		ArrayList<int[]> voice2 = new ArrayList<int[]>();
		voice2.add(mary_had_a_little_lamb);
		voice2.add(mary_had_a_little_lamb2);

		MidiAPI api = new MidiAPI(1500);

		//choose which voice to play!
		Voice v = MidiAPITest.createVoiceFromMidiPitches(voice1);
		Voice v2 = MidiAPITest.createVoiceFromMidiPitches(voice2);

		/*Staff st = new Staff();
		st.getVoices.add(v);
		st.getVoices.add(v2);*/


		api.playVoice(v);
		api.playVoice(v2);

		Piece p = new test.TestPiece();
		api.playPiece(p);
    }

	public static Voice createVoiceFromMidiPitches(ArrayList<int[]> midiPitches){

		Voice v = new Voice();
		List<MultiNote> mns = v.getMultiNotes();

		int numberOfNotes = midiPitches.get(0).length;

		for(int i = 0; i < numberOfNotes; i++){
			//assuming that the duration of each note is a quarter note;
			MultiNote mn = new MultiNote(new Rational(1, 4));
			List<Pitch> pitchList = mn.getPitches();

			//System.out.println("Multinote " + i + " contains:");

			for(int j = 0; j < midiPitches.size(); j++){
				int[] pitchArray = midiPitches.get(j);
				int currentPitch = pitchArray[i];
				Pitch temp = createPitchFromMidiPitch(currentPitch);
				pitchList.add(temp);
				printPitch(temp);
			}

			mns.add(mn);
		}

		return v;
	}

	public static Pitch createPitchFromMidiPitch(int midiPitch){

		int octave = (Integer)(midiPitch/12) - 1;
		int pitchFromC = midiPitch%12;
		Pitch p;


		if(pitchFromC == 0){
			p = new Pitch(NoteLetter.C, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 1){
			p = new Pitch(NoteLetter.C, octave, Accidental.SHARP, false);
		} else if (pitchFromC == 2){
			p = new Pitch(NoteLetter.D, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 3){
			p = new Pitch(NoteLetter.D, octave, Accidental.SHARP, false);
		} else if (pitchFromC == 4){
			p = new Pitch(NoteLetter.E, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 5){
			p = new Pitch(NoteLetter.F, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 6){
			p = new Pitch(NoteLetter.F, octave, Accidental.SHARP, false);
		}  else if (pitchFromC == 7){
			p = new Pitch(NoteLetter.G, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 8){
			p = new Pitch(NoteLetter.G, octave, Accidental.SHARP, false);
		}  else if (pitchFromC == 9){
			p = new Pitch(NoteLetter.A, octave, Accidental.NATURAL, false);
		} else if (pitchFromC == 10){
			p = new Pitch(NoteLetter.A, octave, Accidental.SHARP, false);
		} else {
			p = new Pitch(NoteLetter.B, octave, Accidental.NATURAL, false);
		}

		return p;
	}

	public static void printPitch(Pitch p){
		//System.out.println("Pitch name: "+p.getNoteLetter().toString()+" "+p.getAccidental().toString()+" "+p.getOctave()+"  tied = "+p.getIsTiedToNext());

	}

}