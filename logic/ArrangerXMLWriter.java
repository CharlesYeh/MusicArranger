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
		
		writeTimeSigs(timeSigs, root);
		writeKeySigs(keySigs, root);
		
		List<Staff> staffs = p.getStaffs();
		for (Staff stf : staffs) {
			writeStaff(stf, root);
		}
		
		// TODO: ############ getChordSymbols()
		
		//write to file
		XMLWriter writer = new XMLWriter(new FileWriter(fileName), OutputFormat.createPrettyPrint());
		writer.write(document);
		writer.close();
	}
	
	public void writeTimeSigs(List<TimeSignature> sigs, Element node) {
		Element elemSigs = DocumentHelper.createElement("timeSignatures");
		
		for (TimeSignature sig : sigs) {
			Element obj = DocumentHelper.createElement("timeSignature");
			
			obj.addAttribute("numerator", "" + sig.getNumerator());
			obj.addAttribute("denominator", "" + sig.getDenominator());
			
			writeTimestep(sig, obj);
		}
		
		node.add(elemSigs);
	}
	public void writeKeySigs(List<KeySignature> sigs, Element node) {
		Element elemSigs = DocumentHelper.createElement("keySignatures");
		
		for (KeySignature sig : sigs) {
			Element obj = DocumentHelper.createElement("keySignature");
			
			obj.addAttribute("accidentals", "" + sig.getAccidentalNumber());
			obj.addAttribute("isMajor", "" + sig.getIsMajor());
			
			writeTimestep(sig, obj);
		}
		
		node.add(elemSigs);
	}
	
	public void writeStaff(Staff stf, Element node) {
		Element elemStaff = DocumentHelper.createElement("staff");
		
		// write clefs
		ArrayList<Clef> clefs = stf.getClefs();
		Element elemClefs = DocumentHelper.createElement("clefs");
		for (Clef c : clefs) {
			writeClef(c, elemClefs);
		}
		elemStaff.add(elemClefs);
		
		// write voices
		List<Voice> voices = stf.getVoices();
		for (Voice v : voices) {
			writeVoice(v, elemStaff);
		}
		
		node.add(elemStaff);
	}
	
	public void writeClef(Clef c, Element node) {
		Element elemClef = DocumentHelper.createElement("clef");
		elemClef.addAttribute("type", "" + c.getClefName());
		elemClef.addAttribute("center_line", "" + c.getCenterLine());
		
		writeTimestep(c, elemClef);
		node.add(elemClef);
	}
	
	public void writeVoice(Voice v, Element node) {
		Element elemVoice = DocumentHelper.createElement("voice");
		
		List<MultiNote> mNotes = v.getMultiNotes();
		for (MultiNote mn : mNotes) {
			writeMultiNote(mn, elemVoice);
		}
		
		node.add(elemVoice);
	}
	
	public void writeMultiNote(MultiNote mn, Element node) {
		writeTimestep(mn, node);
		
		Element elemPitches = DocumentHelper.createElement("pitches");
		List<Pitch> pitches = mn.getPitches();
		for (Pitch p : pitches) {
			writePitch(p, elemPitches);
		}
		
		node.add(elemPitches);
	}
	
	public void writePitch(Pitch p, Element node) {
		Element elemPitch = DocumentHelper.createElement("pitch");
		
		elemPitch.addAttribute("octave", "" + p.getOctave());
		elemPitch.addAttribute("key", "" + p.getNoteLetter());
		elemPitch.addAttribute("accidental", "" + p.getAccidental());
		
		node.add(elemPitch);
	}
	
	public void writeTimestep(Timestep t, Element node) {
		Rational r = t.getDuration();
		
		Element obj = DocumentHelper.createElement("timestep");
		obj.addAttribute("durNumerator", "" + r.getNumerator());
		obj.addAttribute("durDenominator", "" + r.getDenominator());
		
		node.add(obj);
	}
	
	public static void main(String[] args) {
		ArrangerXMLWriter writer = new ArrangerXMLWriter();
		
		Piece p = new Piece();
		
		try {
			writer.write(p, "testWrite.xml");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}