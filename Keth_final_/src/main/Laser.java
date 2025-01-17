package main;


public class Laser {
    public int x, y;       // Position actuelle
    public int dx, dy;     // Direction du déplacement (vecteur)
    public boolean active; // Indique si le laser est encore en cours de trajet

    public Laser(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.active = true;
    }

    public void move() {
        if (active) {
            x += dx;
            y += dy;
        }
    }

    public boolean OutPlateau(int maxX, int maxY) {
        return x < 0 || x >= maxX || y < 0 || y >= maxY;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDx() { 
        return dx;
    }
    public int getDy() {
        return dy;
    }
    public boolean isActive(){
        return active;
    }

    public void deactivate() {
        this.active = false;
    }
}
