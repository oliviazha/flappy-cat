import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.Test;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    // Game constants
    public static final int COURT_WIDTH = 700;
    public static final int COURT_HEIGHT = 700;
    
    @Test
    public void testJump() {
        Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=350
        c.jump(); 
        assertEquals(-10, c.getVy());
        assertEquals(340, c.getPy());
    }
    
    @Test
    public void testUpdate() {
        Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=350
        c.update(); 
        assertEquals(2, c.getVy());
        assertEquals(352, c.getPy());
    }

    @Test
	  public void testSetCurrScore() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      c.setCurrScore(10);
	      assertEquals(10, c.getCurrScore());
	  }
    
    @Test
	  public void testGameOverIntersection() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      c.jump();
	      Rectangle r = new Rectangle(GameCourt.COURT_WIDTH/2 - 20, 0, 50, 345);
	      assertTrue(c.intersects(r));
	      assertTrue(c.isGameOver(r));
	  }
    
    @Test
	  public void testGameOverOutOfBounds() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      for (int i=0; i<10; i++) {
	    	  c.jump(); //10 jumps would make cat go out of bounds
	      }
    	  System.out.println(c.getPy());
	      Rectangle r = new Rectangle(GameCourt.COURT_WIDTH/2, 0, 50, 300); //rect not at same x
	      assertTrue(c.isGameOver(r));
	  }
    
    @Test
	  public void testInvincibleChangesCat() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      for (int i=0; i<4; i++) {
	    	  c.jump();
	      }
	      Potion potion = new Potion(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH/2 - 20);
	      potion.changeState(c);
	      assertTrue(c.getInvincibility());
	  }
    
    @Test
	  public void testInvincibleIncreasesScore() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      for (int i=0; i<4; i++) {
	    	  c.jump();
	      }
	      int score = c.getCurrScore();
	      Potion potion = new Potion(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH/2 - 20);
	      potion.changeState(c);
	      assertTrue(c.getCurrScore() > score);
	  }
    
    @Test
 	  public void testPoisonDecreasesScore() {
 	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
 	      for (int i=0; i<4; i++) {
 	    	  c.jump();
 	      }
 	      int score = c.getCurrScore();
 	      Poison p = new Poison(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH/2 - 20);
 	      p.changeState(c);
 	      assertTrue(c.getCurrScore() < score);
 	  }
    
    @Test
	  public void testSwitchInvincible() {
	      Cat c = new Cat(COURT_WIDTH,COURT_HEIGHT); //cat starts at py=330
	      c.switchInvincible();
	      assertTrue(c.getInvincibility());
	      c.switchInvincible();
	      assertFalse(c.getInvincibility());
	  }
    
}
