package music;

/* TimeSig represents a musical time signature, which specifies which type of note represents
 * a beat, and how many beats are contained within a measure.
 */
public class TimeSignature extends Timestep {
	int _numerator;
	int _denominator;
	
	public TimeSignature(Rational duration, int numerator, int denominator){
		super(duration);
		_numerator = numerator;
		_denominator = denominator;
	}
	
	public Rational getMeasureDuration() {
		return new Rational(_numerator, _denominator);
	}
	
	public int getNumerator() {
		return _numerator;
	}
	
	public int getDenominator() {
		return _denominator;
	}
}