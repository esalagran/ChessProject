package Domain;

public class Usuari{
    String nickname;

    FitxaProblema MourePeça(TipusPeça a, int b){
        ParInt pos = new ParInt(b, b);
        return new FitxaProblema(a,pos,true);

    }

    Partida IniciarPartida(Usuari u1, Usuari u2, Modalitat m){

        return new Partida();
    }






}
