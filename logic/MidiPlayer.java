package logic;

import music.*;
import java.lang.Thread;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class MidiPlayer extends Thread {

//	Piece _piece;
	TreeMap<Timestamp, ListIterator<MultiNote>> _starts;
	TreeMap<Timestamp, MultiNote> _ends;
	midiAPI _midi;

//<<<<<<< HEAD
	// list of iterators for playback, each iterator for each voice in the measure
//	ArrayList<ListIterator<MultiNote>> _multiNoteLists;

	List<Staff> _staffList;

	public MidiPlayer(midiAPI midi, Piece piece) {
		_staffList = piece.getStaffs();
//=======
//	// list of iterators for playback
//	ArrayList<ListIterator<MultiNote>> _multiNoteLists;
//	Piece _piece;
//
//	public MidiPlayer(MidiAPI midi, ArrayList<ListIterator<MultiNote>> iList) {
//>>>>>>> 75196a049669dac6aa6084fc628930607d1e2f89
		_midi = midi;
	}

	public void run() {
//<<<<<<< HEAD

		for(int measureNumber = 0; measureNumber < _staffList.get(0).getMeasures().size(); measureNumber ++){

			// initiate the _starts treemap with the iterators to the lists of multinotes (that represent voices)
			_starts = new TreeMap<Timestamp, ListIterator<MultiNote>>();
			_ends = new TreeMap<Timestamp, MultiNote>();
			Timestamp tempTimestamp = new Timestamp();

			Timestamp timestampKey = new Timestamp();
			ListIterator<MultiNote> listIter;

			for(Staff currentStaff: _staffList){

				Measure currentMeasure = currentStaff.getMeasures().get(measureNumber);
				for(Voice currentVoice: currentMeasure.getVoices()){

					listIter = currentVoice.getMultiNotes().listIterator();
					_starts.put(timestampKey, listIter);
				}
			}

		//		for (int i = 0; i < _multiNoteLists.size(); i++) {
		//
		//			Timestamp timestampKey = new Timestamp();
		//			_starts.put(timestampKey, _multiNoteLists.get(i));
		//		}



				while (!_starts.isEmpty() || !_ends.isEmpty()) {

					System.out.println("======New iteration======");

					// find whether start or end is next with timestamps (firstKey())
					Timestamp startTime = _starts.isEmpty() ? null : _starts.firstKey();

					if(startTime != null)
						System.out.println("startTime is : " + startTime.getDuration().toString());
					else
						System.out.println("startTime is null");

					Timestamp endTime = _ends.isEmpty() ? null : _ends.firstKey();

					if(endTime != null)
						System.out.println("endTime is : " + endTime.getDuration().toString());
					else
						System.out.println("endTime is null");
					// if start is empty, default to ends list
					boolean nextIsStart = !_starts.isEmpty();

					// if starts isn't empty, make sure ends isn't empty, and compare end time
					if (nextIsStart && !_ends.isEmpty() && startTime.compareTo(endTime) > 0) {
						// ending a note is next
						nextIsStart = false;
					}

					Rational currentTime = nextIsStart ? startTime.getDuration().clone() : endTime.getDuration().clone();

					if (nextIsStart) {
						ListIterator<MultiNote> itr = _starts.get(startTime);

						// this note has started
						_starts.remove(startTime);
						if(itr.hasNext()) {
							// play multinote
							MultiNote mn = itr.next();

							System.out.println("Turning note on at : " + startTime.getDuration().getNumerator() + "/" + startTime.getDuration().getDenominator());
							_midi.multiNoteOn(mn);

							// get next timestamp
							tempTimestamp.setDuration(startTime.getDuration().clone());
							tempTimestamp.addDuration(mn.getDuration());

							startTime.addDuration(mn.getDuration());
							_starts.put(startTime, itr);
							_ends.put(tempTimestamp, mn);
						}
					}
					else {
						// stop playing a multinote
						System.out.println("endTime is " + endTime);

						System.out.println("Turning note OFF at : " + endTime.getDuration().getNumerator() + "/" + endTime.getDuration().getDenominator());
						MultiNote mn = _ends.get(endTime);
						_midi.multiNoteOff(mn);
						_ends.remove(endTime);
					}

					// calculate sleep time

					// turn on notes
					/*if (!_starts.isEmpty()) {
						Timestamp startTime = _starts.firstKey();
						Rational startRational = sleepDuration = startTime.getDuration();

						ListIterator<MultiNote> itr = _starts.get(startTime);

						// this note has started
						if (startRational.compareTo(currentTime) <= 0) {
							_starts.remove(startTime);

							if(itr.hasNext()) {
								// play multinote
								MultiNote mn = itr.next();

								System.out.println("Turning note on at : " + currentTime.getNumerator() + "/" + currentTime.getDenominator());
								_midi.multiNoteOn(mn);

									// get next timestamp
								startTime.addDuration(mn.getDuration());

								_starts.put(startTime, itr);
								_ends.put(startTime, mn);
							}
						}
					}

					if (!_ends.isEmpty()){
						// turn off notes
						Timestamp endTime = _ends.firstKey();
						Rational endRational = timestamp.getDuration();
						if (endRational.compareTo(sleepDuration) < 0)
							sleepDuration = endRational;

						// if the end of this multinote is already over
						if (endRational.compareTo(currentTime) <= 0){
							// stop playing it

							System.out.println("Turning note OFF at : " + currentTime.getNumerator() + "/" + currentTime.getDenominator());

							MultiNote mn = _ends.get(endTime);
							_ends.remove(timestamp);

							_midi.multiNoteOff(mn);
						}
					}*/

					// find minimmum of start and end
					/*Timestamp minTimestamp = null;
					boolean minStart = true;
					if (!_starts.isEmpty()) {
						minTimestamp = _starts.firstKey();
					}
					if (!_ends.isEmpty()) {
						minTimestamp = _ends.firstKey();

					}*/

					// thread sleeps through
					//sleepDuration = nextTime.plus(currentTime.negate());
					//int sleepMilli = (_midi.getWholeNoteDuration() * sleepDuration.getNumerator()) / sleepDuration.getDenominator();

					// get sleep duration
					if (_starts.isEmpty() && _ends.isEmpty())
						break;

					startTime = _starts.isEmpty() ? null : _starts.firstKey();
					endTime = _ends.isEmpty() ? null : _ends.firstKey();

					Rational sleepDuration = null;

					if (_starts.isEmpty()) {
						sleepDuration = endTime.getDuration().minus(currentTime);
						System.out.println("case 1: sleepDuration is: " + sleepDuration);
					}
					else {
						// starts is not empty so compare with ends list
						Timestamp nextStart = _starts.firstKey();
						if (!_ends.isEmpty() && endTime.compareTo(startTime) < 0) {
							sleepDuration = endTime.getDuration().minus(currentTime);
							System.out.println("case 2: sleepDuration is: " + sleepDuration);
						}
						else {
							sleepDuration = startTime.getDuration().minus(currentTime);
							System.out.println("case 3: sleepDuration is: " + sleepDuration);
						}
					}

					int sleepMilli = 60 * 1000 * sleepDuration.getNumerator() / _midi.getBeatsPerMinute() / sleepDuration.getDenominator();
					System.out.println("currentTime is :" + currentTime);
					System.out.println("sleepMilli is: " + sleepMilli);

					try {
						System.out.println("going to sleep ");
						Thread.sleep(sleepMilli);
						System.out.println("slept ");
					}
					catch (Exception e) {
						System.out.println("Couldn't SLEEP: " + e);
						System.exit(1);
					}
				}
			measureNumber++;
//=======
//		// initiate the _starts treemap with the iterators to the lists of multinotes (that represent voices)
//
//		// used to play things from left to right
//		TreeMap<Timestamp, ListIterator<? extends Timestep>> timeline = new TreeMap<Timestamp, ListIterator<? extends Timestep>>();
//
//		// list of measures in each staff
//		List<ListIterator<Measure>> measureLists = new ArrayList<ListIterator<Measure>>();
//
//		for (Staff staff : _piece.getStaffs()) {
//			List<Measure> staffMeasures = staff.getMeasures();
//			ListIterator<Measure> measureIterator = staffMeasures.listIterator();
//
//			measureLists.add(measureIterator);
//		}
//
//		// play all notes within the measure
//		while (!measureLists.isEmpty()) {
//			// set up lists within each list of measures
//			for (int i = 0; i < measureLists.size(); i++) {
//				ListIterator<Measure> measureIter = measureLists.get(i);
//				Measure m = measureIter.next();
//
//				// if measure list is empty, don't add back to list
//				if (!measureIter.hasNext()) {
//					measureLists.remove(i);
//					i--;
//				}
//
//				List<Voice> voices = m.getVoices();
//
//				for (Voice v : voices) {
//					List<MultiNote> multis = v.getMultiNotes();
//					ListIterator<MultiNote> multisList = multis.listIterator();
//
//					timeline.put(new Timestamp(), multisList);
//				}
//
//			}
//
//			// play all notes within this measure
//			while (timeline.size() > 0) {
//				Timestamp currentTime = timeline.firstKey();
//				ListIterator<? extends Timestep> currList = timeline.get(currentTime);
//
//				if (currList == null) {
//					System.out.println("ERROR: There was an empty list of class: " + currentTime.getAssocClass());
//					System.exit(1);
//				}
//
//				Timestep currDur = null;
//				if (currList.hasNext()) {
//					currDur = currList.next();
//					timeline.remove(currentTime);
//
//					currentTime.addDuration(currDur);
//					timeline.put(currentTime, currList);
//				}
//				else {
//					// don't add back to priority queue
//					timeline.remove(currentTime);
//					continue;
//				}
//
//				// draw duration object
//				if (currDur instanceof MultiNote) {
//					//-----------------------MULTINOTE-----------------------
//					MultiNote mnote = (MultiNote) currDur;
//					Rational dur = mnote.getDuration();
//
//
//				}
//				else if (currDur instanceof ChordSymbol) {
//					//---------------------CHORD SYMBOL----------------------
//					ChordSymbol cSymbol = (ChordSymbol) currDur;
//				}
//				else {
//					System.out.println("ERROR: Unplayable timestep: " + currDur);
//				}
//
//				// sleep until the next note
//				Timestamp nextTime = timeline.firstKey();
//				Rational sleepDuration = nextTime.getDuration().minus(currentTime.getDuration());
//
//				int sleepMilli = 60 * 1000 * sleepDuration.getNumerator() / getBeatsPerMinute() / sleepDuration.getDenominator();
//
//				try {
//					Thread.sleep(sleepMilli);
//				}
//				catch (Exception e) {
//					System.out.println("Playback failed during sleep.");
//					System.exit(1);
//				}
//			}
//>>>>>>> 75196a049669dac6aa6084fc628930607d1e2f89
		}
	}

	public int getBeatsPerMinute() {
		return 60;
	}
}
