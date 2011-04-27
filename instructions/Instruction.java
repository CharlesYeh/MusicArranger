package instructions;

import java.util.List;
import java.util.EventObject;

public abstract class Instruction extends EventObject {
	//InstructionType type;
	List<InstructionIndex> indices;
	
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