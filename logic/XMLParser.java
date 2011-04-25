package logic;

import music.Piece;

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
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			
		}
		while(i.hasNext()){
			Element currElement = i.next();
			
			SolarBody obj;
			String elementType = currElement.attribute("TYPE").getStringValue();
			
		}
	}
	
	public KeySignature parseKeySignature(Element keySig) {
		
	}
	
	public TimeSignature parseTimeSignature(Element timeSig) {
		Duration dur = parseDuration(timeSig.element("duration"));
		
		Element sig = timeSig.element("timeSignature");
		int numer = parseIntAttribute(sig, "numerator");
		int denom = parseIntAttribute(sig, "denominator");
		return new TimeSignature();
	}
	
	public Duration parseDuration(Element dur) {
		int numer = Integer.parseInt(dur.attribute("durNumerator"));
		int denom = Integer.parseInt(dur.attribute("durDenominator"));
		return new Duration(new Rational(numer, denom));
	}
	
	public parseVoice(Element voice) {
		
	}
	
	public int parseIntAttribute(Element e, String attr) {
		return Integer.parseInt(e.attribute(attr));
	}
}