package Domain;

public class PartidaHH extends PartidaRefactor{
    private Huma h1, h2;
    private ParInt[] lastMove;
    private int movH1, movH2;
    private long tempsH1, tempsH2;
    private Color tornH1;

    public PartidaHH(Problema p, Usuari u1, Usuari u2) {
        super(p);
        h1 = (Huma)u1;
        h2 = (Huma)u2;
        movH1 = movH2 = 0;
        tempsH2 = tempsH1 = 0;
        tornH1 = p.GetTorn();
    }

    @Override
    public FitxaProblema[][] MovimentHuma(ParInt origen, ParInt desti) {
        long tempsIni = System.currentTimeMillis();
        boolean isTornH1 = getTorn().equals(tornH1);
        Object[] objects;
        if (isTornH1)
            objects = h1.MourePeça(getProblemaEnJoc(),origen, desti, getTorn());
        else
            objects = h2.MourePeça(getProblemaEnJoc(),origen, desti, getTorn());

        if (objects[0] != null){
            lastMove = ((ParInt[]) objects[0]);
            if (isTornH1) {
                tempsH1 += System.currentTimeMillis() - tempsIni;
                ++movH1;
            } else {
                tempsH2 += System.currentTimeMillis() - tempsIni;
                ++movH2;
            }

            if (super.FiTorn()) {
                InscriureRanking();
            }
        }

        return ((FitxaProblema[][])objects[1]);
    }

    @Override
    public ParInt[] GetLastMove() {
        return lastMove;
    }

    private void InscriureRanking(){
        Problema p = super.getProblemaEnJoc();
        if (super.getGuanyador().equals(p.GetTorn())){
            p.inscriureRanking(h1.GetNickName(),p.calculPuntuacio(movH1, (int)tempsH1));
            if(movH2 == 0)
                movH2 = 1;
            if(movH1 == 0)
                movH1 = 1;
            p.inscriureRanking(h2.GetNickName(),(int)tempsH2/movH2);
        }
        else {
            if(movH1 == 0)
                movH1 = 1;
            if (movH2 == 0)
                movH2 = 1;
            p.inscriureRanking(h1.GetNickName(),(int)tempsH1/movH1);
            p.inscriureRanking(h2.GetNickName(),p.calculPuntuacio(movH2, (int)tempsH2));
        }
    }

}
