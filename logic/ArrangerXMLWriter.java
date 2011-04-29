package logic;

import music.*;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.net.URL;
import org.dom4j.*;
import org.dom4j.io.*;

public class ArrangerXMLWriter {
	public ArrangerXMLWriter() {}

	public void write(Piece p, String fileName) throws Exception {
		Document document = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("piece");
		document.setRootElement(root);

		List<TimeSignature> timeSigs = p.getTimeSignatures();
		List<KeySignature> keySigs = p.getKeySignatures();
		List<Staff> staffs = p.getStaffs();
		List<ChordSymbol> chordSymbols = p.getChordSymbols();

		writeTimeSigs(timeSigs, root);
		writeKeySigs(keySigs, root);
		writeStaffs(staffs, root);
		writeChordSymbols(chordSymbols, root);

		//write to file
		XMLWriter writer = new XMLWriter(new FileWriter(fileName), OutputFormat.createPrettyPrint());
		writer.write(document);
		writer.close();
	}

	private void writeTimeSigs(List<TimeSignature> timeSigs, Element root) {
		Element elemTimeSigs = DocumentHelper.createElement("timeSignatures");
		root.add(elemTimeSigs);

		for (TimeSignature timeSig : timeSigs) {
			Element elemTimeSig = DocumentHelper.createElement("timeSignature");
			elemTimeSigs.add(elemTimeSig);

			writeTimestep(timeSig, elemTimeSig);
			elemTimeSig.addAttribute("numerator", "" + timeSig.getNumerator());
			elemTimeSig.addAttribute("denominator", "" + timeSig.getDenominator());
		}
	}
	private void writeKeySigs(List<KeySignature> keySigs, Element root) {
		Element elemKeySigs = DocumentHelper.createElement("keySignatures");
		root.add(elemKeySigs);

		for (KeySignature keySig : keySigs) {
			Element elemKeySig = DocumentHelper.createElement("keySignature");
			elemKeySigs.add(elemKeySig);

			writeTimestep(keySig, elemKeySig);
			elemKeySig.addAttribute("accidentals", "" + keySig.getAccidentalNumber());
			elemKeySig.addAttribute("isMajor", "" + keySig.getIsMajor());
		}
	}

	private void writeStaffs(List<Staff> staffs, Element root) {
		Element elemStaffs = DocumentHelper.createElement("staffs");
		root.add(elemStaffs);

		for (Staff staff : staffs) {
			Element elemStaff = DocumentHelper.createElement("staff");
			elemStaffs.add(elemStaff);

			List<Clef> clefs = staff.getClefs();
			writeClefs(clefs, elemStaff);

			List<Voice> voices = staff.getVoices();
			writeVoices(voices, elemStaff);
		}
	}

	private void writeChordSymbols(List<ChordSymbol> chordSymbols, Element root) {
		Element elemChordSymbols = DocumentHelper.createElement("chordSymbols");
		root.add(elemChordSymbols);

		for (ChordSymbol chordSymbol : chordSymbols) {
			Element elemChordSymbol = DocumentHelper.createElement("chordSymbol");
			elemChordSymbols.add(elemChordSymbol);

			writeTimestep(chordSymbol, elemChordSymbol);
			elemChordSymbol.addAttribute("scaleDegree", "" + chordSymbol.getScaleDegree());
			elemChordSymbol.addAttribute("chordType", "" + chordSymbol.getChordType());
		}
	}

	private void writeClefs(List<Clef> clefs, Element elemStaff) {
		Element elemClefs = DocumentHelper.createElement("clefs");
		elemStaff.add(elemClefs);

		for (Clef clef : clefs) {
			Element elemClef = DocumentHelper.createElement("clef");
			elemClefs.add(elemClef);

			elemClef.addAttribute("type", "" + clef.getClefName());
			elemClef.addAttribute("center_line", "" + clef.getCenterLine());
			writeTimestep(clef, elemClef);
		}
	}

	private void writeVoices(List<Voice> voices, Element elemStaff) {
		Element elemVoices = DocumentHelper.createElement("voices");
		elemStaff.add(elemVoices);

		for (Voice voice : voices) {
			Element elemVoice = DocumentHelper.createElement("voice");
			elemVoices.add(elemVoice);

			List<MultiNote> multinotes = voice.getMultinotes();
			writeMultiNotes(multinotes, elemVoice);
		}
	}

	private void writeMultiNotes(List<MultiNote> multinotes, Element elemVoice) {
		Element elemMultinotes = DocumentHelper.createElement("multinotes");
		elemVoice.add(elemMultinotes);

		for (MultiNote multinote : multinotes) {
			Element elemMultinote = DocumentHelper.createElement("multinote");
			elemMultinotes.add(elemMultinote);

			writeTimestep(multinote, elemMultinote);
			List<Pitch> pitches = multinote.getPitches();
			writePitches(pitches, elemMultinote);
		}
	}

	private void writePitches(List<Pitch> pitches, Element elemMultinote) {
		Element elemPitches = DocumentHelper.createElement("pitches");
		elemMultinote.add(elemPitches);

		for (Pitch pitch : pitches) {
			Element elemPitch = DocumentHelper.createElement("pitch");
			elemPitches.add(elemPitch);

			elemPitch.addAttribute("octave", "" + pitch.getOctave());
			elemPitch.addAttribute("key", "" + pitch.getNoteLetter());
			elemPitch.addAttribute("accidental", "" + pitch.getAccidental());
		}
	}

	private void writeTimestep(Timestep t, Element node) {
		Rational r = t.getDuration();

		Element obj = DocumentHelper.createElement("timestep");
		obj.addAttribute("durNumerator", "" + r.getNumerator());
		obj.addAttribute("durDenominator", "" + r.getDenominator());

		node.add(obj);
	}

	public static void main(String[] args) {
		ArrangerXMLWriter writer = new ArrangerXMLWriter();
		Editor editor = new Editor();
		ArrangerXMLParser parser = new ArrangerXMLParser(editor);
		Piece p = new Piece();
		editor.setPiece(p);

		try {
			parser.parse("test/testWrite.xml");
			writer.write(editor.getPiece(), "test/testWrite2.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}