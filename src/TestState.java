import java.util.Set;
import java.util.TreeSet;

public class TestState {
    public TestState() {
    }

    public void testInit(String arg) {
        State s = new State();
        TreeSet<Case> ensCase = new TreeSet<>();

        ensCase.add(new Case(false, 0, 0));
        ensCase.add(new Case(false, 6, 6));

        ensCase.add(new Case(true, 6, 0));
        ensCase.add(new Case(true, 0, 6));

        System.out.print("Test initiation state : ");
        System.out.println(ensCase.equals(s.getEnsembleOccupedCases()));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);
    }

    public void testGetMoves(String arg) {
        State s = new State();
        Set<Move> ensMoves = s.getMoves(true);

        // mode verbose
        if (arg.toLowerCase().equals("-v")) {
            System.out.println(s);

            for (Move m : ensMoves)
                System.out.println(m);
        }

        System.out.println("Bon nombre de moves presents : " + (boolean) (ensMoves.size() == 13));
    }

    public void testPawnMoved(String arg) {
        State s = new State();
        TreeSet<Case> ensCase = new TreeSet<>();

        ensCase.add(new Case(false, 0, 0));
        ensCase.add(new Case(false, 0, 1));
        ensCase.add(new Case(false, 6, 6));

        ensCase.add(new Case(true, 6, 0));
        ensCase.add(new Case(true, 2, 6));

        s.play(new Move('J', new Case(true, 0, 6), new Case(true, 2, 6)));
        s.play(new Move('C', new Case(false, 0, 0), new Case(false, 0, 1)));
        s.play(new Move());

        System.out.println("3 types de mouvements bien realises : " + s.getEnsembleOccupedCases().equals(ensCase));

        // mode verbose
        if (arg.toLowerCase().equals("-v")) {
            System.out.println(s);
            System.out.println(ensCase);
        }
    }

    public void testPawnTaken(String arg) {
        State s = new State();
        TreeSet<Case> ensCase = new TreeSet<>();

        ensCase.add(new Case(false, 5, 0));
        ensCase.add(new Case(false, 6, 0));

        ensCase.add(new Case(false, 0, 4));
        ensCase.add(new Case(false, 0, 5));
        ensCase.add(new Case(false, 0, 6));

        ensCase.add(new Case(false, 6, 6));

        // test prise apres jump
        s.play(new Move('C', new Case(false, 0, 0), new Case(false, 1, 0)));
        s.play(new Move('J', new Case(false, 1, 0), new Case(false, 3, 0)));
        s.play(new Move('J', new Case(false, 3, 0), new Case(false, 5, 0)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        // test prise apres clone
        s.play(new Move('J', new Case(false, 0, 0), new Case(false, 0, 2)));
        s.play(new Move('J', new Case(false, 0, 2), new Case(false, 0, 4)));
        s.play(new Move('C', new Case(false, 0, 4), new Case(false, 0, 5)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        System.out
                .println("Pions pris avec les 2 types de deplacement : " + s.getEnsembleOccupedCases().equals(ensCase));
    }

    public void testIsOver2MovePassed(String arg) {
        State s = new State();
        TreeSet<Case> ensCase = new TreeSet<>();

        ensCase.add(new Case(false, 0, 0));
        ensCase.add(new Case(false, 6, 6));

        ensCase.add(new Case(true, 6, 0));
        ensCase.add(new Case(true, 0, 6));

        boolean bool = true;
        for (int i = 0; i < 2; i++) {
            s.play(new Move());

            bool = bool && s.getEnsembleOccupedCases().equals(ensCase);

            // mode verbose
            if (arg.toLowerCase().equals("-v")) {
                System.out.println("Tour " + (int) (i + 1) + " correctement passe : " + bool);
                System.out.println(s);
            }
        }
        System.out.println("Condition last2MovePassed de isOver() bien remplie : " + s.isOver());
    }

    public void testIsOverNoMorePawns(String arg) {
        State s = new State();

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        // test prise apres jump
        s.play(new Move('C', new Case(false, 0, 0), new Case(false, 1, 0)));
        s.play(new Move('J', new Case(false, 1, 0), new Case(false, 3, 0)));
        s.play(new Move('J', new Case(false, 3, 0), new Case(false, 5, 0)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        // test prise apres clone
        s.play(new Move('J', new Case(false, 0, 0), new Case(false, 0, 2)));
        s.play(new Move('J', new Case(false, 0, 2), new Case(false, 0, 4)));
        s.play(new Move('C', new Case(false, 0, 4), new Case(false, 0, 5)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        System.out.println("Condition noMorePawn de isOver() bien remplie : " + s.isOver());
    }

    public void testIsOverStateAlreadyHappened(String arg) {
        State s = new State();

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        s.play(new Move('J', new Case(true, 0, 6), new Case(true, 0, 4)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        s.play(new Move('J', new Case(false, 0, 0), new Case(false, 0, 2)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        s.play(new Move('J', new Case(true, 0, 4), new Case(true, 0, 6)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        s.play(new Move('J', new Case(false, 0, 2), new Case(false, 0, 0)));

        // mode verbose
        if (arg.toLowerCase().equals("-v"))
            System.out.println(s);

        System.out.println("Condition stateAlreadyHappened de isOver() bien remplie : " + s.isOver());
    }

    public void testRed() {
        State s = new State();
        s.play(new Move('C', new Case(true, 0, 6), new Case(true, 0, 5)));
        System.out.println(s);
    }

    public static void main(String[] args) {
        TestState testState = new TestState();
        String arg;

        if (args.length == 0)
            arg = " ";
        else
            arg = args[0];

        System.out.println("");

        testState.testInit(arg);
        testState.testGetMoves(arg);
        testState.testPawnMoved(arg);
        testState.testPawnTaken(arg);
        testState.testIsOver2MovePassed(arg);
        testState.testIsOverNoMorePawns(arg);
        testState.testIsOverStateAlreadyHappened(arg);
        testState.testRed();

    }
}
