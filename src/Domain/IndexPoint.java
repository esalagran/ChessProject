package Domain;

public class IndexPoint {

    public int     iIndex;
    public int     iPoints;

    public int CompareTo(IndexPoint Other) {
        int iRetVal;
        if (iPoints < Other.iPoints) {
            iRetVal = 1;
        } else if (iPoints > Other.iPoints) {
            iRetVal = -1;
        } else {
            iRetVal = (iIndex < Other.iIndex) ? -1 : 1;
        }
        return(iRetVal);
    }

    public IndexPoint(int Points, int Index){
        iIndex = Index;
        iPoints =Points;
    }

    public int getiIndex() {
        return iIndex;
    }
}
