package instructions;

import java.awt.image.BufferedImage;

public class FileInstructionPrint extends FileInstruction{
	BufferedImage _buffer;
	
	public FileInstructionPrint(BufferedImage img) {
		_buffer = img;
	}
	
	public BufferedImage getImage() {
		return _buffer;
	}
}