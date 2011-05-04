package gui;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

import arranger.ArrangerConstants;
import music.*;
import instructions.*;

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
	
	PageSlider _slider;
	boolean _sliding;
	int dragY;
	
	// measure positions within each system
	
	public ScoreWindow(Piece piece) {
		_piece = piece;
		_illustrator = new ScoreIllustrator();
		
		_buffer = new BufferedImage(ArrangerConstants.PAGE_WIDTH,
						ArrangerConstants.PAGES * ArrangerConstants.PAGE_HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
		_bufferGraphics = _buffer.getGraphics();
		
		_slider = new PageSlider();
		_sliding = false;
		
		updateScore();
	}
	
	public void updateScore() {
		// buffer self-image
		_illustrator.drawPiece(_bufferGraphics, _piece);
	}
	
	public void drawSelf(Graphics g) {
		// draw with offset from slider
		int scrollHeight = ArrangerConstants.PAGES * ArrangerConstants.PAGE_HEIGHT - ArrangerConstants.WINDOW_HEIGHT;
		int offset = (int) (_slider.getSlidePercent() * scrollHeight);
		g.drawImage(_buffer, 0, -offset, null);
		
		// draw slider on top
		_slider.drawSelf(g);
	}
	
	public Instruction mouseClicked(MouseEvent e) {
		InstructionIndex index = _illustrator.getEventIndex(e);
		// determine which instruction to send
		List<InstructionIndex> listIndex = new ArrayList<InstructionIndex>();
		listIndex.add(index);
		
		Instruction instr = new EditInstruction(this, listIndex, EditInstructionType.REPLACE, EditType.MULTINOTE, new MultiNote(new Rational(1, 2)));
		
		System.out.println("SCOREWINDOW" + instr);
		
		return instr;
	}
	
	public Instruction mousePressed(MouseEvent e) {
		if (_slider.hitTestPoint(e.getX(), e.getY())) {
			// clicked on slider
			_sliding = true;
			dragY = e.getY() - _slider.getY();
		}
		else {
			// clicked on score sheet
			//InstructionIndex index = _illustrator.getEventIndex(e);
		}
		return null;
	}
	
	public Instruction mouseReleased(MouseEvent e) {
		_sliding = false;
		return null;
	}
	
	public Instruction mouseDragged(MouseEvent e) {
		// drag slider?
		if (_sliding) {
			_slider.setY(e.getY() - dragY);
		}
		else {
			//InstructionIndex index = _illustrator.getEventIndex(e);
			
		}
		return null;
	}
}