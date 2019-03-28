package Domain;

public class Maquina extends Usuari{

    protected int depth;
    protected int heuristica;      //Estimacio

    public Maquina(){}

    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    public int getDepth(){
        return depth;
    }

    void MourePeça(String a){

    }

    boolean validarProblema(int idP){
        return false;
    }

}