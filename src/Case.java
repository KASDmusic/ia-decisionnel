
public class Case implements Comparable<Case>, Cloneable
{
    public static final int[][] range1masks = new int[][] {
        {-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}
    };
    
    public static final int[][] range2masks = new int[][]{
        {-2,-2},{0,-2},{2,-2},{-2,0},{2,0},{-2,2},{0,2},{2,2}
    };

    private Boolean valeur;
    private int[] coords;

    public Case(int x, int y)
    {
        this.valeur = null;
        this.coords = new int[]{x,y};
    }

    public Case(Boolean valeur, int x, int y)
    {
        this.valeur = valeur;
        this.coords = new int[]{x,y};
    }

    public Boolean getValeur() { return this.valeur; }
    public int[] getCoords() { return this.coords; }

    public void setValeur(Boolean bool) { this.valeur = bool; }

    @Override
    public int compareTo(Case c)
    {
        int idThis = this.coords[1] * 7 + this.coords[0];
        int idC = c.getCoords()[1] * 7 + c.getCoords()[0];

        if(idThis == idC)
            return 0;

        if(idThis > idC)
            return 1;

        return -1;
    }

    @Override
    public boolean equals(Object o)
    {
        Case c = (Case)o;

        int idThis = this.coords[1] * 7 + this.coords[0];
        int idC = c.getCoords()[1] * 7 + c.getCoords()[0];

        if(idThis == idC)
            return true;
            
        return false;
    }

    @Override
    public Object clone()
    {
        Case c = null;

        try
        {
            c = (Case)super.clone();
            c.valeur = new Boolean(this.valeur);
            c.coords = this.coords.clone();
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

        return c;
    }

    @Override
    public String toString()
    {
        if(System.getProperty("os.name").split(" ")[0] == "Linux")
        {
            if(this.valeur != null)
            {
                if(this.valeur == true)
                    return "\uD83D\uDFE5";

                return "\uD83D\uDFE6";
            }
            return "\u2B1C";
        }
        else
        {
            if(this.valeur != null)
            {
                if(this.valeur == true)
                    return "B";

                return "R";
            }
            return " ";
        }
        
    }
}