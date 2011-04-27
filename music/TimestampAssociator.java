package music;

import java.util.ListIterator;

public class TimestampAssociator extends Timestep implements Comparable{
	ListIterator<? extends Timestep> _associated;
	Class _assocType;
	
	public TimestampAssociator(ListIterator<? extends Timestep> assoc) {
		_associated = assoc;
		_assocType = null;
	}
	
	public TimestampAssociator(ListIterator<? extends Timestep> assoc, Class type) {
		_associated = assoc;
		_assocType = type;
	}
	
	public int compareTo(Object obj) {
		// weight clefs, key signatures, then time signatures?
		
		TimestampAssociator dur = (TimestampAssociator) obj;
		int diff = getDuration().compareTo(dur.getDuration());
		if (diff == 0 && _assocType != null) {
			int weightAssoc = getWeight();
			int weightOther = dur.getWeight();
			return weightOther - weightAssoc;
		}
		
		return diff;
	}
	
	int getWeight() {
		if (_assocType == Clef.class) {
			return 3;
		}
		else if (_assocType == KeySignature.class) {
			return 2;
		}
		else if (_assocType == TimeSignature.class) {
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