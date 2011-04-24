package music;

import java.util.ArrayList;

/* MultiNote is a broad class definition used to describe either a rest, a single note, or an
 * arbitrary number of notes being played in unison.
 */
public class MultiNote {
	Duration _duration;							// Specifies the number of beats the multinote lasts for
	
	ArrayList<Pitch> _pitches;					// all pitches in the MultiNote, from highest to lowest; an empty list of Pitches represents a rest.
	
	public MultiNote() {
		_durationNumer = _durationDenom = 1;
		_pitches = new ArrayList<Pitch>();
	}
	
	public ArrayList<Pitch> getPitches() {
		return _pitches;
	}
	
	public Duration getDuration() {
		return _duration;
	}
}