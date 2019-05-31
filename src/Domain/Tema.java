package Domain;

public class Tema {
    private Color col;
    private int movimentsFinsMat;

    public Tema(int n, Color c){
        col = c;
        movimentsFinsMat = n;
    }

    public Color getCol() {
        return col;
    }

    public int getMovimentsFinsMat() {
        return movimentsFinsMat;
    }

    public void setMovimentsFinsMat(int movimentsFinsMat) {
        this.movimentsFinsMat = movimentsFinsMat;
    }
}