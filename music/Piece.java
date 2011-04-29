package music;

import java.util.LinkedList;
import java.util.ArrayList;

public class Piece {
	ArrayList<Staff> _staffs;					// staffs in the piece
	
	public Piece() {
		_staffs = new ArrayList<Staff>();
	}
	
	public Piece (int numStaffs, int numMeasures) {
		this();
		for (int i = 0; i < numStaffs; i++) {
			_staffs.add(new Staff(numMeasures));
		}
	}
	
	public ArrayList<Staff> getStaffs() {
		return _staffs;
	}
	
}