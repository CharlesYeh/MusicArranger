package instructions;
import music.*;
import java.util.*;

public class GenerateInstructionAnalyzeChords extends GenerateInstruction {
	InstructionIndex _start;
	InstructionIndex _end;
	Rational _spacing;
	
	public GenerateInstructionAnalyzeChords(InstructionIndex start, InstructionIndex end,
			Rational spacing) {
		
		super(GenerateInstructionType.CHORDS);
		
		_start = start;
		_end = end;
		_spacing = spacing;
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
	
}
