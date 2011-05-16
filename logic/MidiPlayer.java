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
	int _startingMeasure = 0;
	Rational _startTimeInMeasure;
	List<Staff> _staffList;


	// constructor for playing a piece from the start
	public MidiPlayer(MidiAPI midi) {
		_midi = midi;
		_startTimeInMeasure = new Rational(0,4); // default, start at the first beat
	}
	
	public void setPiece(Piece piece) {
		_staffList = piece.getStaffs();
	}

	// Sets the start position of play back by setting the Measure number and the time within the measure to start at
	public void setStartingTime(int startingMeasure, Rational startTimeInMeasure) {
		_startingMeasure = startingMeasure;
		_startTimeInMeasure = startTimeInMeasure;
	}
	
	public void run() {
		for(int measureNumber = _startingMeasure; measureNumber < _staffList.get(0).getMeasures().size(); measureNumber ++){
			
			//System.out.println("-------------------This is measure " + measureNumber + "-------------------");
			
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
				
				boolean doNotPlayYet = false;
				//System.out.println("======New iteration======");

				// find whether start or end is next with timestamps (firstKey())
				Timestamp startTime = _starts.isEmpty() ? null : _starts.firstKey();
				
				Timestamp endTime = _ends.isEmpty() ? null : _ends.firstKey();
								
				// if start is empty, default to ends list
				boolean nextIsEnd = !_ends.isEmpty();

				// if starts isn't empty, make sure ends isn't empty, and compare start time
				if (nextIsEnd && !_starts.isEmpty() && endTime.compareTime(startTime) > 0) {
					// starting a note is next
					nextIsEnd = false;
				}

				Rational currentTime = nextIsEnd ? endTime.getDuration().clone() : startTime.getDuration().clone();
				
				if (nextIsEnd) {
					// stop playing a multinote
					//System.out.println("endTime is " + endTime);

					//System.out.println("Turning note OFF at : " + endTime.getDuration().getNumerator() + "/" + endTime.getDuration().getDenominator());
					MultiNote mn = _ends.get(endTime);
					if(!(measureNumber == _startingMeasure && endTime.getDuration().compareTo(_startTimeInMeasure) < 0)) { // if the current note to be stopped comes after the starting note
						_midi.multiNoteOff(mn);
					}
					else {
						doNotPlayYet = true;
					}
					_ends.remove(endTime);
				}
				else {
					ListIterator<MultiNote> itr = _starts.get(startTime);
					
					// this note has started
					_starts.remove(startTime);
					if(itr.hasNext()) {
						// play multinote
						MultiNote mn = itr.next();

						if(!(measureNumber == _startingMeasure && startTime.getDuration().compareTo(_startTimeInMeasure) < 0)) { // if the current note to be played comes after the starting note
							//System.out.println("Turning note ON at : " + startTime.getDuration().getNumerator() + "/" + startTime.getDuration().getDenominator());
							_midi.multiNoteOn(mn);
						}
						else {
							doNotPlayYet = true;
						}

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
				
				// don't sleep if iterating to start point
				if(!doNotPlayYet) {
					try {
						Thread.sleep(sleepMilli);
					}
					catch (InterruptedException ie){
						// stop playing all the notes when the playback is stopped
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
	}
	
//	public void stopPlayback() {
//		_isPlaying = false;
//	}
}
