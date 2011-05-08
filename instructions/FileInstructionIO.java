package instructions;

public class FileInstructionIO extends FileInstruction {
	FileInstructionType _type;
	String _fileName;
	
	public FileInstructionIO(FileInstructionType type, String fileName) {
		_type = type;
		_fileName = fileName;
	}
	
	public FileInstructionType getType() {
		return _type;
	}
	
	public String getFileName() {
		return _fileName;
	}
}
