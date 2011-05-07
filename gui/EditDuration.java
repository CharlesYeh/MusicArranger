package gui;
import music.Rational;

public enum EditDuration {
	QUARTER(1, 4), HALF(2, 4), WHOLE(4, 4), EIGHTH(1, 8), SIXTEENTH(1, 16);
	
	private Rational _duration;
	
	EditDuration(int numer, int denom) {
		_duration = new Rational(numer, denom);
	}
	
	public Rational getDuration() {
		return _duration;
	}
}
