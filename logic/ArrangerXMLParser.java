package logic;

import music.*;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.net.URL;
import org.dom4j.*;
import org.dom4j.io.*;

public class ArrangerXMLParser {
	SAXReader _reader;
	public ArrangerXMLParser() {
		_reader = new SAXReader();
	}
	
	public Piece parse(String fileName) throws Exception {
		Document doc = _reader.read(new FileReader(fileName));
		Element root = doc.getRootElement();
		
		Piece piece = new Piece();
		
		// iterate through time sig
		Element elemTimeSig = root.element("timeSignatures");
		List<TimeSignature> timeSigs = parseTimeSignatures(elemTimeSig);
		// EVAAAAAAAAAAAAN ADD TIME SIGNATURE!
		
		// iterate through key sig
		Element elemKeySig = root.element("keySignatures");
		List<KeySignature> keySigs = parseKeySignatures(elemKeySig);
		// EVAAAAAAAAAAAAN ADD KEY SIGNATURE!
		
		// iterate through staffs
		List<Element> elemStaffs = root.elements("staff");
		for (Element staff : elemStaffs) {			
			// iterate through clefs
			Element elemClefs = root.element("clefs");
			List<Clef> clefs = parseClefs(elemClefs);
			// EVAAAAAAAAAAAAN ADD CLEFS!
			
			// iterate through voices
			List<Voice> voices = new ArrayList<Voice>();
			List<Element> elemVoices = root.elements("voice");
			for (Element voice : elemVoices) {
				Voice v = parseVoice(voice);
				voices.add(v);
			}
			// EVAAAAAAAAAAAAN ADD VOICES!
			
		}
		
		return piece;
	}
	
	//--------------------STRUCTURE PARSING--------------------
	
	public List<KeySignature> parseKeySignatures(Element elemSig) {
		List<KeySignature> keySigs = new ArrayList<KeySignature>();
		
		List<Element> elemKeySigs = elemSig.elements("keySignature");
		for (Element sig : elemKeySigs) {
			KeySignature keySig = parseKeySignature(sig);
			keySigs.add(keySig);
		}
		
		return keySigs;
	}
	public KeySignature parseKeySignature(Element keySig) {
		Rational dur = parseTimestep(keySig.element("duration"));
		
		int accidentals = parseIntAttribute(keySig, "accidentals");
		boolean isMajor = parseBooleanAttribute(keySig, "isMajor");
		return new KeySignature(dur, accidentals, isMajor);
	}
	
	public List<TimeSignature> parseTimeSignatures(Element elemSig) {
		List<TimeSignature> timeSigs = new ArrayList<TimeSignature>();
		
		List<Element> elemKeySigs = elemSig.elements("timeSignature");
		for (Element sig : elemKeySigs) {
			TimeSignature timeSig = parseTimeSignature(sig);
			timeSigs.add(timeSig);
		}
		
		return timeSigs;
	}
	
	public TimeSignature parseTimeSignature(Element timeSig) {
		Rational dur = parseTimestep(timeSig.element("duration"));
		
		int numer = parseIntAttribute(timeSig, "numerator");
		int denom = parseIntAttribute(timeSig, "denominator");
		return new TimeSignature(dur, numer, denom);
	}
	
	public List<Clef> parseClefs(Element e) {
		List<Clef> clefs = new ArrayList<Clef>();
		
		List<Element> elemClefs = e.elements("clef");
		for (Element clef : elemClefs) {
			Clef c = parseClef(clef);
			clefs.add(c);
		}
		
		return clefs;
	}
	
	public Clef parseClef(Element elemClef) {
		Rational dur = parseTimestep(elemClef.element("duration"));
		
		String type = parseStringAttribute(elemClef, "type");
		int centLine = parseIntAttribute(elemClef, "center_line");
		return new Clef(dur, getClefName(type), centLine);
	}
	
	public ClefName getClefName(String type){
		return ClefName.valueOf(type);
	}
	
	//--------------------MUSIC PARSING--------------------
	
	public Voice parseVoice(Element elemVoice) {
		Voice v = new Voice();
		List<MultiNote> multinotes = v.getMultiNotes();
		
		List<Element> elemMNs = elemVoice.elements("multinote");
		for (Element elemMN : elemMNs) {
			MultiNote mn = parseMultiNote(elemMN);
			multinotes.add(mn);
		}
		
		return v;
	}
	
	public MultiNote parseMultiNote(Element elemmn) {
		Rational dur = parseTimestep(elemmn.element("duration"));
		MultiNote mn = new MultiNote(dur);
		List<Pitch> pitches = mn.getPitches();
		
		Element elemPitchGroup = elemmn.element("pitches");
		List<Element> elemPitches = elemPitchGroup.elements("pitch");
		for (Element elemPitch : elemPitches) {
			Pitch p = parsePitch(elemPitch);
			pitches.add(p);
		}
		
		return mn;
	}
	
	public Pitch parsePitch(Element elemPitch) {
		int octave = parseIntAttribute(elemPitch, "octave");
		NoteLetter letter = getNoteLetter(parseStringAttribute(elemPitch, "key"));
		Accidental accid = getAccidental(parseStringAttribute(elemPitch, "accidental"));
		
		return new Pitch(letter, octave, accid, false);
	}
	
	public NoteLetter getNoteLetter(String letter) {
		return NoteLetter.valueOf(letter);
	}
	
	public Accidental getAccidental(String letter) {
		return Accidental.valueOf(letter);
	}
	
	public Rational parseTimestep(Element dur) {
		int numer = parseIntAttribute(dur, "durNumerator");
		int denom = parseIntAttribute(dur, "durDenominator");
		return new Rational(numer, denom);
	}
	
	public int parseIntAttribute(Element e, String attr) {
		return Integer.parseInt(e.attribute(attr).getValue());
	}
	
	public boolean parseBooleanAttribute(Element e, String attr) {
		return e.attribute(attr).getValue().equals("true");
	}
	
	public String parseStringAttribute(Element e, String attr) {
		return e.attribute(attr).getValue();
	}
	
	public static void main(String[] args) {
		ArrangerXMLParser writer = new ArrangerXMLParser();
		
		try {
			Piece p = writer.parse("testWrite.xml");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		// check piece
	}
}