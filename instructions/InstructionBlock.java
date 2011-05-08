package instructions;

import java.util.List;
import java.util.LinkedList;
import java.util.EventObject;

public class InstructionBlock extends EventObject {
	List<Instruction> _instructions;
	
	public InstructionBlock(Object src) {
		super(src);
		
		_instructions = new LinkedList<Instruction>();
	}
	
	public InstructionBlock(Object src, Instruction instr) {
		this(src);
		
		addInstruction(instr);
	}
	
	public void addInstruction(Instruction instr) {
		if (instr == null)
			return;
		
		_instructions.add(instr);
	}
	
	public List<Instruction> getInstructions() {
		return _instructions;
	}
	
	public boolean isEmpty() {
		return _instructions.isEmpty();
	}
}