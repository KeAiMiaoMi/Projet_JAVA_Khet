package piece;

import main.GamePanel;

public class Obelisk extends Piece{
	public Obelisk(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/whiteObelisk0");
		}else {
			image = getImage("/piece/redObelisk0");
		}
		
	}
}
