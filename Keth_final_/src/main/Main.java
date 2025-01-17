package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame("Khet");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		
		//Add GamePanel to window
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack();
		window.setVisible(true);
		
		gp.launchGame();
		
	}
}
