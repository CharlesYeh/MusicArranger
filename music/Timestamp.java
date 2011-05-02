package music;

public class Timestamp extends Timestep implements Comparable {
	Class _assocType;
	int _id;

	static int nextId = 0;


	public Timestamp() {
		this(null);
	}

	public Timestamp(Class type) {
		_assocType = type;
		_id = nextId++;
	}

	public Class getAssocClass(){
		return _assocType;
	}

	public int getId() {
		return _id;
	}

	public int compareTo(Object obj) {
		Timestamp dur = (Timestamp) obj;

		int diff = getDuration().compareTo(dur.getDuration());
		if (diff == 0) {
			if (_assocType != null) {
				// not a multinote
				int weightAssoc = getWeight();
				int weightOther = dur.getWeight();
				
				// if weights are the same, then use ids too
				int weightDiff = weightOther - weightAssoc;
				if (weightDiff == 0) {
					return _id - dur.getId();
				}
				else {
					return weightDiff;
				}
			}
			else {
				// multinotes shouldn't have the exact same compareto value
				return _id - dur.getId();
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