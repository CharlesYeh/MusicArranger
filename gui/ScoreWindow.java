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
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ScoreWindow extends Drawable {
	Image _buffer;
	Graphics _bufferGraphics;
	
	// reference to song
	Piece _piece;
	ScoreIllustrator _illustrator;
	
	// the y position of each system (set of staff lines)
	int[] _systemPositions;
	
	PageSlider _slider;
	
	// measure positions within each system
	
	public ScoreWindow(Piece piece) {
		_piece = piece;
		_illustrator = new ScoreIllustrator();
		
		_buffer = new BufferedImage(ArrangerConstants.WINDOW_WIDTH,
						ArrangerConstants.WINDOW_HEIGHT,
						BufferedImage.TYPE_INT_RGB);
		_bufferGraphics = _buffer.getGraphics();
		
		_slider = new PageSlider();
	}
	
	public void drawSelf(Graphics g) {
		// buffer self-image
		
		_illustrator.drawPiece(_bufferGraphics, _piece);
		//_illustrator.drawPiece(g, _piece);
		
		int scrollHeight = ArrangerConstants.PAGES * ArrangerConstants.PAGE_HEIGHT - ArrangerConstants.WINDOW_HEIGHT;
		int offset = (int) (_slider.getSlideY() * scrollHeight);
		g.drawImage(_buffer, 0, -offset, null);
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		if (_slider.hitTestPoint(e.getX(), e.getY())) {
			
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		// drag slider?
	}
}