import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Iterator;

public class State implements Cloneable {
    public static final boolean JOUEUR_B = true;
    public static final boolean JOUEUR_R = false;

    private HashSet<State> ensPlayedStates;
    private TreeSet<Case> ensOccupedCases;

    // true = passed / false = played
    private boolean[] last2MovePassed;

    // true = blue / false = red
    private boolean tour;

    private int[] scores;

    public State() {
        // Initialisation des attributs
        this.tour = true;
        this.ensPlayedStates = new HashSet<>();
        this.ensOccupedCases = new TreeSet<Case>();
        this.scores = new int[] { 2, 2 };
        this.last2MovePassed = new boolean[2];

        // Placement des pions initiaux Bleues
        this.ensOccupedCases.add(new Case(true, 0, 6));
        this.ensOccupedCases.add(new Case(true, 6, 0));

        // Placement des pions initiaux Rouges
        this.ensOccupedCases.add(new Case(false, 0, 0));
        this.ensOccupedCases.add(new Case(false, 6, 6));

        // Ajout de du state dans ensPlayedStates
        State s = (State) this.clone();
        this.ensPlayedStates.add(s);
    }

    public HashSet<State> getEnsPlayedStates() {
        return this.ensPlayedStates;
    }

    public TreeSet<Case> getEnsembleOccupedCases() {
        return this.ensOccupedCases;
    }

    public boolean getTour() {
        return this.tour;
    }

    public int getPoints(boolean joueur) {
        if (joueur)
            return this.scores[0];
        else
            return this.scores[1];
    }

    public void setPoints(boolean joueur, int newScore) {
        if (joueur)
            this.scores[0] = newScore;
        else
            this.scores[1] = newScore;
    }

    public double getScore(boolean joueur) {
        double res;

        if (joueur) {
            res = (double) this.scores[0] / (this.scores[0] + this.scores[1]) * 100;
            return res;
        }

        res = (double) this.scores[1] / (this.scores[0] + this.scores[1]) * 100;
        return res;
    }

    public Set<Move> getMoves(boolean joueur) {
        Set<Move> moves = new HashSet<Move>();

        for (Case c : this.ensOccupedCases) {
            if (c.getValeur() == joueur) {
                for (int[] mask : Case.range1masks) {
                    Case tempCase = new Case(c.getValeur(), c.getCoords()[0] + mask[0], c.getCoords()[1] + mask[1]);
                    if (!this.ensOccupedCases.contains(tempCase)
                            && tempCase.getCoords()[0] >= 0 && tempCase.getCoords()[0] <= 6
                            && tempCase.getCoords()[1] >= 0 && tempCase.getCoords()[1] <= 6) {
                        moves.add(new Move('C', c, tempCase));
                    }
                }

                for (int[] mask : Case.range2masks) {
                    Case tempCase = new Case(joueur, c.getCoords()[0] + mask[0], c.getCoords()[1] + mask[1]);
                    if (!this.ensOccupedCases.contains(tempCase)
                            && tempCase.getCoords()[0] >= 0 && tempCase.getCoords()[0] <= 6
                            && tempCase.getCoords()[1] >= 0 && tempCase.getCoords()[1] <= 6) {
                        moves.add(new Move('J', c, tempCase));
                    }
                }
            }

        }
        moves.add(new Move());

        return moves;
    }

    public boolean isOver() {
        // 1) ou 2) tour passé ?
        if (this.last2MovePassed[0] && this.last2MovePassed[1]) {
            return true;
        }

        // 1) ou 2) plus de pion
        if (this.getPoints(true) == 0 || this.getPoints(false) == 0) {
            return true;
        }
        /*
         * //situation déjà arrivée
         * for(State s : this.ensPlayedStates)
         * if(s.getEnsembleOccupedCases().equals(this.ensOccupedCases) &&
         * !this.last2MovePassed[0] && !this.last2MovePassed[1])
         * {
         * return true;
         * }
         */

        return false;
    }

    public State play(Move m) {
        State s;
        this.last2MovePassed[1] = this.last2MovePassed[0];

        // si le tour est passé
        if (m.getMoveType() == 'P')
            this.last2MovePassed[0] = true;
        else {
            this.last2MovePassed[0] = false;

            // si le joueur jump
            if (m.getMoveType() == 'J') {
                // enlever case avant de ensembleOccupedCase
                this.ensOccupedCases.remove(m.getCaseBefore());
            }

            if (m.getMoveType() == 'C') {
                this.ensPlayedStates.clear();
                this.setPoints(m.getCaseBefore().getValeur(), this.getPoints(m.getCaseBefore().getValeur()) + 1);
            }

            // ajouter case apres ensembleoccupedcase
            // System.err.println(this.ensOccupedCases);
            this.ensOccupedCases.add(m.getCaseAfter());

            // capturer case à coté et augmenter le score
            int[] coordsAfter = m.getCaseAfter().getCoords();
            for (int[] mask : Case.range1masks) {
                Case tempCase = new Case(coordsAfter[0] + mask[0], coordsAfter[1] + mask[1]);
                if (this.ensOccupedCases.contains(tempCase)
                        && this.ensOccupedCases.ceiling(tempCase).getValeur() == !m.getCaseBefore().getValeur()
                        && tempCase.getCoords()[0] >= 0 && tempCase.getCoords()[1] >= 0) {
                    this.setPoints(this.ensOccupedCases.ceiling(tempCase).getValeur(),
                            this.getPoints(this.ensOccupedCases.ceiling(tempCase).getValeur()) - 1);
                    this.ensOccupedCases.ceiling(tempCase).setValeur(m.getCaseAfter().getValeur());
                    this.setPoints(this.ensOccupedCases.ceiling(tempCase).getValeur(),
                            this.getPoints(this.ensOccupedCases.ceiling(tempCase).getValeur()) + 1);
                }
            }
            s = (State) this.clone();
            this.ensPlayedStates.add(s);
        }
        this.tour = !this.tour;

        // retourne une copie de l'objet
        s = (State) this.clone();
        return s;
    }

    @Override
    public Object clone() {
        State o = null;

        try {
            o = (State) super.clone();

            o.ensPlayedStates = new HashSet<>();
            for (State s : this.ensPlayedStates)
                o.ensPlayedStates.add((State) s.clone());

            o.ensOccupedCases = new TreeSet<>();
            for (Case c : this.ensOccupedCases)
                o.ensOccupedCases.add((Case) c.clone());

            o.last2MovePassed = (boolean[]) last2MovePassed.clone();
            o.scores = (int[]) scores.clone();
        } catch (Exception e) {
            System.out.println(e);
        }

        return o;
    }

    @Override
    public boolean equals(Object o) {
        State s = (State) o;

        if (this.ensOccupedCases.equals(s.getEnsembleOccupedCases()))
            return true;

        return false;
    }

    @Override
    public String toString() {
        String res = "Tour joue : " + !this.tour + "\n";
        res += "B:" + this.scores[0] + "\tR:" + this.scores[1] + "\n";
        res += this.getScore(true) + "%\t" + this.getScore(false) + "%\n";

        Iterator<Case> iterCase = this.ensOccupedCases.iterator();
        Case c = iterCase.next();

        if (System.getProperty("os.name").split(" ")[0] == "Linux") {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (c.getCoords()[0] == j && c.getCoords()[1] == i) {
                        res += c;
                        if (iterCase.hasNext())
                            c = iterCase.next();
                    } else {
                        res += " ";
                    }
                }
                res += "\n";
            }
            res += "\n";
        } else {
            for (int i = 0; i < 7; i++) {
                res += "|";
                for (int j = 0; j < 7; j++) {
                    if (c.getCoords()[0] == j && c.getCoords()[1] == i) {
                        res += c;
                        if (iterCase.hasNext())
                            c = iterCase.next();
                    } else {
                        res += " ";
                    }
                    res += "|";
                }
                res += "\n";
            }
            res += "\n";
        }

        return res;
    }
}