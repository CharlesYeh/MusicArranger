package music;

import java.util.ListIterator;

public class TimestampAssociator extends Timestep implements Comparable{
	ListIterator<? extends Timestep> _associated;
	
	public TimestampAssociator(ListIterator<? extends Timestep> assoc) {
		_associated = assoc;
	}
	
	public int compareTo(Object obj) {
		// weight clefs, key signatures, then time signatures?
		
		TimestampAssociator dur = (TimestampAssociator) obj;
		int diff = getDuration().compareTo(dur.getDuration());
		if (diff == 0) {
			
			int weightAssoc = getWeight();
			int weightOther = dur.getWeight();
			
			return weightOther - weightAssoc;
		}
		
		return diff;
	}
	
	int getWeight() {
		if (_associated instanceof Clef) {
			return 3;
		}
		else if (_associated instanceof KeySignature) {
			return 2;
		}
		else if (_associated instanceof TimeSignature) {
			return 1;
		}
		
		return 0;
	}
	
	public ListIterator<? extends Timestep> getAssociated() {
		return _associated;
	}
	
	/*public int compareTo(long millis) {
		return ((double) _numerator / _denominator < millis) ? -1 : 1;
	}*/
}