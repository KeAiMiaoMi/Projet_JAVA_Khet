package piece;

import main.GamePanel;

public class Sphinx extends Piece{
	public Sphinx(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Sphinx_0");
		}else {
			image = getImage("/piece/red_Sphinx_0");
		}
		
	}
}
