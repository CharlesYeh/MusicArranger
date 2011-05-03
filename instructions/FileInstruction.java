package instructions;

/* Currently, FileInstruction holds no important information on its own.
 * Use FileInstructionNew for creating new scores and FileInstructionIO
 * for loading and saving scores.
 */
public class FileInstruction extends Instruction {
	public FileInstruction(Object src) {
		super(src);
	}
}
