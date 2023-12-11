package code;

import java.lang.Math;

public class Node {
    State state;
    Node parentNode;
    ActionEnum operator;
    int depth;
    double cost;
    double heuristic;
    double heuristic2;
    double AS;
    double AS2;
    double CostMin;
    double CostMax;
    double ProsperityMax;
    String avoidRepeat;

    public Node(State state, Node parentNode, ActionEnum operator, int depth) {
        this.state = state;
        this.parentNode = parentNode;
        this.operator = operator;
        this.depth = depth;
        CostMin = Math.min(state.getUnitPriceEnergy() + state.getUnitPriceFood() + state.getUnitPriceMaterials(),
                Math.min(state.getPriceBUILD1(), state.getPriceBUILD2()));
        CostMax = Math.max(state.getUnitPriceEnergy() + state.getUnitPriceFood() + state.getUnitPriceMaterials(),
                Math.max(state.getPriceBUILD1(), state.getPriceBUILD2()));
        ProsperityMax = Math.max(state.getProsperityBUILD1(), state.getProsperityBUILD2());
        ;
        switch (operator) {
            case RequestFood:
            case RequestMaterials:
            case RequestEnergy:
            case WAIT:
                cost = ((state.getUnitPriceEnergy() + state.getUnitPriceFood() + state.getUnitPriceMaterials())
                        - CostMin) / (CostMax - CostMin);
                heuristic = 1.1;
                heuristic2 = (100 - state.getInitialProsperity()  / ProsperityMax);
                break;

            case BUILD1:
                cost = (state.getPriceBUILD1() - CostMin) / (CostMax - CostMin);
                if ((state.getProsperityBUILD1() + state.getInitialProsperity()) >= 100) {
                    heuristic = 0;
                    heuristic2 = 0;
                } else {
                    heuristic = 1.1 - ((state.getProsperityBUILD1()) / ProsperityMax);
                    heuristic2 = (100 -(state.getInitialProsperity() + state.getProsperityBUILD1())  / ProsperityMax);
                }

                break;

            case BUILD2:
                cost = (state.getPriceBUILD2() - CostMin) / (CostMax - CostMin);
                if ((state.getProsperityBUILD2() + state.getInitialProsperity()) >= 100) {
                    heuristic = 0;
                    heuristic2 = 0;
                } else {
                    heuristic = 1.1 - (state.getProsperityBUILD2() / ProsperityMax);
                    heuristic2 = (100 -(state.getInitialProsperity() + state.getProsperityBUILD2())  / ProsperityMax);
                }

                break;

            default:
                break;
        }
        AS = cost + heuristic;
        AS2 = cost + heuristic2;

        
    }

    public double getHeuristic() {
        return heuristic;
    }
     public double getHeuristic2() {
        return heuristic2;
    }
     public int getDepth() {
        return depth;
    }
    public double getAS() {
        return AS;
    }
    public double getAS2() {
        return AS2;
    }
   
    public void setAvoidRepeat(String x){
        avoidRepeat = x;
    }
}
