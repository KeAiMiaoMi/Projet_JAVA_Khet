package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Anubis;
import piece.Sphinx;
import piece.Pharaoh;
import piece.Piece;
import piece.Scarab;
import piece.Mirror;


public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	
	
	//Piece
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	
	
	//Color
	public static final int WHITE = 0;
	public static final int RED = 1;
	int currentColor = WHITE;
	
	
	
	public GamePanel() {
		setPreferredSize(new Dimension (WIDTH, HEIGHT));
		setBackground(Color.black);
		
		setPieces();
		copyPieces(pieces, simPieces);
		
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void setPieces() {
		//White team
		pieces.add(new Anubis (WHITE, 5, 5));
		pieces.add(new Sphinx (WHITE, 4, 8));
		pieces.add(new Sphinx (WHITE, 6, 5));
		pieces.add(new Pharaoh (WHITE, 5, 8));
		pieces.add(new Mirror (WHITE, 4, 3));
		pieces.add(new Mirror (WHITE, 3, 4));
		pieces.add(new Mirror (WHITE, 3, 5));
		pieces.add(new Mirror (WHITE, 3, 8));
		pieces.add(new Mirror (WHITE, 8, 7));
		pieces.add(new Mirror (WHITE, 10, 5));
		pieces.add(new Mirror (WHITE, 10, 4));
		pieces.add(new Scarab (WHITE, 6, 5));
		
		//Red team
		pieces.add(new Anubis (RED, 6, 4));
		pieces.add(new Sphinx (RED, 5, 1));
		pieces.add(new Sphinx (RED, 7, 1));
		pieces.add(new Pharaoh (RED, 6, 1));
		pieces.add(new Mirror (RED, 3, 2));
		pieces.add(new Mirror (RED, 1, 4));
		pieces.add(new Mirror (RED, 1, 5));
		pieces.add(new Mirror (RED, 8, 1));
		pieces.add(new Mirror (RED, 8, 4));
		pieces.add(new Mirror (RED, 8, 5));
		pieces.add(new Mirror (RED, 7, 6));
		pieces.add(new Scarab (RED, 5, 4));
	}
	
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
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
		
	}
	
	
}
