package Mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle  {

	
	public int x,y;
	public static final int SIZE =30;//30x30 block
	public Color c ;
	
	public Block(Color c) {
		this.c=c;
	}
	public void draw (Graphics2D g2 ) {
		int margine =1;
		g2.setColor(c);
		g2.fillRect(x+margine, y+margine, SIZE-(2*margine), SIZE-(2*margine));
	}
	}





