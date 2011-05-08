package music;

public class Interval {
	IntervalType _type;
	int _size;
	
	public Interval(IntervalType type, int size) {
		_type = type;
		_size = size;
	}
	
	public IntervalType getIntervalType() {
		return _type;
	}
	
	public int getSize() {
		return _size;
	}
}
