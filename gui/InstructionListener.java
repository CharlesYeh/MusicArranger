package gui;

import java.util.EventListener;
import instructions.Instruction;

public interface InstructionListener extends EventListener {
	public void instructionReceived(Instruction instr);
}