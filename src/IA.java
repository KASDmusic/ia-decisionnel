public abstract class IA 
{
    protected static int nbVisitedNodes = 0;

    protected boolean joueur;
    protected int deepness;

    public IA(boolean joueur, int deepness)
    {
        this.joueur = joueur;
        this.deepness = deepness;
    }

    public static int getNbVisitedNodes() { return MinMax.nbVisitedNodes; }
    public static void clearNbVisitedNodes() { MinMax.nbVisitedNodes = 0; }

    public boolean getJoueur() { return this.joueur; }
    public int getDeepness() { return this.deepness; }

    public abstract Move getBestMove(State s);

}
