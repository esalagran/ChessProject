package Domain;

public class VectMov {
    private int[] _maxMov; //maxim que es pot moure en horitzontal, vertical i diagonal

    public VectMov( int h, int v, int d){
        _maxMov = new int[3];
        _maxMov[0] = h;
        _maxMov[1] = v;
        _maxMov[2] = d;
    }

    /**
     * \pre
     * \post Es retorna els moviments màxims en horitzontal*/
    public int getH() {return _maxMov[0];}

    /**
     * \pre
     * \post Es retorna els moviments màxims en vertical*/
    public int getV() {return _maxMov[1];}

    /**
     * \pre
     * \post Es retorna els moviments màxims en diagonal*/
    public int getD() {return _maxMov[2];}
}
