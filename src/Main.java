
public class Main {
    public Main(int profondeurTrue, int profondeurFalse, boolean isAlphaBeta) {
        if (isAlphaBeta) {
            Main.testAlphaBeta(profondeurTrue, profondeurFalse);
        } else {
            Main.testMinMax(profondeurTrue, profondeurFalse);
        }
    }

    public static void testMinMax(int profondeurTrue, int profondeurFalse) {
        State s = new State();
        MinMax minMaxTrue = new MinMax(true, profondeurTrue);
        MinMax minMaxFalse = new MinMax(false, profondeurFalse);
        Move m;

        System.out.println(s);

        m = minMaxTrue.getBestMove(s);
        s = s.play(m);

        System.out.println(m);
        System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
        System.out.println(s);

        MinMax.clearNbVisitedNodes();

        while (!s.isOver()) {
            m = minMaxFalse.getBestMove(s);
            s = s.play(m);

            System.out.println(m);
            System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
            System.out.println(s);

            MinMax.clearNbVisitedNodes();

            if (!s.isOver()) {
                m = minMaxTrue.getBestMove(s);
                s = s.play(m);

                System.out.println(m);
                System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
                System.out.println(s);

                MinMax.clearNbVisitedNodes();
            }
        }
    }

    public static void testAlphaBeta(int profondeurTrue, int profondeurFalse) {
        State s = new State();
        AlphaBeta alphaBetaTrue = new AlphaBeta(true, profondeurTrue);
        AlphaBeta alphaBetaFalse = new AlphaBeta(false, profondeurFalse);
        Move m;

        System.out.println(s);

        m = alphaBetaTrue.getBestMove(s);
        s.play(m);

        System.out.println(m);
        System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
        System.out.println(s);

        MinMax.clearNbVisitedNodes();

        while (!s.isOver()) {
            m = alphaBetaFalse.getBestMove(s);
            s.play(m);

            System.out.println(m);
            System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
            System.out.println(s);

            MinMax.clearNbVisitedNodes();

            if (!s.isOver()) {
                m = alphaBetaTrue.getBestMove(s);
                s.play(m);

                System.out.println(m);
                System.out.println("nbVisitedNodes : " + MinMax.getNbVisitedNodes());
                System.out.println(s);

                MinMax.clearNbVisitedNodes();
            }
        }
    }

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            System.err.println("Erreur dans les arguments !");
        }

        // Main.testMinMax(1, 1);
        Main.testAlphaBeta(3, 3);
    }
}