/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 1.0, Apr 2019
 * @author oliviazha
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
    // Game constants
    public static final int COURT_WIDTH = 700;
    public static final int COURT_HEIGHT = 700;

    private Cat cat;
    private ArrayList<Rectangle> columns; 
    private ArrayList<Potion> potions; 
    private ArrayList<Poison> poisons; 
    
    // the state of the game logic
    private int highScore;
    private boolean playing; // whether the game is running 
    private boolean gameOver;
    private JLabel status; // Current status text, i.e. "Running..."
    private String currPlayer;
    private static Leaderboard leaders;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public int timeSteps = 0;
	public int speed = 10;
	public int invincibleTime = 0;

    public GameCourt(JLabel status, Leaderboard leaders) {
    	this.leaders = leaders;
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        
		//ask for name at start
	    JPanel myPanel = new JPanel(new GridLayout(3, 1, 0, 1));
	    myPanel.add(new Label("Insert a player nickname: "));     
	    currPlayer = JOptionPane.showInputDialog(myPanel);

        // This key listener sets state of game
        //(The tick method below actually moves the
        // cat.)
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	 if(e.getKeyCode() == KeyEvent.VK_SPACE) {
         			if (!playing && !gameOver) //if wasn't playing but now starting game
         			{ 
         				playing = true;
         			}
         			else if (gameOver) //if game was over but playing again
        			{
        				reset();
        				playing = true;
        			}
         			else if (!gameOver) { //if playing, then makes cat jump
         				cat.jump();
         			}
                 }
            }

            public void keyPressed(KeyEvent e) {

            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        cat = new Cat(COURT_WIDTH, COURT_HEIGHT);
        columns = new ArrayList<Rectangle>();
        potions = new ArrayList<Potion>();
        poisons = new ArrayList<Poison>();
        cat.setCurrScore(0);
        timeSteps = 0;
        
        gameOver = false;
        status.setText("Running...");
        
		addColumn();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
   }

    /**
     * This method adds a column to the screen
     */
    public void addColumn()
	{
		int bottomHeight = (int) (150 + (Math.random()*250)); 
		int spaceAbove = 170;
		int width = 55; 

		columns.add(new Rectangle(COURT_WIDTH, COURT_HEIGHT - bottomHeight, width, bottomHeight));
		columns.add(new Rectangle(COURT_WIDTH, 0, width, COURT_HEIGHT - bottomHeight - spaceAbove));
	}
    
    /**
     * This method adds a potion to the screen
     */
    public void addCoin()
	{
		potions.add(new Potion(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH - 20));
	}
    
    /**
     * This method adds a poison to the screen
     */
    public void addPoison()
	{
		poisons.add(new Poison(COURT_WIDTH, COURT_HEIGHT, COURT_WIDTH - 20));
	}
    
    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
        	timeSteps++; //count number of time steps
            speed = 40; //speed if cat is invincible
        	if (!cat.getInvincibility()) {
        		speed = 10;
        		//every so many time steps, add another coin/pois
             	if (timeSteps % 213 == 0) {
            		addCoin();
            	}
             	if (timeSteps % 111 == 0) {
            		addPoison();
            	}
    			cat.update(); //make cat move
    			
             	//check if cat touches coin
    	        for (Potion potion : potions) {
    				if (cat.intersects(potion)) {
    					potion.changeState(cat);
    					System.out.println("cat state:" + cat.getInvincibility());
    				} 
    			}
    	        
            	//check if cat touches poison
    	        for (Poison pois : poisons) {
    				if (cat.intersects(pois)) {
    					pois.changeState(cat);
//    					poisons.remove(pois); 
    				} 
    			}
    	        
    			//every 20 time steps, add another column and update score
            	if (timeSteps % 20 == 0) {
            		addColumn();
            		if (timeSteps > 30) {
            			cat.setCurrScore(cat.getCurrScore()+1);
            		}
            	}
    	        
    	        //check for the game end conditions

            	for (Rectangle r: columns) {
	    	        if (cat.isGameOver(r)) {
	    				if (cat.getCurrScore() > highScore) {
	    					highScore = cat.getCurrScore();
	    					endGame(highScore, currPlayer);
	    					System.out.println("gameOver potions size:" + potions.size());
	    				}
	    				else { //end game but don't write to file
	    					playing = false;
	    					gameOver = true;
	    					status.setText("You scored: " + cat.getCurrScore());
	    				}
	    			}   
            	}
            	
        	}
        	if (cat.getInvincibility()) {
        		invincibleTime++;
        		if (invincibleTime > 20) { //invincible only for 20 timesteps
            		cat.switchInvincible();
            		invincibleTime = 0;
            	}
        	}
        	
        	//set x position of rectangle or gameobj to move left on screen
	        for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x = column.x - speed;
			} 
	        for (int i = 0; i < potions.size(); i++) {
				Potion potion = potions.get(i);
				potion.move();
			}
	        for (int i = 0; i < poisons.size(); i++) {
				Poison pois = poisons.get(i);
				pois.move();
			}
           
            //for each column and game object, if out of screen, remove it from list
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);
				if (column.x < -30)
				{
					columns.remove(column);
				}
			}
			for (int i = 0; i < potions.size(); i++)
			{
				Potion potion = potions.get(i);
				if (potion.getPx() < -10)
				{
					potions.remove(potion);
				}
			}
			for (int i = 0; i < poisons.size(); i++)
			{
				Poison pois = poisons.get(i);
				if (pois.getPx() < -10)
				{
					poisons.remove(pois);
				}
			}
			
        	
        }

        // update the display
        repaint();
    }
    
    /**
     * Helper to end the game
     */
    public void endGame(int score, String play) {
		playing = false;
		gameOver = true;
	    ArrayList<String> allLeaders = leaders.getLeaderboard();
	    ArrayList<Integer> scores = leaders.getLeaderScores(allLeaders);
	    if (scores.size() < 10 || (cat.getCurrScore() > scores.get(scores.size() - 1))) { //if it is high score
	    	int i = getLeaderboardIndex(cat.getCurrScore(), scores);
	        editLeaderboard(cat.getCurrScore(), currPlayer, allLeaders, i);
	        status.setText("You scored: " + cat.getCurrScore());
	    } 
    }
    
    /** 
     * Finds the index in list to insert the new score into if is high score
     */
    public static int getLeaderboardIndex(int score, ArrayList<Integer> scores) {
        for(int i = 0; i < scores.size(); i++) { // Checks the first 9 elements
            if (score > scores.get(i)) {
                return i; 
            } 	
        }
        return scores.size(); //if less than 10 entries and not greater than any, then index is size
    }
      
    /**
     * Changes the new LEADERBOARD.TXT
     */
    public static void editLeaderboard(int score, String player, ArrayList<String> entries, int index) {
        String newest = player + ": " + score + '\n';
        
        try {
	        BufferedWriter f = new BufferedWriter(new FileWriter(leaders.getFile()));
	        if (entries.size() == 0) { //if first entry
	            f.write(newest); 
	            f.close(); 
	        } 
	        else { //else there exists prev entries, then first write prev up until index
		        for (int i = 0; i < index; i++) {
		            f.write(entries.get(i) + '\n');
		        }
		        f.write(newest);
		        for (int i = index; i < entries.size(); i++) { //continue writing prev
		        	f.write(entries.get(i) + '\n'); 
		        }
		        f.close(); 
	        }
        }
        catch (IOException e) {
        	System.out.println("edit leaderboard error");
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);    
        //draw background
        Color lightBlue = new Color(51,220,255);
        g.setColor(lightBlue);
		g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
		
		//draw rectangles
        for (Rectangle column : columns) {
        	Color darkGreen = new Color(0,130,0);
			g.setColor(darkGreen);
			g.fillRect(column.x, column.y, column.width, column.height);
        }
        
		//draw coins
        for (Potion potion : potions) {
			potion.draw(g);
        } 
        
        //draw poisons
        for (Poison pois : poisons) {
			pois.draw(g);
        } 
        
        //draw background details
		g.setColor(Color.orange);
		g.fillRect(0, COURT_HEIGHT - 100, COURT_WIDTH, 100);
        
        if (cat.getInvincibility()) {
        	for (int i=0; i<5; i++) {
    			g.setColor(Color.RED);
    			g.fillRect(0, cat.getPy()+15, 350, 4);
    			g.setColor(Color.ORANGE);
    			g.fillRect(0, cat.getPy()+19, 350, 4);
    			g.setColor(Color.YELLOW);
    			g.fillRect(0, cat.getPy()+23, 350, 4);
    			g.setColor(Color.GREEN);
    			g.fillRect(0, cat.getPy()+27, 350, 4);
    			g.setColor(Color.BLUE);
    			g.fillRect(0, cat.getPy()+31, 350, 4);
    			Color purple = new Color(102, 0, 153);
    			g.setColor(purple);
    			g.fillRect(0, cat.getPy()+35, 350, 4);
            }
        }
        cat.draw(g);
        
		//draw text
        g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 40));
		if (!playing && !gameOver)
		{
			g.drawString("Press [SPACE] to start!", 140, COURT_HEIGHT / 2 - 50);
		}

		if (gameOver)
		{
			g.drawString("GAME OVER", 220, COURT_HEIGHT / 2 - 50);
			g.drawString("Press [SPACE] to play again!", 90, COURT_HEIGHT / 2);
		}

		if (playing && !gameOver)
		{
			g.drawString(String.valueOf(cat.getCurrScore()), COURT_WIDTH / 2 - 25, 100);
		}
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    /*** GETTERS **********************************************************************************/
    public int getHighScore() {
        return highScore;
    }
   
    
    public String getPlayer() {
        return currPlayer;
    }
    
    public boolean getPlaying() {
        return playing;
    }
    
    public boolean getGameOver() {
        return gameOver;
    }
    
    public Cat getCat() {
        return cat;
    }
}