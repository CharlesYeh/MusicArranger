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
}
