package music;

/* Duration is a class used to represent a rhytmic duration.  It implements rational arithmetic
 * by representing all durations as Rationals.
 */
public class Duration {
	Rational _duration;	// Time duration is stored as a rational number.
	
	public Duration() {
		_duration = new Rational(0, 1);
	}
	
	public Duration(Rational duration) {
		_duration = duration;
   }
	
	public Rational getDuration() {
		return _duration;
	}
	
	public Duration setDuration(Rational newDuration) {
		_duration = newDuration;
		return this;
	}
	
	public void addDuration(Duration dur) {
		setDuration(_duration.plus(dur.getDuration()));
	}
}