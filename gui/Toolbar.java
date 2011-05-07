package gui;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import instructions.Instruction;

public abstract class Toolbar extends Drawable {
	static BufferedImage IMG_HORIZ, IMG_VERT;
	
	Image _buffer;
	Graphics _bufferGraphics;
	
	DockController _dockControl;
	Orientation _orientation;

	ToolbarButton[] _buttons;

	boolean _drag;
	int _dragX, _dragY;

	Color myColor;

	public static void init(String imgHoriz, String imgVert) {
		try{
			IMG_HORIZ = ImageIO.read(new File(imgHoriz));
			IMG_VERT = ImageIO.read(new File(imgVert));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Toolbar(DockController dockControl, Orientation orient) {
		_dockControl = dockControl;

		_x = (int) (Math.random() * 300);
		_y = 20;
		_width = 100;
		_height = 30;

		_drag = false;
		_dragX = _dragY = 0;

		// JUST FOR TESTING
		myColor = new Color((int)(Math.random() * 0xFFFFFF));
		
		_buffer = new BufferedImage(_width,
						_height,
						BufferedImage.TYPE_INT_ARGB);
		_bufferGraphics = _buffer.getGraphics();
		
		createButtons();
		setOrientation(orient);
	}

	/* Creates the buttons on this toolbar - subclasses will extend this
	 *
	 */
	protected void createButtons(){
		_buttons = new ToolbarButton[]{};
	}

	public void setOrientation(Orientation or) {

		// calculate length
		int length = 0;
		for (ToolbarButton btn : _buttons) {
			length += (or == Orientation.HORIZONTAL) ? btn.getWidth() : btn.getHeight();
		}

		_orientation = or;
		if (_orientation == Orientation.HORIZONTAL) {
			_width = length;
			_height = 30;
		}
		else {
			_width = 30;
			_height = length;
		}
		
		drawBuffer();
	}
	
	public void drawBuffer() {
		_bufferGraphics.setColor(myColor);
		
		_bufferGraphics.fillRect(0, 0, _width, _height);

		// draw buttons
		//int buttonX = _x, buttonY = _y;
		int buttonX = 0, buttonY = 0;
		
		for (ToolbarButton btn : _buttons) {
			btn.setX(buttonX);
			btn.setY(buttonY);

			if(_orientation == Orientation.HORIZONTAL)
				buttonX += btn.getWidth();
			else
				buttonY += btn.getHeight();

			btn.drawSelf(_bufferGraphics);
		}
	}
	
	public void drawSelf(Graphics g) {
		g.drawImage(_buffer, _x, _y, null);
	}

	public Instruction mouseClicked(Point e) {
		
		// test button clicks
		for (ToolbarButton btn : _buttons) {
			if (btn.hitTestPoint(e)) {
				return btn.getInstruction();
			}
		}
		return null;
	}

	/* Begin dragging and undock
	 *
	 */
	public Instruction mousePressed(Point e) {
		_drag = true;

		_dragX = (int) e.getX() - _x;
		_dragY = (int) e.getY() - _y;

		_dockControl.unDock(this);
		return null;
	}
	
	/* Stop dragging and check dockings
	 *
	 */
	public Instruction mouseReleased(Point e) {
		_drag = false;

		// check docking to a side
		if (_y < 0) {
			_dockControl.dockToTop(this);
		}
		else if (_x < 0) {
			_dockControl.dockToLeft(this);
		}
		
		return null;
	}

	/* Drag this toolbar
	 *
	 */
	public Instruction mouseDragged(Point e) {
		if (!_drag) return null;

		// if being dragged, move toolbar
		_x = (int) e.getX() - _dragX;
		_y = (int) e.getY() - _dragY;
		
		return null;
	}
}