package instructions;
import music.*;
import java.util.*;

public class GenerateInstructionVoices extends GenerateInstruction {
	InstructionIndex _start;
	InstructionIndex _end;
	Rational _spacing;
	
	int _numVoices;
	
	public GenerateInstructionVoices(InstructionIndex start, InstructionIndex end, Rational spacing,
											int numVoices) {
		
		super(GenerateInstructionType.VOICES);
		
		_start = start;
		_end = end;
		_spacing = spacing;
		
		_numVoices = numVoices;
	}

	public InstructionIndex getStartIndex() {
		return _start;
	}

	public InstructionIndex getEndIndex() {
		return _end;
	}

	public Rational getSpacing() {
		return _spacing;
	}
	
	public int getNumVoices() {
		return _numVoices;
	}
}
