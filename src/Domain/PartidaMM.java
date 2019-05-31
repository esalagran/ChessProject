package Domain;

public class PartidaMM  extends PartidaRefactor{

    private Algorithm a1;
    private Algorithm a2;
    private long timeA1, timeA2;
    private int moveA1, moveA2;

    public PartidaMM(Problema p, Usuari u1, Usuari u2) {
        super(p);
        a1 = (Algorithm) u1;
        a2 = (Algorithm) u2;
        timeA1 = timeA2 = 0;
        moveA1 = moveA2 = 0;
    }

    @Override
    public Object[] MovimentMaquina() {
        boolean hasEnded = false;
        boolean isA1Torn = true;
        Problema p = getProblemaEnJoc();
        Tauler t = p.getTauler();
        Color torn = p.GetTorn();
        while (!hasEnded){
            Move m;
            if (isA1Torn){
                long iniTime = System.currentTimeMillis();
                m = a1.FindBestMoveConcr(t, torn, p.GetMovimentsPerGuanyar() - getMoviments());
                if (m != null && m.getStartPos() != null && m.getEndPos() != null) {
                    timeA1 += System.currentTimeMillis() - iniTime;
                    t.moureFitxa(m);
                    ++moveA1;
                }
            }
            else{
                long iniTime = System.currentTimeMillis();
                m = a2.FindBestMoveConcr(t, torn, p.GetMovimentsPerGuanyar() - getMoviments());
                if (m != null && m.getStartPos() != null && m.getEndPos() != null){
                    timeA2 += System.currentTimeMillis() - iniTime;
                    t.moureFitxa(m);
                    ++moveA2;
                }
            }
            setEstatPartida(m);
            hasEnded = FiTorn();
            isA1Torn = !isA1Torn;
        }
        if (moveA2 == 0) moveA2 = 1;
        if (moveA1 == 0) moveA1 = 1;
        return new Object[]{
                getGuanyador(), timeA1/moveA1, timeA2/moveA2
        };
    }

    @Override
    public ParInt[] GetLastMove() {
        return null;
    }
}
