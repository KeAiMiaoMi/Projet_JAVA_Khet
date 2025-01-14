package piece;

import main.GamePanel;

public class Pyramid extends Piece {
	
	public Pyramid(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/whitePyramid0");
		}else {
			image = getImage("/piece/redPyramid0");
		}
		
	}
	
	
}