package test;

import music.*;

public class SimplePiece extends Piece {

	public SimplePiece() {
		super();
	
		// pitches
		Pitch cn4 = new Pitch(NoteLetter.C, 4, Accidental.NATURAL, false);
		Pitch dn4 = new Pitch(NoteLetter.D, 4, Accidental.NATURAL, false);
		Pitch en4 = new Pitch(NoteLetter.E, 4, Accidental.NATURAL, false);
		Pitch fs4 = new Pitch(NoteLetter.B, 4, Accidental.SHARP, false);
		Pitch gn4 = new Pitch(NoteLetter.C, 5, Accidental.NATURAL, false);
		Pitch af5 = new Pitch(NoteLetter.A, 5, Accidental.FLAT, false);
		
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
		
		// voices
		Voice voicetreble1 = new Voice();
			voicetreble1.getMultiNotes().add(treble1);
			voicetreble1.getMultiNotes().add(treble2);
			voicetreble1.getMultiNotes().add(treble3);
			voicetreble1.getMultiNotes().add(treble4);
			voicetreble1.getMultiNotes().add(treble5);
			voicetreble1.getMultiNotes().add(treble6);

		// clefs
		Clef cleftreble = new Clef(new Rational(3, 1), ClefName.GCLEF, -2);

		// staffs
		Staff stafftreble = new Staff();
			stafftreble.getVoices().add(voicetreble1);
			stafftreble.getClefs().add(cleftreble);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(3, 1), 2, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(3, 1), 0, true);

		// piece
			getStaffs().add(stafftreble);
			
			getKeySignatures().add(keysig1);
			
			getTimeSignatures().add(timesig1);
	}
	
	public static void main(String[] args) {
		new SimplePiece();
	}
}