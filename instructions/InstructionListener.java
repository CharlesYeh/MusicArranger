package instructions;

import java.util.EventListener;

public interface InstructionListener extends EventListener {
	public void receiveInstruction(Instruction instr);
}
