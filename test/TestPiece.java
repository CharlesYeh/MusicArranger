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
			voicetreble1.getMultinotes().add(treble1);
			voicetreble1.getMultinotes().add(treble2);
			voicetreble1.getMultinotes().add(treble3);
			voicetreble1.getMultinotes().add(treble4);
			voicetreble1.getMultinotes().add(treble5);
		Voice voicetreble2 = new Voice();
		Voice voicetreble3 = new Voice();
		Voice voicetreble4 = new Voice();

		Voice voicebass1 = new Voice();
			voicebass1.getMultinotes().add(bass1);
			voicebass1.getMultinotes().add(bass2);
			voicebass1.getMultinotes().add(bass3);
			voicebass1.getMultinotes().add(bass4);
			voicebass1.getMultinotes().add(bass5);
		Voice voicebass2 = new Voice();
		Voice voicebass3 = new Voice();
		Voice voicebass4 = new Voice();

		// clefs
		Clef cleftreble = new Clef(new Rational(3, 2), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(3, 2), ClefName.FCLEF, 2);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(3, 2), 2, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(2, 1), 0, true);

		// chord symbols
		ChordSymbol chordsymbol1 = new ChordSymbol(new Rational(1, 2), 1, ChordType.MAJOR);
		ChordSymbol chordsymbol2 = new ChordSymbol(new Rational(1, 2), 4, ChordType.MINOR);
		ChordSymbol chordsymbol3 = new ChordSymbol(new Rational(1, 2), 5, ChordType.HDIMIN7);

		// measures
		Measure measure1Treble = new Measure();
			measure1Treble.getVoices().add(voicetreble1);
			measure1Treble.getVoices().add(voicetreble2);
			measure1Treble.getVoices().add(voicetreble3);
			measure1Treble.getVoices().add(voicetreble4);
			measure1Treble.getClefs().add(cleftreble);

			measure1Treble.getKeySignatures().add(keysig1);

			measure1Treble.getTimeSignatures().add(timesig1);

			measure1Treble.getChordSymbols().add(chordsymbol1);
			measure1Treble.getChordSymbols().add(chordsymbol2);
			measure1Treble.getChordSymbols().add(chordsymbol3);

		Measure measure1Bass = new Measure();
			measure1Bass.getVoices().add(voicebass1);
			measure1Bass.getVoices().add(voicebass2);
			measure1Bass.getVoices().add(voicebass3);
			measure1Bass.getVoices().add(voicebass4);
			measure1Bass.getClefs().add(clefbass);

			measure1Bass.getKeySignatures().add(keysig1);

			measure1Bass.getTimeSignatures().add(timesig1);

			measure1Bass.getChordSymbols().add(chordsymbol1);
			measure1Bass.getChordSymbols().add(chordsymbol2);
			measure1Bass.getChordSymbols().add(chordsymbol3);

		Measure measure2Treble = new Measure();
			measure2Treble.getVoices().add(voicetreble1);
			measure2Treble.getVoices().add(voicetreble2);
			measure2Treble.getVoices().add(voicetreble3);
			measure2Treble.getVoices().add(voicetreble4);
			measure2Treble.getClefs().add(cleftreble);

			measure2Treble.getKeySignatures().add(keysig1);

			measure2Treble.getTimeSignatures().add(timesig1);

			measure2Treble.getChordSymbols().add(chordsymbol1);
			measure2Treble.getChordSymbols().add(chordsymbol2);
			measure2Treble.getChordSymbols().add(chordsymbol3);

		Measure measure2Bass = new Measure();
			measure2Bass.getVoices().add(voicebass1);
			measure2Bass.getVoices().add(voicebass2);
			measure2Bass.getVoices().add(voicebass3);
			measure2Bass.getVoices().add(voicebass4);
			measure2Bass.getClefs().add(clefbass);

			measure2Bass.getKeySignatures().add(keysig1);

			measure2Bass.getTimeSignatures().add(timesig1);

			measure2Bass.getChordSymbols().add(chordsymbol1);
			measure2Bass.getChordSymbols().add(chordsymbol2);
			measure2Bass.getChordSymbols().add(chordsymbol3);

		// staffs
		Staff stafftreble = new Staff();
			stafftreble.getMeasures().add(measure1Treble);
			stafftreble.getMeasures().add(measure2Treble);

		Staff staffbass = new Staff();
			stafftreble.getMeasures().add(measure1Bass);
			stafftreble.getMeasures().add(measure2Bass);

		// piece
		getStaffs().add(stafftreble);
		getStaffs().add(staffbass);


	}

	public static void main(String[] args) {
		new TestPiece();
	}
}