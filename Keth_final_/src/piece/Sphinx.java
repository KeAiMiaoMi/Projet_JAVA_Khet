package piece;

import main.GamePanel;

public class Sphinx extends Piece{
	public Sphinx(GamePanel panel, int color, int col, int row, int orientation) {
		super(panel, color, col, row, orientation);
		this.orientation = orientation;
		
		updateImage();
	}

	public boolean canMove(int targetCol,int targetRow){
		return false;
	}

	public void shootLaser(){
		int dx = 0;
		int dy = 0;

		switch (orientation) {
            case 0:  dx = -1; dy = 0; break; // Tire à gauche
            case 90: dx = 0; dy = 1; break;  // Tire vers le bas
            case 180: dx = 1; dy = 0; break; // Tire à droite
            case 270: dx = 0; dy = -1; break; // Tire vers le haut
        }

		getPanel().shootLaser(col, row, dx, dy, color == GamePanel.WHITE ? java.awt.Color.WHITE : java.awt.Color.RED);
	}

	public void updateImage(){
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Sphinx_" + orientation);
		}else {
			image = getImage("/piece/red_Sphinx_" + orientation);
		}
	}
}