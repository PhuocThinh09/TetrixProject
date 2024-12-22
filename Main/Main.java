package Main;

import javax.swing.JFrame;

public class Main {
public static void main (String[] args) {
	
	JFrame window =new JFrame ("Tetrix");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	
	//add GamePanel to the window
	
	GamePanel gp =new GamePanel();
	window.add(gp);
	window.pack();// window adapt to window size
	gp.launchGame();
	
	window.setLocationRelativeTo(null);
	window.setVisible(true);
	
	if(KeyHandler.resetPressed) {
		window.add(gp);
		window.pack();// window adapt to window size
		gp.launchGame();
		
		
	}
	
}


}
