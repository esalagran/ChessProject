package Domain;

public class Possibilitats {
    ParInt[] moviments;

    public void validMoves(FitxaProblema x) {
        VectMov[] vm = x.getIFitxa().GetMoviments();

        ParInt ini = x.GetCoordenades();
        ParInt newPos;
        ParInt move;

        boolean outLimits, PeçaMeva, PeçaRival = false;
        for (int i = 0; i < vm.length; i++){
            if (x.getIFitxa().getClass().getName() == "Torre") {

            }

        }

    }

    public Boolean safe(ParInt x) {
        return (x.GetFirst() >= 0 && x.GetFirst() < 8 && x.GetSecond() >= 0 && x.GetSecond() < 8 );
    }
}
