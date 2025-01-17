package piece;

import main.GamePanel;


public class Pharoah extends Piece{
	public Pharoah(GamePanel panel, int color, int col, int row, int orientation) {
		super(panel, color, col, row, orientation);
		this.orientation = orientation;

		updateImage();	
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

	@Override
	public void reactLaser(int dx, int dy){
		getPanel().getPieces().remove(this);
		getPanel().endGame(color);
	}


	public void updateImage(){
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Pharoah_" + orientation);
		}else {
			image = getImage("/piece/red_Pharoah_" + orientation);
		}
	}
	
}
