package music;

/* TimeSig represents a musical time signature, which specifies which type of note represents
 * a beat, and how many beats are contained within a measure.
 */
public class TimeSignature extends Duration {
	Duration _measureDuration;			// Specifies the duration of a measure under the time signature.  NOTE: This is different from the time signature's duration within the piece!

	public TimeSignature(Duration duration, Duration measureDuration){
		super(duration);
		_measureDuration = measureDuration;
	}
	
	public Duration getMeasureDuration() {
		return _measureDuration;
	}
}