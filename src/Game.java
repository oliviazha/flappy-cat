/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 1.0, Apr 2019
 * @author oliviazha
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
    	
    	/**
         * Top level frame in which game components live
         */
        final JFrame frame = new JFrame("Flappy Cat");
        frame.setLocation(500, 500);
        
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        Leaderboard leaders = new Leaderboard();
        final GameCourt court = new GameCourt(status, leaders);
        frame.add(court, BorderLayout.CENTER);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        /**
         * Instructions frame
         */
        final JFrame instructions = new JFrame("Instructions");
        instructions.setLocation(500, 500);
        final JPanel instr = new JPanel();
        instr.setBackground(Color.WHITE);
        JLabel label = new JLabel("<html>"
                + "<br>"
                + "CIS 120 Final Project: <br>"
                + "Flappy Cat  <br><br><br>" 
                + "You are nyan cat, trying to fly as far as you can!      <br>"
                + "Avoid the columns and press the space bar to fly higher.       <br>"
                + "The game is over if you hit a column, hit the ground, or "
                + "fly out of the screen <br><br>"
                + "Magic Potions will make you invincible for a short period.      <br>"
                + "Poison will lower your score.      <br>"
                + "Good luck!      <br>"
                + "<br>" 
                + "      Press [SPACE] to start the game...      <br><br><br>"
                + "</html>");
        instr.add(label);
        instructions.add(instr, BorderLayout.CENTER);
        instructions.setBackground(Color.WHITE);

        // Put the frame on the screen
        instructions.pack();
        instructions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructions.setVisible(false);
        
        /**
         * Leaderboard frame
         */
        final JFrame leaderboard = new JFrame("High Scores");
        leaderboard.setMinimumSize(new Dimension(400,400));
        leaderboard.setLocation(500, 500);
        leaderboard.setBackground(Color.WHITE);
        
        final JPanel leadersPanel = new JPanel();
        leadersPanel.setBackground(Color.WHITE);

        leaderboard.pack();
        leaderboard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboard.setVisible(false);        
        
        
        // Leaderboard button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton highScores = new JButton("Leaderboard");
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ArrayList<String> l = leaders.getLeaderboard();
                 String allLeaders = leaders.makeBigString(l);

	               final JLabel displayLeaders = new JLabel("<html>" + "<br><br>"
	               + "Top 10 scores <br><br>"
	               + allLeaders
	               + "</html>");  

	             leadersPanel.removeAll();
                 leadersPanel.add(displayLeaders);
                 leaderboard.add(leadersPanel);
                 leaderboard.setVisible(true);
                 court.reset();
            }
        });
        control_panel.add(highScores);
        
        final JButton inst = new JButton("Instructions");
        inst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructions.setVisible(true);
                court.reset();
            }
        });
        control_panel.add(inst);
          
        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}