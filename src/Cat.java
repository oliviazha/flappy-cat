/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 1.0, Apr 2019
 * @author oliviazha
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cat {
	
	//start cat at middle of panel
    private int px = GameCourt.COURT_WIDTH/2 - 20;
    private int py;
    private int lift;
    private double gravity = 2;
    private boolean isInvincible = false;
    private int courtWidth;
    private int courtHeight;
    private int currScore;

    /* Size of object, in pixels. */
    public final int SIZE = 20;

    /* Velocity: number of pixels to move every time jump() is called. */
    private int vy;
	
    private Image img;
    
    public Cat(int courtWidth, int courtHeight) {
    	this.courtWidth = courtWidth;
    	this.courtHeight = courtHeight;
    	py = GameCourt.COURT_HEIGHT/2;
    	vy = 0;
    	lift = 10;
        
        try {
            img = ImageIO.read(new File("files/nyan.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method makes the cat "jump" after an action (space) is performed by player
     */
    public void jump() {
		if (vy > 0)
		{
			vy = 0;
		}
		
    	vy -= lift;
    	py += vy;	
    }
    
    /**
     * This method updates the cat's vy and py to account for gravity effect
     */
    public void update() {
	    vy += gravity;
    	py += vy;	
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, px, py, 75, 60, null);
    }
    
    /**
     * This method checks if the cat has intersected a column
     */
    public boolean intersects(Rectangle col) {
        return (this.px + this.SIZE >= col.x
            && this.py + this.SIZE >= col.y
            && col.x + col.width >= this.px 
            && col.y + col.height >= this.py);
    }
    
    /**
     * This method checks if the cat has intersected a game object
     */
    public boolean intersects(GameObj c) {
        return (this.px + this.SIZE >= c.getPx()
            && this.py + this.SIZE >= c.getPy()
            && c.getPx() + c.getWidth() >= this.px 
            && c.getPy() + c.getHeight() >= this.py);
    }
    
    /**
     * checking state of game over
     */
    public boolean isGameOver(Rectangle rect) {
    	 if (py < 0 || py > courtHeight - 100) {
    		 return true;
    	 }
    	 if (this.intersects(rect)) {
					return true;
    	 } return false;
    }
    
    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public boolean getInvincibility() {
        return this.isInvincible;
    }
    
    public int getCurrScore() {
        return this.currScore;
    }
    

    /*** SETTERS **********************************************************************************/

    public void setPy(int py) {
        this.py = py;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    public void switchInvincible() {
        this.isInvincible = !this.getInvincibility();
    }

    public void setCurrScore(int newScore) {
        this.currScore = newScore;
    }
}
