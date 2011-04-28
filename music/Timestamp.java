package music;

public class Timestamp extends Timestep implements Comparable {
	Class _assocType;

	public Timestamp() {
		_assocType = null;
	}

	public Timestamp(Class type) {
		_assocType = type;
	}
	
	public Class getAssocClass(){
		return _assocType;
	}

	public int compareTo(Object obj) {
		Timestamp dur = (Timestamp) obj;

		int diff = getDuration().compareTo(dur.getDuration());
		if (diff == 0) {
			if (_assocType != null) {
				// not a multinote
				int weightAssoc = getWeight();
				int weightOther = dur.getWeight();
				return weightOther - weightAssoc;
			}
			else {
				// multinotes shouldn't have the exact same compareto value
				return 1;
			}
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