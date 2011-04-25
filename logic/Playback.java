package logic;

import music.*;
import javax.sound.midi.*;
import java.util.*;
import java.lang.Object;

public class Playback{

	int _wholeNoteDuration = 1000; //The duration of a whole note;


	public Playback(int wholeNoteDuration){
		_wholeNoteDuration = wholeNoteDuration;
	}
	
    public void playVoice(Voice v) {

            LinkedList<MultiNote> mns = v.getMultiNotes();

            try{

            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            Receiver receiver = synth.getReceiver();
            //navigate to a single multinote
            for (int i = 0; i < mns.size(); i++) {
                MultiNote mn = mns.get(i);
                ArrayList<Pitch> _pitches = mn.getPitches();
                Rational beatduration = mn.getDuration();

				System.out.println("beat is "+beatduration.getNumerator()+"/"+beatduration.getDenominator());
				System.out.println("_wholeNoteDuration is "+_wholeNoteDuration);
				double durationDouble = _wholeNoteDuration * (double) beatduration.getNumerator()/ beatduration.getDenominator();
                int duration = (int) durationDouble;

                //navigate to a single note
                //turn on the notes
                for (int j = 0; j < _pitches.size(); j++) {
                    Pitch p = _pitches.get(j);
                    MidiMessage noteMessage = getMessage(ShortMessage.NOTE_ON, computeMidiPitch(p));

                    // 0 means execute immediately
                    receiver.send(noteMessage, 0);
                }

                //let thread sleep for the duration of the note
                    Thread.sleep(duration);
                    System.out.println("duration of playing the note is:  " + duration);

                //turn off the notes
                for (int j = 0; j < _pitches.size(); j++) {
                    Pitch p = _pitches.get(j);
                    MidiMessage noteMessage = getMessage(ShortMessage.NOTE_OFF, computeMidiPitch(p));

                    // 0 means execute immediately
                    receiver.send(noteMessage, 0);
                }
            }
            } catch (Exception e){
            	e.printStackTrace();
            	System.out.println("Error in playing voice");
            }
    }

    //helper method for creating a midimesage;
    //volume and channel number is presumed for now. They will not be changed to not be hardcoded.
    public MidiMessage getMessage(int cmd, int note) throws Exception {

        // no error checking

        ShortMessage msg = new ShortMessage();

        // command (on/off), channel, note pitch, volume
        msg.setMessage(cmd, 0, note, 100);
        return (MidiMessage) msg;
    }

    //for playing a single note
    public void playNote(int note, int duration) throws Exception {

        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        Receiver receiver = synth.getReceiver();
        MidiMessage noteMessage = getMessage(ShortMessage.NOTE_ON, note);
        // 0 means execute immediately
        receiver.send(noteMessage, 0);

        Thread.sleep(duration);

        noteMessage = getMessage(ShortMessage.NOTE_OFF, note);
        receiver.send(noteMessage, 0);
    }

    //takes a Pitch object and returns its midiPitch value
    public int computeMidiPitch(Pitch p){
		int octaveC = 12 + (12*p.getOctave());

		NoteLetter noteltr = p.getNoteLetter();
		Accidental accid = p.getAccidental();
		int midiPitch = octaveC + noteltr.pitchValue() + accid.intValue();

		return midiPitch;

	}
}