package instructions;

public class GenerateInstruction extends Instruction {
	GenerateInstructionType _type;
	Object _value;
	
	public GenerateInstruction(GenerateInstructionType type) {
		_type = type;
	}
	
	public GenerateInstructionType getType() {
		return _type;
	}
}