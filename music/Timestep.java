package music;

/* Duration is a class used to represent a rhytmic duration.  It implements rational arithmetic
 * by representing all durations as Rationals.
 */
public abstract class Timestep {
	Rational _duration;	// Time duration is stored as a rational number.
	
	public Timestep() {
		_duration = new Rational(0, 1);
	}
	
	public Timestep(Rational duration) {
		_duration = duration;
   }
	
	public Rational getDuration() {
		return _duration;
	}
	
	public Timestep setDuration(Rational newDuration) {
		_duration = newDuration;
		return this;
	}
	
	public void addDuration(Timestep dur) {
		addDuration(dur.getDuration());
	}
	public void addDuration(Rational dur) {
		setDuration(_duration.plus(dur));
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Timestep) {
			Timestep ts = (Timestep) obj;
			return _duration.equals(ts.getDuration());
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "Timestep: " + _duration;
	}
}