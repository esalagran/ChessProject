package Drivers;

import Domain.Color;
import Domain.Problema;
import Domain.Tema;
import Persistence.CtrlPersistence;

import java.util.List;
import java.util.Scanner;

public class DriverCtrlPersistance {

    private static CtrlPersistence a;
    static Scanner sc = new Scanner(System.in);

    public static void start(int cas) {
            switch (cas) {
                case 1:
                    Problema aux = new Problema("2B5/2p1B1nn/2PR2p1/1K2k3/1p2p3/2b1P3/5R2/8",new Tema(4, Color.blanc), true, "");
                    aux.inscriureRanking("Joan",200);
                    aux.inscriureRanking("fff",203);
                    aux.inscriureRanking("fluf",202);
                    a.guardarProblema(aux.GetFEN(), aux.GetValid(), aux.GetMovimentsPerGuanyar(), aux.GetTorn().toString(), aux.GetCreador());
                    aux = new Problema("3K4/4B3/3Rp3/8/4pk2/1Qp1Np2/2p2P2/2R5",new Tema(3, Color.blanc), true, "");
                    aux.inscriureRanking("Joan",5764);
                    aux.inscriureRanking("fff",457);
                    aux.inscriureRanking("fluf",24574);
                    a.guardarProblema(aux.GetFEN(), aux.GetValid(), aux.GetMovimentsPerGuanyar(), aux.GetTorn().toString(), aux.GetCreador());
                    aux = new Problema("8/5K2/5p2/5Qbk/8/8/5P2/8",new Tema(7, Color.negre), false, "");
                    a.guardarProblema(aux.GetFEN(), aux.GetValid(), aux.GetMovimentsPerGuanyar(), aux.GetTorn().toString(), aux.GetCreador());
                    break;
                case 2:
                    a.eliminarProblema("3K4/4B3/3Rp3/8/4pk2/1Qp1Np2/2p2P2/2R5");
                    break;
                case 3:
                    List<Object[]> fuck = a.GetProblemes();
                    for (Object[] p : fuck){
                        System.out.println(p[0].toString() + '\n');
                    }
                    break;
                case 4:
                    a.eliminarJugadorProblema("2B5/2p1B1nn/2PR2p1/1K2k3/1p2p3/2b1P3/5R2/8", "fff");
                    break;
                case 5:
                    a.afegirJugadorProblema("3K4/4B3/3Rp3/8/4pk2/1Qp1Np2/2p2P2/2R5","wuuuut", 25000);
                    a.afegirJugadorProblema("3K4/4B3/3Rp3/8/4pk2/1Qp1Np2/2p2P2/2R5","wuuuut2", 25);
                    break;
                case 10:
                    System.out.println("Bye Bye");
                    break;
                default:
                    System.out.println("Input a correct option");
            }
    }

    public static void main(String[] args) {
        int num;
        do{
            System.out.print( "1. Guardar problema" + '\n' +
                    "2. Eliminar problema" + '\n' +
                    "3. Carregar problemes" + '\n' +
                    "4. Eliminar jugador problema" + '\n' +
                    "5. Afegeix jugador problema" + '\n' +
                    "Input a new number"
                    );
            num = sc.nextInt();
            a = new CtrlPersistence();
            start(num);
        }while(num != 0);
    }
}
