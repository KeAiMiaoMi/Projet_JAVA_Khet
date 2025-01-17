package piece;

import main.GamePanel;

public class Obelisk extends Piece{

	private boolean isDouble;

	public Obelisk(int color, int col, int row, boolean isDouble) {
		super(color, col, row);
		this.isDouble = isDouble;
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

	private void updateImage(){
		if (isDouble){
			if(color == GamePanel.WHITE){
				image = getImage("/piece/whiteObeliskDouble");
			} else {
				image = getImage("/piece/redObeliskDouble");
			}
		} else {
			if(color == GamePanel.WHITE){
				image = getImage("/piece/whiteObelisk");
			} else {
				image = getImage("/piece/redObelisk");
			}
		}
	}

	public boolean isDouble(){
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
        this.isDouble = isDouble;
		updateImage();
    }

	public void empileObelisk(Obelisk obe){
		if (obe != null && this.color == obe.color && !this.isDouble && !obe.isDouble){
			this.setDouble(true);
            GamePanel.pieces.remove(obe);
		}
	}

	@Override
    public void reactLaser(int dx, int dy) {
        if (isDouble) {
            setDouble(false);
        } else {
            GamePanel.pieces.remove(this);
        }
    }
}
