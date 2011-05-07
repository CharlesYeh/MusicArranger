package instructions;

public class GenerateInstruction extends Instruction {
	GenerateInstructionType _type;
	Object _value;
	
	public GenerateInstruction(Object src, GenerateInstructionType type) {
		super(src);
		
		_type = type;
	}
	
	public GenerateInstructionType getType() {
		return _type;
	}
}