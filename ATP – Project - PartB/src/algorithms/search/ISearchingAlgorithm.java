package algorithms.search;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable searcher);
    String getName();
    int getNumberOfNodesEvaluated();
    Solution finalSol(AState state);
}
