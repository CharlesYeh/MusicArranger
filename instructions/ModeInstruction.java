package instructions;

public class ModeInstruction extends Instruction {
	ModeInstructionType _type;
	Object _value;
	
	public ModeInstruction(ModeInstructionType type, Object value) {
		_type = type;
		_value = value;
	}
	
	public ModeInstructionType getType() {
		return _type;
	}
	
	public Object getValue() {
		return _value;
	}
}
