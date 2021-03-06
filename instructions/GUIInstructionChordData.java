package instructions;

import java.util.*;
import music.*;
import util.*;

/*
 * GUIInstructionChordData holds an InstructionIndex and a list of ChordSymbols, which
 * represent the chords that the analyzer has listed as possibilities at that point.
 */
public class GUIInstructionChordData extends GUIInstruction {
	List<InstructionIndex> _indices;
	List<List<Node<ChordSymbol>>> _indexChords;
	
	public GUIInstructionChordData(List<InstructionIndex> listIndex, List<List<Node<ChordSymbol>>> listChords) {
		super();
		
		if (listIndex.size() != listChords.size()) {
			System.out.println("Error, arrays in GUIInstructionChordData do not have the same dimension");
			return;
		}
		
		_indices = listIndex;
		_indexChords = listChords;
		
		/*
		Iterator<InstructionIndex> iterInstr = listIndex.iterator();
		Iterator<List<Node<ChordSymbol>>> iterChords = listChords.iterator();
		
		_indexChords = new HashMap<InstructionIndex, List<Node<ChordSymbol>>>();
		while (iterInstr.hasNext()) {
			_indexChords.put(iterInstr.next(), iterChords.next());
		}*/
	}
	
	/*public Map<InstructionIndex, List<Node<ChordSymbol>>> getIndexChords() {
		return _indexChords;
	}*/
	
	public List<InstructionIndex> getIndices() {
		return _indices;
	}
	
	public List<List<Node<ChordSymbol>>> getChords() {
		return _indexChords;
	}
}
