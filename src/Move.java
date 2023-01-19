public class Move
{
    //'C' = clone | 'J' = jump | 'P' = pass
    private char moveType;

    private Case caseBefore;
    private Case caseAfter;

    public Move()
    {
        this.moveType = 'P';
    }

    public Move(char moveType, Case caseBefore, Case caseAfter)
    {
        this.moveType = moveType;
        this.caseBefore = caseBefore;
        this.caseAfter = caseAfter;
    }

    public char getMoveType() { return this.moveType; }
    public Case getCaseBefore() { return this.caseBefore; }
    public Case getCaseAfter() { return this.caseAfter; }

    public String toString()
    {
        String res = this.moveType + "";

        if(this.caseBefore == null)
            return res;

        res+=" ";
        res+=this.caseBefore.getValeur();
        res+="(" + this.caseBefore.getCoords()[0] + "," + this.caseBefore.getCoords()[1] + ")";
        res+=" ";
        res+="(" + this.caseAfter.getCoords()[0] + "," + this.caseAfter.getCoords()[1] + ")";

        return res;
    }
}