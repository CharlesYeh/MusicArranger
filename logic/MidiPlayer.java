package logic;

import music.*;
import java.lang.Thread;

public class MidiPlayer extends Thread {
	
	/*PriorityQueue<TimestampAssociator<MultiNote>> _starts;
	PriorityQueue<TimestampAssociator<MultiNote>> _ends;*/
	
	public MidiPlayer() {
		/*_starts = new PriorityQueue<TimestampAssociator<MultiNote>>();
		_ends = new PriorityQueue<TimestampAssociator<MultiNote>>();*/
	}
	
	public void start() {
		// get start timestamp
		
	}
	
	public void addMultiNote(MultiNote mn) {
		
	}
	
	public void run() {
		/*
		//System.currentTimeMillis()
		long nextTime = 0;
		
		while (true) {
			long currentTime = System.getMillisSeconds();
			// turn on notes
			TimestampAssociator<MultiNote> ts = _starts.peek();
			if (ts.compareTo(currentTime)){
				// play it
				_starts.poll();
				MultiNote mn = ts.getAssociated();
				ts.addDuration(mn);
				
				_ends.add(ts);
			}
			else{
				nextTime = ts.getDuration();
			}
			
			// turn off notes
			TimestampAssociator<MultiNote> ts = _end.peek();
			
			if (ts.compareTo(currentTime)){
				// play it
				_end.poll();
				
			}
			else{
				nextTime = Math.min(nextTime, ts.getDuration());
			}
			
			Thread.sleep(nextTime - currentTime);
		}*/
	}
}
