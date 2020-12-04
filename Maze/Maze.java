package hw4;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    
    //Returns true if a path is found; if not, then returns boolean statement false.
    public boolean findMazePath(int x, int y) {
    	if (x < 0 || y < 0 || x >= maze.getNCols() || y >= maze.getNRows()) {
    		return false; 
		}
    	else if (!maze.getColor(x, y).equals(NON_BACKGROUND)){	
			return false; 
		}
		else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1){
			maze.recolor(x, y, PATH); 
			return true; 
		}
		else { 
			maze.recolor(x, y, PATH);
			if (findMazePath(x - 1, y) || findMazePath(x + 1, y) || findMazePath(x, y - 1) || findMazePath(x, y + 1))
			{
				return true;
			}
			else {
				maze.recolor(x, y, TEMPORARY); 
				return false;
			}
		}
	}

    // ADD METHOD FOR PROBLEM 2 HERE
    //List of all solutions to the maze should be returned as a set of coordinates.
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
    	ArrayList <ArrayList <PairInt>> result = new ArrayList<>();
		if(!findMazePath(x,y)) {
			maze.recolor(PATH, NON_BACKGROUND);
			maze.recolor(TEMPORARY, NON_BACKGROUND);
			ArrayList<PairInt> list = new ArrayList<PairInt>();
			result.add(list);
			
		} 
		else {
			maze.recolor(PATH, NON_BACKGROUND);
			maze.recolor(TEMPORARY, NON_BACKGROUND);
    		Stack<PairInt> trace = new Stack<>(); 
			findMazePathStackBased(x, y, result, trace);
		}
	return result;
    }
    
    //Helper method for findAllMazePaths.
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
		PairInt p = new PairInt(x,y);
		PairInt nP = new PairInt(x,y);
		ArrayList<PairInt> list = new ArrayList<PairInt>();
	
		if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
			maze.recolor(x, y, PATH);
			trace.push(p);
			list.addAll(trace);
			result.add(list);
			trace.pop();
		} 
		
		else if (x>=maze.getNCols() || y>=maze.getNRows() || x<0 || y<0 || maze.getColor(x,y) != NON_BACKGROUND) {
			return;
		} 
		
		else {
			maze.recolor(x,y, PATH);
			trace.push(nP); 
   			findMazePathStackBased(x+1, y, result, trace);
   			findMazePathStackBased(x, y+1, result, trace);
   			findMazePathStackBased(x-1, y, result, trace);
   			findMazePathStackBased(x, y-1, result, trace);
   			maze.recolor(x, y, NON_BACKGROUND);
   			trace.pop();
		}
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    //Returns shortest path of all paths found
    public ArrayList<PairInt> findMazePathMin(int x, int y){
    	maze.recolor(PATH, NON_BACKGROUND);
    	ArrayList<ArrayList<PairInt>> lst = findAllMazePaths(x,y);
    	if(lst.size() != 0){
	    	ArrayList<PairInt> min = lst.get(0);
	    	int minLnth = min.size();
	    	for(int i=1; i<lst.size(); i++){
	    		if(minLnth >= lst.get(i).size()){
	    			min = lst.get(i);
	    			minLnth = min.size();
	    		}
	    	}
	    	return min;
    	}
    	else{
    		return new ArrayList<PairInt>();
    	}
    }

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/