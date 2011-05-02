package instructions;

import music.*;
import java.util.List;

/*
 * EditInstruction is used for basic editing functions (addition, removal,
 * and replacement of elements)
 */

public class EditInstruction extends Instruction {
	EditInstructionType _type;		// insertion, addition, or removal
	
	List<InstructionIndex> _index;		// where in the piece is being edited
	EditType _elemType;				// what type of element is being modified
	Timestep _element;				// element to insert/add, left blank if EditInstruction is a removal
	
	
	public EditInstruction(Object src, List<InstructionIndex> index, EditInstructionType type, 
			EditType elemType) {
		super(src);
		
		_index = index;
		_type = type;
		_elemType = elemType;
	}
	
	public EditInstruction(Object src, List<InstructionIndex> index, EditInstructionType type, 
			EditType elemType, Timestep element) {
		this(src, index, type, elemType);
		_element = element;
	}
	
	public List<InstructionIndex> getIndex() {
		return _index;
	}

	public void setIndex(List<InstructionIndex> index) {
		this._index = index;
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
