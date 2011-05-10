package music;

public class Interval {
	IntervalType _type;
	int _size;
	
	public Interval(IntervalType type, int size) {
		_type = type;
		_size = size;
	}
	
	// Alternative constructor: accidental represents the offset from either
	// the MAJOR or PERFECT variant of the interval.
	public Interval(int accidental, int size) {
		IntervalType type = null;
		
		if (size == 1 || size == 4 || size == 5) {
			switch (accidental) {
			case -1:
				type = IntervalType.DIMINISHED;
				break;
			case 0:
				type = IntervalType.PERFECT;
				break;
			case 1:
				type = IntervalType.AUGMENTED;
				break;
			}
		}
		else {
			switch (accidental) {
			case -2:
				type = IntervalType.DIMINISHED;
				break;
			case -1:
				type = IntervalType.MINOR;
				break;
			case 0:
				type = IntervalType.MAJOR;
				break;
			case 1:
				type = IntervalType.AUGMENTED;
				break;
			}
		}
		_type = type;
		_size = size;
	}
	
	public IntervalType getIntervalType() {
		return _type;
	}
	
	public int getSize() {
		return _size;
	}
	
	public int getHalfSteps() {
		int numHalfSteps;
		
		switch (_size) {
		case 1:
			numHalfSteps = 0;
			switch (_type) {
				case PERFECT:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 2:
			numHalfSteps = 2;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 2;
					break;
				case MINOR:
					numHalfSteps -= 1;
					break;
				case MAJOR:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 3:
			numHalfSteps = 4;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 2;
					break;
				case MINOR:
					numHalfSteps -= 1;
					break;
				case MAJOR:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 4:
			numHalfSteps = 5;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 1;
					break;
				case PERFECT:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 5:
			numHalfSteps = 7;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 1;
					break;
				case PERFECT:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 6:
			numHalfSteps = 9;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 2;
					break;
				case MINOR:
					numHalfSteps -= 1;
					break;
				case MAJOR:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		case 7:
			numHalfSteps = 11;
			switch (_type) {
				case DIMINISHED:
					numHalfSteps -= 2;
					break;
				case MINOR:
					numHalfSteps -= 1;
					break;
				case MAJOR:
					break;
				case AUGMENTED:
					numHalfSteps += 1;
					break;
				default:
					throw new RuntimeException("Interval does not exist");
			}
			break;
		default:
			throw new RuntimeException("Interval does not exist");
		}
		
		return numHalfSteps;
	}
	
	public String toString() {
		return _type.toString() + _size;
	}
	
	public Interval invert() {
		int invertedSize = 8 - _size;
		IntervalType invertedType = IntervalType.PERFECT;
		switch (_type) {
		case MINOR:
			invertedType = IntervalType.MAJOR;
			break;
		case MAJOR:
			invertedType = IntervalType.MINOR;
			break;
		case AUGMENTED:
			invertedType = IntervalType.DIMINISHED;
			break;
		case DIMINISHED:
			invertedType = IntervalType.AUGMENTED;
			break;
		}
		return new Interval(invertedType, invertedSize);
	}
}
