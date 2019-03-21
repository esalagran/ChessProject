package Domain;

public class VectMov {
    private int[] _maxMov = new int[3]; //Màxim que es pot mour en horitzontal, vertical i diagonal

    public VectMov(ParInt[] Coordenades, int [] MaxMov){

        if (_maxMov.length != 3){
            throw new ArrayIndexOutOfBoundsException("Els MaxMov han de tenir 3 paràmetres");
        }
        _maxMov = MaxMov;
    }
}
