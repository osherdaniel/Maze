package algorithms.search;

import java.util.ArrayList;
import java.io.Serializable;

public class Solution implements Serializable{
    private ArrayList<AState> Solution = null;

    public Solution() {
        Solution = new ArrayList<AState>();
    }

    /**
     * Add the state into the solution
     * @param state - Add the current state into the solution
     */
    public void setSolution(AState state) {
        if(state != null)
            Solution.add(state);
    }

    /**
     * Return the solution
     * @return - The solution path
     */
    public ArrayList<AState> getSolution() {
        return Solution;
    }

    /**
     * Return the Solution of the maze
     * @return - The solution path
     */
    public ArrayList<AState> getSolutionPath() {
        return Solution;
    }
}
