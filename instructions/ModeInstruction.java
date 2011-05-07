package instructions;

import gui.EditMode;

public class ModeInstruction extends Instruction {
	ModeInstructionType _type;
	Object _value;
	
	public ModeInstruction(Object src, ModeInstructionType type, Object value) {
		super(src);
		
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
