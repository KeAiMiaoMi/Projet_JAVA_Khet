package piece;

import main.GamePanel;

public class Djed extends Piece {
	
	public Djed(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/whiteDjed0");
		}else {
			image = getImage("/piece/redDjed0");
		}
		
	}
	
	
}
