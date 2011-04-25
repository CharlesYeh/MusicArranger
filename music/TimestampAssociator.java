package music;

import java.util.ListIterator;

public class TimestampAssociator extends Timestep implements Comparable{
	ListIterator<? extends Timestep> _associated;
	
	public TimestampAssociator(ListIterator<? extends Timestep> assoc) {
		_associated = assoc;
	}
	
	public int compareTo(Object obj) {
		TimestampAssociator dur = (TimestampAssociator) obj;
		return getDuration().compareTo(dur.getDuration());
	}
	
	public ListIterator<? extends Timestep> getAssociated() {
		return _associated;
	}
	
	public int compareTo(long millis) {
		return ((double) _numerator / _denominator < millis) ? -1 : 1;
	}
}