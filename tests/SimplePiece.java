package tests;

import logic.ArrangerXMLWriter;
import music.*;

public class SimplePiece extends Piece {

	public SimplePiece() {
		super();

		// pitches
		Pitch cn4 = new Pitch(NoteLetter.C, 4, Accidental.NATURAL, false);
		Pitch dn5 = new Pitch(NoteLetter.D, 5, Accidental.NATURAL, false);
		Pitch en4 = new Pitch(NoteLetter.E, 4, Accidental.NATURAL, false);
		Pitch fs4 = new Pitch(NoteLetter.B, 4, Accidental.SHARP, false);
		Pitch cn3 = new Pitch(NoteLetter.C, 3, Accidental.NATURAL, false);
		Pitch ff3 = new Pitch(NoteLetter.F, 3, Accidental.FLAT, false);

		// multinotes
		MultiNote treble1 = new MultiNote(new Rational(1, 1));
			treble1.getPitches().add(cn4);
		MultiNote treble2 = new MultiNote(new Rational(1, 2));
			treble2.getPitches().add(dn5);
		MultiNote treble3 = new MultiNote(new Rational(1, 4));
			treble3.getPitches().add(cn4);
			treble3.getPitches().add(en4);
		MultiNote treble4 = new MultiNote(new Rational(1, 4));
			treble4.getPitches().add(fs4);
		MultiNote treble5 = new MultiNote(new Rational(1, 4));
			treble5.getPitches().add(cn3);
		MultiNote treble6 = new MultiNote(new Rational(1, 2)); // rest
		MultiNote treble7 = new MultiNote(new Rational(1, 4));
			treble7.getPitches().add(ff3);

		// voices
		Voice voice1_1 = new Voice();
			voice1_1.getMultiNotes().add(treble1);
		Voice voice1_2 = new Voice();
			voice1_2.getMultiNotes().add(treble2);
			voice1_2.getMultiNotes().add(treble3);
			voice1_2.getMultiNotes().add(treble4);
		Voice voice2_1 = new Voice();
			voice2_1.getMultiNotes().add(treble7);
			voice2_1.getMultiNotes().add(treble7);
			voice2_1.getMultiNotes().add(treble7);
			voice2_1.getMultiNotes().add(treble7);
		Voice voice2_2 = new Voice();
			voice2_2.getMultiNotes().add(treble5);
			voice2_2.getMultiNotes().add(treble6);
			voice2_2.getMultiNotes().add(treble7);

		// clefs
		Clef cleftreble = new Clef(new Rational(1, 1), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(1, 1), ClefName.FCLEF, 2);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(1, 1), 4, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(1, 1), 0, true);

		// measures
		Measure measure1Treble = new Measure();
			measure1Treble.getVoices().add(voice1_1);
			measure1Treble.getVoices().add(voice1_2);
			measure1Treble.getClefs().add(cleftreble);
			measure1Treble.getKeySignatures().add(keysig1);
			measure1Treble.getTimeSignatures().add(timesig1);

		Measure measure1Bass = new Measure();
			measure1Bass.getVoices().add(voice2_1);
			measure1Bass.getVoices().add(voice2_2);
			measure1Bass.getClefs().add(clefbass);
			measure1Bass.getKeySignatures().add(keysig1);
			measure1Bass.getTimeSignatures().add(timesig1);
		
		// staffs
		Staff stafftreble = new Staff();
			stafftreble.getMeasures().add(measure1Treble);

		Staff staffbass = new Staff();
			staffbass.getMeasures().add(measure1Bass);

		// piece
			getStaffs().add(stafftreble);
			getStaffs().add(staffbass);

	}

	public static void main(String[] args) {
		ArrangerXMLWriter writer = new ArrangerXMLWriter();
		try {
			writer.write(new SimplePiece(), "simple_piece.xml");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}