package piece;

import main.GamePanel;

public class Mirror extends Piece {
    
    public Mirror(int color, int col, int row, int orientation) {
        super(color, col, row, orientation);
        this.orientation = orientation;
        updateImage();
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) { // col:row ratio needs to be 1:1
                if (isValidSquare(targetCol, targetRow)) {
                    if (isTileSameColor(targetCol, targetRow)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void reactLaser(int dx, int dy) {
        int newdx = dx;
        int newdy = dy;
        boolean isReflective = false;

        // Gestion des réflexions en fonction de l'orientation et de la provenance du laser
        if (orientation == 0) {
            if (dx == -1 && dy == 0) { // Laser venant de la gauche
                newdx = 0;
                newdy = -1; // Réflexion vers le haut
                isReflective = true;
            } else if (dx == 0 && dy == 1) { // Laser venant du bas
                newdx = -1;
                newdy = 0; // Réflexion vers la gauche
                isReflective = true;
            }
        } else if (orientation == 90) {
            if (dx == 1 && dy == 0) { // Laser venant de la droite
                newdx = 0;
                newdy = -1; // Réflexion vers le haut
                isReflective = true;
            } else if (dx == 0 && dy == 1) { // Laser venant du bas
                newdx = 1;
                newdy = 0; // Réflexion vers la droite
                isReflective = true;
            }
        } else if (orientation == 180) {
            if (dx == 1 && dy == 0) { // Laser venant de la droite
                newdx = 0;
                newdy = 1; // Réflexion vers le bas
                isReflective = true;
            } else if (dx == 0 && dy == -1) { // Laser venant du haut
                newdx = 1;
                newdy = 0; // Réflexion vers la droite
                isReflective = true;
            }
        } else if (orientation == 270) {
            if (dx == -1 && dy == 0) { // Laser venant de la gauche
                newdx = 0;
                newdy = 1; // Réflexion vers le bas
                isReflective = true;
            } else if (dx == 0 && dy == -1) { // Laser venant du haut
                newdx = -1;
                newdy = 0; // Réflexion vers la gauche
                isReflective = true;
            }
        }

        // Si la réflexion n'a pas eu lieu, la pièce est détruite
        if (!isReflective) {
            GamePanel.pieces.remove(this);
            return;
        }

        // Propage le laser réfléchi
        GamePanel panel = (GamePanel) getParentPanel(); // Vérifie si getParentPanel est valide
        panel.shootLaser(color);
    }

    public void updateImage() {
        if (color == GamePanel.WHITE) {
            image = getImage("/piece/white_Mirror_" + orientation);
        } else {
            image = getImage("/piece/red_Mirror_" + orientation);
        }
    }
}
