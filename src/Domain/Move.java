package Domain;

public class Move {

    /// <summary>
    /// Ctor
    /// </summary>
    /// <param name="eOriginalPiece">   Piece which has been eaten if any</param>
    /// <param name="iStartPos">        Starting position</param>
    /// <param name="iEndPos">          Ending position</param>
    /// <param name="eType">            Move type</param>
    public Move(FitxaProblema eOriginalPiece, ParInt iStartPos, ParInt iEndPos) {
        OriginalPiece   = eOriginalPiece;
        StartPos        = iStartPos;
        EndPos          = iEndPos;
    }

    /// <summary>Original piece if a piece has been eaten</summary>
    private FitxaProblema OriginalPiece;
    /// <summary>Start position of the move (0-63)</summary>
    private ParInt StartPos;
    /// <summary>End position of the move (0-63)</summary>
    private ParInt EndPos;
    /// <summary>Type of move</summary>
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