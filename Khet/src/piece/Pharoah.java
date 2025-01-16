package piece;

import main.GamePanel;

public class Pharoah extends Piece{
	public Pharoah(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Pharoah_0");
		}else {
			image = getImage("/piece/red_Pharoah_0");
		}
		
	}
}
