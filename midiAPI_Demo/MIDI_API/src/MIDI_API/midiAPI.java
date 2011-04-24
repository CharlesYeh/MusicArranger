/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIDI_API;

/**
 *
 * @author Kaijian Gao
 */

import javax.sound.midi.*;
import java.util.*;

public class midiAPI {

    public static void playVoice(Voice v) throws Exception {
        
            LinkedList<multiNote> mns = v.getMultiNotes();
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            
            Receiver receiver = synth.getReceiver();
            //navigate to a single multinote
            for (int i = 0; i < v.size(); i++) {
                multiNote mn = mns.get(i);
                ArrayList<Integer> notes = mn.getNotes();
                int duration = mn.getDuration();
                //navigate to a single note
                //turn on the notes
                for (int j = 0; j < notes.size(); j++) {
                    int pitch = notes.get(j);
                    MidiMessage noteMessage = getMessage(ShortMessage.NOTE_ON, pitch);

                    // 0 means execute immediately
                    receiver.send(noteMessage, 0);
                }

                //let thread sleep for the duration of the note
                    Thread.sleep(duration);

                //turn off the notes
                for (int j = 0; j < notes.size(); j++) {
                    int pitch = notes.get(j);
                    MidiMessage noteMessage = getMessage(ShortMessage.NOTE_OFF, pitch);

                    // 0 means execute immediately
                    receiver.send(noteMessage, 0);
                }
            }
    }

    //helper method for creating a midimesage;
    //volume and channel number is presumed for now. They will not be changed to not be hardcoded.
    public static MidiMessage getMessage(int cmd, int note) throws Exception {

        // no error checking

        ShortMessage msg = new ShortMessage();

        // command (on/off), channel, note pitch, volume
        msg.setMessage(cmd, 0, note, 100);
        return (MidiMessage) msg;
    }

    //for playing a single note
    public static void playNote(int note, int duration) throws Exception {

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
}
