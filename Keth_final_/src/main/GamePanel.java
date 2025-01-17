package main;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Anubis;
import piece.Sphinx;
import piece.Pharoah;
import piece.Piece;
import piece.Scarab;
import piece.Mirror;
import piece.Obelisk;

public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse(this);
	
	
	//Piece
	public static ArrayList<Piece> pieces = new ArrayList<>(); //as backup list if player want to reset the changes
	public static ArrayList<Piece> simPieces = new ArrayList<>(); //mainly use this array
	Piece activeP; //to hold a piece
	public static Piece switchingP;
	
	
	//Color
	public static final int WHITE = 0;
	public static final int RED = 1;
	int currentColor = WHITE;
	
	//Booleans
	boolean canMove;
	boolean validSquare;

	
	public GamePanel() {
		setPreferredSize(new Dimension (WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces, simPieces);
		
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void setPieces() {
		//White team
		pieces.add(new Anubis (this, WHITE, 5, 5, 0));
		pieces.add(new Obelisk (this, WHITE, 4, 8, 0));
		pieces.add(new Obelisk (this, WHITE, 6, 8, 0));
		pieces.add(new Pharoah (this, WHITE, 5, 8, 0));
		pieces.add(new Mirror (this, WHITE, 4, 3, 0));
		pieces.add(new Mirror (this, WHITE, 3, 4, 0));
		pieces.add(new Mirror (this, WHITE, 3, 5, 0));
		pieces.add(new Mirror (this, WHITE, 3, 8, 0));
		pieces.add(new Mirror (this, WHITE, 8, 7, 0));
		pieces.add(new Mirror (this, WHITE, 10, 5, 0));
		pieces.add(new Mirror (this, WHITE, 10, 4, 0));
		pieces.add(new Scarab (this, WHITE, 6, 5, 0));
		pieces.add(new Sphinx (this, WHITE, 10, 8, 0));
		
		//Red team
		pieces.add(new Anubis (this, RED, 6, 4, 0));
		pieces.add(new Obelisk (this, RED, 5, 1, 0));
		pieces.add(new Obelisk (this, RED, 7, 1, 0));
		pieces.add(new Pharoah (this, RED, 6, 1, 0));
		pieces.add(new Mirror (this, RED, 3, 2, 0));
		pieces.add(new Mirror (this, RED, 1, 4, 0));
		pieces.add(new Mirror (this, RED, 1, 5, 0));
		pieces.add(new Mirror (this, RED, 8, 1, 0));
		pieces.add(new Mirror (this, RED, 8, 4, 0));
		pieces.add(new Mirror (this, RED, 8, 5, 0));
		pieces.add(new Mirror (this, RED, 7, 6, 0));
		pieces.add(new Scarab (this, RED, 5, 4, 0));
		pieces.add(new Sphinx (this, RED, 1, 1, 0));
	}
	
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}

	public static void endGame(int color){
		if(color == WHITE){
			System.out.println("Le Pharaon blanc a été touché. Le joueur rouge gagne !");
		}else{
			System.out.println("Le Pharaon rouge a été touché. Le joueur blanc gagne !");
		}
	}

	public void rotateSelectedPiece(boolean isRightTurn) {
		if (activeP != null) {
			activeP.rotate(isRightTurn);
			repaint();
		}
	}

	public void handleLaser() {
		Sphinx sphinx = findSphinxForColor(currentColor);
		if (sphinx != null) {
			int startX = sphinx.getCol();
			int startY = sphinx.getRow();
			int dx = 0;
			int dy = 0;

			switch (sphinx.getOrientation()) {
				case 0: dx = -1; break;
				case 90: dy = 1; break;
				case 180: dx = 1; break;
				case 270: dy = -1; break;
			}

			Color laserColor = (currentColor == WHITE) ? Color.WHITE : Color.RED; 

			shootLaser(startX, startY, dx, dy, laserColor);
		}
	}

	private Sphinx findSphinxForColor(int color) {
		for (Piece piece : pieces) {
			if (piece instanceof Sphinx && piece.color == color) {
				return (Sphinx) piece;
			}
		}
		return null;
	}


	private Piece getPieceAt(int x, int y) {
		for (Piece piece : pieces) {
			if (piece.getCol() == x && piece.getRow() == y) {
				return piece;
			}
		}
		return null;
	}

	private ArrayList<int[]> laserTrajectory = new ArrayList<>();
	private Color currentLaserColor = Color.RED;

	public void shootLaser(int startX, int startY, int dx, int dy, Color laserColor){
		Laser laser = new Laser(startX, startY, dx, dy);
		laserTrajectory.clear();

		while (laser.isActive() && !laser.OutPlateau(Board.MAX_COL, Board.MAX_ROW)){
			laser.move();
			laserTrajectory.add(new int[]{laser.getX(), laser.getY()});

			Piece piece = getPieceAt(laser.getX(), laser.getY());
			if (piece != null){
				piece.reactLaser(laser.getDx(), laser.getDy());
				laser.deactivate();
			}
		}

		this.currentLaserColor = laserColor;
		repaint();
	}

	public ArrayList<Piece> getPieces() {
    	return pieces;
	}



	@Override
	public void run() {
		//Game loop
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime =  System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	private void update() {
		// Mouse button pressed
		if (mouse.pressed) {
			if (activeP == null) {
				//If the activeP is null, check if you can pick up a piece
				for (Piece piece : simPieces) {
					// if the mouse is on an ally piece, pick it up as activeP
					if (piece.color == currentColor &&
							piece.col == mouse.x / Board.SQUARE_SIZE &&
							piece.row == mouse.y / Board.SQUARE_SIZE) {
						activeP = piece;
					} //if player's mouse on the piece so he can pick it up
				}
			} else {
				//means player is already holding a piece, simulate the move
				simulate();
			}
		}

		// Mouse button released
		if (!mouse.pressed) {
			if (activeP != null) {
				if (validSquare) {
					//copyPieces(simPieces, pieces);
					activeP.updatePosition();//adjust position
					
					// Mouvement d'empilement pour les Obelisk
					if(activeP instanceof Obelisk){
						Obelisk obelisk = (Obelisk) activeP;
						if(obelisk.canStack(activeP.col, activeP.row)){
							Obelisk targetObelisk = (Obelisk) activeP.getHittingP(activeP.col, activeP.row);
							obelisk.empileObelisk(targetObelisk);
						}
					}

				
					if (switchingP != null) {
						switchingP.updatePosition();
						switchingP = null;
					}
					handleLaser();
					changePlayer();
				} else {
					activeP.resetPosition();
				}
				activeP = null;
			}
		}
	}

		
	private void simulate() { //hypothetical move (thinking phase)
		
		canMove = false;
		validSquare = false;
		
		//Need a reset piece for restoring the removed piece during simul
		////////////////copyPieces(pieces, simPieces);
		
		//Reset switching piece's position
		if(switchingP != null) {
			switchingP.col = switchingP.preCol;
			switchingP.x = switchingP.getX(switchingP.col);
			switchingP.row = switchingP.preRow;
			switchingP.y = switchingP.getY(switchingP.row);
			switchingP = null;
		}
			
			
		//If a piece is being held, update its position
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		//Check if the piece is hovering over a reachable square
		if(activeP.canMove(activeP.col, activeP.row)) {
			
			canMove = true;
			switching(activeP, switchingP);
			validSquare = true;
		}
	}
	///////////SWITCHING
	private void switching(Piece activeP, Piece switchingP) {
		if(switchingP != null && activeP != null) {
			activeP.col = switchingP.col;
			activeP.row = switchingP.row;
			switchingP.col = activeP.preCol;
			switchingP.row = activeP.preRow;
			
			activeP.x = activeP.getX(activeP.col);
			activeP.y = activeP.getY(activeP.row);
			switchingP.x = switchingP.getX(switchingP.col);
			switchingP.y = switchingP.getY(switchingP.row);
			
		}
	}
		
		
	private void changePlayer() {
		if(currentColor == WHITE) {
			currentColor = RED;
		}else {
			currentColor = WHITE;
		}
		activeP = null;
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Board
		board.draw(g2);
			
		//Pieces
		for(Piece p : simPieces) {
			p.draw(g2);
		}
			
		if(activeP != null) {
			if(canMove) {
				g2.setColor(Color.yellow);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE,
						Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					
			}
			//Draw activeP in the end so won't be hidden by the board or colored square
			activeP.draw(g2);
				
		}
			
		//status message
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
		g2.setColor(Color.white);
			
		if(currentColor == WHITE) {
			g2.drawString("White's turn", 840, 550);
		}else {
			g2.drawString("Red's turn", 840, 250);
		}

		// Dessiner le laser
		g2.setColor(currentLaserColor != null ? currentLaserColor : Color.RED); // Défaut rouge si non défini
		for (int[] pos : laserTrajectory) {
			int x = pos[0] * Board.SQUARE_SIZE + Board.SQUARE_SIZE / 2;
			int y = pos[1] * Board.SQUARE_SIZE + Board.SQUARE_SIZE / 2;
			g2.fillRect(x, y, 5, 5);
		}

	}
}