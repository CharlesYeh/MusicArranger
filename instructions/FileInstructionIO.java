package instructions;

public class FileInstructionIO extends FileInstruction {
	FileInstructionType _type;
	String _filename;
	
	public FileInstructionIO(Object src, FileInstructionType type, String filename) {
		super(src);
		_type = type;
		_filename = filename;
	}
	
	public FileInstructionType getType() {
		return _type;
	}
}
