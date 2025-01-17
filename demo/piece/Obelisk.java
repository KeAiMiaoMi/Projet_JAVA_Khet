package piece;

import main.GamePanel;

public class Obelisk extends Piece{

	private boolean isDouble;

	public Obelisk(int color, int col, int row, int orientation) {
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

	public void updateImage(){
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

	public boolean canStack(int targetCol, int targetRow) {
        // Vérifie si l'Obelisk peut s'empiler sur un autre Obelisk
        for (Piece piece : GamePanel.pieces) {
            if (piece instanceof Obelisk &&
                piece.col == targetCol && piece.row == targetRow &&
                piece.color == this.color) {
                return true;
            }
        }
        return false;
    }
}