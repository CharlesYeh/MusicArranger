package gui;

import java.awt.Graphics;
import java.awt.Color;
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
	
	final static double LOG_2 = Math.log(2);
	
	final static int TOP_MARGIN	= 150;
	final static int LEFT_MARGIN	= 50;
	final static int RIGHT_MARGIN	= 50;
	final static int SYSTEM_LINE_SPACING = 10;
	final static int SYSTEM_SPACING = 80;
	
	final static int NOTE_WIDTH = SYSTEM_LINE_SPACING;
	final static int NOTE_HEIGHT = SYSTEM_LINE_SPACING;
	
	final static int MEASURE_WIDTH = 100;
	final static int LEDGER_WIDTH = (int) (SYSTEM_LINE_SPACING * 1.2);
	
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
		
		//-------------------------listiters-----------------------
		// load structure
		List<KeySignature> keySigs 	= _piece.getKeySignatures();
		List<TimeSignature> timeSigs 	= _piece.getTimeSignatures();
		List<ChordSymbol> chords		= _piece.getChordSymbols();
		
		ListIterator<KeySignature> keyIter 	= keySigs.listIterator();
		ListIterator<TimeSignature> timeIter = timeSigs.listIterator();
		ListIterator<ChordSymbol> chordIter = chords.listIterator();
		
		/*
		pQueue.add(new TimestampAssociator(keyIter));
		pQueue.add(new TimestampAssociator(timeIter));
		pQueue.add(new TimestampAssociator(chordIter));
		*/
		//-----------------------end listiters----------------------
		
		//-----------------------current data-----------------------
		// use map to find which staff each voice is on
		Map<ListIterator<? extends Timestep>, Staff> timestepStaff = new HashMap<ListIterator<? extends Timestep>, Staff>();
		// use map to find which clef each staff is currently using
		Map<Staff, Clef> currClefs = new HashMap<Staff, Clef>();
		
		KeySignature currKeySig 	= keySigs.get(0);
		TimeSignature currTimeSig 	= timeSigs.get(0);
		//ChordSymbol currChord		= chords.get(0);
		//---------------------end current data---------------------
		
		//------------------------load notes------------------------
		// add multinote lists from each staff>voice to pQueue
		List<Staff> staffs = _piece.getStaffs();
		for (Staff st : staffs) {
			List<Voice> voices = st.getVoices();
			
			// handle clefs
			List<Clef> initClef = st.getClefs();
			ListIterator<Clef> clefIter = initClef.listIterator();
			timestepStaff.put(clefIter, st);
			
			//###pQueue.add(new TimestampAssociator(clefIter);
			
			// get first clef on each staff
			currClefs.put(st, initClef.get(0));
			
			for (Voice v : voices) {
				List<MultiNote> multis = v.getMultiNotes();
				ListIterator<MultiNote> multisList = multis.listIterator();
				
				// so we can get the staff a voice is on easily
				timestepStaff.put(multisList, st);
				pQueue.add(new TimestampAssociator(multisList));
			}
		}
		//----------------------end load notes----------------------
		
		// start drawing
		boolean startDrawing = true;
		int systemY 	= TOP_MARGIN - SYSTEM_SPACING;
		int staffX		= LEFT_MARGIN;
		int noteX = staffX;
		
		while (pQueue.size() > 0) {
			TimestampAssociator timeAssoc = pQueue.poll();
			ListIterator<? extends Timestep> listDur = timeAssoc.getAssociated();//################
			
			Timestep nextDur = null;
			if (listDur.hasNext()) {
				nextDur = (Timestep) listDur.next();		//################
				
				timeAssoc.addDuration(nextDur);
				pQueue.add(timeAssoc);
			}
			else {
				// don't add back to priority queue
				continue;
			}
			
			int measureWidth = 100;
			Rational measureTime = currTimeSig.getMeasureDuration();
			if (staffX + measureWidth > ArrangerConstants.WINDOW_WIDTH - RIGHT_MARGIN || startDrawing) {
				startDrawing = false;
				
				// if new line, draw systems
				systemY += SYSTEM_SPACING;
				staffX = LEFT_MARGIN;
				drawSystem(g, systemY);
				
				noteX = staffX;
			}
			
			// draw duration object
			if (nextDur instanceof KeySignature) {
				KeySignature keySig = (KeySignature) nextDur;
				currKeySig = keySig;
				
				// draw key sig
				
			}
			else if (nextDur instanceof TimeSignature) {
				TimeSignature timeSig = (TimeSignature) nextDur;
				currTimeSig = timeSig;
				
				// draw time sig
			}
			else if (nextDur instanceof Clef) {
				Clef clef = (Clef) nextDur;
				
				drawClef(g, clef, noteX, systemY);
			}
			else if (nextDur instanceof ChordSymbol) {
				ChordSymbol cSymbol = (ChordSymbol) nextDur;
				
				
			}
			else if (nextDur instanceof MultiNote) {
				MultiNote mn = (MultiNote) nextDur;
				Rational dur = mn.getDuration();
				
				// draw all pitches
				Staff stf = timestepStaff.get(listDur);
				Clef currClef = currClefs.get(stf);
				
				List<Pitch> pitches = mn.getPitches();
				for (Pitch p : pitches) {
					// add 4 since the third line is "number 0"
					int line = getLineNumber(currClef, p);
					int noteY = -(line - 4) * SYSTEM_LINE_SPACING / 2 + systemY;
					
					// if too low or too high, draw ledger line
					if (line < -5 || line > 5)
						drawLedgerLine(g, noteX, noteY);
					
					drawNote(g, dur, line, noteX, noteY);
				}
				
				// noteX should increase proportional to note length
				int noteWidth = (int) ((double) dur.getNumerator() / dur.getDenominator() * MEASURE_WIDTH);
				noteX += noteWidth;
			}
			else {
				System.out.println("Unrecognized timestep: " + nextDur);
			}
			
			staffX += measureWidth;
		}
	}
	
	private void drawSystem(Graphics g, int yc) {
		for (int i = 0; i < 5; i++) {
			int yp = yc + i * SYSTEM_LINE_SPACING;
			g.drawLine(LEFT_MARGIN, yp, ArrangerConstants.WINDOW_WIDTH - RIGHT_MARGIN, yp);
		}
	}
	
	private void drawClef(Graphics g, Clef c, int xc, int yc) {
		//g.drawImage(
	}
	
	private void drawNote(Graphics g, Rational r, int line, int xc, int yc) {
		// draw circle on the correct line
		//g.drawImage(_imgQuarter, xc, yc, null);
		int radius = SYSTEM_LINE_SPACING / 2;
		
		int numer = r.getNumerator();
		int denom = r.getDenominator();
		
		// factor out triplets etc
		
		int numerValue = (int) (Math.log(numer) / LOG_2);
		int denomValue = (int) (Math.log(denom) / LOG_2);
		if (numer == 1) {
			drawNote(g, denomValue, line, xc, yc);
		}
		else {
			int dots = numerValue;
			int base = numerValue - numerValue / 2;
			drawNote(g, base, line, xc, yc);
			
			// draw dots
			
		}
	}
	
	private void drawNote(Graphics g, int denomValue, int line, int xc, int yc) {
		int stemExtension = (line > 0 ) ? 50 : -50;
		
		if (denomValue < 3) {
			switch (denomValue) {
			case 0:
				// whole note
				g.setColor(Color.WHITE);
				g.fillOval(xc - NOTE_WIDTH / 2, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
				
				g.setColor(Color.BLACK);
				g.drawOval(xc - NOTE_WIDTH, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
				break;
				
			case 1:
				// half note
				g.setColor(Color.WHITE);
				g.fillOval(xc - NOTE_WIDTH / 2, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
				
				g.setColor(Color.BLACK);
				g.drawOval(xc - NOTE_WIDTH, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
				
				//draw stem
				drawStem(g, xc, yc, yc + stemExtension);
				break;
				
			case 2:
				// quarter note
				g.setColor(Color.BLACK);
				g.fillOval(xc - NOTE_WIDTH, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
				
				//draw stem
				drawStem(g, xc, yc, yc + stemExtension);
				break;
			default:
				
			}
		}
		else {
			// eighth or smaller
			g.setColor(Color.BLACK);
			g.fillOval(xc - NOTE_WIDTH, yc - NOTE_HEIGHT, NOTE_WIDTH, NOTE_HEIGHT);
			
			//draw stem
			drawStem(g, xc, yc, yc + stemExtension);
		}
	}
	
	private void drawStem(Graphics g, int xc, int sy, int ey) {
		g.drawLine(xc, sy, xc, ey);
	}
	
	private void drawLedgerLine(Graphics g, int xc, int yc) {
		g.drawLine(xc - LEDGER_WIDTH / 2, yc, xc + LEDGER_WIDTH / 2, yc);
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
	
	// getLineNumber takes in a pitch and returns an int representing the pitch's line number.
	public int getLineNumber(Clef c, Pitch pitch) {
		int centerValue = c.getCenterValue();
		int pitchValue = pitch.getNoteLetter().intValue() + pitch.getOctave() * 7;
		return pitchValue - centerValue + c.getCenterLine();
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