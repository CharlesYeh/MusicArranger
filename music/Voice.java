package music;

import java.util.ArrayList;

/* Voice represents a single musical voice within a staff.  It is comprised of a linked list of
 * MultiNotes.
 */
public class Voice{
	ArrayList<MultiNote> _notes;
	
	public Voice(){
		_notes = new ArrayList<MultiNote>();
	}
	
	public ArrayList<MultiNote> getNotes() {
		return _notes;
	}
}