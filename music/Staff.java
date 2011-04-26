package music;

import java.util.ArrayList;

/* Staff represents a single musical staff within the piece, which contains information regarding
 * four separate voices, as well as the clef(s) assigned to the staff.
 */
public class Staff{
	ArrayList<Voice> _voices;		// Voices contained within the staff.
	ArrayList<Clef> _clefs;			// List of Clefs and their durations.

	public Staff() {
		_voices = new ArrayList<Voice>();
		/*for (int i = 0; i<4; i++) {
			_voices.add(new Voice());	// Staffs will, by default, support exactly four voices.
		}*/
		
		_clefs = new ArrayList<Clef>();
	}
	
	public ArrayList<Voice> getVoices() {
		return _voices;
	}
	
	public ArrayList<Clef> getClefs() {
		return _clefs;
	}
}