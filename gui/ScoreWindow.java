package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

import arranger.ArrangerConstants;
import music.*;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ScoreWindow extends Drawable {
	
	final static int TOP_MARGIN	= 100;
	final static int LEFT_MARGIN	= 50;
	final static int RIGHT_MARGIN	= 50;
	final static int SYSTEM_LINE_SPACING = 10;
	final static int SYSTEM_SPACING = 50;
	
	Image _imgQuarter, _imgHalf, _imgWhole, _imgRest,
			_imgDoubleFlat, _imgFlat, _imgNatural, _imgSharp, _imgDoubleSharp; 
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
			_imgQuarter		= ImageIO.read(new File("images/score/score_quarter.gif"));
			_imgHalf			= ImageIO.read(new File("images/score/score_half.gif"));
			_imgWhole	 	= ImageIO.read(new File("images/score/score_whole.gif"));
			_imgRest			= ImageIO.read(new File("images/score/score_rest.gif"));
			
			_imgDoubleFlat	= ImageIO.read(new File("images/score/score_dflat.gif"));
			_imgFlat			= ImageIO.read(new File("images/score/score_flat.gif"));
			_imgNatural	 	= ImageIO.read(new File("images/score/score_natural.gif"));
			_imgSharp		= ImageIO.read(new File("images/score/score_sharp.gif"));
			_imgDoubleSharp= ImageIO.read(new File("images/score/score_dsharp.gif"));
		}
		catch (IOException e) {
			System.out.println("Error while loading musical images: " + e);
		}
	}
	
	public void drawSelf(Graphics g) {
		// buffer self-image
		
		drawPiece(g);
	}
	
	private void drawPiece(Graphics g) {
		// list manuevering
		PriorityQueue<TimestampAssociator> pQueue = new PriorityQueue<TimestampAssociator>();
		
		// load structure
		List<KeySignature> keySigs 	= _piece.getKeySignatures();
		List<TimeSignature> timeSigs 	= _piece.getTimeSignatures();
		List<ChordSymbol> chords		= _piece.getChordSymbols();
		/*pQueue.add(new TimestampAssociator(keySigs.listIterator()));
		pQueue.add(new TimestampAssociator(timeSigs.listIterator()));
		pQueue.add(new TimestampAssociator(chords.listIterator()));*/
		
		// load notes
		// mapping of notes (voice) to staffs
		Map<ListIterator<MultiNote>, Staff> notes = new HashMap<ListIterator<MultiNote>, Staff>();
		// mapping of staffs to clefs
		//Map<Staff, Clef> clefs = new HashMap<Staff, Clef>();
		
		// add multinote lists from each staff>voice to pQueue
		List<Staff> staffs = _piece.getStaffs();
		for (Staff st : staffs) {
			List<Voice> voices = st.getVoices();
			//clefs.put(st, st.getClef());
			
			for (Voice v : voices) {
				List<MultiNote> multis = v.getMultiNotes();
				ListIterator<MultiNote> multisList = multis.listIterator();
				
				// so we can get the staff a voice is on easily
				notes.put(multisList, st);
				pQueue.add(new TimestampAssociator(multisList));
			}
		}
		
		Map<MultiNote, Clef> currClefs;
		KeySignature currKeySig;
		TimeSignature currTimeSig;
		ChordSymbol currChord;
		
		// start drawing
		boolean startDrawing = true;
		int systemY 	= TOP_MARGIN - SYSTEM_SPACING;
		int staffX		= LEFT_MARGIN;
		int noteX;
		while (pQueue.size() > 0) {
			TimestampAssociator timeAssoc = pQueue.poll();
			ListIterator<? extends Timestep> listDur = timeAssoc.getAssociated();
			Timestep nextDur = listDur.next();
			if (listDur.hasNext()) {
				timeAssoc.addDuration(nextDur);
				pQueue.add(timeAssoc);
			}
			else {
				// don't add back to priority queue
			}
			
			int measureWidth = 100;
			if (staffX + measureWidth > ArrangerConstants.WINDOW_WIDTH - RIGHT_MARGIN || startDrawing) {
				startDrawing = false;
				
				// if new line, draw systems
				drawSystem(g, systemY);
				systemY += SYSTEM_SPACING;
				staffX = LEFT_MARGIN;
			}
			
			// start of system
			noteX = staffX;
			
			
			/*for (ListIterator<MultiNote> mnotes : notes.keySet()) {
				
				List<Pitch> pitches = mnote.getPitches();
				
				// print all pitches in the mnote if they're not tied
				for (Pitch p : pitches) {
					int noteY = systemY + 20;
					drawNote(g, noteX, noteY);
					
					// draw accidental (check key signature
					drawAccidental(g, p.getAccidental(), noteX - 5, noteY);
					
				}
				
				// position next multi note
				noteX += mnote.getNumerator() / mnote.getDenominator() * measureWidth;
			}*/
			
			// check key signatures
			
			
			// check time signatures
			
			
			// check chords
			
			staffX += measureWidth;
		}
	}
	
	private void drawSystem(Graphics g, int yc) {
		for (int i = 0; i < 5; i++) {
			int yp = yc + i * SYSTEM_LINE_SPACING;
			g.drawLine(LEFT_MARGIN, yp, ArrangerConstants.WINDOW_WIDTH - RIGHT_MARGIN, yp);
		}
	}
	
	private void drawNote(Graphics g, int xc, int yc) {
		// draw circle on the correct line
		g.drawImage(_imgQuarter, xc, yc, null);
		
		//draw stem
		g.drawLine(xc, yc, xc, yc - 50);
		
	}
	
	private void drawAccidental(Graphics g, Accidental accid, int xc, int yc) {
		Image accidImage = _imgNatural;
		
		switch (accid) {
			case DOUBLEFLAT:
				accidImage = _imgDoubleFlat;
				break;
			case FLAT:
				accidImage = _imgFlat;
				break;
			case NATURAL:
				accidImage = _imgNatural;
				break;
			case SHARP:
				accidImage = _imgSharp;
				break;
			case DOUBLESHARP:
				accidImage = _imgDoubleSharp;
				break;
			default:
				System.out.println("Accidental " + accid);
				System.exit(1);
				break;
		}
		
		g.drawImage(accidImage, xc, yc, null);
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