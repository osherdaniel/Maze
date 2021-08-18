package algorithms.search;
import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    int numberOfNodes = 0;

    /**
     * Solve The problem
     * @param searcher
     * @return - The solution of the problem
     */
    @Override
    public Solution solve(ISearchable searcher) {
        return null;
    }

    /**
     * Return the name of the search Algorithm
     * @return - the name of the class
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * Return the length of the solution
     * @return - The length of the solution
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodes;
    }

    /**
     * Get the all solution path
     * @param state - the last state
     * @return - the solution path
     */
    @Override
    public Solution finalSol(AState state) {
        Solution solution = new Solution();
        if(state != null) {
            Stack<AState> stack = new Stack<AState>();
            stack.push(state);
            while (state.getCameFrom() != null) {
                stack.push(state.getCameFrom());
                state = state.getCameFrom();
            }
            while(!stack.isEmpty())
                solution.setSolution(stack.pop());
        }
        return solution;
    }
}
