package gui;

import java.util.List;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseEvent;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Font;

import music.ChordSymbol;
import music.ChordType;
import music.ScaleDegree;
import music.Accidental;

import util.*;

public class ChordGrid extends Drawable {
	
	final static int CELL_SIZE = 30;
	
	ChordSymbol[][] _symbols;
	List<Node<ChordSymbol>> _chords;
	
	BufferedImage _imgChordGrid;
	final static String IMG_CHORDGRID = "images/gui/chordgrid.png";
	
	public ChordGrid() {
		
		try{
			_imgChordGrid = ImageIO.read(new File(IMG_CHORDGRID));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		_width = CELL_SIZE * 8;
		_height = CELL_SIZE * 4;
		
		ChordSymbol chordI		= new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MAJOR);
		ChordSymbol chordii		= new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.MINOR);
		ChordSymbol chordiii		= new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MINOR);
		ChordSymbol chordIV		= new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MAJOR);
		ChordSymbol chordV		= new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR);
		ChordSymbol chordvi		= new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MINOR);
		ChordSymbol chordviio	= new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN);
		
		ChordSymbol chordi		= new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MINOR);
		ChordSymbol chordiio		= new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.DIMIN);
		ChordSymbol chordIII		= new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MAJOR);
		ChordSymbol chordiv		= new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MINOR);
		
		ChordSymbol chordVI		= new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MAJOR);
		
		
		ChordSymbol chordN		= new ChordSymbol(new ScaleDegree(2, Accidental.FLAT), ChordType.NEAPOLITAN);
		ChordSymbol chordIt6		= new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.ITAUG6);
		ChordSymbol chordFr6		= new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.FRAUG6);
		ChordSymbol chordGer6	= new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.GERAUG6);
		
		_symbols = new ChordSymbol[3][7];
		_symbols[0] = new ChordSymbol[] {chordI, chordii, chordiii, chordIV, chordV, chordvi, chordviio};
		_symbols[1] = new ChordSymbol[] {chordi, chordiio, chordIII, chordiv, null, chordVI, chordviio};
		_symbols[2] = new ChordSymbol[] {chordN, chordIt6, chordFr6, chordGer6, null, null, null};
	}
	
	public void drawSelf(Graphics g) {
		/*
		g.setColor(Color.WHITE);
		g.fillRect(_x, _y, _width, _height);
		
		g.setColor(Color.BLACK);
		g.drawRect(_x, _y, _width, _height);
		*/
		g.drawImage(_imgChordGrid, _x, _y, null);
		
		for (int row = 0; row < _symbols.length; row++) {
			for (int col = 0; col < _symbols[row].length; col++) {
				
				if (isSuggested(_symbols[row][col]))
					g.setColor(new Color(0x00CC00));
				else 
					g.setColor(new Color(0xBB0000));
				
				drawChordSymbol(g, _symbols[row][col], _x + col * CELL_SIZE, _y + row * CELL_SIZE);
			}
		}
	}
	
	public void setSuggested(List<Node<ChordSymbol>> lst) {
		_chords = lst;
	}
	
	private boolean isSuggested(ChordSymbol c) {
		if (_chords == null)
			return false;
		
		for (Node<ChordSymbol> n : _chords) {
			if (n.getValue().equals(c))
				return true;
		}
		
		return false;
	}
	
	public void drawChordSymbol(Graphics g, ChordSymbol symb, int xc, int yc) {
		if (symb == null)
			return;
		
		g.setFont(new Font("Cambria", 0, 24));
		g.drawString(symb.getSymbolText(), xc + 5, yc + 25);
		
		g.setFont(new Font("Cambria", 0, 12));
		g.drawString(symb.getTopInversionText(), xc + 20, yc - 10 + 25);
		g.drawString(symb.getBotInversionText(), xc + 20, yc + 2 + 25);
	}
	
	public ChordSymbol getChordSymbolAt(MouseEvent p) {
		int col = (p.getX() - _x) / CELL_SIZE;
		int row = (p.getY() - _y) / CELL_SIZE;
		
		if (p.getX() - _x < 0 || p.getY() - _y < 0 || row >= _symbols.length || col > _symbols[row].length)
			return null;
		
		return _symbols[row][col];
	}
}