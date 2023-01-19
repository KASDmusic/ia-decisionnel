
public class MinMax extends IA {
    public MinMax(boolean joueur, int deepness) {
        super(joueur, deepness);
    }

    public Move getBestMove(State s) {
        Move bestAction = new Move();
        double bestValue = -1;

        s = (State) s.clone();

        for (Move action : s.getMoves(s.getTour())) {
            State nextState = (State) s.clone();
            double value = minMax(nextState.play(action), this.deepness - 1);
            MinMax.nbVisitedNodes++;

            if (value > bestValue) {
                bestValue = value;
                bestAction = action;
            }
            // System.out.println(action.toString() + " : " + value);
        }
        // System.out.println("tt" + bestAction.toString() + " : " + bestValue + "\n");
        return bestAction;
    }

    public double minMax(State s, int deepness) {
        double value, newValue;

        if (deepness == 0 || s.isOver())
            return s.getScore(!s.getTour());
        else {
            if (this.joueur == s.getTour()) {
                value = -1000;
                for (Move m : s.getMoves(s.getTour())) {
                    MinMax.nbVisitedNodes++;
                    State newState = (State) s.clone();
                    newValue = minMax(newState.play(m), deepness - 1);
                    if (value < newValue) {
                        value = newValue;
                    }
                }
            } else {
                value = 1000;
                for (Move m : s.getMoves(s.getTour())) {
                    MinMax.nbVisitedNodes++;
                    State newState = (State) s.clone();
                    newValue = minMax(newState.play(m), deepness - 1);
                    if (value > newValue) {
                        value = newValue;
                    }
                }
            }
        }
        return value;
    }
}
