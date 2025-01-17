package piece;

import main.GamePanel;

public class Djed extends Piece {
	
	public Djed(int color, int col, int row, int orientation) {
		super(color, col, row);
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
		int newdx = 0;
		int newdy = 0;

		if (orientation == 0 || orientation == 180) {
            if (dx == -1 && dy == 0) {
                newdx = 0;
                newdy = -1;
            } else if (dx == 0 && dy == 1) {
                newdx = 1;
                newdy = 0;
            } else if (dx == 1 && dy == 0) {
                newdx = 0;
                newdy = 1;
            } else if (dx == 0 && dy == -1) {
                newdx = -1;
                newdy = 0;
            }
		} else if (orientation == 90 || orientation == 270) {
            if (dx == -1 && dy == 0) {
                newdx = 0;
                newdy = 1;
            } else if (dx == 0 && dy == 1) {
                newdx = -1;
                newdy = 0;
            } else if (dx == 1 && dy == 0) {
                newdx = 0;
                newdy = -1;
            } else if (dx == 0 && dy == -1) {
                newdx = 1;
                newdy = 0;
            }
        }
	
	GamePanel panel = (GamePanel) getParentPanel();
	panel.shootLaser(getCol(), getRow(), newdx, newdy);
}

public void updateImage(){
    if(color == GamePanel.WHITE) {
        image = getImage("/piece/white_Scarab_" + orientation);
    }else {
        image = getImage("/piece/red_Scarab_" + orientation);
    }
}