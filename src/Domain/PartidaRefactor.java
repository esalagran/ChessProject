package Domain;

import static Domain.Convert.*;

public abstract class PartidaRefactor {

    private Problema problemaEnJoc;
    private Color torn;
    private int moviments;
    private Color guanyador;
    private EstatPartida estatPartida;

    public PartidaRefactor(Problema p){
        torn = p.GetTorn();
        problemaEnJoc = p;
        moviments = 0;
        guanyador = null;
        estatPartida = EstatPartida.jugant;
    }

    public FitxaProblema[][] MovimentHuma(ParInt origen, ParInt desti){ return null; }

    public Object[] MovimentMaquina(){ return null; }

    public abstract ParInt[] GetLastMove();

    public Problema getProblemaEnJoc(){
        return problemaEnJoc;
    }

    boolean FiTorn(){
        torn = InvertColor(torn);
        if (estatPartida.equals(EstatPartida.jugant)){
            if (problemaEnJoc.getTauler().IsMate(torn)) {
                estatPartida = EstatPartida.mate;
            } else if (problemaEnJoc.getTauler().IsTaules(torn))
                estatPartida = EstatPartida.taules;
            else if (moviments++ >= problemaEnJoc.GetMovimentsPerGuanyar())
                 estatPartida = EstatPartida.jugadesSuperades;
        }
        if (!estatPartida.equals(EstatPartida.jugant)){
            guanyador = estatPartida.equals(EstatPartida.mate)? problemaEnJoc.GetTorn() :
                    InvertColor(problemaEnJoc.GetTorn());
            return true;
        }
        return false;
    }

    public boolean hasEnded() {
        return guanyador != null;
    }

    public Color getGuanyador() {
        return guanyador;
    }

    public Color getTorn() {
        return torn;
    }

    public Color GetColorAt(ParInt coord){
        return problemaEnJoc.getTauler().FitxaAt(coord).GetColor();
    }

    public void setEstatPartida(Move move) {
        if (move == null)
            estatPartida = EstatPartida.mate;
        else if (move.getEndPos() == null)
            estatPartida = EstatPartida.taules;
        else if (move.getStartPos() == null)
            estatPartida = EstatPartida.jugadesSuperades;
        else{
            estatPartida = EstatPartida.jugant;
        }
    }

    public EstatPartida getEstatPartida() {
        return estatPartida;
    }

    public int getMoviments() {
        return moviments;
    }
}
