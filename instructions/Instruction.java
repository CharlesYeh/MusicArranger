package instructions;

import java.util.List;
import java.util.EventObject;

/*
 * An Instruction is an abstract class, extended by a number of different types of
 * Instruction classes, used for communication between the GUI and logic components
 * of the Arranger.
 */
public abstract class Instruction extends EventObject {
	
	public Instruction(Object src) {
		super(src);
	}
}

/* POSSIBLE INSTRUCTIONS
	Editor
		Notes
			Data
				Index (represented with:)
					Rational - (Rhythmic position in song)
					int - Staff index
					int - Voice index
		Clefs
			Data
				Index (represented with:)
					Rational - (Rhythmic position in song)
					int - Staff index
		KeySignatures
			Data
				Index (represented with:)
					Rational - (Rhythmic position in song)
		TimeSignatures
			Data
				Index (represented with:)
					Rational - (Rhythmic position in song)
		ChordSymbols
			Data
				Index (represented with:)
					Rational - (Rhythmic position in song)
					
*/