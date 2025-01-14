package piece;

import main.GamePanel;

public class Mirror extends Piece {
	
	public Mirror(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Mirror_0");
		}else {
			image = getImage("/piece/red_Mirror_0");
		}
		
	}
	
	
}