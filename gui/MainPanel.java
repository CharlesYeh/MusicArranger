package gui;

// for graphics
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

// data struct
import java.util.*;

// for events
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import instructions.*;
import javax.swing.event.EventListenerList;
import java.awt.Component;

import arranger.ArrangerConstants;
import music.*;
import util.*;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener,
														MouseWheelListener, ComponentListener {
	
	static final long serialVersionUID = 0;
	boolean _disabled;
	
	// the last toolbar which was used
	Toolbar _activeToolbar;
	
	List<Toolbar> _toolbars;
	Toolbar _modeToolbar, _noteToolbar, _playToolbar;
	
	ChordGrid _chordGrid;
	ScoreWindow _scoreWindow;
	
	EventListenerList _listeners = new EventListenerList();
	
	//--------------------state information--------------------
	EditMode _currMode		= EditMode.NOTE;
	EditDuration _currDuration = EditDuration.QUARTER;
	
	Accidental _currAccidental	= null;	// is the accidental modifier set?
	InstructionIndex _insertChord = null;		// in the middle of choosing a chord symbol if != null
	boolean _currRest 		= false;		// whether each click inserts rests
	
	// currently selected
	Set<InstructionIndex> _selected;
	
	Piece _piece;
	List<InstructionIndex> _indices;
	List<List<Node<ChordSymbol>>> _suggestions;
	Image _imgBackground;
	
	//------------------end state information------------------
	
	public MainPanel(Piece piece) {
		_piece = piece;
		
		try {
			_imgBackground = ImageIO.read(new File("images/gui/background.jpg"));
		}
		catch (IOException e) {
			System.out.println("Error while loading icon for button: " + e);
		}
		
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
		
		_chordGrid = new ChordGrid();
		
		_selected = new HashSet<InstructionIndex>();
		
	   addMouseListener(this);
	   addMouseMotionListener(this);
	   addMouseWheelListener(this);
	   addComponentListener(this);
		
	   //setBackground(Color.WHITE);
		
	   repaint();
	}

	public Image getScoreImage() {
		return _scoreWindow.getScoreImage();
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(_imgBackground, 0, 0, null);
		_scoreWindow.drawSelf(g);
		
		if (_insertChord != null)
			_chordGrid.drawSelf(g);
		
	   ListIterator<Toolbar> iter = _toolbars.listIterator(_toolbars.size());
	   while (iter.hasPrevious()) {
			Drawable drawer = iter.previous();
			drawer.drawSelf(g);
	   }
	}

	public void updateScore() {
	   _scoreWindow.updateScore(_selected);
	   repaint();
	}

	public Point getEventPoint(MouseEvent e) {
	   return e.getPoint();
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
			List<InstructionIndex> listIndex = _scoreWindow.mousePressed(evtPoint);
			handleScoreMousePressed(listIndex);
			
			_chordGrid.setX((int) evtPoint.getX());
			_chordGrid.setY((int) evtPoint.getY() + 20);
	   }
	   else {
		   // clicked on a toolbar, start drag?
			_activeToolbar.mousePressed(evtPoint);
	   }
		
		// in case chord grid pops up
		repaint();
	}
	
	private void handleScoreMousePressed(List<InstructionIndex> listIndex) {
		if (listIndex == null)
			   return;
		
		InstructionBlock instr = new InstructionBlock(this);
		for (InstructionIndex index : listIndex) {
			
			if (index.getIsChord()) {
				// insert/replace chord symbol
				/*Instruction editInstr = new EditInstruction(index, EditInstructionType.REPLACE, EditType.CHORD_SYMBOL, new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MAJOR));
				instr.addInstruction(editInstr);*/
				_insertChord = index;
				
				if (_indices != null) {
					int ind = _indices.indexOf(index);
					
					if (ind != -1)
						_chordGrid.setSuggested(_suggestions.get(ind));
				}
				
				return;
			}
			else {
				// insert/replace multinote or pitch
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
					index.setAccidental(_currAccidental);
					Instruction editInstr = new EditInstruction(index, EditInstructionType.INSERT, EditType.PITCH);
					instr.addInstruction(editInstr);
				}
			}
		}
		
		if (!instr.isEmpty())
			sendInstruction(instr);
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
		
		if (_insertChord != null) {
			ChordSymbol newSymbol = _chordGrid.getChordSymbolAt(e);
			if (newSymbol != null) {
				// insert chord if mouse was released within box
				Instruction editInstr = new EditInstruction(_insertChord, EditInstructionType.REPLACE, EditType.CHORD_SYMBOL, newSymbol);
				InstructionBlock instr = new InstructionBlock(this, editInstr);
				
				sendInstruction(instr);
			}
			
			_insertChord = null;
		}
		else {
			if (tbar == null) {
				// release on scorewindow
				_scoreWindow.mouseReleased(evtPoint);
			}
			else {
				// clicked on a toolbar
				Instruction ins = tbar.mouseReleased(evtPoint);
				repaint();
				
				if (ins == null)
					return;
				
				sendInstruction(new InstructionBlock(this, ins));
			}
		}
		
	   repaint();
	}

	public void mouseDragged(MouseEvent e) {
		if (_disabled)
			return;
		
		Point evtPoint = getEventPoint(e);
		
		if (_insertChord != null) {
			// inserting chord
			
		}
		else {
			// clicked on which toolbar?
			Instruction instr;
			if (_activeToolbar == null) {
				// dragging on score window
				List<InstructionIndex> listIndex = _scoreWindow.mouseDragged(evtPoint);
		   }
		   else {
			   // dragging a toolbar
				_activeToolbar.mouseDragged(evtPoint);
		   }
		}

	   repaint();
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

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
			   _toolbars.add(0, drawer);
				
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
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		
		InstructionBlock instrBlock = new InstructionBlock(this);
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DELETE:
			for (InstructionIndex index : _selected) {
				Instruction editInstr = new EditInstruction(index, EditInstructionType.CLEAR, EditType.MULTINOTE);
				instrBlock.addInstruction(editInstr);
			}
			break;
			
		case KeyEvent.VK_UP:
			for (InstructionIndex index : _selected) {
				Instruction editInstr = new EditInstruction(index, EditInstructionType.TRANSPOSE_UP, EditType.MULTINOTE);
				instrBlock.addInstruction(editInstr);
			}
			break;
			
		case KeyEvent.VK_DOWN:
			for (InstructionIndex index : _selected) {
				Instruction editInstr = new EditInstruction(index, EditInstructionType.TRANSPOSE_DOWN, EditType.MULTINOTE);
				instrBlock.addInstruction(editInstr);
			}
			break;
		}
		
		if (!instrBlock.isEmpty())
			sendInstruction(instrBlock);
	}
	
	public void interpretInstrBlock(InstructionBlock instrBlock) {
		List<Instruction> listInstr = instrBlock.getInstructions();
		
		JOptionPane.showMessageDialog(this, "Chord generation complete. Click below staffs to view possible chord options.");
		
		for (Instruction instr : listInstr) {
			interpretInstr(instr);
		}
	}
	
	public void interpretInstr(Instruction instr) {
		if (instr instanceof GUIInstructionChordData) {
			GUIInstructionChordData GUIInstr = (GUIInstructionChordData) instr;
			// make unspecified ChordSymbol
			// store data in score window
			_indices = GUIInstr.getIndices();
			_suggestions = GUIInstr.getChords();
		}
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
					_currAccidental = (_currAccidental == Accidental.FLAT) ? null : Accidental.FLAT;
					break;
				case SHARP:
					_currAccidental = (_currAccidental == Accidental.SHARP) ? null : Accidental.SHARP;
					break;
				case NATURAL:
					_currAccidental = (_currAccidental == Accidental.NATURAL) ? null : Accidental.NATURAL;
					break;
				case REST:
					_currRest = !_currRest;
					break;
				}
				break;
				
			case GENERATE:
				// translate to generation instruction
				GenerateInstructionType genType = (GenerateInstructionType) modeInstr.getValue();
				
				InstructionIndex pieceStart	= new InstructionIndex(0, new Rational(0, 1));
				InstructionIndex pieceEnd		= new InstructionIndex(_piece.getNumMeasures(), new Rational(1, 1));
				
				Instruction genInstr = null;
				
				switch (genType) {
				case CHORDS:
					genInstr = new GenerateInstructionAnalyzeChords(pieceStart, pieceEnd, new Rational(1, 4));
					break;
					
				case VOICES:
					genInstr = new GenerateInstructionVoices(pieceStart, pieceEnd, new Rational(1, 4), 4);
					break;
					
				default:
					System.out.println("UNRECOGNIZED SUB ENUM UNDER MODE GENERATE");
				}
				
				InstructionBlock genBlock = new InstructionBlock(this, genInstr);
				
				sendInstruction(genBlock);
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