package Domain;

public class VectMov {
    private int[] _maxMov = new int[3]; //maxim que es pot moure en horitzontal, vertical i diagonal

    public VectMov( int [] MaxMov){

        if (_maxMov.length != 3){
            throw new ArrayIndexOutOfBoundsException("Hi ha 3 direccions(horitzontal, vertical, diagonal");
        }
        for (int i:MaxMov) {
            if (i < 0 || i > 7)
                throw new ArithmeticException("El valor ha de ser entre 0 i 7");
        }
        _maxMov = MaxMov;
    }
}
