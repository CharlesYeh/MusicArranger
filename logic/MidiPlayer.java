package logic;

import arranger.ArrangerConstants;
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
	MidiAPI _midi;

	List<Staff> _staffList;

	boolean _isPlaying;

	public MidiPlayer(MidiAPI midi, Piece piece) {
		_midi = midi;
		_staffList = piece.getStaffs();
	}

	public void run() {
		_isPlaying = true;

		for(int measureNumber = 0; measureNumber < _staffList.get(0).getMeasures().size(); measureNumber ++){

			System.out.println("+++++This is measure " + measureNumber + "+++++++");

			// initiate the _starts treemap with the iterators to the lists of multinotes (that represent voices)
			_starts = new TreeMap<Timestamp, ListIterator<MultiNote>>();
			_ends = new TreeMap<Timestamp, MultiNote>();

			ListIterator<MultiNote> listIter;
			Measure currentMeasure;

			for(Staff currentStaff: _staffList){

				currentMeasure = currentStaff.getMeasures().get(measureNumber);
				for(Voice currentVoice: currentMeasure.getVoices()){

					listIter = currentVoice.getMultiNotes().listIterator();
					Timestamp timestampKey = new Timestamp();
					_starts.put(timestampKey, listIter);
				}
			}


			while (!_starts.isEmpty() || !_ends.isEmpty()) {


				System.out.println("======New iteration======");

				// find whether start or end is next with timestamps (firstKey())
				Timestamp startTime = _starts.isEmpty() ? null : _starts.firstKey();

				/*if(startTime != null)
					System.out.println("startTime is : " + startTime.getDuration().toString());
				else
					System.out.println("startTime is null");*/

				Timestamp endTime = _ends.isEmpty() ? null : _ends.firstKey();

				/*if(endTime != null)
					System.out.println("endTime is : " + endTime.getDuration().toString());
				else
					System.out.println("endTime is null");*/
				
				// if start is empty, default to ends list
				boolean nextIsStart = !_starts.isEmpty();
				//System.out.println(nextIsStart);

				// if starts isn't empty, make sure ends isn't empty, and compare end time
				if (nextIsStart && !_ends.isEmpty() && startTime.compareTo(endTime) >= 0) {
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

//						System.out.println("Turning note ON at : " + startTime.getDuration().getNumerator() + "/" + startTime.getDuration().getDenominator());
						_midi.multiNoteOn(mn);

						// get next timestamp

						Timestamp tempTimestamp = new Timestamp();
						tempTimestamp.setDuration(startTime.getDuration());
						tempTimestamp.addDuration(mn.getDuration());

						startTime.addDuration(mn.getDuration());
						if(itr.hasNext())
							_starts.put(startTime, itr);
						_ends.put(tempTimestamp, mn);
					}
				}
				else {
					// stop playing a multinote
					//System.out.println("endTime is " + endTime);

					//System.out.println("Turning note OFF at : " + endTime.getDuration().getNumerator() + "/" + endTime.getDuration().getDenominator());
					MultiNote mn = _ends.get(endTime);
					_midi.multiNoteOff(mn);
					_ends.remove(endTime);
				}

				// get sleep duration
				if (_starts.isEmpty() && _ends.isEmpty())
					break;

				startTime = _starts.isEmpty() ? null : _starts.firstKey();
				endTime = _ends.isEmpty() ? null : _ends.firstKey();

				Rational sleepDuration = null;

				if (_starts.isEmpty()) {
					// case 1 when all the notes have been turned on
					sleepDuration = endTime.getDuration().minus(currentTime);
					//System.out.println("case 1: sleepDuration is: " + sleepDuration);
				}
				else {
					// starts is not empty so compare with ends list
					Timestamp nextStart = _starts.firstKey();
					if (!_ends.isEmpty() && endTime.compareTo(startTime) < 0) {

						// case 2 when there are still notes to turn on and notes to turn off, but a note should be turned off first.
						sleepDuration = endTime.getDuration().minus(currentTime);
						//System.out.println("case 2: sleepDuration is: " + sleepDuration);
					}
					else {

						// case 3 when thre are notes to turn on and notes to turn off, but a note should be turned on first
						sleepDuration = startTime.getDuration().minus(currentTime);
						//System.out.println("case 3: sleepDuration is: " + sleepDuration);
					}
				}

				int sleepMilli = 60 * 1000 * sleepDuration.getNumerator() / ArrangerConstants.WHOLE_NOTES_PER_MINUTE / sleepDuration.getDenominator();
				/*System.out.println("currentTime is :" + currentTime);
				System.out.println("sleepMilli is: " + sleepMilli);*/

				try {
					Thread.sleep(sleepMilli);
				}
				catch (InterruptedException ie){
					//stop playing all the notes when the playback is stopped
					while(!_ends.isEmpty()){

							Timestamp ts = _ends.firstKey();
							MultiNote mn = _ends.get(ts);
							_ends.remove(ts);
							_midi.multiNoteOff(mn);
					}
					return;
				}
			}
		}
	}

//	public void stopPlayback() {
//		_isPlaying = false;
//	}
}
