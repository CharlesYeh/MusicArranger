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
	Editor _editor;
	
	public ArrangerXMLParser(Editor editor) {
		_editor = editor;
		_reader = new SAXReader();
	}
	
	public void parse(String fileName) throws Exception {
		Document doc = _reader.read(new FileReader(fileName));
		
		Element root = doc.getRootElement();
		_editor.clearScore();									// make new piece

		Element elemStaffs = root.element("staffs");
		parseStaffs(elemStaffs);								// parse staffs list
			
		Element elemTimeSigs = root.element("timeSignatures");
		parseTimeSignatures(elemTimeSigs);						// parse timesigs list
		
		Element elemKeySigs = root.element("keySignatures");
		parseKeySignatures(elemKeySigs);						// parse keysigs list
		
		Element elemChordSymbols = root.element("chordSymbols");
		parseChordSymbols(elemChordSymbols);
		}
	
	private void parseStaffs(Element elemStaffs) {
		List<Element> elemStaffList = elemStaffs.elements("staff");
		for (Element elemStaff : elemStaffList) {
			parseStaff(elemStaff);								// parse staff data
		}
	}
	
	private void parseStaff(Element elemStaff) {
		_editor.insertStaff();									// insert new staff
		
		Element elemClefList = elemStaff.element("clefs");		// parse clef list
		parseClefs(elemClefList);
		
		Element elemVoiceList = elemStaff.element("voices");	// parse voice list
		parseVoices(elemVoiceList);
	}
	
	//--------------------STRUCTURE PARSING--------------------
	
	private void parseKeySignatures(Element elemKeySigs) {
		List<Element> elemKeySigList = elemKeySigs.elements("keySignature");
		for (Element keySig : elemKeySigList) {
			parseKeySignature(keySig);							// parse keysig data
		}
	}
	private void parseKeySignature(Element elemKeySig) {
		Rational dur = parseTimestep(elemKeySig.element("timestep"));
		int accidentals = parseIntAttribute(elemKeySig, "accidentals");
		boolean isMajor = parseBooleanAttribute(elemKeySig, "isMajor");
		_editor.insertKeySig(dur, accidentals, isMajor);		// insert keysig
	}
	
	private void parseTimeSignatures(Element elemTimeSigs) {
		List<Element> elemTimeSigList = elemTimeSigs.elements("timeSignature");
		for (Element timeSig : elemTimeSigList) {
			parseTimeSignature(timeSig);						// parse timesig data
		}
	}
	
	private void parseTimeSignature(Element elemTimeSig) {
		Rational dur = parseTimestep(elemTimeSig.element("timestep"));
		int numer = parseIntAttribute(elemTimeSig, "numerator");
		int denom = parseIntAttribute(elemTimeSig, "denominator");
		_editor.insertTimeSig(dur, numer, denom);				// insert timesig
	}
		
	private void parseChordSymbols(Element elemChordSymbols) {
		List<Element> elemChordSymbolList = elemChordSymbols.elements("chordSymbol");
		for (Element elemChordSymbol : elemChordSymbolList) {
			parseChordSymbol(elemChordSymbol);					// parse chordsymbol data
		}
	}
	
	private void parseChordSymbol(Element elemChordSymbol) {
		Rational dur = parseTimestep(elemChordSymbol.element("timestep"));
		int scaleDegree = parseIntAttribute(elemChordSymbol, "scaleDegree");
		ChordType chordType = getChordType(parseStringAttribute(elemChordSymbol, "chordType"));
		_editor.insertChordSymbol(dur, scaleDegree, chordType);	// insert chordsymbol
	}

	private void parseClefs(Element e) {
		List<Element> elemClefs = e.elements("clef");
		for (Element clef : elemClefs) {
			parseClef(clef);									// parse clef data
		}
	}
	
	private void parseClef(Element elemClef) {
		Rational dur = parseTimestep(elemClef.element("timestep"));
		ClefName type = getClefName(parseStringAttribute(elemClef, "type"));
		int centLine = parseIntAttribute(elemClef, "center_line");
		_editor.insertClef(dur, type, centLine);				// insert clef
	}
	
	//--------------------MUSIC PARSING--------------------
	
	private void parseVoices(Element elemVoices) {
		List<Element> elemVoiceList = elemVoices.elements("voice");
		for (Element elemVoice : elemVoiceList) {
			parseVoice(elemVoice);								// parse voice list
		}
	}
	
	private void parseVoice(Element elemVoice) {
		_editor.insertVoice();									// insert voice
		
		Element elemMNs = elemVoice.element("multinotes");
		parseMultiNotes(elemMNs);								// parse multinote list
	}
	
	private void parseMultiNotes(Element elemMNs) {
		List<Element> elemMNList = elemMNs.elements("multinote");
		for (Element elemMN : elemMNList) {
			parseMultiNote(elemMN);								// parse multinote data
		}
	}
	
	private void parseMultiNote(Element elemMN) {
		Rational dur = parseTimestep(elemMN.element("timestep"));
		_editor.insertMultiNote(dur);							// insert multinote
		
		Element elemPitches = elemMN.element("pitches");
		parsePitches(elemPitches);								// parse pitch list
	}
	
	private void parsePitches(Element elemPitches) {
		List<Element> elemPitchList = elemPitches.elements("pitch");
		for (Element elemPitch : elemPitchList) {
			parsePitch(elemPitch);								// parse pitch data
		}
	}
	
	private void parsePitch(Element elemPitch) {
		NoteLetter letter = getNoteLetter(parseStringAttribute(elemPitch, "key"));
		int octave = parseIntAttribute(elemPitch, "octave");
		Accidental accid = getAccidental(parseStringAttribute(elemPitch, "accidental"));
		boolean isTied = false; 								// TODO: THIS NEEDS TO BE CHANGED
		
		_editor.insertPitch(letter, octave, accid, isTied);		// insert pitch
	}
	
	private NoteLetter getNoteLetter(String letter) {
		return NoteLetter.valueOf(letter);
	}
	
	private Accidental getAccidental(String letter) {
		return Accidental.valueOf(letter);
	}
	
	private ClefName getClefName(String type){
		return ClefName.valueOf(type);
	}
	
	private ChordType getChordType(String type){
		return ChordType.valueOf(type);
	}

	private Rational parseTimestep(Element dur) {
		int numer = parseIntAttribute(dur, "durNumerator");
		int denom = parseIntAttribute(dur, "durDenominator");
		return new Rational(numer, denom);
	}
	
	private int parseIntAttribute(Element e, String attr) {
		return Integer.parseInt(e.attribute(attr).getValue());
	}
	
	private boolean parseBooleanAttribute(Element e, String attr) {
		return e.attribute(attr).getValue().equals("true");
	}
	
	private String parseStringAttribute(Element e, String attr) {
		return e.attribute(attr).getValue();
	}
}