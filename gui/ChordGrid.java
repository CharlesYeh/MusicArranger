package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseEvent;

import music.ChordSymbol;
import music.ChordType;

public class ChordGrid extends Drawable {
	
	final static int CELL_SIZE = 30;
	
	ChordSymbol[][] _symbols;
	
	public ChordGrid() {
		_width = CELL_SIZE * 8;
		_height = CELL_SIZE * 4;
		
		/*_symbols = new ChordSymbol[8][4];
		_symbols[0] = new ChordSymbol[] {c};
		_symbols[1] = new ChordSymbol[] {};
		_symbols[2] = new ChordSymbol[] {};
		_symbols[3] = new ChordSymbol[] {};
		_symbols[4] = new ChordSymbol[] {};
		_symbols[5] = new ChordSymbol[] {};
		_symbols[6] = new ChordSymbol[] {};
		_symbols[7] = new ChordSymbol[] {};*/
	}
	
	public void drawSelf(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(_x, _y, _width, _height);
		
		g.setColor(Color.RED);
		g.drawRect(_x, _y, _width, _height);
	}
	
	public ChordSymbol getChordSymbolAt(MouseEvent p) {
		int col = (p.getX() - _x) / CELL_SIZE;
		int row = (p.getY() - _y) / CELL_SIZE;
		
		return null;
	}
}