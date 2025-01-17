package piece;

import main.GamePanel;

public class Mirror extends Piece {
	
	public Mirror(int color, int col, int row, int orientation) {
		super(color, col, row, orientation);
		this.orientation = orientation;
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Mirror_0");
		}else {
			image = getImage("/piece/red_Mirror_0");
		}
		
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol, targetRow)) {
			if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
					Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) { //col:row ratio needs to be 1:1
				if(isValidSquare(targetCol, targetRow)) {
					if(isTileSameColor(targetCol, targetRow)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
}