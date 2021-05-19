/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 1.0, Apr 2019
 * @author oliviazha
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Poison extends GameObj {

    public static int py = 300;
    
    /* Size of object, in pixels. */
    public static final int SIZE = 40;

	
    private Image img;
    
    public Poison(int courtWidth, int courtHeight, int px) {
    	super(px, py, SIZE, SIZE, courtWidth, courtHeight);
        
        try {
            img = ImageIO.read(new File("files/poison.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, this.getPx(), py, SIZE, SIZE, null);
    }
    
    public void changeState(Cat c) {
    	c.setCurrScore(c.getCurrScore() - 2);
    }
}
