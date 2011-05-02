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

	// list of iterators for playback, each iterator for each voice in the measure
//	ArrayList<ListIterator<MultiNote>> _multiNoteLists;

	List<Staff> _staffList;

	public MidiPlayer(midiAPI midi, Piece piece) {
		_staffList = piece.getStaffs();
		_midi = midi;
	}

	public void run() {

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
		}
	}

	//returns the minimum of two Rationals
	public Rational min(Rational a, Rational b){

		if(a.compareTo(b) > 0){
			return b;
		} else {
			return a;
		}
	}
}
