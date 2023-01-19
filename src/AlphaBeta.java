public class AlphaBeta extends IA {
    public AlphaBeta(boolean joueur, int deepness) {
        super(joueur, deepness);
    }

    public Move getBestMove(State s) {
        Move bestAction = new Move();
        double bestValue = -1;
        double alpha = -1;
        double beta = 101;

        s = (State) s.clone();

        for (Move action : s.getMoves(s.getTour())) {
            State nextState = (State) s.clone();
            double value = alphaBeta(nextState.play(action), alpha, beta, this.deepness - 1);
            MinMax.nbVisitedNodes++;

            if (value > bestValue) {
                bestValue = value;
                alpha = value;
                bestAction = action;
            }
            // System.out.println(action.toString() + " : " + value);
        }
        // System.out.println("tt" + bestAction.toString() + " : " + bestValue + "\n");
        return bestAction;
    }

    public double alphaBeta(State s, double alpha, double beta, int deepness) {
        if (deepness == 0 || s.isOver())
            return s.getScore(!s.getTour());
        else {
            if (this.joueur == s.getTour()) {
                for (Move m : s.getMoves(s.getTour())) {
                    MinMax.nbVisitedNodes++;
                    State newState = (State) s.clone();
                    alpha = Math.max(alpha, alphaBeta(newState.play(m), alpha, beta, deepness - 1));
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
                return alpha;
            } else {
                for (Move m : s.getMoves(s.getTour())) {
                    MinMax.nbVisitedNodes++;
                    State newState = (State) s.clone();
                    beta = Math.min(beta, alphaBeta(newState.play(m), alpha, beta, deepness - 1));
                    if (alpha >= beta) {
                        return beta;
                    }
                }
                return beta;
            }
        }
    }
}
