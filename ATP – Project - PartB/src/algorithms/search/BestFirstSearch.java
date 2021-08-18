package algorithms.search;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstSearch extends BreadthFirstSearch {
    /**
     * Best SOLVER
     * @param searcher - The problem we want to solve
     * @return - The solution of the problem
     */
    @Override
    public Solution solve(ISearchable searcher) {
        if (searcher == null)
            return new Solution();

        PriorityQueue<AState> queue = new PriorityQueue<>(AState::compareTo);
        queue.add(searcher.getStartState());

        return SolveProblem(queue, searcher);
    }
}
