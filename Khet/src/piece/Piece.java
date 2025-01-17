package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;
import main.GamePanel;

public class Piece {
	
	public BufferedImage image;
	public int x, y;
	public int col, row, preCol, preRow;
	public int color;
	public Piece hittingP;
	
	public Piece(int color, int col, int row) {
		this.color = color;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;
	}
	
	public BufferedImage getImage(String imagePath) {
		BufferedImage image  = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public int getX(int col) {
		return col * Board.SQUARE_SIZE;
	}
	public int getY(int row) {
		return row * Board.SQUARE_SIZE;
	}
	//Center  of the piece
	public int getCol(int x) {
		return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	
	public int getRow(int y) {
		return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	
	public void updatePosition() {
		x = getX(col);
		y = getY(row);
		preCol = getCol(x);
		preRow = getRow(y);
	}
	
	public void resetPosition() {
		col = preCol;
		row = preRow;
		x = getX(col);
		y = getY(row);
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		return false;
	}
	
	public boolean isWithinBoard(int targetCol, int targetRow) {
		if(targetCol >= 1 && targetCol <= 10 && targetRow >= 1 && targetRow <= 8) {
			return true;
		}
		return false;
	}

	public boolean isTileSameColor(int targetCol, int targetRow) {
		if(this.color == GamePanel.RED) {
			if (targetCol != 1 && (targetCol != 9 && (targetRow != 1 || targetRow != 8))) {
				return true;
			}
		}else if(this.color == GamePanel.WHITE) {
			if (targetCol != 10 && (targetCol != 2 && (targetRow != 1 || targetRow != 8))) {
				return true;
			}
		}
		
		return false;
	}
	
	public Piece getHittingP(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.col == targetCol && piece.row == targetRow && piece != this) {
				return piece;
			}
		}
		return null;
	}
	
	public boolean isValidSquare(int targetCol, int targetRow) {
		hittingP = getHittingP(targetCol, targetRow);
		
		if(hittingP == null) { //square vacant
			return true;
		}else { //square occupied
			hittingP = null;
		}
		
		return false;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
	}
	
	
	// Getters
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
	
}
