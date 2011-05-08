package instructions;

public class PlaybackInstruction extends Instruction {
	PlaybackInstructionType _type;	// type of instruction
	
	public PlaybackInstruction(PlaybackInstructionType type) {
		_type = type;
	}
	
	public PlaybackInstructionType getType() {
		return _type;
	}
}
