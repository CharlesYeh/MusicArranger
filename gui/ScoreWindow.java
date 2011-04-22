package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class ScoreWindow implements Drawable{
	
	final static int TOP_MARGIN	= 100;
	final static int LEFT_MARGIN	= 50;
	final static int RIGHT_MARGIN	= 50;
	final static int SYSTEM_LINE_SPACING = 10;
	
	// the y position of each system (set of staff lines)
	int[] _systemPositions;
	
	// measure positions within each system
	
	public ScoreWindow() {
		
	}
	
	public void drawSelf(Graphics g) {
		// draw systems while there are space for more
		int nextY = TOP_MARGIN;
		while (nextY < 500) {
			drawSystem(g, nextY);
			nextY += 100;
		}
		
		// draw measures, shifting to the next system when horizontal space runs out
		
		// draw slurs and ties
	}
	
	private void drawSystem(Graphics g, int yc) {
		for (int i = 0; i < 5; i++) {
			int yp = yc + i * SYSTEM_LINE_SPACING;
			g.drawLine(LEFT_MARGIN, yp, 500 - RIGHT_MARGIN, yp);
			
			// while there's space, draw a measure
		}
	}
	
	private void drawMeasure(Graphics g){
		// draw the notes
		
		// draw extra system lines for notes below D and above G
		
		
		// connect consecutive 8th notes
		
		// draw chord symbols?
		
	}
	
	private void drawNote(Graphics g, int xc, int yc) {
		// draw circle on the correct line
		// g.drawOval(x, y, SYSTEM_LINE_SPACING, SYSTEM_LINE_SPACING);
		
		//draw stem
		
		
		// draw accidental
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