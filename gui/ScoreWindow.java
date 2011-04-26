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
	Image _buffer;
	
	// reference to song
	Piece _piece;
	ScoreIllustrator _illustrator;
	
	// the y position of each system (set of staff lines)
	int[] _systemPositions;
	
	// measure positions within each system
	
	public ScoreWindow(Piece piece) {
		_piece = piece;
		_illustrator = new ScoreIllustrator();
		
	}
	
	public void drawSelf(Graphics g) {
		// buffer self-image
		
		_illustrator.drawPiece(g, _piece);
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