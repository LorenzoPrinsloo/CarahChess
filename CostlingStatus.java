//package root;

public class CostlingStatus {

    boolean hasKingCostling;
    boolean hasQueenCostling;

    public CostlingStatus(boolean kStatus, boolean qStatus) {
        hasKingCostling = kStatus;
        hasQueenCostling = qStatus;
    }

    public CostlingStatus() {
        hasKingCostling = true;
        hasQueenCostling = true;
    }

    public boolean isHasKingCostling() {
        return hasKingCostling;
    }

    public void setHasKingCostling(boolean hasKingCostling) {
        this.hasKingCostling = hasKingCostling;
    }

    public boolean isHasQueenCostling() {
        return hasQueenCostling;
    }

    public void setHasQueenCostling(boolean hasQueenCostling) {
        this.hasQueenCostling = hasQueenCostling;
    }
}
