package logic;

import music.*;
import javax.sound.midi.*;
import java.util.*;
import java.lang.Object;

public class midiAPI{

	//int _wholeNoteDuration = 1000; //The duration of a whole note
	MidiPlayer _mp;
	ArrayList<ListIterator<MultiNote>> _voices;
	Synthesizer _synth;
	Receiver _receiver;

	int _wholeNoteDuration;
	
	public midiAPI(int wholeNoteDuration){
		_wholeNoteDuration = wholeNoteDuration;
		try{
				_synth = MidiSystem.getSynthesizer();
	        _synth.open();
	        _receiver = _synth.getReceiver();
		} catch (Exception e){
			System.out.println("Error loading synth: " + e);
		}
	}
	
	public int getBeatsPerSecond() {
		return 60;
	}
	
	public void playPiece(Piece p){
		_voices = new ArrayList<ListIterator<MultiNote>>();
		addPiece(_voices, p);
		
		_mp = new MidiPlayer(this, _voices);
		_mp.start();
	}
	
	public void playStaff(Staff s){
		_voices = new ArrayList<ListIterator<MultiNote>>();
		addStaff(_voices, s);
		
		_mp = new MidiPlayer(this, _voices);
		_mp.start();
	}
	
    public void playVoice(Voice v) {
		_voices = new ArrayList<ListIterator<MultiNote>>();
		addVoice(_voices, v);
		
		_mp = new MidiPlayer(this, _voices);
		_mp.start();
    }
    
    private void addPiece(List<ListIterator<MultiNote>> list, Piece p) {
    	for (Staff s : p.getStaffs()) {
    		addStaff(list, s);
    	}
    }
    
    private void addStaff(List<ListIterator<MultiNote>> list, Staff s) {
    	for (Voice v : s.getVoices()) {
    		addVoice(list, v);
    	}
    }
    
    private void addVoice(List<ListIterator<MultiNote>> list, Voice v) {
    	list.add(v.getMultiNotes().listIterator());
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

    //for playing a single pitch with a specified duration
    public void playPitch(Pitch p, int duration) throws Exception {

		try{
	        Synthesizer synth = MidiSystem.getSynthesizer();
	        synth.open();
	        Receiver receiver = synth.getReceiver();
	        MidiMessage noteMessage = getMessage(ShortMessage.NOTE_ON, computeMidiPitch(p));
	        // 0 means execute immediately
	        receiver.send(noteMessage, 0);

	        Thread.sleep(duration);

	        noteMessage = getMessage(ShortMessage.NOTE_OFF, computeMidiPitch(p));
	        receiver.send(noteMessage, 0);
		} catch(Exception e){
			e.printStackTrace();
		}
    }

    //takes a Pitch object and returns its midiPitch value
    public int computeMidiPitch(Pitch p){
		int octaveC = 12 + (12*p.getOctave());

		NoteLetter noteltr = p.getNoteLetter();
		Accidental accid = p.getAccidental();
		int midiPitch = octaveC + noteltr.pitchValue() + accid.intValue();

		return midiPitch;

	}

	public void multiNoteOn(MultiNote mn){
//		System.out.println("note is turned on: "+mn.toString());
		multiNoteEvent(mn, ShortMessage.NOTE_ON);

	}

	public void multiNoteOff(MultiNote mn){
//		System.out.println("note is turned off: "+mn.toString());
		multiNoteEvent(mn, ShortMessage.NOTE_OFF);
	}

	public void multiNoteEvent(MultiNote mn, int msg){
		try{
            ArrayList<Pitch> _pitches = mn.getPitches();
            Rational beatduration = mn.getDuration();

			//System.out.println("beat is "+beatduration.getNumerator()+"/"+beatduration.getDenominator());
			//System.out.println("_wholeNoteDuration is "+_wholeNoteDuration);
			double durationDouble = _wholeNoteDuration * (double) beatduration.getNumerator()/ beatduration.getDenominator();
            int duration = (int) durationDouble;

            //navigate to a single note
            //send the wanted note message
            for (int j = 0; j < _pitches.size(); j++) {
                Pitch p = _pitches.get(j);
                MidiMessage noteMessage = getMessage(msg, computeMidiPitch(p));

                // 0 means execute immediately
                _receiver.send(noteMessage, 0);
            }
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println("Error in playing voice");
        }
	}

	public int getWholeNoteDuration(){
		return _wholeNoteDuration;
	}
}