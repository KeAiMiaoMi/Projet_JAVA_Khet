package piece;

import main.GamePanel;

public class Scarab extends Piece{
	
	public Scarab(int color, int col, int row) {
		super(color, col, row);
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Scarab_0");
		}else {
			image = getImage("/piece/red_Scarab_0");
		}
		
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol, targetRow)) {
			if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
					Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) { //col:row ratio needs to be 1:1
				if(isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
			
			//Switch
			if(isSwitchPossible(targetCol, targetRow)) {   
				for(Piece piece : GamePanel.simPieces) {
					if(piece.col == targetCol && piece.row == targetRow){
						GamePanel.switchingP = piece;
						return true;
					}
				}
			}
			
		}
		
		return false;
		
	}
	
	private boolean isSwitchPossible(int targetCol, int targetRow) {
	    if (Math.abs(targetCol - this.col) <= 1 && Math.abs(targetRow - this.row) <= 1 && 
	    		(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
					Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1)) {
	        // Trouver la pièce sur la case cible
	        for (Piece piece : GamePanel.simPieces) {
	            if (piece.col == targetCol && piece.row == targetRow) {
	                if (!(piece instanceof Scarab) && !(piece instanceof Pharoah) && piece.color == this.color) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
}
