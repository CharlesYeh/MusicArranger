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
	midiAPI _midi;

	// list of iterators for playback
	ArrayList<ListIterator<MultiNote>> _multiNoteLists;

	public MidiPlayer(midiAPI midi, ArrayList<ListIterator<MultiNote>> iList) {
		_midi = midi;
		_multiNoteLists = iList;
	}

	public void run() {
		// initiate the _starts treemap with the iterators to the lists of multinotes (that represent voices)
		_starts = new TreeMap<Timestamp, ListIterator<MultiNote>>();
		_ends = new TreeMap<Timestamp, MultiNote>();

		for (ListIterator<MultiNote> listIter : _multiNoteLists) {
			Timestamp ts = new Timestamp();
			_starts.put(ts, listIter);
		}


		while (!_starts.isEmpty() || !_ends.isEmpty()) {

			// find whether start or end is next with timestamps (firstKey())
			Timestamp startTime = _starts.isEmpty() ? null : _starts.firstKey();
			System.out.println("startTime is : " + startTime.getDuration().toString());
			Timestamp endTime = _ends.isEmpty() ? null : _ends.firstKey();
			System.out.println("endTime is : " + endTime.getDuration().toString());

			// if start is empty, default to ends list
			boolean nextIsStart = !_starts.isEmpty();

			// if starts isn't empty, make sure ends isn't empty, and compare end time
			if (nextIsStart && !_ends.isEmpty() && startTime.compareTo(endTime) > 0) {
				// ending a note is next
				nextIsStart = false;
			}

			if (nextIsStart) {
				System.out.println(startTime);
				ListIterator<MultiNote> itr = _starts.get(startTime);

				// this note has started
				_starts.remove(startTime);
				System.out.println(itr);
				if(itr.hasNext()) {
					// play multinote
					MultiNote mn = itr.next();

					System.out.println("Turning note on at : "/* + currentTime.getDuration().getNumerator() + "/" + currentTime.getDuration().getDenominator()*/);
					_midi.multiNoteOn(mn);

						// get next timestamp
					startTime.addDuration(mn.getDuration());
					_starts.put(startTime, itr);
					_ends.put(startTime, mn);
				}
			}
			else {
				// stop playing it
				_ends.remove(endTime);

				System.out.println("Turning note OFF at : "/* + currentTime.getDuration().getNumerator() + "/" + currentTime.getDuration().getDenominator()*/);
				MultiNote mn = _ends.get(endTime);
				_midi.multiNoteOff(mn);
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
			Rational currentTime = nextIsStart ? startTime.getDuration() : endTime.getDuration();

			if (_starts.isEmpty() && _ends.isEmpty())
				break;

			startTime = _starts.isEmpty() ? null : _starts.firstKey();
			endTime = _ends.isEmpty() ? null : _ends.firstKey();

			Rational sleepDuration = null;

			if (_starts.isEmpty()) {
				sleepDuration = endTime.getDuration().minus(currentTime);
			}
			else {
				// starts is not empty so compare with ends list
				Timestamp nextStart = _starts.firstKey();
				if (!_ends.isEmpty() && endTime.compareTo(startTime) < 0) {
					sleepDuration = endTime.getDuration().minus(currentTime);
				}
				else {
					sleepDuration = startTime.getDuration().minus(currentTime);
				}
			}

			int sleepMilli = 1000 * sleepDuration.getNumerator() / _midi.getBeatsPerSecond() / sleepDuration.getDenominator();

			try {
				Thread.sleep(sleepMilli);
			}
			catch (Exception e) {
				System.out.println("Couldn't SLEEP: " + e);
				System.exit(1);
			}
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
