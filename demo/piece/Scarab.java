package piece;

import main.GamePanel;

public class Scarab extends Piece{
	
	public Scarab(int color, int col, int row, int orientation) {
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
	                if (!(piece instanceof Scarab) && !(piece instanceof Pharoah) && !(piece instanceof Obelisk) && piece.color == this.color) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}

	@Override
	public void reactLaser(int dx, int dy) {
    	GamePanel panel = (GamePanel) getParentPanel();
    	boolean hasFirstReflection = false;
    	boolean hasSecondReflection = false;

		// Première réflexion basée sur l'orientation et la direction du laser
		if (orientation == 0 && dx == -1 && dy == 0) {
			hasFirstReflection = true;
		} else if (orientation == 0 && dx == 0 && dy == 1) {
			hasSecondReflection = true;
		} else if (orientation == 90 && dx == 1 && dy == 0) {
			hasFirstReflection = true;
		} else if (orientation == 90 && dx == 0 && dy == 1) {
			hasSecondReflection = true;
		} else if (orientation == 180 && dx == 1 && dy == 0) {
			hasFirstReflection = true;
		} else if (orientation == 180 && dx == 0 && dy == -1) {
			hasSecondReflection = true;
		} else if (orientation == 270 && dx == -1 && dy == 0) {
			hasFirstReflection = true;
		} else if (orientation == 270 && dx == 0 && dy == -1) {
			hasSecondReflection = true;
		}

		// Exécuter la première réflexion si elle est valide
		if (hasFirstReflection) {
			panel.shootLaser(color);
		}

		// Exécuter la deuxième réflexion si elle est valide
		if (hasSecondReflection) {
			panel.shootLaser(color);
		}

		// Si aucune réflexion, la pièce est détruite
		if (!hasFirstReflection && !hasSecondReflection) {
			GamePanel.pieces.remove(this);
		}
	}

    public void updateImage(){
        if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Horus_" + orientation);
		}else {
			image = getImage("/piece/red_Horus_" + orientation);
		}
    }
}