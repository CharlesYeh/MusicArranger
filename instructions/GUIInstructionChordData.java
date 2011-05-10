package instructions;

import java.util.*;
import music.*;
import util.*;

/*
 * GUIInstructionChordData holds an InstructionIndex and a list of ChordSymbols, which
 * represent the chords that the analyzer has listed as possibilities at that point.
 */
public class GUIInstructionChordData extends GUIInstruction {
	Map<InstructionIndex, List<Node<ChordSymbol>>> _indexChords;
	
	public GUIInstructionChordData() {
		super();
	}
	
	public Map<InstructionIndex, List<Node<ChordSymbol>>> getIndexChords() {
		return _indexChords;
	}
}
