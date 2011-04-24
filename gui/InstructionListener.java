package gui;

import java.util.EventListener;
import logic.Instruction;

public interface InstructionListener extends EventListener {
	public void instructionReceived(Instruction instr);
}