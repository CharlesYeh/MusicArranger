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
	
	Image _buffer;
	
	// reference to song
	Piece _piece;
	
	// the y position of each system (set of staff lines)
	int[] _systemPositions;
	
	// measure positions within each system
	
	public ScoreWindow(Piece piece) {
		_piece = piece;
		_illustrator = new ScoreIllustrator();
		
	}
	
	public void drawSelf(Graphics g) {
		// buffer self-image
		
		_illustrator.drawPiece(g);
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