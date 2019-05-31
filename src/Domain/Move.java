package Domain;

public class Move {
    
    public Move(FitxaProblema eOriginalPiece, ParInt iStartPos, ParInt iEndPos) {
        OriginalPiece   = eOriginalPiece;
        StartPos        = iStartPos;
        EndPos          = iEndPos;
    }

    private FitxaProblema OriginalPiece;
    private ParInt StartPos;
    private ParInt EndPos;
    private boolean Type;

    public ParInt getStartPos() {
        return StartPos;
    }

    public ParInt getEndPos() {
        return EndPos;
    }

    public FitxaProblema getOriginalPiece() {
        return OriginalPiece;
    }

    public void setOriginalPiece(FitxaProblema originalPiece) {
        OriginalPiece = originalPiece;
    }

    public void setStartPos(ParInt p){ StartPos = p; }

    public void setEndPos(ParInt endPos) { EndPos = endPos; }
}