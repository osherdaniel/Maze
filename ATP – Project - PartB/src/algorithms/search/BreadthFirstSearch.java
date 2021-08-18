package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    /**
     * BFS SOLVER
     * @param searcher - The problem we want to solve
     * @return - The solution of the problem
     */
    @Override
    public Solution solve(ISearchable searcher) {
        if (searcher == null)
            return new Solution();

        Queue<AState> queue = new LinkedList<>();
        queue.add(searcher.getStartState());

        return SolveProblem(queue, searcher);
    }

    /**
     * Ezer Function
     * @param queue
     * @param searcher - The problem we want to solve
     * @return - The solution of the problem
     */
    public Solution SolveProblem(Queue<AState> queue, ISearchable searcher){
        Solution solution = new Solution();

        HashSet<String> Visited = new HashSet<>();
        Visited.add(searcher.getStartState().toString());

        ArrayList<AState> Neighbors;

        while (!queue.isEmpty()) {
            AState curr = queue.poll();
            if (curr.equals(searcher.getGoalState())) {
                solution = finalSol(curr);
                return solution;
            }
            numberOfNodes = numberOfNodes + 1;
            Neighbors = searcher.getAllPossibleStates(curr);
            for (int i = 0; i < Neighbors.size(); i++) {
                AState currState = Neighbors.get(i);
                int newCost = curr.getCost() + currState.getCost();
                if (!Visited.contains(currState.toString())) {
                    currState.setCameFrom(curr);
                    Visited.add(currState.toString());
                    currState.setCost(newCost);
                    queue.add(currState);
                }
            }
        }
        return solution;
    }
}
