package piece;

import main.GamePanel;


public class Pharoah extends Piece{
	public Pharoah(int color, int col, int row, int orientation) {
		super(color, col, row, orientation);
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
		boolean MirrorFace = false;

		if(
			(orientation == 0 && dx == -1 && dy == 0) ||
			(orientation == 90 && dx == 0 && dy == 1) ||
			(orientation == 180 && dx == 1 && dy == 0) ||
			(orientation == 270 && dx == 0 && dy == -1)
		){
			MirrorFace = true;
		}

		if(MirrorFace){
			GamePanel panel = (GamePanel) getParentPanel();
			panel.shootLaser(color);
		} else {
			GamePanel.pieces.remove(this);
			System.out.println("Le Pharaon a été touché ! Fin de la partie.");
			GamePanel.endGame(color);
		}
	}


	public void updateImage(){
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Anubis_" + orientation);
		}else {
			image = getImage("/piece/red_Anubis_" + orientation);
		}
	}
	
}
