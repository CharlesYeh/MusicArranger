package instructions;

public class FileInstruction extends Instruction {
	FileInstructionType _type;	// type of instruction
	String _filePath;			// filepath, if loading/saving a file
	
	public FileInstruction(Object src, FileInstructionType type, String filePath) {
		super(src);
		
		_filePath = filePath;
		_type = type;
		
	}
	public FileInstruction(Object src, FileInstructionType type) {
		super(src);
		
		_filePath = null;
		_type = type;
	}
}
