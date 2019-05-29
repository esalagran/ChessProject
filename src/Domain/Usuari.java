package Domain;


/**Classe no rellevant per aquesta entrega*/

public abstract class Usuari{
    String _nickname;

    FitxaProblema MourePeça(TipusPeça a, int b){
        ParInt pos = new ParInt(b, b);
        return new FitxaProblema(a,pos,Color.negre);

    }

    public String GetNickName(){
        return _nickname;
    }

    public ParInt GetRankingProblema(int idProblema){
        return new ParInt(0,0);
    }

    public Usuari (String nickname){
        _nickname = nickname;
    }







}
