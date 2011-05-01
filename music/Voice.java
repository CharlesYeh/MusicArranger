package music;

import java.util.List;
import java.util.ArrayList;

/* Voice represents a single musical voice within a staff.  It is comprised of a linked list of
 * MultiNotes.
 */
public class Voice {
	List<MultiNote> _multiNotes;
	
	public Voice(){
		_multiNotes = new ArrayList<MultiNote>();
	}
	
	public List<MultiNote> getMultiNotes() {
		return _multiNotes;
	}
}