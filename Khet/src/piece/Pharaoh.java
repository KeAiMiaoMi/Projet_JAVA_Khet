package piece;

import main.GamePanel;

public class Pharaoh extends Piece{
	public Pharaoh(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Pharaoh_0");
		}else {
			image = getImage("/piece/red_Pharaoh_0");
		}
		
	}
}
