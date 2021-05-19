
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 1.0, Apr 2019
 * @author oliviazha
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Leaderboard {
    
    private final File leaderboard;
    
    public Leaderboard() {
    	leaderboard = new File("files/LEADERBOARD.TXT");
    }

    /**
     * Gets current leaderboard read in from the "LEADERBOARD.TXT" file
     */
    public ArrayList<String> getLeaderboard() {
        try { 
            FileReader f = new FileReader(leaderboard);
            BufferedReader r = new BufferedReader(f);
            ArrayList<String> list = new ArrayList<String>();
            String toRead;
            while ((toRead = r.readLine()) != null) { // While there are still lines to read
                list.add(toRead);
            }
            r.close();
            return list;
        } catch (IOException e) {
            System.out.println("getLeaderBoard game error");
            return new ArrayList<String>();
        } 
    }
    
    /**
     * makes arraylist of string entries
     */
	  public String makeBigString(ArrayList<String> leaderEntries) {
		String leadersText = "";
		if (leaderEntries!= null) {
	    	for (int i = 0; i < leaderEntries.size(); i++) {
	    		if (i < 10) { //only add first 10 to string
	    		leadersText = leadersText + (i+1) + ".       " + leaderEntries.get(i) + "<br>";
	    		}
	    	}
		}
	    return leadersText;
	}
    /**
     * gets scores on the leaderboard to compare scores
     */
    public ArrayList<Integer> getLeaderScores(ArrayList<String> highscores) {
       ArrayList<Integer> scores = new ArrayList<Integer>();
       if (highscores != null) {
	       for (String players : highscores) {
	           Integer score = Integer.parseInt(players.substring(players.indexOf(':') + 2));
	           scores.add(score);     
	       }
       }
       return scores;
    }

    /*** GETTERS **********************************************************************************/
    public File getFile() {
        return leaderboard;
    }
    
}