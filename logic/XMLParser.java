package logic;

import music.*;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.net.URL;
import org.dom4j.*;
import org.dom4j.io.*;

public class XMLParser {
	SAXReader _reader;
	public XMLParser() {
		_reader = new SAXReader();
	}
	
	public Piece parse(String fileName) throws Exception {
		Document doc = _reader.read(new FileReader(fileName));
		Element root = doc.getRootElement();
		
		// iterate through staffs
		List<Element> staffs = root.elements("staff");
		for (Element staff : staffs) {
			// iterate through time sig
			Element timeSig = root.element("timeSignatures");
			parseTimeSignature(timeSig);
			// EVAAAAAAAAAAAAN ADD TIME SIGNATURE!
			
			// iterate through key sig
			Element keySig = root.element("keySignatures");
			parseKeySignature(keySig);
			// EVAAAAAAAAAAAAN ADD KEY SIGNATURE!
			
			// iterate through clefs
			Element clefs = root.element("clefs");
			parseClefs(clefs);
			// EVAAAAAAAAAAAAN ADD CLEFS!
			
			// iterate through voices
			List<Element> voices = root.elements("voice");
			for (Element voice : voices) {
				parseVoice(voice);
			}
			// EVAAAAAAAAAAAAN ADD VOICES!
			
		}
		
		return new Piece();
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
		Timestep dur = parseTimestep(keySig.element("duration"));
		
		int accidentals = parseIntAttribute(keySig, "accidentals");
		boolean isMajor = parseBooleanAttribute(keySig, "isMajor");
		return new KeySignature(dur.getDuration(), accidentals, isMajor);
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
		Timestep dur = parseTimestep(timeSig.element("duration"));
		
		int numer = parseIntAttribute(timeSig, "numerator");
		int denom = parseIntAttribute(timeSig, "denominator");
		return new TimeSignature(dur.getDuration(), numer, denom);
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
		Timestep dur = parseTimestep(elemClef.element("duration"));
		
		String type = parseStringAttribute(elemClef, "type");
		int centLine = parseIntAttribute(elemClef, "center_line");
		return new Clef(dur.getDuration(), getClefName(type), centLine);
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
		Timestep dur = parseTimestep(elemmn.element("duration"));
		MultiNote mn = new MultiNote(dur.getDuration());
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
	
	public Timestep parseTimestep(Element dur) {
		int numer = parseIntAttribute(dur, "durNumerator");
		int denom = parseIntAttribute(dur, "durDenominator");
		return new Timestep(new Rational(numer, denom));
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
}