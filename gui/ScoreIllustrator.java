package gui;

import arranger.ArrangerConstants;
import music.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;

import java.awt.Point;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import instructions.*;
import java.awt.event.MouseEvent;

public class ScoreIllustrator {
	
	final static double LOG_2 = Math.log(2);
	
	final static int TOP_MARGIN	= 100;
	final static int LEFT_MARGIN	= 15;
	final static int RIGHT_MARGIN	= 15;
	final static int BOTTOM_MARGIN = 50;
	
	final static int SYSTEM_SPACING = 150;
	final static int STAFF_SPACING = 100;
	
	final static int SYSTEM_LINE_SPACING = 10;
	
	final static int TIMESIG_WIDTH = 25;
	final static int KEYSIG_WIDTH = 7;
	final static int CLEF_WIDTH = 45;
	
	final static int CHORD_SPACING = 45;
	
	final static int NOTE_WIDTH = SYSTEM_LINE_SPACING;
	final static int NOTE_HEIGHT = SYSTEM_LINE_SPACING;
	
	final static int STEM_LENGTH = 30;
	
	final static int MEASURE_WIDTH = 180;
	final static int LEDGER_WIDTH = (int) (SYSTEM_LINE_SPACING * 1.5);
	
	final static int CLEF_IMG_OFFSET = 50;
	final static int ACCID_IMG_OFFSET = 15;
	final static int REST_IMG_OFFSET = 15;
	final static int NOTE_IMG_OFFSET = 15;
	
	final static int OFFSET_Y = 25;
	
	Image _imgQuarter, _imgHalf, _imgWhole, _imgWholeRest, _imgQuarterRest, _imgHalfRest, _imgEighthRest, _imgSixteenthRest,
			_imgDoubleFlat, _imgFlat, _imgNatural, _imgSharp, _imgDoubleSharp,
			_imgClefG, _imgClefF, _imgClefC,
			_imgQuarterNote, _imgHalfNote, _imgWholeNote, _imgQuarterNoteS, _imgHalfNoteS, _imgWholeNoteS;
	
	// map each system to its y coordinate
	List<Integer> _systemPositions;
	Map<Staff, Integer> _staffPositions;
	
	// staff to position > measure index
	List<TreeMap<Integer, Integer>> _measurePositions;
	// staff to measure > multinote position > timestamp value of this mnote
	List<Map<Integer, TreeMap<Integer, Rational>>> _mNotePositions;
	
	public ScoreIllustrator() {
		// load images
		try {
			_imgWholeRest	= ImageIO.read(new File("images/score/score_whole_rest.png"));
			_imgQuarterRest= ImageIO.read(new File("images/score/score_quarter_rest.png"));
			_imgHalfRest	= ImageIO.read(new File("images/score/score_half_rest.png"));
			_imgEighthRest	= ImageIO.read(new File("images/score/score_8_rest.png"));
			_imgSixteenthRest	= ImageIO.read(new File("images/score/score_16_rest.png"));
			
			_imgDoubleFlat	= ImageIO.read(new File("images/score/score_dflat.png"));
			_imgFlat			= ImageIO.read(new File("images/score/score_flat.png"));
			_imgNatural	 	= ImageIO.read(new File("images/score/score_natural.png"));
			_imgSharp		= ImageIO.read(new File("images/score/score_sharp.png"));
			_imgDoubleSharp= ImageIO.read(new File("images/score/score_dsharp.png"));

			_imgClefG		= ImageIO.read(new File("images/score/score_clef_g.png"));
			_imgClefF		= ImageIO.read(new File("images/score/score_clef_f.png"));
			_imgClefC		= ImageIO.read(new File("images/score/score_clef_c.png"));
			
			_imgQuarterNote= ImageIO.read(new File("images/score/score_quarter_note.png"));
			_imgHalfNote	= ImageIO.read(new File("images/score/score_half_note.png"));
			_imgWholeNote	= ImageIO.read(new File("images/score/score_whole_note.png"));
			
			_imgQuarterNoteS= ImageIO.read(new File("images/score/score_quarter_note_selected.png"));
			_imgHalfNoteS	= ImageIO.read(new File("images/score/score_half_note_selected.png"));
			_imgWholeNoteS	= ImageIO.read(new File("images/score/score_whole_note_selected.png"));
		}
		catch (IOException e) {
			System.out.println("Error while loading musical images: " + e);
		}
	}
	
	public void drawPiece(Graphics g, Piece piece, Set<InstructionIndex> selectedNotes) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ArrangerConstants.PAGE_WIDTH, ArrangerConstants.SCORE_HEIGHT);

		// used to draw things from left to right
		TreeMap<Timestamp, ListIterator<? extends Timestep>> timeline = new TreeMap<Timestamp, ListIterator<? extends Timestep>>();

		// positions
		_systemPositions = new ArrayList<Integer>();
		_staffPositions = new HashMap<Staff, Integer>();
		_measurePositions = new ArrayList<TreeMap<Integer, Integer>>();
		
		_mNotePositions = new ArrayList<Map<Integer, TreeMap<Integer, Rational>>>();

		// add first system to mNotePositions
		Map<Integer, TreeMap<Integer, Rational>> firstSys = new HashMap<Integer, TreeMap<Integer, Rational>>();
		_mNotePositions.add(firstSys);
		
		// the staff each object is in
		Map<ListIterator<Measure>, Staff> measureStaffs = new HashMap<ListIterator<Measure>, Staff>();
		Map<ListIterator<? extends Timestep>, Staff> timestepStaff = new HashMap<ListIterator<? extends Timestep>, Staff>();
		Map<ListIterator<? extends Timestep>, Voice> timestepVoice = new HashMap<ListIterator<? extends Timestep>, Voice>();

		List<Clef> clefs;
		List<KeySignature> keySigs;
		List<TimeSignature> timeSigs;
		List<ChordSymbol> chords;

		ListIterator<Clef> clefIter;
		ListIterator<KeySignature> keyIter;
		ListIterator<TimeSignature> timeIter;
		ListIterator<ChordSymbol> chordIter;

		// use map to find which clef each staff is currently using
		ChordSymbol currChord = null;
		Map<Staff, Clef> currClefs = new HashMap<Staff, Clef>();
		Map<Staff, KeySignature> currKeySigs 	= new HashMap<Staff, KeySignature>();
		Map<Staff, TimeSignature> currTimeSigs 	= new HashMap<Staff, TimeSignature>();
		
		Map<Staff, List<MultiNote>> stemGroups = new HashMap<Staff, List<MultiNote>>();
		Map<MultiNote, Point> stemMinLines = new HashMap<MultiNote, Point>();
		Map<MultiNote, Point> stemMaxLines = new HashMap<MultiNote, Point>();

		// list of measures in each staff
		List<ListIterator<Measure>> measureLists = new ArrayList<ListIterator<Measure>>();
		// current horizontal positions within each staff
		Map<Staff, Integer> staffX = new HashMap<Staff, Integer>();
		Map<Voice, Integer> voiceX = new HashMap<Voice, Integer>();
		
		// padding of non-note elements (sigs, clefs)
		boolean lastFree = false;
		Map<Staff, Integer> freeX = new HashMap<Staff, Integer>();
		
		int finalVoiceX = 0;
		int chordX = LEFT_MARGIN;

		boolean startDrawing = true;

		//Map<ListIterator<? extends Timestep>, Boolean> nextSystem = new HashMap<ListIterator<? extends Timestep>, Boolean>();
		int systemY	= TOP_MARGIN;
		int numStaffs	= piece.getStaffs().size();
		
		for (Staff staff : piece.getStaffs()) {
			List<Measure> staffMeasures = staff.getMeasures();
			ListIterator<Measure> measureIterator = staffMeasures.listIterator();

			measureLists.add(measureIterator);
			measureStaffs.put(measureIterator, staff);
			
			_staffPositions.put(staff, _staffPositions.size());
			staffX.put(staff, LEFT_MARGIN);
			
			stemGroups.put(staff, new ArrayList<MultiNote>());
		}

		int measureCount = 0;

		while (!measureLists.isEmpty()) {

			// start new map for measure multinotes
			Map<Integer, TreeMap<Integer, Rational>> currSysMeasures = _mNotePositions.get(_mNotePositions.size() - 1);
			currSysMeasures.put(measureCount, new TreeMap<Integer, Rational>());

			// set up lists within each list of measures
			for (int i = 0; i < measureLists.size(); i++) {
				ListIterator<Measure> measureIter = measureLists.get(i);
				Measure m = measureIter.next();

				Staff staff = measureStaffs.get(measureIter);

				// if measure list is empty, don't add back to list
				if (!measureIter.hasNext()) {
					measureLists.remove(i);
					i--;
				}

				// get all the lists within each measure
				//-------------------------listiters-----------------------
				// load structure
				clefs		= m.getClefs();
				keySigs 	= m.getKeySignatures();
				timeSigs	= m.getTimeSignatures();
				chords		= m.getChordSymbols();

				clefIter	= clefs.listIterator();
				keyIter 	= keySigs.listIterator();
				timeIter	= timeSigs.listIterator();
				chordIter	= chords.listIterator();

				timeline.put(new Timestamp(Clef.class), clefIter);
				timeline.put(new Timestamp(KeySignature.class), keyIter);
				timeline.put(new Timestamp(TimeSignature.class), timeIter);
				timeline.put(new Timestamp(ChordSymbol.class), chordIter);

				// keep track of which staff each list is on
				timestepStaff.put(clefIter, staff);
				timestepStaff.put(keyIter, staff);
				timestepStaff.put(timeIter, staff);
				timestepStaff.put(chordIter, staff);

				//-----------------------end listiters----------------------
				List<Voice> voices = m.getVoices();

				for (Voice v : voices) {
					List<MultiNote> multis = v.getMultiNotes();
					ListIterator<MultiNote> multisList = multis.listIterator();

					voiceX.put(v, 0);
					finalVoiceX = 0;

					timeline.put(new Timestamp(MultiNote.class), multisList);
					timestepStaff.put(multisList, staff);
					timestepVoice.put(multisList, v);
				}
			}
			
			// TODO: GO ONTO NEXT LINE################################################
			//int measureWidth = 100 * currTimeSig.getNumerator() / currTimeSig.getDenominator();
			// if extending into the margin, make a new line
			Iterator<TimeSignature> iterTimeSig = currTimeSigs.values().iterator();
			Integer measureX = staffX.values().iterator().next();
			
			if (iterTimeSig.hasNext()) {
				TimeSignature firstTimeSig = iterTimeSig.next();
				measureX += MEASURE_WIDTH * firstTimeSig.getNumerator() / firstTimeSig.getDenominator();
			}
			
			if (measureX > ArrangerConstants.PAGE_WIDTH - RIGHT_MARGIN || startDrawing) {
				// draw system lines
				if (!startDrawing){
					systemY += SYSTEM_SPACING + (numStaffs - 1) * STAFF_SPACING;
					
					Map<Integer, TreeMap<Integer, Rational>> systemMeasures = new HashMap<Integer, TreeMap<Integer, Rational>>();
					systemMeasures.put(measureCount, new TreeMap<Integer, Rational>());
					_mNotePositions.add(systemMeasures);
				}
				
				startDrawing = false;

				// if new line, set left position
				//nextX = LEFT_MARGIN;
				
				// reset x for all staffs
				Set<Staff> staffs = staffX.keySet();
				for (Staff stf : staffs) {
					staffX.put(stf, LEFT_MARGIN);
					freeX.put(stf, 0);
					
					// redraw clefs and keysigs on each new line
					currClefs.put(stf, null);
					currKeySigs.put(stf, null);
				}

				// reset x for all voices
				Set<Voice> voices = voiceX.keySet();
				for (Voice v : voices) {
					voiceX.put(v, 0);
				}

				finalVoiceX = 0;

				_systemPositions.add(systemY);

				g.setColor(Color.BLACK);
				for (int i = 0; i < numStaffs; i++){
					drawSystem(g, systemY + i * STAFF_SPACING);
				}

				_measurePositions.add(new TreeMap<Integer, Integer>());
			}
			//---------------------END NEW SYSTEM---------------------
			
			while (timeline.size() > 0) {
				Timestamp timestamp = timeline.firstKey();
				ListIterator<? extends Timestep> currList = timeline.get(timestamp);

				if (currList == null) {
					System.out.println("There was an empty list of class: " + timestamp.getAssocClass());
					System.exit(1);
				}
				
				Rational currTime = null;
				Timestep currDur = null;
				if (currList.hasNext()) {
					currTime = timestamp.getDuration();
					currDur = currList.next();

					timeline.remove(timestamp);
					timestamp.addDuration(currDur);
					timeline.put(timestamp, currList);
				}
				else {
					// don't add back to priority queue
					timeline.remove(timestamp);
					continue;
				}

				// get data related to this timestep
				Staff currStaff = timestepStaff.get(currList);
				Voice currVoice = timestepVoice.get(currList);

				Clef currClef = currClefs.get(currStaff);
				KeySignature currKeySig = currKeySigs.get(currStaff);
				TimeSignature currTimeSig = currTimeSigs.get(currStaff);
				
				
				if (currDur instanceof MultiNote || currDur instanceof ChordSymbol){
					if (lastFree) {
						updateStaffSpacing(freeX, staffX, staffX.get(currStaff));
						lastFree = false;
					}
				}
				else {
					lastFree = true;
				}
				
				int nextX = staffX.get(currStaff) + freeX.get(currStaff);
				if (currDur instanceof MultiNote) {
					nextX += voiceX.get(currVoice);
				}
				else {
					nextX += finalVoiceX;
				}
				int nextY = systemY + _staffPositions.get(currStaff) * STAFF_SPACING;
				
				// draw duration object
				if (currDur instanceof MultiNote) {
					//-----------------------MULTINOTE-----------------------
					MultiNote mnote = (MultiNote) currDur;
					
					int noteX = nextX;
					int noteY = nextY;
					
					// check for whole rest
					if (mnote.getPitches().size() == 0 && currDur.getDuration().equals(currTimeSig.asRational())) {
						drawWholeRest(g, noteX, noteY);
					}
					else {
						// see if this multinote is selected
						InstructionIndex mnoteIndex = new InstructionIndex(_staffPositions.get(currStaff), measureCount, 0, currTime);
						drawMultiNote(g, stemGroups.get(currStaff), stemMinLines, stemMaxLines, currClef, currKeySig, mnote, noteX, noteY, selectedNotes != null && selectedNotes.contains(mnoteIndex));
					}
					
					Rational dur = mnote.getDuration();
					
					// nextX should increase proportional to note length
					int noteWidth = (int) (((double) dur.getNumerator()) / dur.getDenominator() * MEASURE_WIDTH);
					
					voiceX.put(currVoice, voiceX.get(currVoice) + noteWidth);
					finalVoiceX += noteWidth / voiceX.size();

					Map<Integer, TreeMap<Integer, Rational>> systemMeasures = _mNotePositions.get(_mNotePositions.size() - 1);
					TreeMap<Integer, Rational> measureMNotes = systemMeasures.get(measureCount);
					measureMNotes.put(noteX, currTime);
				}
				else if (currDur instanceof ChordSymbol) {
					// only get from first staff
					//---------------------CHORD SYMBOL----------------------
					ChordSymbol cSymbol = (ChordSymbol) currDur;
					
					//if (currChord == null || !currDur.equals(currChord)) {
					if (_staffPositions.get(currStaff) == 0) {
						int chordY = nextY + 5 * SYSTEM_LINE_SPACING + (numStaffs - 1) * STAFF_SPACING + CHORD_SPACING;
						drawChordSymbol(g, cSymbol, chordX + staffX.get(currStaff), chordY);
					}
					
					currChord = cSymbol;
					Rational dur = cSymbol.getDuration();
					chordX += (int) (((double) dur.getNumerator()) / dur.getDenominator() * MEASURE_WIDTH) / numStaffs;
				}
				else if (currDur instanceof KeySignature && (currKeySig == null || !currDur.equals(currKeySig))) {
					//-----------------------KEY SIG-----------------------
					KeySignature keySig = (KeySignature) currDur;
					
					// draw key sig
					int accids = keySig.getAccidentalNumber();
					if (accids > 0) {
						// sharps
						for (int a = 0; a < accids; a++){
							int accidY = (3 * a + 1) % 7 - currClef.getCenterLine();
							switch (currClef.getClefName()) {
								case GCLEF:
									accidY += -3;
									break;
								case FCLEF:
									accidY += 3;
									break;
								case CCLEF:
									accidY += 0;
									break;
							}
							drawAccidental(g, Accidental.SHARP, nextX + KEYSIG_WIDTH * a, nextY + accidY * SYSTEM_LINE_SPACING / 2);
						}
					}
					else {
						// flats
						for (int a = 0; a < -accids; a++){
							int accidY = -(3 + 3 * a) % 7 - currClef.getCenterLine();
							switch (currClef.getClefName()) {
								case GCLEF:
									accidY += 5;
									break;
								case FCLEF:
									accidY += 11;
									break;
								case CCLEF:
									accidY += 8;
									break;
							}
							drawAccidental(g, Accidental.FLAT, nextX + KEYSIG_WIDTH * a, nextY + accidY * SYSTEM_LINE_SPACING / 2);
						}
					}
					freeX.put(currStaff, freeX.get(currStaff) + KEYSIG_WIDTH * Math.abs(accids));
					//staffX.put(currStaff, nextX + KEYSIG_WIDTH * Math.abs(accids));
					
					currKeySigs.put(currStaff, keySig);
				}
				else if (currDur instanceof TimeSignature && (currTimeSig == null || !currDur.equals(currTimeSig))) {
					//-----------------------TIME SIG-----------------------
					TimeSignature timeSig = (TimeSignature) currDur;

					// draw time sig
					g.setFont(new Font("Arial", 0, 24));
					g.drawString("" + timeSig.getNumerator(), nextX, (int) (nextY + 1.5 * SYSTEM_LINE_SPACING));
					g.drawString("" + timeSig.getDenominator(), nextX, (int) (nextY + 3.5 * SYSTEM_LINE_SPACING));
					//staffX.put(currStaff, nextX + TIMESIG_WIDTH);

					freeX.put(currStaff, freeX.get(currStaff) + TIMESIG_WIDTH);
					currTimeSigs.put(currStaff, timeSig);
				}
				else if (currDur instanceof Clef && (currClef == null || !currDur.equals(currClef))) {
					//-------------------------CLEF-------------------------
					Clef clef = (Clef) currDur;
					
					drawClef(g, clef, nextX, nextY - (clef.getCenterLine() - 4) * SYSTEM_LINE_SPACING / 2);
					
					freeX.put(currStaff, freeX.get(currStaff) + CLEF_WIDTH);
					currClefs.put(currStaff, clef);
				}
				else {
				}
			}
			
			//-----------------------FINISH MEASURE-----------------------
			measureX = 0;
			chordX = 0;
			
			// draw barline
			Staff stff = _staffPositions.keySet().iterator().next();
			int barX = staffX.get(stff) + finalVoiceX;
			
			for (Staff stf : _staffPositions.keySet()) {
				int pos = _staffPositions.get(stf);
				
				int stfX = staffX.get(stf) + finalVoiceX;
				staffX.put(stf, stfX + 15);
				
				measureX = stfX;
				
				renderStemGroup(g, stemGroups.get(stf), stemMinLines, stemMaxLines, currClefs.get(stf));
			}
			
			int startY = systemY;
			int endY = systemY + (numStaffs - 1) * STAFF_SPACING + 4 * SYSTEM_LINE_SPACING;
			
			g.drawLine(barX, startY, barX, endY);
			voiceX.clear();
			finalVoiceX = 0;

			// store end of measure position
			Map<Integer, Integer> lastPositions = _measurePositions.get(_measurePositions.size() - 1);
			lastPositions.put(measureX, measureCount);
			
			measureCount++;
			//---------------------END FINISH MEASURE---------------------
		}
		
		ArrangerConstants.setScoreHeight(_systemPositions.get(_systemPositions.size() - 1) + SYSTEM_SPACING + (_staffPositions.size() - 1) * STAFF_SPACING + BOTTOM_MARGIN);
	}
	
	public void updateStaffSpacing(Map<Staff, Integer> freeX, Map<Staff, Integer> staffX, int nextX) {
		int max = 0;
		for (Staff stf : freeX.keySet()) {
			int n = freeX.get(stf);
			if (max < n)
				max = n;
			
			freeX.put(stf, 0);
		}
		
		for (Staff stf : staffX.keySet()) {
			staffX.put(stf, nextX + max);
		}
	}
	
	//------------------specific drawing calculations------------------

	/* Draws all pitches within the multinote
	 *
	 */
	private void drawMultiNote(Graphics g, List<MultiNote> stemGroup, Map<MultiNote, Point> stemMinLines, Map<MultiNote, Point> stemMaxLines, Clef currClef, KeySignature currKeySig, MultiNote mn, int nextX, int nextY, boolean selected) {
		Rational dur = mn.getDuration();

		int numer = dur.getNumerator();
		int denom = dur.getDenominator();

		int numerValue = (int) (Math.log(numer) / LOG_2);
		int denomValue = (int) (Math.log(denom) / LOG_2);
		
		List<Pitch> pitches = mn.getPitches();
		if (pitches.size() == 0) {
			// draw rest
			drawRest(g, numerValue, denomValue - numerValue, nextX, nextY);
			drawDots(g, numerValue, nextX + 10, nextY + 23);
			
			// render previous group
			renderStemGroup(g, stemGroup, stemMinLines, stemMaxLines, currClef);
			
			// don't render stem
			return;
		}
		else {
			// determine stem direction
			int minLine, maxLine;
			minLine = maxLine = getLineNumber(currClef, pitches.get(0));
			
			for (Pitch p : pitches) {
				// add 4 since the third line is "number 0"
				int line = getLineNumber(currClef, p);
				minLine = Math.min(minLine, line);
				maxLine = Math.max(maxLine, line);
			}
			
			int dir = 1;
			
			int minOffset = getLineOffset(currClef, minLine);
			int maxOffset = getLineOffset(currClef, maxLine);
			if (denomValue != 0) {
				// don't draw stem for whole notes
				int stemX = nextX;
				
				dir = (minLine + maxLine <= 0) ? -1 : 1;
				// stem information
				if (dir == -1)
					maxOffset += dir * STEM_LENGTH;
				else
					minOffset += dir * STEM_LENGTH;
				
				if (denomValue < 3)
					drawStem(g, stemX - dir * SYSTEM_LINE_SPACING / 2, nextY, minOffset, maxOffset);
			}
			
			// draw stem or add to stem group
			if (denomValue >= 3) {
				stemGroup.add(mn);
				stemMinLines.put(mn, new Point(nextX, nextY + getLineOffset(currClef, minLine)));
				stemMaxLines.put(mn, new Point(nextX, nextY + getLineOffset(currClef, maxLine)));
			}
			else {
				// render previous group
				renderStemGroup(g, stemGroup, stemMinLines, stemMaxLines, currClef);
			}
			
			int adjust = dir * SYSTEM_LINE_SPACING / 2;
			
			Pitch prevPitch = null;
			for (Pitch p : pitches) {
				int line = getLineNumber(currClef, p);
				
				int noteX = nextX;
				int noteY = getLineOffset(currClef, line) + nextY;
				
				// if too low or too high, draw ledger line
				if (line < -5 || line > 5) {
					for (int i = line - (line % 2); Math.abs(i) > 5; i += (line < 0) ? 2 : -2) {
						int lineY = nextY + getLineOffset(currClef, i);
						drawLedgerLine(g, noteX, lineY);
					}
				}
				
				if (prevPitch != null && Math.abs(line - getLineNumber(currClef, prevPitch)) == 1) {
					// if adjacent, alternate side
					adjust = -adjust;
				}
				else {
					// if not adjacent, switch to correct side;
					adjust = dir * SYSTEM_LINE_SPACING / 2;
				}
				
				prevPitch = p;
				drawPitch(g, numerValue, denomValue, noteX + adjust - dir * SYSTEM_LINE_SPACING / 2, noteY, selected);
				
				// draw accidental if not in key
				if (!currKeySig.hasPitch(p))
					drawAccidental(g, p.getAccidental(), noteX + adjust - 10 - dir * SYSTEM_LINE_SPACING / 2, noteY);
			}
		}
	}

	private void drawChordSymbol(Graphics g, ChordSymbol symb, int xc, int yc) {
		g.setFont(new Font("Cambria", 0, 24));
		g.drawString(symb.getSymbolText(), xc - 10, yc);
		
		g.setFont(new Font("Cambria", 0, 12));
		g.drawString(symb.getTopInversionText(), xc + 5, yc - 10);
		g.drawString(symb.getBotInversionText(), xc + 5, yc + 2);
	}
	
	private void drawRest(Graphics g, int numerValue, int denomValue, int xc, int yc) {
		xc -= REST_IMG_OFFSET;
		yc -= REST_IMG_OFFSET - SYSTEM_LINE_SPACING * 2;
		
		// draw rest
		switch (denomValue) {
		case 0:
			g.drawImage(_imgWholeRest, xc, yc, null);
			break;
		case 1:
			g.drawImage(_imgHalfRest, xc, yc, null);
			break;
		case 2:
			g.drawImage(_imgQuarterRest, xc, yc, null);
			break;
		case 3:
			g.drawImage(_imgEighthRest, xc, yc, null);
			break;
		case 4:
			g.drawImage(_imgSixteenthRest, xc, yc, null);
			break;
		}
	}
	
	private void drawWholeRest(Graphics g, int xc, int yc) {
		xc -= REST_IMG_OFFSET;
		yc -= REST_IMG_OFFSET - SYSTEM_LINE_SPACING * 2;
		
		g.drawImage(_imgWholeRest, xc, yc, null);
	}

	private void drawStem(Graphics g, int xc, int yc, int minOffset, int maxOffset) {
		g.drawLine(xc, yc + minOffset, xc, yc + maxOffset);
	}

	private void renderStemGroup(Graphics g, List<MultiNote> stemGroup, Map<MultiNote, Point> stemMinLines, Map<MultiNote, Point> stemMaxLines, Clef currClef) {
		if (stemGroup.size() == 0) {
			return;
		}
		else if (stemGroup.size() == 1) {
			// draw single stem
			
			MultiNote mn = stemGroup.get(0);
			
			int totalLines = 0;
			List<Pitch> pitches = mn.getPitches();
			for (Pitch p : pitches) {
				totalLines += getLineNumber(currClef, p);
			}
			
			int stemDirection = (totalLines < 0) ? -1 : 1;
			
			Point pts = (stemDirection == -1) ? stemMinLines.get(mn) : stemMaxLines.get(mn);
			Point pte = (stemDirection == -1) ? stemMaxLines.get(mn) : stemMinLines.get(mn);
			
			int stemX = (int) (pts.getX() - stemDirection * SYSTEM_LINE_SPACING / 2);
			int stemY = (int) (pts.getY());
			int stemEY = (int) pte.getY() + stemDirection * STEM_LENGTH;
			
			g.drawLine(stemX, stemY, stemX, stemEY);
			
			int diff = -stemDirection * 30;
			
			// swirly
			g.drawLine(stemX, 		stemEY, 				stemX + 7, stemEY + diff / 4);
			g.drawLine(stemX + 7, 	stemEY + diff / 4, stemX + 5, stemEY + 3 * diff / 4);
			
			// draw extra lines parallel to bar for short durations (16ths etc)
			int mnoteDenom = (int) (Math.log(mn.getDuration().getDenominator()) / Math.log(2));
			for (int i = 1; i <= mnoteDenom - 3; i++ ) {
				g.drawLine(stemX, stemEY - stemDirection * 5 * i, stemX + 7 - i, stemEY - stemDirection * (5 * i + 6));
			}
			
			stemGroup.clear();
			return;
		}
		
		Rational totalDuration = new Rational(0, 1);
		int totalLines = 0;
		
		for (int i = stemGroup.size() - 1; i >= 0; i--) {
			MultiNote mnote = stemGroup.get(i);
			
			// calc average (above center = stems downward, below center = stems upward)
			List<Pitch> pitches = mnote.getPitches();
			for (Pitch p : pitches) {
				totalLines += getLineNumber(currClef, p);
			}
		}

		// -1 = upwards, 1 = downwards
		int stemDirection = (totalLines < 0) ? -1 : 1;

		// calc slope of stem bar (last - first) / width
		MultiNote firstMN = stemGroup.get(0);
		MultiNote lastMN = stemGroup.get(stemGroup.size() - 1);
		
		Point first = (stemDirection == -1) ? stemMaxLines.get(firstMN) : stemMinLines.get(firstMN);
		Point last = (stemDirection == -1) ? stemMaxLines.get(lastMN) : stemMinLines.get(lastMN);
		
		double slope = (last.getY() - first.getY()) / (last.getX() - first.getX());
		double maxBarOffset = 0;
		
		// also calc how much to offset the bar vertically
		for (MultiNote mn : stemGroup) {
			Point pt = (stemDirection == -1) ? stemMaxLines.get(mn) : stemMinLines.get(mn);
			
			int dx = (int) (pt.getX() - first.getX());
			int expectedY = (int) (first.getY() + dx * slope);
			
			if (stemDirection == -1) {
				maxBarOffset = Math.max(maxBarOffset, expectedY - pt.getY());
			}
			else {
				maxBarOffset = Math.min(maxBarOffset, expectedY - pt.getY());
			}
		}
		
		int barSX = (int) first.getX();
		int barEX = (int) last.getX();
		
		int barSY = (int) (first.getY() - maxBarOffset + stemDirection * STEM_LENGTH);
		int barEY = (int) (last.getY() - maxBarOffset + stemDirection * STEM_LENGTH);
		
		// shift horizontally
		barSX -= stemDirection * SYSTEM_LINE_SPACING / 2;
		barEX -= stemDirection * SYSTEM_LINE_SPACING / 2;
		
		g.drawLine(barSX, barSY, barEX, barEY);
		
		boolean firstNote = true;
		int prevNX = 0;
		int prevBarY = 0;
		int prevDenom = 0;
		
		// draw stems and additional bars next to first bar (for 16th, etc)
		for (MultiNote mn : stemGroup) {
			Point pt = (stemDirection == -1) ? stemMinLines.get(mn) : stemMaxLines.get(mn);
			
			int dx = (int) (pt.getX() - first.getX());
			int barY = (int) (first.getY() + dx * slope - maxBarOffset + stemDirection * STEM_LENGTH);
			
			int nx = (int) pt.getX() - stemDirection * SYSTEM_LINE_SPACING / 2;
			g.drawLine(nx, (int) pt.getY(), nx, barY);
			
			int mnoteDenom = (int) (Math.log(mn.getDuration().getDenominator()) / Math.log(2));
			
			if (!firstNote) {
				
				// draw extra lines parallel to bar for short durations (16ths etc)
				for (int i = 1; i <= mnoteDenom - 3; i++ ) {
					int leftDetail = prevBarY - stemDirection * 5 * i;
					int rightDetail = barY - stemDirection * 5 * i;
					
					int drawNX = (i <= prevDenom - 3) ? prevNX : (prevNX + nx) / 2;
					g.drawLine(drawNX, leftDetail, nx, rightDetail);
				}
			}
			else
				firstNote = false;
			
			prevNX = nx;
			prevBarY = barY;
			prevDenom = mnoteDenom;
		}
		
		stemGroup.clear();
	}

	private void drawPitch(Graphics g, int numerValue, int denomValue, int xc, int yc, boolean selected) {
		// draw circle on the correct line
		//g.drawImage(_imgQuarter, xc, yc, null);
		
		if (numerValue == 0) {
			// note is a base note (eighth, quarter, half, etc)
			drawBaseNoteHead(g, denomValue, xc, yc, selected);
		}
		else {
			// note is a base note + dots
			int base = numerValue - numerValue / 2;
			drawBaseNoteHead(g, base, xc, yc, selected);
			
			// draw dots
			drawDots(g, numerValue, xc + 10, yc);
		}
	}
	
	//-----------------------actual drawing methods-----------------------
	
	private void drawDots(Graphics g, int dots, int xc, int yc) {
		for (int i = 0; i < dots; i++) {
			g.setColor(Color.BLACK);
			g.fillOval(xc + i * 5, yc, 4, 4);
		}
	}
	
	/*	Draws the note head for a base value (numerator is 1)
	 *
	 */
	private void drawBaseNoteHead(Graphics g, int denomValue, int xc, int yc, boolean selected) {
		
		xc -= NOTE_IMG_OFFSET;
		yc -= NOTE_IMG_OFFSET;
		
		if (denomValue < 3) {
			switch (denomValue) {
			case 0:
				// whole note
				//dynamicDrawNoteHead(g, xc, yc, true, selected);
				g.drawImage(selected ? _imgWholeNoteS : _imgWholeNote, xc, yc, null);
				break;
				
			case 1:
				// half note
				//dynamicDrawNoteHead(g, xc, yc, true, selected);
				g.drawImage(selected ? _imgHalfNoteS :_imgHalfNote, xc, yc, null);
				break;
				
			case 2:
				// quarter note
				//dynamicDrawNoteHead(g, xc, yc, false, selected);
				g.drawImage(selected ? _imgQuarterNoteS :_imgQuarterNote, xc, yc, null);
				break;
			default:
				
			}
		}
		else {
			// eighth or smaller
			//dynamicDrawNoteHead(g, xc, yc, false, selected);
			g.drawImage(selected ? _imgQuarterNoteS :_imgQuarterNote, xc, yc, null);
		}
	}

	private void dynamicDrawNoteHead(Graphics g, int xc, int yc, boolean whiteFill, boolean selected) {
		int sx = xc - NOTE_WIDTH / 2;
		int sy = yc - NOTE_HEIGHT / 2;
		
		if (whiteFill)
			g.setColor(Color.WHITE);
		else
			g.setColor(selected ? Color.RED : Color.BLACK);
		
		g.fillOval(sx, sy, NOTE_WIDTH, NOTE_HEIGHT);
		
		if (whiteFill){
			// draw colored outline
			g.setColor(selected ? Color.RED : Color.BLACK);
			g.drawOval(sx, sy, NOTE_WIDTH, NOTE_HEIGHT);
		}
		
		g.setColor(Color.BLACK);
	}

	private void drawSystem(Graphics g, int yc) {
		for (int i = 0; i < 5; i++) {
			int yp = yc + i * SYSTEM_LINE_SPACING;
			g.drawLine(LEFT_MARGIN, yp, ArrangerConstants.PAGE_WIDTH - RIGHT_MARGIN, yp);
		}
	}
	
	private void drawClef(Graphics g, Clef c, int xc, int yc) {
		switch (c.getClefName()) {
		case GCLEF:
			g.drawImage(_imgClefG, xc - CLEF_IMG_OFFSET, yc - CLEF_IMG_OFFSET, null);
			break;
		case FCLEF:
			g.drawImage(_imgClefF, xc - CLEF_IMG_OFFSET, yc - CLEF_IMG_OFFSET, null);
			break;
		case CCLEF:
			g.drawImage(_imgClefC, xc - CLEF_IMG_OFFSET, yc - CLEF_IMG_OFFSET, null);
			break;
		}
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
				System.out.println("Accidental error: " + accid);
				System.exit(1);
				break;
		}

		g.drawImage(accidImage, xc - ACCID_IMG_OFFSET, yc - ACCID_IMG_OFFSET, null);
	}

	// getLineNumber takes in a pitch and returns an int representing the pitch's line number.
	public int getLineNumber(Clef c, Pitch pitch) {
		int centerValue = c.getCenterValue();
		int pitchValue = pitch.getNoteLetter().intValue() + pitch.getOctave() * 7;
		return pitchValue - centerValue + c.getCenterLine();
	}
	
	public int getLineOffset(Clef c, int line) {
		// - 4 since
		return -(line - 4) * SYSTEM_LINE_SPACING / 2;
	}
	
	public InstructionIndex getEventIndex(Point e) {
		// check x bounds
		if (e.getX() < LEFT_MARGIN || e.getX() > ArrangerConstants.PAGE_WIDTH - RIGHT_MARGIN)
			return null;

		//------------------Y COORDINATE PARSE------------------
		// determine system index
		int totalSystemHeight = (_staffPositions.size() - 1) * STAFF_SPACING + SYSTEM_SPACING;
		int systemOffset = (int) e.getY() - TOP_MARGIN + OFFSET_Y;
		
		if (systemOffset < 0) {
			// clicked above systems
			return null;
		}

		int indexSystem = systemOffset / totalSystemHeight;

		int staffY = systemOffset % totalSystemHeight;
		int indexStaff = (staffY) / STAFF_SPACING;
		
		// check for clicks in the extra space between systems (on chord symbols)
		boolean isChord = false;
		if (indexStaff >= _staffPositions.size()) {
			// clicked on a chord symbol
			isChord = true;
			indexStaff = 0;
		}
		
		// actually represents the line/spaces
		int lineY = staffY % STAFF_SPACING + SYSTEM_LINE_SPACING / 2;
		
		// add offset for better snapping then get line number
		int indexLine = 5 - (lineY - SYSTEM_LINE_SPACING / 4) / (SYSTEM_LINE_SPACING / 2) + OFFSET_Y / SYSTEM_LINE_SPACING * 2;
		
		//------------------X COORDINATE PARSE------------------
		// which measure
		int staffFromTop = indexSystem;
		// offset so clicking to the left of a note still counts as that note
		int staffX = (int) e.getX();
		
		// if clicked past all the systems, return null
		if (staffFromTop >= _measurePositions.size() && !isChord)
			return null;
		
		Map<Integer, Integer> staffMeasures = _measurePositions.get(staffFromTop);
		int indexMeasure = 0;
		
		// get index of measure right after the x position: staffX
		for (int measureX : staffMeasures.keySet()) {
			indexMeasure = staffMeasures.get(measureX);
			
			if (staffX < measureX) {
				indexMeasure--;
				break;
			}
		}
		indexMeasure++;

		/*System.out.println("----------------------");
		System.out.println("System: " + indexSystem);
		System.out.println("Staff: " + indexStaff);
		System.out.println("Measure: " + indexMeasure);
		System.out.println("Line: " + indexLine);
		System.out.println("----------------------");*/

		Map<Integer, TreeMap<Integer, Rational>> measureMNotes = _mNotePositions.get(indexSystem);
		TreeMap<Integer, Rational> multiNotePositions = measureMNotes.get(indexMeasure);
		
		// if clicked past last measure in last system
		if (multiNotePositions == null)
			return null;
		
		// get the measure offset by traversing through the part of the measure on the staff
		int prevNoteX = 0;
		for (int mNoteX: multiNotePositions.keySet()) {
			if (staffX + SYSTEM_LINE_SPACING < mNoteX) {
				break;
			}
			
			prevNoteX = mNoteX;
		}
		Rational measurePosition = new Rational(0, 1);
		if (prevNoteX != 0) {
			measurePosition = multiNotePositions.get(prevNoteX);
		}
		
		return new InstructionIndex(indexStaff, indexMeasure, 0, measurePosition, indexLine, isChord);
	}
}
