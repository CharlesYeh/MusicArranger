package tests;

import music.*;

public class LongMelodyPiece extends Piece {

	public LongMelodyPiece() {
		super();

		// pitches
		Pitch cs4 = new Pitch(NoteLetter.C, 4, Accidental.SHARP, false);
		Pitch dn4 = new Pitch(NoteLetter.D, 4, Accidental.NATURAL, false);
		Pitch en4 = new Pitch(NoteLetter.E, 4, Accidental.NATURAL, false);
		Pitch fs4 = new Pitch(NoteLetter.F, 4, Accidental.SHARP, false);
		Pitch gn4 = new Pitch(NoteLetter.G, 4, Accidental.NATURAL, false);
		Pitch an4 = new Pitch(NoteLetter.A, 4, Accidental.NATURAL, false);
		Pitch bn4 = new Pitch(NoteLetter.B, 4, Accidental.NATURAL, false);

		Pitch cs3 = new Pitch(NoteLetter.C, 3, Accidental.SHARP, false);
		Pitch fs3 = new Pitch(NoteLetter.F, 3, Accidental.SHARP, false);
		Pitch gn3 = new Pitch(NoteLetter.G, 3, Accidental.NATURAL, false);

		// multinotes
		MultiNote I = new MultiNote(new Rational(2, 4));
			I.getPitches().add(dn4);
			I.getPitches().add(fs4);
			I.getPitches().add(an4);
		MultiNote IV = new MultiNote(new Rational(1, 4));
			IV.getPitches().add(dn4);
			IV.getPitches().add(gn4);
			IV.getPitches().add(bn4);
		MultiNote V = new MultiNote(new Rational(1, 4));
			V.getPitches().add(cs4);
			V.getPitches().add(en4);
			V.getPitches().add(an4);

		MultiNote IBass = new MultiNote(new Rational(2, 4));
			IBass.getPitches().add(fs3);
		MultiNote IVBass = new MultiNote(new Rational(1, 4));
			IVBass.getPitches().add(gn3);
		MultiNote VBass = new MultiNote(new Rational(1, 4));
			VBass.getPitches().add(cs3);

		// clefs
		Clef cleftreble = new Clef(new Rational(4, 4), ClefName.GCLEF, -2);
		Clef clefbass = new Clef(new Rational(4, 4), ClefName.FCLEF, 2);

		// time signatures
		TimeSignature timesig1 = new TimeSignature(new Rational(4, 4), 4, 4);

		// key signatures
		KeySignature keysig1 = new KeySignature(new Rational(4, 4), -6, true);

		// chord symbols
		ChordSymbol chordsymbol1 = new ChordSymbol(new Rational(2, 4), new ScaleDegree(1, Accidental.NATURAL), ChordType.MAJOR);
		ChordSymbol chordsymbol2 = new ChordSymbol(new Rational(1, 4), new ScaleDegree(4, Accidental.NATURAL), ChordType.MINOR);
		ChordSymbol chordsymbol3 = new ChordSymbol(new Rational(1, 4), new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR7, 1);

		// staffs
		Staff stafftreble = new Staff();
		Staff staffbass = new Staff();


		for (int i = 0; i < 10; i++) {

			Voice voice1_1_1 = new Voice();
				// staff 1, measure 1, voice 1
				voice1_1_1.getMultiNotes().add(I);
				voice1_1_1.getMultiNotes().add(IV);
				voice1_1_1.getMultiNotes().add(V);

			Voice voice2_1_1 = new Voice();
				voice2_1_1.getMultiNotes().add(IBass);
				voice2_1_1.getMultiNotes().add(IVBass);
				voice2_1_1.getMultiNotes().add(VBass);
			// measures
			Measure measure1_1 = new Measure();
				measure1_1.getKeySignatures().add(keysig1);
				measure1_1.getTimeSignatures().add(timesig1);
				measure1_1.getClefs().add(cleftreble);
				measure1_1.getVoices().add(voice1_1_1);
				measure1_1.getChordSymbols().add(chordsymbol1);
				measure1_1.getChordSymbols().add(chordsymbol2);
				measure1_1.getChordSymbols().add(chordsymbol3);

			Measure measure2_1 = new Measure();
				measure2_1.getKeySignatures().add(keysig1);
				measure2_1.getTimeSignatures().add(timesig1);
				measure2_1.getClefs().add(clefbass);
				measure2_1.getVoices().add(voice2_1_1);
				measure2_1.getChordSymbols().add(chordsymbol1);
				measure2_1.getChordSymbols().add(chordsymbol2);
				measure2_1.getChordSymbols().add(chordsymbol3);

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