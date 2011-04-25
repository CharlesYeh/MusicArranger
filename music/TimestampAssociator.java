package music;

import java.util.ListIterator;

public class TimestampAssociator<T> extends Timestep implements Comparable{
	T _associated;
	
	public TimestampAssociator(T assoc) {
		_associated = assoc;
	}
	
	public int compareTo(Object obj) {
		TimestampAssociator dur = (TimestampAssociator) obj;
		return getDuration().compareTo(dur.getDuration());
	}
	
	public T getAssociated() {
		return _associated;
	}
	
	/*public int compareTo(long millis) {
		return ((double) _numerator / _denominator < millis) ? -1 : 1;
	}*/
}