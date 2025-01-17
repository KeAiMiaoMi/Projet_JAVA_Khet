package piece;

import main.GamePanel;

public class Sphinx extends Piece{
	public Sphinx(int color, int col, int row, int orientation) {
		super(color, col, row, orientation);
		this.orientation = orientation;
		
		updateImage();
	}

	public boolean canMove(int targetCol,int targetRow){
		return false;
	}

	@Override
	public void updateImage(){
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white_Sphinx_" + orientation);
		}else {
			image = getImage("/piece/red_Sphinx_" + orientation);
		}
	}
}