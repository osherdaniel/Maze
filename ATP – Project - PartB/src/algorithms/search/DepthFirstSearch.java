package algorithms.search;
import java.util.*;


public class DepthFirstSearch extends ASearchingAlgorithm {

    /**
     * DFS SOLVER
     * @param searcher - The problem we want to solve
     * @return - The solution of the problem
     */
    @Override
    public Solution solve(ISearchable searcher) {
        if(searcher == null)
            return new Solution();

        Stack<AState> stack = new Stack<AState>();
        stack.push(searcher.getStartState());

        HashSet<String> Visited = new HashSet<>();
        Visited.add(searcher.getStartState().toString());

        ArrayList<AState> Neighbors;

        Solution solution = new Solution();
        AState curr = searcher.getStartState();
        while(!stack.isEmpty()){
            if(curr.equals(searcher.getGoalState())){
                Visited.add(curr.toString());
                solution = finalSol(curr);
                numberOfNodes = Visited.size() - 1;
                return solution;
            }

            Neighbors = searcher.getAllPossibleStates(curr);
            int i = 0;
            for(i = 0; i < Neighbors.size(); i++){
                AState currState = Neighbors.get(i);
                if(!Visited.contains(currState.toString())){
                    stack.push(curr);
                    Visited.add(currState.toString());
                    currState.setCameFrom(curr);
                    curr = currState;
                    break;
                }

                if(currState.equals(searcher.getGoalState())){
                    currState.setCameFrom(curr);
                    Visited.add(curr.toString());
                    solution = finalSol(currState);
                    numberOfNodes = Visited.size() - 1;
                    return solution;
                }
            }

            if(Neighbors.size() == 0 || Neighbors.size() == i){
                if(!Visited.contains(curr.toString()))
                    Visited.add(curr.toString());
                if(!stack.isEmpty())
                    curr = stack.pop();
            }
        }
        numberOfNodes = Visited.size();
        return solution;
    }
}
