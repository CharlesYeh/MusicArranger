package gui;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class DockController {
	LinkedList<Toolbar> _topDock, _botDock, _leftDock, _rightDock;
	
	// keep track of which dock each toolbar is on
	Map<Toolbar, LinkedList<Toolbar>> _toolbarDock;
	
	public DockController() {
		_topDock = new LinkedList<Toolbar>();
		_botDock = new LinkedList<Toolbar>();
		_leftDock = new LinkedList<Toolbar>();
		_rightDock = new LinkedList<Toolbar>();
		
		_toolbarDock = new HashMap<Toolbar, LinkedList<Toolbar>>();
	}
	
	/* Docks and sets the position of the given toolbar to the top of the screen
	 *
	 */
	public void dockToTop(Toolbar tb) {
		tb.setOrientation(Orientation.HORIZONTAL);
		
		int nextY = getDockSize(Orientation.VERTICAL, _topDock);
		
		// adjust positions of toolbars on left dock
		shiftDock(_leftDock, 0, tb.getHeight());
		
		// adjust positions of toolbars on right dock
		shiftDock(_rightDock, 0, tb.getHeight());
		
		_topDock.add(tb);
		_toolbarDock.put(tb, _topDock);
		
		tb.setX(0);
		tb.setY(nextY);
	}
	
	/* Docks and sets the position of the given toolbar to the left of the screen
	 *
	 */
	public void dockToLeft(Toolbar tb) {
		tb.setOrientation(Orientation.VERTICAL);
		
		int nextX = getDockSize(Orientation.HORIZONTAL, _leftDock);
		int nextY = getDockSize(Orientation.VERTICAL, _topDock);
		
		_leftDock.add(tb);
		_toolbarDock.put(tb, _leftDock);
		
		tb.setX(nextX);
		tb.setY(nextY);
		
		// one column toolbars
	}
	public void dockToRight(Toolbar tb) {
		
	}
	public void dockToBot(Toolbar tb) {
		
	}
	public void unDock(Toolbar tb) {
		// get the dock the toolbar is on
		LinkedList<Toolbar> dock = _toolbarDock.get(tb);
		// if this toolbar isn't docked, quit
		if (dock == null)
			return;
		
		// index of the toolbar within the dock
		int tbDockIndex = dock.indexOf(tb);
		
		if (dock == _topDock) {
			// reposition lower toolbars in the same dock
			shiftDockIndexes(_topDock, 0, -tb.getHeight(), tbDockIndex + 1, dock.size() - 1);
			
			// reposition left and right docks
			shiftDock(_leftDock, 0, -tb.getHeight());
			shiftDock(_rightDock, 0, -tb.getHeight());
		}
		else if (dock == _leftDock) {
			// reposition lower toolbars in the same dock
			shiftDockIndexes(_leftDock, -tb.getWidth(), 0, tbDockIndex + 1, dock.size() - 1);
		}
		else if (dock == _rightDock) {
			// reposition lower toolbars in the same dock
			shiftDockIndexes(_rightDock, tb.getWidth(), 0, tbDockIndex + 1, dock.size() - 1);
		}
		
		dock.remove(tbDockIndex);
		_toolbarDock.remove(tb);
	}
	
	/* Translate the toolbars in the given dock indexes (startIndex, endIndex) by (dx, dy)
	 *
	 */
	public void shiftDockIndexes(LinkedList<Toolbar> dock, int dx, int dy,
											int startIndex, int endIndex) {
		int index = -1;
		
		if(startIndex > endIndex)
			return;
		
		// if within the bounds, translate
		Iterator<Toolbar> iter = dock.iterator();
		while (iter.hasNext()) {
			Toolbar tb = iter.next();
			index++;
			
			if (index < startIndex) continue;
			if (index > endIndex) break;
			
			if (dx != 0)	tb.setX(tb.getX() + dx);
			if (dy != 0)	tb.setY(tb.getY() + dy);
		}
	}
	
	/* Translate the toolbars in the given dock indexes (startIndex, endIndex) by (dx, dy)
	 *
	 */
	public void shiftDock(LinkedList<Toolbar> dock, int dx, int dy) {
		shiftDockIndexes(dock, dx, dy, 0, dock.size());
	}
	
	/* 
	 * Returns: the size of the given dimension (orient)
	 * 			of dock toolbars 0 to endIndex (inclusive)
	 */
	public int getDockSize(Orientation orient, LinkedList<Toolbar> dock) {
		return getDockSize(orient, dock, dock.size());
	}
	public int getDockSize(Orientation orient, LinkedList<Toolbar> dock, int endIndex) {
		int index = 0, length = 0;
		
		Iterator<Toolbar> iter = dock.iterator();
		while (iter.hasNext()) {
			if(index > endIndex)
				break;
			
			// add the correct dimension
			Toolbar tb = iter.next();
			length += (orient == Orientation.HORIZONTAL) ? tb.getWidth() : tb.getHeight();
			
			index++;
		}
		
		return length;
	}
}