package test;

import music.*;

public class TestPiece extends Piece {

	public TestPiece() {
		super();
	
		// pitches 
		Pitch cn4 = new Pitch(NoteLetter.C, 4, Accidental.NATURAL, false);
		Pitch dn4 = new Pitch(NoteLetter.D, 4, Accidental.NATURAL, false);
		Pitch en4 = new Pitch(NoteLetter.E, 4, Accidental.NATURAL, false);
		Pitch fs4 = new Pitch(NoteLetter.F, 4, Accidental.SHARP, false);
		Pitch gn4 = new Pitch(NoteLetter.G, 4, Accidental.NATURAL, false);

		Pitch an2 = new Pitch(NoteLetter.A, 2, Accidental.NATURAL, false);
		Pitch bf2 = new Pitch(NoteLetter.B, 2, Accidental.FLAT, false);
		Pitch bn2 = new Pitch(NoteLetter.B, 3, Accidental.NATURAL, false);
		Pitch cn3 = new Pitch(NoteLetter.C, 3, Accidental.NATURAL, false);

		// multinotes
		MultiNote treble1 = new MultiNote(new Rational(1, 2));
			treble1.getPitches().add(cn4);
		MultiNote treble2 = new MultiNote(new Rational(1, 4));
			treble2.getPitches().add(dn4);
		MultiNote treble3 = new MultiNote(new Rational(1, 8));
			treble3.getPitches().add(en4);
			treble3.getPitches().add(gn4);
		MultiNote treble4 = new MultiNote(new Rational(1, 8));
			treble4.getPitches().add(fs4);
		MultiNote treble5 = new MultiNote(new Rational(1, 2)); // rest

		MultiNote bass1 = new MultiNote(new Rational(1, 4));
			bass1.getPitches().add(cn3);
		MultiNote bass2 = new MultiNote(new Rational(1, 4));
			bass2.getPitches().add(bf2);
		MultiNote bass3 = new MultiNote(new Rational(1, 4));
			bass3.getPitches().add(an2);
		MultiNote bass4 = new MultiNote(new Rational(1, 4));
			bass4.getPitches().add(bn2);
		MultiNote bass5 = new MultiNote(new Rational(1, 2)); // rest

		// voices
		Voice voicetreble1 = new Voice();
			voicetreble1.getMultiNotes().add(treble1);
			voicetreble1.getMultiNotes().add(treble2);
			voicetreble1.getMultiNotes().add(treble3);
			voicetreble1.getMultiNotes().add(treble4);
			voicetreble1.getMultiNotes().add(treble5);
		Voice voicetreble2 = new Voice();
		Voice voicetreble3 = new Voice();
		Voice voicetreble4 = new Voice();

		Voice voicebass1 = new Voice();
			voicebass1.getMultiNotes().add(bass1);
			voicebass1.getMultiNotes().add(bass2);
			voicebass1.getMultiNotes().add(bass3);
			voicebass1.getMultiNotes().add(bass4);
			voicebass1.getMultiNotes().add(bass5);
		Voice voicebass2 = new Voice();
		Voice voicebass3 = new Voice();
		Voice voicebass4 = new Voice();

		// clefs
		Clef cleftreble = new Clef(new Rational(3, 2), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(3, 2), ClefName.FCLEF, 2);

		// staffs
		Staff stafftreble = new Staff();
			stafftreble.getVoices().add(voicetreble1);
			stafftreble.getVoices().add(voicetreble2);
			stafftreble.getVoices().add(voicetreble3);
			stafftreble.getVoices().add(voicetreble4);
			stafftreble.getClefs().add(cleftreble);
		Staff staffbass = new Staff();
			staffbass.getVoices().add(voicebass1);
			staffbass.getVoices().add(voicebass2);
			staffbass.getVoices().add(voicebass3);
			staffbass.getVoices().add(voicebass4);
			staffbass.getClefs().add(clefbass);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(3, 2), 2, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(2, 1), 0, true);

		// chord symbols
		ChordSymbol chordsymbol1 = new ChordSymbol(new Rational(1, 2), 1, ChordType.MAJOR);
		ChordSymbol chordsymbol2 = new ChordSymbol(new Rational(1, 2), 4, ChordType.MINOR);
		ChordSymbol chordsymbol3 = new ChordSymbol(new Rational(1, 2), 5, ChordType.HDIMIN7);

		// piece
			getStaffs().add(stafftreble);
			getStaffs().add(staffbass);
			
			getKeySignatures().add(keysig1);
			
			getTimeSignatures().add(timesig1);
			
			getChordSymbols().add(chordsymbol1);
			getChordSymbols().add(chordsymbol2);
			getChordSymbols().add(chordsymbol3);
	}
	
	public static void main(String[] args) {
		new TestPiece();
	}
}