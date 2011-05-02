package logic;

import music.*;
import java.lang.Thread;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class MidiPlayer extends Thread {

	TreeMap<Timestamp, ListIterator<MultiNote>> _starts;
	TreeMap<Timestamp, MultiNote> _ends;
	MidiAPI _midi;

	// list of iterators for playback
	ArrayList<ListIterator<MultiNote>> _multiNoteLists;
	Piece _piece;
	
	public MidiPlayer(MidiAPI midi, ArrayList<ListIterator<MultiNote>> iList) {
		_midi = midi;
		_multiNoteLists = iList;
	}

	public void run() {
		// initiate the _starts treemap with the iterators to the lists of multinotes (that represent voices)
		
		// used to play things from left to right
		TreeMap<Timestamp, ListIterator<? extends Timestep>> timeline = new TreeMap<Timestamp, ListIterator<? extends Timestep>>();
		
		// list of measures in each staff
		List<ListIterator<Measure>> measureLists = new ArrayList<ListIterator<Measure>>();
		
		for (Staff staff : _piece.getStaffs()) {
			List<Measure> staffMeasures = staff.getMeasures();
			ListIterator<Measure> measureIterator = staffMeasures.listIterator();
			
			measureLists.add(measureIterator);
		}
		
		// play all notes within the measure
		while (!measureLists.isEmpty()) {
			// set up lists within each list of measures
			for (int i = 0; i < measureLists.size(); i++) {
				ListIterator<Measure> measureIter = measureLists.get(i);
				Measure m = measureIter.next();
				
				// if measure list is empty, don't add back to list
				if (!measureIter.hasNext()) {
					measureLists.remove(i);
					i--;
				}
				
				List<Voice> voices = m.getVoices();
				
				for (Voice v : voices) {
					List<MultiNote> multis = v.getMultiNotes();
					ListIterator<MultiNote> multisList = multis.listIterator();
					
					timeline.put(new Timestamp(), multisList);
				}
				
			}
			
			// play all notes within this measure
			while (timeline.size() > 0) {
				Timestamp currentTime = timeline.firstKey();
				ListIterator<? extends Timestep> currList = timeline.get(currentTime);
				
				if (currList == null) {
					System.out.println("ERROR: There was an empty list of class: " + currentTime.getAssocClass());
					System.exit(1);
				}
				
				Timestep currDur = null;
				if (currList.hasNext()) {
					currDur = currList.next();
					timeline.remove(currentTime);
					
					currentTime.addDuration(currDur);
					timeline.put(currentTime, currList);
				}
				else {
					// don't add back to priority queue
					timeline.remove(currentTime);
					continue;
				}
				
				// draw duration object
				if (currDur instanceof MultiNote) {
					//-----------------------MULTINOTE-----------------------
					MultiNote mnote = (MultiNote) currDur;
					Rational dur = mnote.getDuration();
					
					
				}
				else if (currDur instanceof ChordSymbol) {
					//---------------------CHORD SYMBOL----------------------
					ChordSymbol cSymbol = (ChordSymbol) currDur;
				}
				else {
					System.out.println("ERROR: Unplayable timestep: " + currDur);
				}
				
				// sleep until the next note
				Timestamp nextTime = timeline.firstKey();
				Rational sleepDuration = nextTime.getDuration().minus(currentTime.getDuration());
				
				int sleepMilli = 60 * 1000 * sleepDuration.getNumerator() / getBeatsPerMinute() / sleepDuration.getDenominator();
				
				try {
					Thread.sleep(sleepMilli);
				}
				catch (Exception e) {
					System.out.println("Playback failed during sleep.");
					System.exit(1);
				}
			}
		}
	}
	
	public int getBeatsPerMinute() {
		return 60;
	}
}
