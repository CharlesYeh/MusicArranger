package music;

public class Timestamp extends Timestep implements Comparable {
	Class _assocType;
	
	public TimestampAssociator() {
		_assocType = null;
	}
	
	public TimestampAssociator(Class type) {
		_assocType = type;
	}
	
	public int compareTo(Object obj) {
		Timestamp dur = (Timestamp) obj;
		
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
	
}