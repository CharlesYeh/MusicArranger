package gui;

import arranger.ArrangerConstants;
import music.*;

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

	final static int TOP_MARGIN	= 150;
	final static int LEFT_MARGIN	= 50;
	final static int RIGHT_MARGIN	= 50;

	final static int SYSTEM_SPACING = 90;
	final static int SYSTEM_LINE_SPACING = 10;

	final static int STAFF_SPACING = 70;

	final static int TIMESIG_WIDTH = 30;
	final static int KEYSIG_WIDTH = 30;
	final static int CLEF_WIDTH = 50;
	final static int CHORD_SPACING = 10;

	final static int NOTE_WIDTH = SYSTEM_LINE_SPACING;
	final static int NOTE_HEIGHT = SYSTEM_LINE_SPACING;

	final static int STEM_LENGTH = 30;

	final static int MEASURE_WIDTH = 100;
	final static int LEDGER_WIDTH = (int) (SYSTEM_LINE_SPACING * 1.5);

	Image _imgQuarter, _imgHalf, _imgWhole, _imgRest,
			_imgDoubleFlat, _imgFlat, _imgNatural, _imgSharp, _imgDoubleSharp,
			_imgClefG, _imgClefF, _imgClefC;

	// map each system to its y coordinate
	List<Integer> _systemPositions;
	Map<Staff, Integer> _staffPositions;
	// staff to position > measure index
	List<TreeMap<Integer, Integer>> _measurePositions;
	// staff to measure > multinote position > timestamp value of this mnote
	List<Map<Integer, TreeMap<Integer, Rational>>> _mNotePositions;
	Map<MultiNote, Clef> _mNoteClefs;

	public ScoreIllustrator() {
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

			_imgClefG		= ImageIO.read(new File("images/score/score_clef_g.png"));
			_imgClefF		= ImageIO.read(new File("images/score/score_clef_f.png"));
			_imgClefC		= ImageIO.read(new File("images/score/score_clef_c.png"));
		}
		catch (IOException e) {
			System.out.println("Error while loading musical images: " + e);
		}
	}

	public void drawPiece(Graphics g, Piece piece) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ArrangerConstants.PAGE_WIDTH, ArrangerConstants.PAGES * ArrangerConstants.PAGE_HEIGHT);

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

		// list of measures in each staff
		List<ListIterator<Measure>> measureLists = new ArrayList<ListIterator<Measure>>();
		// current horizontal positions within each staff
		Map<Staff, Integer> staffX = new HashMap<Staff, Integer>();
		Map<Voice, Integer> voiceX = new HashMap<Voice, Integer>();
		int finalVoiceX = 0;

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

				int nextX = staffX.get(currStaff);
				if (currDur instanceof MultiNote) {
					nextX += voiceX.get(currVoice);
				}
				else {
					nextX += finalVoiceX;
				}

				//int measureWidth = 100 * currTimeSig.getNumerator() / currTimeSig.getDenominator();
				// if extending into the margin, make a new line
				if (nextX > ArrangerConstants.PAGE_WIDTH - RIGHT_MARGIN || startDrawing) {
					// draw system lines
					if (!startDrawing){
						systemY += SYSTEM_SPACING + (numStaffs - 1) * STAFF_SPACING;

						Map<Integer, TreeMap<Integer, Rational>> systemMeasures = new HashMap<Integer, TreeMap<Integer, Rational>>();
						systemMeasures.put(measureCount, new TreeMap<Integer, Rational>());
						_mNotePositions.add(systemMeasures);
					}

					startDrawing = false;

					// if new line, set left position
					nextX = LEFT_MARGIN;

					// reset x for all staffs
					Set<Staff> staffs = staffX.keySet();
					for (Staff stf : staffs) {
						staffX.put(stf, nextX);
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
				int nextY = systemY + _staffPositions.get(currStaff) * STAFF_SPACING;

				// draw duration object
				if (currDur instanceof MultiNote) {
					//-----------------------MULTINOTE-----------------------
					MultiNote mnote = (MultiNote) currDur;

					int noteX = nextX;
					int noteY = nextY;

					drawMultiNote(g, stemGroups.get(currStaff), currClef, mnote, noteX, noteY);
					Rational dur = mnote.getDuration();

					// nextX should increase proportional to note length
					int noteWidth = (int) (((double) dur.getNumerator()) / dur.getDenominator() * MEASURE_WIDTH);

					voiceX.put(currVoice, voiceX.get(currVoice) + noteWidth);
					finalVoiceX += noteWidth / voiceX.size();

					Map<Integer, TreeMap<Integer, Rational>> systemMeasures = _mNotePositions.get(_mNotePositions.size() - 1);
					TreeMap<Integer, Rational> measureMNotes = systemMeasures.get(measureCount);
					measureMNotes.put(noteX, currTime);

				}
				else if (currDur instanceof ChordSymbol && (currChord == null || !currDur.equals(currChord))) {
					//---------------------CHORD SYMBOL----------------------
					ChordSymbol cSymbol = (ChordSymbol) currDur;

					int chordX = nextX;
					int chordY = nextY + 5 * SYSTEM_LINE_SPACING + (numStaffs - 1) * STAFF_SPACING + CHORD_SPACING;
					drawChordSymbol(g, cSymbol, chordX, chordY);

					currChord = cSymbol;
				}
				else if (currDur instanceof KeySignature && (currKeySig == null || !currDur.equals(currKeySig))) {
					//-----------------------KEY SIG-----------------------
					KeySignature keySig = (KeySignature) currDur;

					// draw key sig
					drawAccidental(g, Accidental.SHARP, nextX, nextY);
					staffX.put(currStaff, nextX + KEYSIG_WIDTH);

					currKeySigs.put(currStaff, keySig);
				}
				else if (currDur instanceof TimeSignature && (currTimeSig == null || !currDur.equals(currTimeSig))) {
					//-----------------------TIME SIG-----------------------
					TimeSignature timeSig = (TimeSignature) currDur;

					// draw time sig
					g.setFont(new Font("Arial", 0, 24));
					g.drawString("" + timeSig.getNumerator(), nextX, nextY + 1 * SYSTEM_LINE_SPACING);
					g.drawString("" + timeSig.getDenominator(), nextX, nextY + 3 * SYSTEM_LINE_SPACING);
					staffX.put(currStaff, nextX + TIMESIG_WIDTH);

					currTimeSigs.put(currStaff, timeSig);
				}
				else if (currDur instanceof Clef && (currClef == null || !currDur.equals(currClef))) {
					//-------------------------CLEF-------------------------
					Clef clef = (Clef) currDur;

					drawClef(g, clef, nextX, nextY);
					staffX.put(currStaff, nextX + CLEF_WIDTH);

					currClefs.put(currStaff, clef);
				}
				else {
				}
			}

			int measureX = 0;
			// draw barline
			for (Staff stf : _staffPositions.keySet()) {
				int pos = _staffPositions.get(stf);

				Measure m = stf.getMeasures().get(0);

				int stfX = staffX.get(stf) + finalVoiceX;
				measureX = stfX;

				int startY = systemY + pos * STAFF_SPACING;
				int endY = systemY + pos * STAFF_SPACING + 4 * SYSTEM_LINE_SPACING;

				g.drawLine(stfX, startY, stfX, endY);
				staffX.put(stf, stfX + 15);
			}
			voiceX.clear();
			finalVoiceX = 0;

			// store end of measure position
			Map<Integer, Integer> lastPositions = _measurePositions.get(_measurePositions.size() - 1);
			lastPositions.put(measureX, measureCount);

			measureCount++;
		}
	}

	//------------------specific drawing calculations------------------

	/* Draws all pitches within the multinote
	 *
	 */
	private void drawMultiNote(Graphics g, List<MultiNote> stemGroup, Clef currClef, MultiNote mn, int nextX, int nextY) {
		Rational dur = mn.getDuration();

		int numer = dur.getNumerator();
		int denom = dur.getDenominator();

		int numerValue = (int) (Math.log(numer) / LOG_2);
		int denomValue = (int) (Math.log(denom) / LOG_2);

		int minLine = 0, maxLine = 0;

		List<Pitch> pitches = mn.getPitches();
		if (pitches.size() == 0) {
			// draw rest
			drawRest(g, numerValue, denomValue, nextX, nextY);

			// render previous group
			renderStemGroup(stemGroup);

			// don't render stem
			return;
		}
		else {
			// determine stem direction
			minLine = maxLine = getLineNumber(currClef, pitches.get(0));

			for (Pitch p : pitches) {
				// add 4 since the third line is "number 0"
				int line = getLineNumber(currClef, p);
				minLine = Math.min(minLine, line);
				maxLine = Math.max(maxLine, line);

				int noteX = nextX;
				int noteY = getLineOffset(currClef, line) + nextY;

				// if too low or too high, draw ledger line
				if (line < -5 || line > 5)
					drawLedgerLine(g, noteX, noteY);

				drawPitch(g, numerValue, denomValue, noteX, noteY);
			}
		}

		// draw stem or add to stem group
		if (denomValue >= 3) {
			stemGroup.add(mn);
		}
		else {
			if (denomValue != 0) {
				// don't draw stem for whole notes
				int minOffset = getLineOffset(currClef, minLine);
				int maxOffset = getLineOffset(currClef, maxLine);

				int stemX = nextX;
				if (minLine + maxLine <= 0) {
					// upwards stem
					maxOffset -= STEM_LENGTH;
					stemX += NOTE_WIDTH / 2;
				}
				else {
					// downwards stem
					minOffset += STEM_LENGTH;
					stemX -= NOTE_WIDTH / 2;
				}

				drawStem(g, stemX, nextY, minOffset, maxOffset);
			}

			// render previous group
			renderStemGroup(stemGroup);
		}
	}

	private void drawChordSymbol(Graphics g, ChordSymbol symb, int xc, int yc) {
		g.setFont(new Font("Arial", 0, 24));

		g.drawString(symb.getSymbolText(), xc, yc);
		g.drawString(symb.getTopInversionText(), xc + 10, yc);
		g.drawString(symb.getBotInversionText(), xc + 10, yc + 10);
	}

	private void drawRest(Graphics g, int numerValue, int denomValue, int xc, int yc) {
		// draw rest
		g.drawImage(_imgRest, xc, yc, null);
	}

	private void drawStem(Graphics g, int xc, int yc, int minOffset, int maxOffset) {
		g.drawLine(xc, yc + minOffset, xc, yc + maxOffset);
	}

	private void renderStemGroup(List<MultiNote> stemGroup) {
		if (stemGroup.size() == 0) {
			return;
		}
		else if (stemGroup.size() == 1) {
			// draw single stem




			stemGroup.clear();
			return;
		}
		/*
		Rational totalDuration = new Rational(0, 1);
		int totalLines = 0;

		for (int i = stemGroup.size() - 1; i >= 0; i--) {
			MultiNote mnote = stemGroup.get(i);

			// calc average (above center = stems downward, below center = stems upward)
			List<Pitch> pitches = mnote.getPitches();
			for (Pitch p : pitches) {
				totalLines += getLineNumber(clef, p);
			}

			// calc total duration
			totalDuration = totalDuration.plus(mnote.getDuration());
		}

		// -1 = upwards, 1 = downwards
		boolean stemDirection = (totalLines >= 0) ? -1 : 1;

		// calc slope of stem bar (last - first) / totalDuration
		MultiNote first = stemGroup.get(0);
		MultiNote last = stemGroup.get(stemGroup.size() - 1);

		double slope = (last.getY() - first.getY()) / (last.getX() - first.getX())
		double maxBarOffset = 0;

		// also calc how much to offset the bar vertically
		for (MultiNote mn : stemGroup) {
			int dx = mn.getX() - first.getX();
			int expectedY = first.getY() + dx * slope;
			maxBarOffset = Math.max(maxBarOffset, expectedY);

			int stemX = mn.getX();
			g.drawLine(stemX, mn.getY(), );
		}

		// draw stems and additional bars next to first bar (for 16th, etc)
		for (MultiNote mn : stemGroup) {

		}

		int barSX = first.getX();
		int barSY = first.getY() + stemDirection * (stemLength + maxBarOffset);
		int barEX = last.getX();
		int barEY = last.getX() + stemDirection * (stemLength + maxBarOffset);
		g.drawLine(barSX, barSY, barEX, barEY);
		*/
		//stemGroup = new ArrayList<MultiNote>();
		stemGroup.clear();
	}

	private void drawPitch(Graphics g, int numerValue, int denomValue, int xc, int yc) {
		// draw circle on the correct line
		//g.drawImage(_imgQuarter, xc, yc, null);

		if (numerValue == 0) {
			// note is a base note (eighth, quarter, half, etc)
			drawBaseNoteHead(g, denomValue, xc, yc);
		}
		else {
			// note is a base note + dots
			int base = numerValue - numerValue / 2;
			drawBaseNoteHead(g, base, xc, yc);

			// draw dots
			int dots = numerValue - 3;

		}
	}

	//-----------------------actual drawing methods-----------------------

	/*	Draws the note head for a base value (numerator is 1)
	 *
	 */
	private void drawBaseNoteHead(Graphics g, int denomValue, int xc, int yc) {

		if (denomValue < 3) {
			switch (denomValue) {
			case 0:
				// whole note
				dynamicDrawNoteHead(g, xc, yc, true);
				break;

			case 1:
				// half note
				dynamicDrawNoteHead(g, xc, yc, true);
				break;

			case 2:
				// quarter note
				dynamicDrawNoteHead(g, xc, yc, false);
				break;
			default:

			}
		}
		else {
			// eighth or smaller
			dynamicDrawNoteHead(g, xc, yc, false);
		}
	}

	private void dynamicDrawNoteHead(Graphics g, int xc, int yc, boolean whiteFill) {
		int sx = xc - NOTE_WIDTH / 2;
		int sy = yc - NOTE_HEIGHT / 2;

		if (whiteFill)
			g.setColor(Color.WHITE);

		g.fillOval(sx, sy, NOTE_WIDTH, NOTE_HEIGHT);

		if (whiteFill){
			g.setColor(Color.BLACK);
			g.drawOval(sx, sy, NOTE_WIDTH, NOTE_HEIGHT);
		}
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
			g.drawImage(_imgClefG, xc, yc, null);
			break;
		case FCLEF:
			g.drawImage(_imgClefF, xc, yc, null);
			break;
		case CCLEF:
			g.drawImage(_imgClefC, xc, yc, null);
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

		g.drawImage(accidImage, xc, yc, null);
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
		int systemOffset = (int) e.getY() - TOP_MARGIN;

		if (systemOffset < 0) {
			// clicked above systems
			return null;
		}

		int indexSystem = systemOffset / totalSystemHeight;

		int staffY = systemOffset % totalSystemHeight;
		// min to account for the extra space between systems
		int indexStaff = Math.min(staffY / STAFF_SPACING, _staffPositions.size() - 1);

		int lineY = staffY % STAFF_SPACING + SYSTEM_LINE_SPACING / 4;
		// actually represents the line/spaces
		int indexLine = 5 - lineY / (SYSTEM_LINE_SPACING / 2);

		//------------------X COORDINATE PARSE------------------
		// which measure
		int staffFromTop = indexSystem;
		int staffX = (int) e.getX();

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

		System.out.println("----------------------");
		System.out.println("System: " + indexSystem);
		System.out.println("Staff: " + indexStaff);
		System.out.println("Measure: " + indexMeasure);
		System.out.println("Line: " + indexLine);
		System.out.println("----------------------");
		// current voice being edited
		/*System.out.println("Voice: " + 0);*/

		Map<Integer, TreeMap<Integer, Rational>> measureMNotes = _mNotePositions.get(indexSystem);
		TreeMap<Integer, Rational> multiNotePositions = measureMNotes.get(indexMeasure);

		if (multiNotePositions == null) {
			// indexSystem doesn't have the measure indexMeasure -> UH OH

		}

		// get the measure offset by traversing through the part of the measure on the staff
		int prevNoteX = 0;
		for (int mNoteX: multiNotePositions.keySet()) {
			if (staffX < mNoteX) {
				break;
			}

			prevNoteX = mNoteX;
		}

		Rational measurePosition = new Rational(0, 1);
		if (prevNoteX != 0) {
			measurePosition = multiNotePositions.get(prevNoteX);
		}

		return new InstructionIndex(indexStaff, indexMeasure, 0, measurePosition);
	}
}
