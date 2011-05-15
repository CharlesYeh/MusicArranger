package gui;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Set;
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
	
	final static int SHADOW_IMG_WIDTH = 100;
	final static int SHADOW_IMG_HIDE = 40;
	
	Image _buffer;
	Graphics _bufferGraphics;

	// reference to song
	Piece _piece;
	ScoreIllustrator _illustrator;

	PageSlider _slider;
	boolean _sliding;
	int dragY;
	
	Image _imgShadowLeft, _imgShadowRight;
	
	// keep track of number of measures and staffs. If either changes, redraw twice and recreate buffer
	int _prevMeasures = 0;
	int _prevStaffs = 0;
	
	// measure positions within each system

	public ScoreWindow(Piece piece) {
		_piece = piece;
		_illustrator = new ScoreIllustrator();
		
		createBuffer();

		_slider = new PageSlider();
		_sliding = false;
		
		// for page shadow
		try {
			_imgShadowLeft = ImageIO.read(new File("images/gui/left_shadow.png"));
			_imgShadowRight = ImageIO.read(new File("images/gui/right_shadow.png"));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
		
		updateScore(null);
	}
	
	private void createBuffer() {
		_buffer = new BufferedImage(ArrangerConstants.PAGE_WIDTH,
						ArrangerConstants.SCORE_HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
		_bufferGraphics = _buffer.getGraphics();
	}
	
	public Image getScoreImage() {
		return _buffer;
	}

	public void updateScore(Set<InstructionIndex> selectedNotes) {
		// buffer self-image
		List<Staff> staffList = _piece.getStaffs();
		int measures = staffList.get(0).getMeasures().size();
		int staffs = staffList.size();
		if (measures != _prevMeasures || staffs != _prevStaffs) {
			// recalculate piece height and recreate buffer
			_illustrator.drawPiece(_bufferGraphics, _piece, selectedNotes);
			createBuffer();
		}
		
		_illustrator.drawPiece(_bufferGraphics, _piece, selectedNotes);
	}

	public void drawSelf(Graphics g) {
		// draw with offset from slider
		int scrollHeight = Math.max(0, ArrangerConstants.SCORE_HEIGHT - ArrangerConstants.WINDOW_HEIGHT);
		int offsetY = (int) (_slider.getSlidePercent() * scrollHeight);
		
		int offsetX = (ArrangerConstants.WINDOW_WIDTH - ArrangerConstants.PAGE_WIDTH) / 2;
		
		// score sheet drop shadow
		g.drawImage(_imgShadowLeft, offsetX - SHADOW_IMG_WIDTH + SHADOW_IMG_HIDE, 0, null);
		g.drawImage(_imgShadowRight, offsetX + ArrangerConstants.PAGE_WIDTH - SHADOW_IMG_HIDE, 0, null);
		
		g.drawImage(_buffer, offsetX, -offsetY, null);
		
		// draw slider on top
		_slider.drawSelf(g);
	}

	public void slide(int dy) {
		_slider.setY(_slider.getY() + dy);
	}

	public List<InstructionIndex> mouseClicked(Point e) {
		return null;
	}

	public List<InstructionIndex> mousePressed(Point e) {
		if (_slider.hitTestPoint(e.getX(), e.getY())) {
			// clicked on slider
			_sliding = true;
			dragY = (int) e.getY() - _slider.getY();
			return null;
		}
		else {
			// clicked on score sheet
			adjustScorePoint(e);
			InstructionIndex index = _illustrator.getEventIndex(e);
			if (index == null)
				return null;
			
			List<InstructionIndex> listIndex = new ArrayList<InstructionIndex>();
			listIndex.add(index);
			
			return listIndex;
		}
	}

	public List<InstructionIndex> mouseReleased(Point e) {
		// account for sliding offset
		_sliding = false;
		adjustScorePoint(e);
		
		// mouse release actions from score sheet
		InstructionIndex index = _illustrator.getEventIndex(e);
		if (index == null)
			return null;
		
		List<InstructionIndex> listIndex = new ArrayList<InstructionIndex>();
		listIndex.add(index);

		return listIndex;
	}

	public List<InstructionIndex> mouseDragged(Point e) {
		if (_sliding) {
			// drag slider
			_slider.setY((int) e.getY() - dragY);
		}
		else {
			// dragging on score sheet
			e.setLocation(e.getX(), e.getY() + _slider.getY());
			InstructionIndex index = _illustrator.getEventIndex(e);
			
		}
		return null;
	}

	public void adjustScorePoint(Point p) {
		int scrollHeight = Math.max(0, ArrangerConstants.SCORE_HEIGHT - ArrangerConstants.WINDOW_HEIGHT);
		int offsetX = (ArrangerConstants.WINDOW_WIDTH - ArrangerConstants.PAGE_WIDTH) / 2;
		
		p.setLocation(p.getX() - offsetX,
							p.getY() + _slider.getSlidePercent() * scrollHeight);
	}
}