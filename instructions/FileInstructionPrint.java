package instructions;

import java.awt.Image;

public class FileInstructionPrint extends FileInstruction{
	Image _buffer;

	public FileInstructionPrint(Image img) {
		_buffer = img;
	}

	public Image getImage() {
		return _buffer;
	}
}