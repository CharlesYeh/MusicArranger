package tests;

import music.*;

public class NewPiece extends Piece {
	
	public NewPiece() {
		super();
		
		// pitches
		
		// multinotes
		MultiNote I = new MultiNote(new Rational(4, 4));
		
		
		// clefs
		Clef cleftreble = new Clef(new Rational(4, 4), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(4, 4), ClefName.FCLEF, 2);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(4, 4), 4, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(4, 4), 2, true);

		// staffs
		Staff stafftreble = new Staff();
		Staff staffbass = new Staff();
		
		ChordSymbol emptyChord = new ChordSymbol(new Rational(4, 4), new ScaleDegree(0, Accidental.NATURAL), ChordType.UNSPECIFIED);
		
		for (int i = 0; i < 10; i++) {

			Voice voice1_1_1 = new Voice();
				// staff 1, measure 1, voice 1
				voice1_1_1.getMultiNotes().add(I);

			Voice voice2_1_1 = new Voice();
				voice2_1_1.getMultiNotes().add(I);
				
			// measures
			Measure measure1_1 = new Measure();
				measure1_1.getKeySignatures().add(keysig1);
				measure1_1.getTimeSignatures().add(timesig1);
				measure1_1.getClefs().add(cleftreble);
				measure1_1.getVoices().add(voice1_1_1);
				measure1_1.getChordSymbols().add(emptyChord);

			Measure measure2_1 = new Measure();
				measure2_1.getKeySignatures().add(keysig1);
				measure2_1.getTimeSignatures().add(timesig1);
				measure2_1.getClefs().add(clefbass);
				measure2_1.getVoices().add(voice2_1_1);
				measure2_1.getChordSymbols().add(emptyChord);

			stafftreble.getMeasures().add(measure1_1);
			staffbass.getMeasures().add(measure2_1);
		}

		// piece
		getStaffs().add(stafftreble);
		getStaffs().add(staffbass);
	}

	public static void main(String[] args) {
		new TestPiece();
	}
}