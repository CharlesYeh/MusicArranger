package instructions;

import gui.EditMode;

public class ModeInstruction extends Instruction {
	EditMode _type;
	
	public ModeInstruction(Object src, EditMode type) {
		super(src);
		
		_type = type;
	}
	
	public EditMode getType() {
		return _type;
	}
}
