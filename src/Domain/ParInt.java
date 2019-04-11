package Domain;

public class ParInt {

    private int x, y;

    public ParInt(){
        x = 0;
        y = 0;
    }

    public ParInt(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int GetFirst(){
        return x;
    }

    public void SetFirst(int x){
        this.x = x;
    }

    public int GetSecond(){
        return y;
    }

    public void SetSecond(int y){
        this.y = y;
    }
}