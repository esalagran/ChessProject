package Domain;


/**Classe no rellevant per aquesta entrega*/

public class Usuari{
    String _nickname;

    FitxaProblema MourePeça(TipusPeça a, int b){
        ParInt pos = new ParInt(b, b);
        return new FitxaProblema(a,pos,Color.negre);

    }

    Partida IniciarPartida(Usuari u1, Usuari u2, Problema p){
        return new Partida(u1, u2, p);
    }

    public String GetNickName(){
        return _nickname;
    }

    public ParInt GetRankingProblema(int idProblema){
        return new ParInt(0,0);
    }







}
