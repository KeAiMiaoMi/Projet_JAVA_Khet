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


public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();
	
	
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
		pieces.add(new Anubis (WHITE, 5, 5, 0));
		pieces.add(new Mirror (WHITE, 4, 8, 0));
		pieces.add(new Mirror (WHITE, 6, 8, 0));
		pieces.add(new Pharoah (WHITE, 5, 8, 0));
		pieces.add(new Mirror (WHITE, 4, 3, 0));
		pieces.add(new Mirror (WHITE, 3, 4, 0));
		pieces.add(new Mirror (WHITE, 3, 5, 0));
		pieces.add(new Mirror (WHITE, 3, 8, 0));
		pieces.add(new Mirror (WHITE, 8, 7, 0));
		pieces.add(new Mirror (WHITE, 10, 5, 0));
		pieces.add(new Mirror (WHITE, 10, 4, 0));
		pieces.add(new Scarab (WHITE, 6, 5, 0));
		pieces.add(new Sphinx (WHITE, 10, 8, 0));
		
		//Red team
		pieces.add(new Anubis (RED, 6, 4, 0));
		pieces.add(new Mirror (RED, 5, 1, 0));
		pieces.add(new Mirror (RED, 7, 1, 0));
		pieces.add(new Pharoah (RED, 6, 1, 0));
		pieces.add(new Mirror (RED, 3, 2, 0));
		pieces.add(new Mirror (RED, 1, 4, 0));
		pieces.add(new Mirror (RED, 1, 5, 0));
		pieces.add(new Mirror (RED, 8, 1, 0));
		pieces.add(new Mirror (RED, 8, 4, 0));
		pieces.add(new Mirror (RED, 8, 5, 0));
		pieces.add(new Mirror (RED, 7, 6, 0));
		pieces.add(new Scarab (RED, 5, 4, 0));
		pieces.add(new Sphinx (RED, 1, 1, 0));
	}
	
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}

	public ArrayList<int[]> shootLaser(int color) {
    	ArrayList<int[]> trajectoire = new ArrayList<>(); // Pour stocker les coordonnées du laser
		
		for(Piece piece : pieces){
			if(piece instanceof Sphinx && piece.color == color){
				Sphinx sphinx = (Sphinx) piece;
				int orientation = sphinx.getOrientation();
				int dx = 0;
				int dy = 0;

				if(orientation == 0){
					dx = -1;
					dy = 0;
				} else if(orientation == 90){
					dx = 0;
					dy = 1;
				} else if(orientation == 180){
					dx = 1;
					dy = 0;
				} else if(orientation == 270){
					dx = 0;
					dy = -1;
				}

				int x = sphinx.getCol();
				int y = sphinx.getRow();

				while (x >= 0 && x < Board.MAX_COL && y >= 0 && y < Board.MAX_ROW){
					trajectoire.add(new int[]{x, y});

					boolean pieceTouched = false;

					for(Piece target : pieces){
						if(target.getCol() == x && target.getRow() == y){
							target.reactLaser(dx, dy);
							pieceTouched = true;
							break;
						}
					}

					if(pieceTouched){
						break;
					}

					x += dx;
					y += dy;
				}
			}
		}

		return trajectoire;
	}

	public void drawLaser(Graphics2D g2, ArrayList<int[]> trajectoire, Color laserColor){
		g2.setColor(laserColor);

		for(int[] position : trajectoire){
			int x = position[0];
			int y = position[1];

			g2.fillRect(x * Board.SQUARE_SIZE + Board.SQUARE_SIZE / 2, // Centre le laser dans chaque case 
			y * Board.SQUARE_SIZE + Board.SQUARE_SIZE / 2,
			5, 5); // largeur, hauteur du rectangle en pixels
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
		if (switchingP != null) {
			switchingP.rotate(isRightTurn);
			switchingP.updatePosition();
			repaint();
		}
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
		
		//Mouse button pressed
		if(mouse.pressed) {
			if(activeP == null) {
				
				//If the activeP is null, check if you can pick up a piece
				for(Piece piece : simPieces) {
					// if the mouse is on an ally piece, pick it up as activeP
					if(piece.color == currentColor && 
							piece.col == mouse.x/Board.SQUARE_SIZE &&
							piece.row == mouse.y/Board.SQUARE_SIZE) {
						activeP = piece;
					} //if player's mouse on the piece so he can pick it up
				}
			}else { //means player is already holding a piece, simulate the move
				simulate();
			}
		}
		//Mouse button released
		if(mouse.pressed == false) {
			if(activeP != null) {
				
				if(validSquare) {
					//copyPieces(simPieces, pieces);
					activeP.updatePosition(); //adjust position
					if(switchingP != null) {
						switchingP.updatePosition();
						switchingP = null;
					}
					changePlayer();
				}else {
					activeP.resetPosition();
					activeP = null;
				}
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

		//Laser
		ArrayList<int[]> trajectoireBlanche = shootLaser(GamePanel.WHITE);
		drawLaser(g2, trajectoireBlanche, Color.WHITE);

		ArrayList<int[]> trajectoireRouge = shootLaser(GamePanel.RED);
		drawLaser(g2, trajectoireRouge, Color.RED);
			
		//status message
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
		g2.setColor(Color.white);
			
		if(currentColor == WHITE) {
			g2.drawString("White's turn", 840, 550);
		}else {
			g2.drawString("Red's turn", 840, 250);
		}
	}
}