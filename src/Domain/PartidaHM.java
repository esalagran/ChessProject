package Domain;

public class PartidaHM extends PartidaRefactor{
    private Huma h1;
    private Algorithm a2;
    private ParInt[] lastMove;
    private int movH1;
    private long tempsH1;
    private Color tornH1;
    private boolean isWinnerHuman;



    public PartidaHM(Problema p, Usuari u1, Usuari u2){
        super(p);
        if (u1 instanceof Huma){
            h1 = (Huma)u1;
            a2 = (Algorithm)u2;
            isWinnerHuman = true;
        }
        else{
            h1 = (Huma)u2;
            a2 = (Algorithm)u1;
            isWinnerHuman = false;
        }
        movH1 = 0;
        tempsH1 = 0;
        tornH1 = p.GetTorn();
    }

    @Override
    public FitxaProblema[][] MovimentHuma(ParInt origen, ParInt desti) {
        long tempsIni = System.currentTimeMillis();
        Color torn = getTorn();
        Object[] objects = h1.MourePeça(getProblemaEnJoc(), origen, desti, torn);
        lastMove = ((ParInt[]) objects[0]);
        if (lastMove != null) {
            if (torn == tornH1) {
                tempsH1 += System.currentTimeMillis() - tempsIni;
                ++movH1;
            }

            if (super.FiTorn()) {
                InscriureRanking();
            }
        }

        return ((FitxaProblema[][])objects[1]);
    }

    @Override
    public FitxaProblema[][] MovimentMaquina() {
        Problema p = getProblemaEnJoc();
        Tauler t = p.getTauler();
        Move m = a2.FindBestMoveConcr(t, getTorn(), p.GetMovimentsPerGuanyar() - getMoviments());
        if (m != null) {
            lastMove = new ParInt[]{m.getStartPos(), m.getEndPos()};
            t.moureFitxa(m);
        }
        else
            lastMove = null;
        setEstatPartida(m);
        //if (getEstatPartida().equals(EstatPartida.jugant))
          //  t.moureFitxa(m);

        if (super.FiTorn()){
            InscriureRanking();
        }
        return t.getTaulell();
    }

    @Override
    public ParInt[] GetLastMove() {
        return lastMove;
    }

    private void InscriureRanking(){
        Problema p = super.getProblemaEnJoc();
        //l'huma ha guanyat si el teoric jugador es huma i ha fet mate o si no es el teoric guanyador i
        //S'han superat el nombre màxim de jugades permeses
        if ((isWinnerHuman && super.getGuanyador().equals(p.GetTorn())) ||
                !isWinnerHuman && !getGuanyador().equals(p.GetTorn())){
            if (tempsH1 > 0)
                p.inscriureRanking(h1.GetNickName(),p.calculPuntuacio(movH1, (int)tempsH1));
            else
                p.inscriureRanking(h1.GetNickName(),p.calculPuntuacio(1, (int)tempsH1));
        }
        else {
            if (movH1 > 0)
                p.inscriureRanking(h1.GetNickName(),(int)tempsH1/movH1);
            else
                p.inscriureRanking(h1.GetNickName(),(int)tempsH1);
        }

    }

    public boolean isWinnerHuman() {
        return isWinnerHuman;
    }
}
