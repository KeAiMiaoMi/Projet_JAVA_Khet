package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Board {
	final int MAX_COL = 10;
	final int MAX_ROW = 8;
	public static final int SQUARE_SIZE = 64;
	private BufferedImage boardImage;
	
	/**
	//public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;
	
	public void draw(Graphics2D g2) {
        // Définir la couleur de fond
        g2.setColor(new Color(128, 128, 128));  // Gris
        g2.fillRect(0, 0, MAX_COL * SQUARE_SIZE, MAX_ROW * SQUARE_SIZE);

        // Dessiner les cases et la grille
        g2.setColor(Color.BLACK);  // Couleur de la grille

        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                // Dessiner une case vide
                g2.drawRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }**/
	
	// Constructeur pour charger l'image du plateau
    public Board() {
        try {
            // Remplacez "path_to_image.png" par le chemin de votre fichier
            boardImage = ImageIO.read(getClass().getResource("/board/GameBoard.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur : Impossible de charger l'image du plateau.");
        }
    }

    // Dessiner le plateau avec l'image
    public void draw(Graphics2D g2) {
        if (boardImage != null) {
            g2.drawImage(boardImage, +Board.SQUARE_SIZE, +Board.SQUARE_SIZE, MAX_COL * SQUARE_SIZE, MAX_ROW * SQUARE_SIZE, null);
            
        }
        
        
    }
}
