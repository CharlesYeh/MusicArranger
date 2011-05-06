package instructions;

public class PlaybackInstruction extends Instruction {
	PlaybackInstructionType _type;	// type of instruction
	
	public PlaybackInstruction(PlaybackInstructionType type) {
		super(null);
		
		_type = type;
	}
	
	public PlaybackInstruction(Object src, PlaybackInstructionType type) {
		super(src);
		
		_type = type;
	}
	
	public PlaybackInstructionType getType() {
		return _type;
	}
}
