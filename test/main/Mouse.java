package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
	
	public int x, y;
	public boolean pressed;

	private GamePanel panel;

	public Mouse(GamePanel panel) {
		this.panel = panel;
	}

	
	
	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public void mouseClicked(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			panel.rotateSelectedPiece(false);
		} else if(e.getButton() == MouseEvent.BUTTON3){
			panel.rotateSelectedPiece(true);
		}
	}
}