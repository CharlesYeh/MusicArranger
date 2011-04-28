package gui;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import logic.Instruction;

public abstract class Toolbar extends Drawable {
	static BufferedImage IMG_HORIZ, IMG_VERT;

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

	}

	public void drawSelf(Graphics g) {
		g.setColor(myColor);
		//g.drawImage((_orientation == Orientation.HORIZONTAL) ? IMG_HORIZ : IMG_VERT, _x, _y, null);
		g.fillRect(_x, _y, _width, _height);

		// draw buttons
		int buttonX = _x, buttonY = _y;

		for (ToolbarButton btn : _buttons) {
			btn.setX(buttonX);
			btn.setY(buttonY);

			if(_orientation == Orientation.HORIZONTAL)
				buttonX += btn.getWidth();
			else
				buttonY += btn.getHeight();

			btn.drawSelf(g);
		}
	}

	public void mouseClicked(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();

		// test button clicks
		for (ToolbarButton btn : _buttons) {
			if (btn.hitTestPoint(ex, ey)) {
				btn.getInstruction();
			}
		}
	}

	/* Begin dragging and undock
	 *
	 */
	public void mousePressed(MouseEvent e) {
		_drag = true;

		_dragX = e.getX() - _x;
		_dragY = e.getY() - _y;

		_dockControl.unDock(this);
	}

	/* Stop dragging and check dockings
	 *
	 */
	public void mouseReleased(MouseEvent e) {
		_drag = false;

		// check docking to a side
		if (_y < 0) {
			_dockControl.dockToTop(this);
		}
		else if (_x < 0) {
			_dockControl.dockToLeft(this);
		}
	}

	/* Drag this toolbar
	 *
	 */
	public void mouseDragged(MouseEvent e) {
		if (!_drag) return;

		// if being dragged, move toolbar
		_x = e.getX() - _dragX;
		_y = e.getY() - _dragY;
	}
}