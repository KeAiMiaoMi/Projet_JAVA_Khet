package piece;

import main.GamePanel;

public class Scarab extends Piece{
	
	public Scarab(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Scarab_0");
		}else {
			image = getImage("/piece/red_Scarab_0");
		}
		
	}
	
}
