package instructions;

public class EditInstruction extends Instruction {
	InstructionIndex _index;
	EditInstructionType _type;
	
	public EditInstruction(Object src, InstructionIndex index, EditInstructionType type) {
		super(src);
		_index = index;
		_type = type;
	}
}
