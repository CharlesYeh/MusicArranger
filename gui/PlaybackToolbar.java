package gui;

import java.awt.Graphics;
import instructions.PlaybackInstruction;
import instructions.PlaybackInstructionType;

public class PlaybackToolbar extends Toolbar {
	public PlaybackToolbar(DockController dockControl) {
		super(dockControl, Orientation.HORIZONTAL);
		
		dockControl.dockToTop(this);
	}
	
	protected void createButtons(){
		// add mode buttons (note, selection, zoom)
		ToolbarButton playPlay 	= new ToolbarButton("images/btns/play_play.png", false);
		ToolbarButton playStop 	= new ToolbarButton("images/btns/play_stop.png", false);
		
		playPlay.setInstruction(new PlaybackInstruction(PlaybackInstructionType.START));
		playStop.setInstruction(new PlaybackInstruction(PlaybackInstructionType.STOP));
		
		_buttons = new ToolbarButton[]{playPlay, playStop};
	}
	
	public void drawSelf(Graphics g) {
		super.drawSelf(g);
		
		// slider
		
	}
}