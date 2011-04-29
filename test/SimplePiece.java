package test;

import logic.ArrangerXMLWriter;
import music.*;

public class SimplePiece extends Piece {

	public SimplePiece() {
		super();

		// pitches
		Pitch cn4 = new Pitch(NoteLetter.C, 4, Accidental.NATURAL, false);
		Pitch dn4 = new Pitch(NoteLetter.D, 5, Accidental.NATURAL, false);
		Pitch en4 = new Pitch(NoteLetter.E, 4, Accidental.NATURAL, false);
		Pitch fs4 = new Pitch(NoteLetter.B, 4, Accidental.SHARP, false);
		Pitch gn4 = new Pitch(NoteLetter.C, 3, Accidental.NATURAL, false);
		Pitch af5 = new Pitch(NoteLetter.F, 3, Accidental.FLAT, false);

		// multinotes
		MultiNote treble1 = new MultiNote(new Rational(1, 1));
			treble1.getPitches().add(cn4);
		MultiNote treble2 = new MultiNote(new Rational(1, 2));
			treble2.getPitches().add(dn4);
		MultiNote treble3 = new MultiNote(new Rational(1, 4));
			treble3.getPitches().add(en4);
		MultiNote treble4 = new MultiNote(new Rational(1, 4));
			treble4.getPitches().add(fs4);
		MultiNote treble5 = new MultiNote(new Rational(1, 4));
			treble5.getPitches().add(gn4);
		MultiNote treble6 = new MultiNote(new Rational(1, 2)); // rest
		MultiNote treble7 = new MultiNote(new Rational(1, 4));
			treble7.getPitches().add(af5);

		Pitch p1 = new Pitch(NoteLetter.A, 3, Accidental.NATURAL, false);
		MultiNote t1 = new MultiNote(new Rational(1, 1));
			t1.getPitches().add(p1);

		// voices
		Voice voicetreble1 = new Voice();
			voicetreble1.getMultinotes().add(treble1);
			voicetreble1.getMultinotes().add(treble2);
			voicetreble1.getMultinotes().add(treble3);
			voicetreble1.getMultinotes().add(treble4);
			voicetreble1.getMultinotes().add(treble5);
			voicetreble1.getMultinotes().add(treble6);
			voicetreble1.getMultinotes().add(treble7);

		Voice v2 = new Voice();
			v2.getMultinotes().add(t1);

		// clefs
		Clef cleftreble = new Clef(new Rational(2, 1), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(1, 1), ClefName.FCLEF, 2);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(3, 1), 2, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(3, 1), 0, true);

		// measures
		Measure measure1Treble = new Measure();
			measure1Treble.getVoices().add(voicetreble1);
			measure1Treble.getClefs().add(cleftreble);

			measure1Treble.getKeySignatures().add(keysig1);

			measure1Treble.getTimeSignatures().add(timesig1);

		Measure measure1Bass = new Measure();
			measure1Bass.getClefs().add(clefbass);
			//measure1Bass.getVoices().add(v2);

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