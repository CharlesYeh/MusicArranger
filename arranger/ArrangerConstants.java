package arranger;

public class ArrangerConstants {
	public static int WINDOW_WIDTH = 100;
	public static int WINDOW_HEIGHT = 100;
	
	// 72 pixels per inch
	public static int PAGES = 2;
	final public static int PAGE_WIDTH = 900;//(int) (8.5 * 72);
	final public static int PAGE_HEIGHT = 11*72;
	public static int SCORE_HEIGHT = 800;
	
	// for playback
	public static int TEMPO = 80;
	public static int WHOLE_NOTES_PER_MINUTE = TEMPO / 4;
	
	public static void setTempo(int t) {
		TEMPO = t;
		WHOLE_NOTES_PER_MINUTE = TEMPO / 4;
	}
}
