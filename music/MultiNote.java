package music;

import java.util.LinkedList;

public class MultiNote extends Duration {
	int _durationNumer, _durationDenom;
	
	LinkedList<Pitch> _notes;
	
	public MultiNote() {
		_durationNumer = _durationDenom = 1;
		
		_notes = new LinkedList<Pitch>();
	}
}