package Domain;

public class Usuari{
    String _nickname;

    FitxaProblema MourePeça(TipusPeça a, int b){
        ParInt pos = new ParInt(b, b);
        return new FitxaProblema(a,pos,true);

    }

    Partida IniciarPartida(Usuari u1, Usuari u2, Problema p){
        return new Partida(u1, u2, p);
    }

    public String GetNickName(){
        return _nickname;
    }







}
