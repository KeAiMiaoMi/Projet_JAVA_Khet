package piece;

import main.GamePanel;

public class Anubis extends Piece {
	
	public Anubis(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Anubis_0");
		}else {
			image = getImage("/piece/red_Anubis_0");
		}
		
	}
	
	
}
