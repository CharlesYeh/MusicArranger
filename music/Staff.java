package music;

import java.util.List;
import java.util.ArrayList;

/* Staff represents a single musical staff within the piece, which contains information regarding
 * four separate voices, as well as the clef(s) assigned to the staff.
 */
public class Staff{
	List<Measure> _measures;	// measures in the staff
	
	// constructor
	public Staff() {
		_measures = new ArrayList<Measure>();
	}
	
	// constructor, if a number of initial measures is specified
	public Staff(int numMeasures) {
		this();
		for (int i = 0; i < numMeasures; i++) {
			_measures.add(new Measure());
		}
	}
	
	public List<Measure> getMeasures() {
		return _measures;
	}
}