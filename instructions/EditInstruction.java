package instructions;

import music.*;
import java.util.List;

/*
 * EditInstruction is used for basic editing functions (addition, removal,
 * and replacement of elements)
 */

public class EditInstruction extends Instruction {
	EditInstructionType _type;		// insertion, addition, or removal
	
	InstructionIndex _indices;		// where in the piece is being edited
	EditType _elemType;				// what type of element is being modified
	Timestep _element;				// element to insert/add, left blank if EditInstruction is a removal
	
	
	public EditInstruction(InstructionIndex indices, EditInstructionType type, 
			EditType elemType) {
		_indices = indices;
		_type = type;
		_elemType = elemType;
	}
	
	public EditInstruction(InstructionIndex index, EditInstructionType type, EditType elemType, Timestep element) {
		this(index, type, elemType);
		_element = element;
	}
	
	public InstructionIndex getIndex() {
		return _indices;
	}

	public void setIndex(InstructionIndex indices) {
		this._indices = indices;
	}

	public EditInstructionType getType() {
		return _type;
	}

	public void setType(EditInstructionType type) {
		this._type = type;
	}

	public EditType getElemType() {
		return _elemType;
	}

	public void setElemType(EditType elemType) {
		this._elemType = elemType;
	}

	public Timestep getElement() {
		return _element;
	}

	public void setElement(Timestep element) {
		this._element = element;
	}
}
