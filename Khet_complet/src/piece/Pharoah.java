package piece;

import main.GamePanel;

public class Pharoah extends Piece{
	public Pharaoh(int color, int col, int row, int orientation) {
		super(color, col, row);
		this.orientation = orientation;
		
		updateImage();
	}

	private boolean isSwitchPossible(int targetCol, int targetRow) {
	    if (Math.abs(targetCol - this.col) <= 1 && Math.abs(targetRow - this.row) <= 1 && 
	    		(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
					Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1)) {
	        // Trouver la pièce sur la case cible
	        for (Piece piece : GamePanel.simPieces) {
	            if (piece.col == targetCol && piece.row == targetRow) {
	                if (!(piece instanceof Scarab) && !(piece instanceof Pharoah) && !(piece instanceof Djed) && !(piece instanceof Obelisk) && piece.color == this.color) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}

	@Override

	public void reactLaser(int dx, int dy){
		System.out.println("Le Pharaon a été touché ! Fin de la partie.");
		GamePanel.endGame(color);
	}

	public void updateImage(){
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Pharaoh_" + orientation);
		}else {
			image = getImage("/piece/red_Pharaoh_" + orientation);
		}
	}
}
