package piece;

import main.GamePanel;

public class Scarab extends Piece{
	
	public Scarab(int color, int col, int row, int orientation) {
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
	public void reactLaser(int dx, int dy) {
		int newdx_ld = dx;
		int newdy_ld = dy;
		int newdx_90;
		int newdy_90;
        boolean isReflective = false;

		if (orientation == 0) {
            if (dx == -1 && dy == 0) {
                newdx_90 = 0;
				newdy_90 = -1;
                isReflective = true;
            } else if (dx == 0 && dy == 1) {
                newdx_90 = -1;
				newdy_90 = 0;
                isReflective = true;
            }
        } else if (orientation == 90) {
            if (dx == 1 && dy == 0) {
                newdx_90 = 0;
				newdy_90 = -1;
                isReflective = true;
            } else if (dx == 0 && dy == 1) {
                newdx_90 = 1;
				newdy_90 = 0;
                isReflective = true;
            }
        } else if (orientation == 180) {
            if (dx == 1 && dy == 0) {
                newdx_90 = 0;
				newdy_90 = 1;
                isReflective = true;
            } else if (dx == 0 && dy == -1) {
                newdx_90 = 1;
				newdy_90 = 0;
                isReflective = true;
            }
        } else if (orientation == 270) {
            if (dx == -1 && dy == 0) {
                newdx_90 = 0;
				newdy_90 = 1;
                isReflective = true;
            } else if (dx == 0 && dy == -1) {
                newdx_90 = -1;
				newdy_90 = 0;
                isReflective = true;
            }
        }

        if(isReflective == false){
            GamePanel.pieces.remove(this);
            return;
        }
        GamePanel panel = (GamePanel) getParentPanel();
        panel.shootLaser(getCol(), getRow(), newdx_ld, newdy_ld);
        panel.shootLaser(getCol(), getRow(), newdx_90, newdy_90);

	}

    public void updateImage(){
        if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Horus_" + orientation);
		}else {
			image = getImage("/piece/red_Horus_" + orientation);
		}
    }
}
