package Domain;

/** classe no rellevant en aquesta entrega*/
public class Tema {
    Color col;
    int movimentsFinsMat;

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
}