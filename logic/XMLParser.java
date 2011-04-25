package logic;

import music.*;

import java.util.List;

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
	}
	
	//--------------------STRUCTURE PARSING--------------------
	
	public KeySignature parseKeySignature(Element keySig) {
		
		return new KeySignature();
	}
	
	public TimeSignature parseTimeSignature(Element timeSig) {
		Timestep dur = parseTimestep(timeSig.element("duration"));
		
		Element sig = timeSig.element("timeSignature");
		int numer = parseIntAttribute(sig, "numerator");
		int denom = parseIntAttribute(sig, "denominator");
		return new TimeSignature(dur.getDuration(), numer, denom);
	}
	
	public List<Clef> parseClefs(Element e) {
		List<Element> clefs = e.elements("clef");
		for (Element clef : clefs) {
			parseClef(clef);
		}
	}
	
	public Clef parseClef(Element elemClef) {
		Timestep dur = parseTimestep(timeSig.element("duration"));
		
		String type = parseStringAttribute(elemClef, "type");
		int centLine = parseIntAttribute(elemClef, "center_line");
		return new Clef(dur.getDuration(), type, centLine);
	}
	
	//--------------------MUSIC PARSING--------------------
	
	public Voice parseVoice(Element elemVoice) {
		
	}
	
	public MultiNote parseMultiNote(Element mn) {
		Timestep 
	}
	
	public Timestep parseTimestep(Element dur) {
		int numer = parseIntAttribute(dur, "durNumerator");
		int denom = parseIntAttribute(dur, "durDenominator");
		return new Timestep(new Rational(numer, denom));
	}
	
	public int parseIntAttribute(Element e, String attr) {
		return Integer.parseInt(e.attribute(attr).getValue());
	}
	public String parseStringAttribute(Element e, String attr) {
		return e.attribute(attr).getValue();
	}
}