package gui;

// for graphics
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

// data struct
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

// for events
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import instructions.*;
import javax.swing.event.EventListenerList;
import java.awt.Component;

import arranger.ArrangerConstants;
import music.*;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	boolean _disabled;

   // the last toolbar which was used
	Toolbar _activeToolbar;

	List<Toolbar> _toolbars;
	Toolbar _modeToolbar, _noteToolbar, _playToolbar;
	ScoreWindow _scoreWindow;

	EventListenerList _listeners = new EventListenerList();

	//--------------------state information--------------------
	EditMode _currMode		= EditMode.NOTE;
	EditDuration _currDuration = EditDuration.QUARTER;
	
	boolean _currDefaultAccid = true;
	int _currAccidental 		= 0;
	
	// whether each click inserts rests
	boolean _currRest 		= false;
	
	// currently selected
	Set<InstructionIndex> _selected;

	//------------------end state information------------------

	public MainPanel(Piece piece) {
	   Toolbar.init("images/gui/toolbarHorizontal.png", "images/gui/toolbarVertical.png");
	   ToolbarButton.init("images/gui/button.png", "images/gui/button_over.png");

	   _toolbars = new LinkedList<Toolbar>();

	   DockController dockControl = new DockController();

		// add left hand side toolbar with buttons for input modes
	   _modeToolbar = new ModeToolbar(dockControl);
	   _noteToolbar = new NoteToolbar(dockControl);
	   _playToolbar = new PlaybackToolbar(dockControl);
	   _scoreWindow = new ScoreWindow(piece);

		// add toolbars to list of toolbars
	   _toolbars.add(_modeToolbar);
	   _toolbars.add(_noteToolbar);
	   _toolbars.add(_playToolbar);
		
		_selected = new HashSet<InstructionIndex>();
		
	   addMouseListener(this);
	   addMouseMotionListener(this);
	   addMouseWheelListener(this);
	   addComponentListener(this);
		
	   setBackground(Color.WHITE);
		
	   repaint();
	}

	public Image getScoreImage() {
		return _scoreWindow.getScoreImage();
	}

	public void paint(Graphics g) {
	   super.paint(g);

	   _scoreWindow.drawSelf(g);

	   ListIterator<Toolbar> iter = _toolbars.listIterator(_toolbars.size());
	   while (iter.hasPrevious()) {
			Drawable drawer = iter.previous();
			drawer.drawSelf(g);
	   }
	}

	public void updateScore() {
	   _scoreWindow.updateScore();
	   repaint();
	}

	public Point getEventPoint(MouseEvent e) {
	   Point pt = e.getPoint();
		//pt.setLocation(pt.getX(), pt.getY() + _scoreWindow.getSlideY());
	   return pt;
	}

   /* Handle a mouse press on a toolbar or the score window
    *
    */
	public void mousePressed(MouseEvent e) {
	   if (_disabled)
			return;

	   Point evtPoint = getEventPoint(e);

		// clicked on which toolbar?
	   _activeToolbar = mouseEventToolbar(evtPoint);
	   if (_activeToolbar == null) {
		   // clicked on score window
			_scoreWindow.mousePressed(evtPoint);
	   }
	   else {
		   // clicked on a toolbar
			_activeToolbar.mousePressed(evtPoint);
	   }
	}

   /* Handle a mouse release on a toolbar or the score window
    *
    */
	public void mouseReleased(MouseEvent e) {
	   if (_disabled)
			return;

		// factor in offset
	   Point evtPoint = getEventPoint(e);

		// clicked on which toolbar?
	   _activeToolbar = null;
	   Toolbar tbar = mouseEventToolbar(evtPoint);

	   InstructionBlock instr;
	   if (tbar == null) {
			// clicked on score window
			List<InstructionIndex> listIndex = _scoreWindow.mouseReleased(evtPoint);
			if (listIndex == null)
			   return;
			
			instr = new InstructionBlock(this);
			for (InstructionIndex index : listIndex) {
				
				if (_selected.contains(index)) {
					// don't replace this note
				}
				else {
					// replace this note
					Instruction editInstr = new EditInstruction(index, EditInstructionType.REPLACE, EditType.MULTINOTE, new MultiNote(_currDuration.getDuration()));
					instr.addInstruction(editInstr);
					
					_selected = new HashSet<InstructionIndex>(listIndex);
				}
				
				if (!_currRest) {
					// insert pitches
					System.out.println(index.getLineNumber());
					Instruction editInstr = new EditInstruction(index, EditInstructionType.INSERT, EditType.PITCH);
					instr.addInstruction(editInstr);
				}
			}
		}
		else {
			// clicked on a toolbar
			Instruction ins = tbar.mouseReleased(evtPoint);
			repaint();
			
			if (ins == null)
				return;

			instr = new InstructionBlock(this, ins);
	   }

	   sendInstruction(instr);

	   repaint();
	}
	
   /* Handle a mouse click on a toolbar or the score window
    *
    */
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (_disabled)
			return;

		Point evtPoint = getEventPoint(e);

		// clicked on which toolbar?
		Instruction instr;
		if (_activeToolbar == null) {
			// clicked on score window
			List<InstructionIndex> listIndex = _scoreWindow.mouseDragged(evtPoint);
	   }
	   else {
		   // dragging a toolbar
			_activeToolbar.mouseDragged(evtPoint);
	   }

	   repaint();
	}

	public void mouseEntered(MouseEvent e) {


		Point evtPoint = getEventPoint(e);
	}

	public void mouseExited(MouseEvent e) {

	   Point evtPoint = getEventPoint(e);

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		// slide 50 pixels for every mouse wheel notch
	   _scoreWindow.slide(50 * e.getWheelRotation());
	   repaint();
	}

   /*
    * Returns: the toolbar on which the mouse event occurred
    */
	private Toolbar mouseEventToolbar(Point e) {
	   Iterator<Toolbar> iter = _toolbars.iterator();
	   while (iter.hasNext()) {
			Toolbar drawer = iter.next();

			if (drawer.hitTestPoint(e.getX(), e.getY())) {
			// mouse clicked on this toolbar

			// move this toolbar to the front (higher priority for future events)
			   iter.remove();
			   _toolbars.add(drawer);

			   return drawer;
			}
		}

		// mouse event was not on a toolbar
	   return null;
	}

	public void componentResized(ComponentEvent e) {
		// redraw score sheet
	   Component comp = e.getComponent();
	   ArrangerConstants.WINDOW_WIDTH = comp.getWidth();
	   ArrangerConstants.WINDOW_HEIGHT = comp.getHeight();

	   repaint();
	}
	public void componentHidden(ComponentEvent e) {

	}
	public void componentMoved(ComponentEvent e) {

	}
	public void componentShown(ComponentEvent e) {

	}

	public synchronized void addInstructionListener(InstructionListener listener)  {
		_listeners.add(InstructionListener.class, listener);
	}

	public synchronized void removeInstructionListener(InstructionListener listener) {
		_listeners.remove(InstructionListener.class, listener);
	}

	// call this method whenever you want to notify
	//the event listeners of the particular event
	private synchronized void sendInstruction(InstructionBlock instrBlock) {

		// intercept GUI instructions (check first instruction)
		Instruction instr = instrBlock.getInstructions().get(0);

		if (instr instanceof ModeInstruction) {
			ModeInstruction modeInstr = (ModeInstruction) instr;

			switch (modeInstr.getType()) {
				case MODE:
					_currMode = (EditMode) modeInstr.getValue();
					break;

				case DURATION:
					_currDuration = (EditDuration) modeInstr.getValue();
					break;

				case MODIFIER:
					EditModifier currModifier = (EditModifier) modeInstr.getValue();
					switch (currModifier) {
					case FLAT:
						_currAccidental = -1;
						break;
					case SHARP:
						_currAccidental = 1;
						break;
					case REST:
						_currRest = !_currRest;
						break;
					}
					break;
			}
			return;
		}

		// broadcast instruction
	   if (instrBlock == null || instrBlock.isEmpty())
			return;

	   InstructionListener[] listeners = _listeners.getListeners(InstructionListener.class);

	   for (int i = 0; i < listeners.length; i++) {
			listeners[i].receiveInstruction(instrBlock);
	   }
	}
}