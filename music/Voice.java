package music;

import java.util.ArrayList;

/* Voice represents a single musical voice within a staff.  It is comprised of a linked list of
 * MultiNotes.
 */
public class Voice {
	ArrayList<MultiNote> _multiNotes;
	
	public Voice(){
		_multiNotes = new ArrayList<MultiNote>();
	}
	
	public ArrayList<MultiNote> getMultinotes() {
		return _multiNotes;
	}
}