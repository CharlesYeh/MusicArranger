package music;

import java.util.ListIterator;

public class TimestampAssociator extends Duration implements Comparable{
	ListIterator<? extends Duration> _associated;
	public TimestampAssociator(ListIterator<? extends Duration> assoc) {
		_associated = assoc;
	}
	
	public int compareTo(Object obj) {
		TimestampAssociator dur = (TimestampAssociator) obj;
		return compareTo(dur);
	}
	
	public ListIterator<? extends Duration> getAssociated() {
		return _associated;
	}
}