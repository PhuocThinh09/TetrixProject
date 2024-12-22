package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import Mino.Block;
import Mino.Mino;
import Mino.Mino_L1;
import Mino.Mino_L2;
import Mino.Mino_Square;
import Mino.Mino_T;
import Mino.Mino_Z1;
import Mino.Mino_Z2;
import Mino.Mino_bar;

// like handle which draw play area, manage bricks, or gameplay action
public class PlayManager {

	//main playarea
	
	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;

	
	//Mino
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y;
	Mino nextMino;
	final int NEXTMINO_X;
	final int NEXTMINO_Y;
	public static ArrayList<Block> staticBlocks =new ArrayList<>();
	
	
	
	
	//other 
	public static int dropInterval =60;// the speed of the drop is 60 frames
	boolean gameOver;
	
	
	
	
	// effect 
	boolean eco;
	int ec;
	ArrayList<Integer> effectY = new ArrayList<>();
	
	//score
	int level =1;
	int lines;
	int score;

	public PlayManager() {
		
		//Main Play Area Frame 
		left_x=(GamePanel.WIDTH/2)-(WIDTH/2);
		right_x= left_x + WIDTH;
		top_y=50;
		bottom_y=top_y+HEIGHT;
	
		MINO_START_X = left_x +(WIDTH/2)- Block.SIZE;
		MINO_START_Y = top_y+ Block.SIZE;
		
		NEXTMINO_X = right_x + 175;
		NEXTMINO_Y = top_y + 510;
		
		
		
		
	// set the starting Mino
		currentMino = pickMino();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino =pickMino();
		nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	
	}
	private Mino pickMino() {
		
		//pick random mino
		Mino mino =null;
		int i = new Random().nextInt(7);
		
		switch(i) {
		case 0: mino =new Mino_L1();break;
		case 1: mino =new Mino_L2();break;
		case 2: mino =new Mino_bar();break;
		case 3: mino =new Mino_Square();break;
		case 4: mino =new Mino_Z1();break;
		case 5: mino =new Mino_Z2();break;
		case 6: mino =new Mino_T();break;
		}
		return mino;
		
		
	}
	
	
	
	
	public void update() {
		
		
		//check if the currentMino is active 
		if(currentMino.active == false) {
			//if the Mino no active , put it in static blocks
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);
			
			
			//check if the game is over
			if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y ) {
				//if it collied xy the same with nextMino
				gameOver=true;
			}
			
			
			
			

			currentMino.deactivating =false;
			
			//replace crrMino with nextMino
			currentMino = nextMino;
			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino = pickMino();
			nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
			
			//when mino become deactivate , check if line can be delete 
			checkdelete();
		}else {
		
		currentMino.update();
		
		}
			
	}
	
	private void checkdelete() {
		
		int x = left_x;
		int y= top_y;
		int blockCount = 0 ;
		int lineCount= 0;
		
		
		while ( x < right_x && y< bottom_y) {
			
			for(int i =0;i< staticBlocks.size();i++) {
				if(staticBlocks.get(i).x == x && staticBlocks.get(i).y==y) {
					//increase the count if there if there is a static Block
					blockCount++;
			}
		}
			
			x+= Block.SIZE;
			
			if(x == right_x) {
				if(blockCount ==12) {
					
					eco =true;
					effectY.add(y);
					
					
					
					for(int i = staticBlocks.size()-1 ; i>-1 ; i--) {
						//remove all the blocks int in the current y line 
						if(staticBlocks.get(i).y==y) {
							staticBlocks.remove(i);
						}
				}
					lineCount++;
					lines++;
					//drop speed
					if(lines % 10==0 && dropInterval> 1) {
						
						level++;
						if(dropInterval> 10) {
							dropInterval -=10;
						}
						else {
							dropInterval -=1;
						}
					}
					
					
					
					
				// a slide above line down	
					for(int i =0 ; i< staticBlocks.size() ; i++) {
						if(staticBlocks.get(i).y<y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
				
					}}
				blockCount =0;
				x= left_x;
				y+= Block.SIZE;
			}
			}
		// add Score 
		if(lineCount >0) {
			int SingleLineScore =10 * level;
			score += SingleLineScore * lineCount;
		}
		
		
		
		
}
	
	
	public void draw(Graphics2D g2) {
		
		
		//draw mainPlayARrea
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
	
		
		int x = right_x +100;
		int y= bottom_y -200;
	
		g2.drawRect(x, y, 200, 200);
		g2.setFont(new Font("Ariel", Font.PLAIN,30));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT :" , x+60,y+60);
		
		
		
		// ScoreBoard
		g2.drawRect(x, top_y, 250, 300);
		x+=40;
		y= top_y +90;
		g2.drawString("LEVEL "+ level, x, y);y+=70;
		g2.drawString("LINES "+ lines, x, y);y+=70;
		g2.drawString("SCORE "+ score, x, y);

		
	
		//draw the currentMino
	if(currentMino!= null) {
		currentMino.draw( g2);
		
	}
	//draw next Mino
	nextMino.draw(g2);
	 
	// draw static Blocks 
	for(int i= 0 ; i<staticBlocks.size();i++) {
		staticBlocks.get(i).draw(g2);
	}
	//draw effect 
	if(eco) {
		ec++;
		g2.setColor(Color.black);
		for(int i=0 ;i< effectY.size();i++) {
			g2.fillRect(left_x, effectY.get(i), WIDTH,Block.SIZE);
		}
		if(ec == 10 ) {
			eco =false;
			ec =0;
			effectY.clear();
		}
		
		
		
	}
	
	//draw pause
	g2.setColor(Color.pink);
	g2.setFont(g2.getFont().deriveFont(50f));
	if(gameOver) {
		x= left_x +25;
		y = top_y +320;
		
		g2.drawString("GAME OVER", x, y);
		
	}
	else if(KeyHandler.pausePressed) {
	x= left_x +70;
	y= top_y +320;
	g2.drawString("Chờ xíu !!!", x, y);
	}
	
	x =35 ;
	y = top_y +320;
	g2.setColor(Color.white);
	g2.setFont(new Font("Ariel" , Font.ITALIC , 60));
	g2.drawString("Super Bros", x, y);
	
	
	
	}
	
	
}
