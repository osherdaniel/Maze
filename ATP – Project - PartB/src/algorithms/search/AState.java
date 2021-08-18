package algorithms.search;

public abstract class AState implements Comparable<AState>{
    private int cost;
    private AState cameFrom;

    public AState() {
        this.cost = 10;
        this.cameFrom = null;
    }

    @Override
    public int compareTo(AState o) {
        if (o == null)
            return -2;

        AState a = (AState)o;
        if(this.getCost() > a.getCost())
            return 1;
        else if(this.getCost() < a.getCost())
            return -1;
        return 0;
    }

    public AState(int cost, AState cameFrom) {
        this.cost = cost;
        if(cameFrom != null)
            this.cameFrom = cameFrom;
        else
            this.cameFrom = null;
    }

    /**
     * Set a new Cost
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Set a new CameFrom
     * @param cameFrom
     */
    public void setCameFrom(AState cameFrom) {
        if(cameFrom != null)
            this.cameFrom = cameFrom;
        else
            this.cameFrom = null;
    }

    /**
     * Return the current Cost
     * @return - the current Cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Return the current cameFrom
     * @return - the current cameFrom
     */
    public AState getCameFrom() {
        return cameFrom;
    }

    public abstract  String toString();
}
