package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class ScoreWindow extends Drawable {
	
	final static int TOP_MARGIN	= 100;
	final static int LEFT_MARGIN	= 50;
	final static int RIGHT_MARGIN	= 50;
	final static int SYSTEM_LINE_SPACING = 10;
	
	Image _buffer;
	
	// reference to song
	Piece _piece;
	
	// the y position of each system (set of staff lines)
	int[] _systemPositions;
	
	// measure positions within each system
	
	public ScoreWindow(Piece piece) {
		_piece = piece;
		
		// load images
		try {
			_icon = ImageIO.read(new File(iconFile));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
		image;
	}
	
	public void drawSelf(Graphics g) {
		// buffer self-image
		
		drawPiece(g);
		// draw measures, shifting to the next system when horizontal space runs out
		
		// draw slurs and ties
	}
	
	private void drawPiece(Graphics g) {
		// load structure
		List keySigs 	= _piece.getKeySignatures();
		List timeSigs 	= _piece.getTimeSignatures();
		List chords		= _piece.getChordSymbols();
		
		// load notes
		// mapping of notes to staffs
		Map<ArrayList<MultiNote>, Staff> notes = new HashMap<ArrayList<MultiNote>, Staff>();
		
		List<Staff> staffs = _piece.getStaffs();
		for (Staff st : staffs) {
			List<Voice> voices = st.getVoices();
			
			for (Voice v : voices) {
				ArrayList<MultiNote> multis = v.getMultiNotes();
				notes.put(multis, st);
			}
		}
		
		// list manuevering
		Map<MultiNote, Clef> currClefs;
		KeySignature currKeySig;
		TimeSignature currTimeSig;
		
		Map<MultiNote, ListIterator<Clef>> iterClef;
		ListIterator<KeySignature> iterKeySig;
		ListIterator<TimeSignature> iterTimeSig;
		ListIterator<ChordSymbol> iterChord;
		
		// start drawing
		int timestamp 	= 0;
		int systemY 	= TOP_MARGIN;
		int completed;
		while (completed < notes.size()) {
			
			// if new line draw systems
			drawSystem(g, nextY, systemY);
			
			completed = 0;
			for (MultiNote mnote : notes) {
				List<Pitch> pitches = mnote.getPitches();
				
				// print all pitches in the mnote if they're not tied
				for (Pitch p : pitches) {
					
				}
			}
			
			// check key signatures
			
			
			// check time signatures
			
			
			// check chords
			
		}
	}
	
	private void drawSystem(Graphics g, int yc) {
		for (int i = 0; i < 5; i++) {
			int yp = yc + i * SYSTEM_LINE_SPACING;
			g.drawLine(LEFT_MARGIN, yp, 500 - RIGHT_MARGIN, yp);
			
			// while there's space, draw a measure
		}
	}
	
	private void drawMeasure(Graphics g){
		// draw the notes
		
		// draw extra system lines for notes below D and above G
		
		
		// connect consecutive 8th notes
		
		// draw chord symbols?
		
	}
	
	private void drawNote(Graphics g, int xc, int yc) {
		// draw circle on the correct line
		// g.drawOval(x, y, SYSTEM_LINE_SPACING, SYSTEM_LINE_SPACING);
		
		//draw stem
		
		
		// draw accidental
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseDragged(MouseEvent e) {
	}
}