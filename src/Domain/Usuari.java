package Domain;

public class Usuari{
    String nickname;

    FitxaProblema MourePeça(String a, int b){
        ParInt pos = new ParInt(b, b);
        return new FitxaProblema(a,pos,true);

    }

    Partida IniciarPartida(String a, String b, int c){

        return new Partida();
    }






}
