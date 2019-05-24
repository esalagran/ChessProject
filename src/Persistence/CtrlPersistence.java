package Persistence;
import Domain.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @class CtrlPersistence
 * \brief Classe encarregada de gestionar el .txt que actua com a base de dades
 *        Per cada problema s'encarregade guardar:
 *              - Codi FEN
 *              - Validesa (true/false)
 *              - Numero de passos fins el mat
 *              - Color del jugador que realitza el mat
 *              - Ranking
 */

public class CtrlPersistence {

    List<Problema> problemes = new ArrayList<>();

    public CtrlPersistence() {}

    public List<Problema> GetProblemes() {
        carregarProblemes();
        return problemes;
    }

    /**
     * Guarda el problema si no existeix a la base de dades o si existeix pero no es valid en la base de dades i si en
     * el parametre passat, altrament no fa res
     * @param p Problema a guardar
     */

    public void guardarProblema(Problema p) {
        try {
            if (!hiHaProblema(p.GetFEN())) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/problemes2.txt"), true));
                writer.append(p.GetFEN() + '\n' + p.GetValid() + '\n');                     //Guardo codi FEN i validesa
                writer.append(String.valueOf(p.getTema().getMovimentsFinsMat()) + '\n');    //Guardo numero moviments
                if (p.getTema().getCol() == Color.blanc) {
                    writer.append("blanc"+'\n');    //Guardo color blanc
                }
                else{
                    writer.append("negre"+'\n');    //Guardo color negre
                }
                writer.append("Fi\n");
                writer.close();
                return;
            }
            else if (hiHaProblema(p.GetFEN()) && p.GetValid()) {
                File input = new File("localData/problemes2.txt");
                BufferedReader reader = new BufferedReader(new FileReader(input));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(p.GetFEN())) {
                        currentLine = reader.readLine();
                        if (currentLine.contains("false")) {
                            eliminarProblema(p.GetFEN());
                            guardarProblema(p);
                        }
                    }
                }
                reader.close();
                return;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * Elimina un problema de la base de dades si existeix, altrement llença un missatge d'error
     * @param FEN Codi FEN del problema a eliminar
     */

    public void eliminarProblema(String FEN){
        try{
            if (hiHaProblema(FEN)){
                File temp = new File("localData/problemestemp.txt");
                File input = new File ("localData/problemes2.txt");
                BufferedReader reader = new BufferedReader(new FileReader(input));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp,true));
                String currentLine;
                while ((currentLine = reader.readLine()) != null){
                    if (currentLine.contains(FEN)){
                        while(!currentLine.contains("Fi")){
                            currentLine = reader.readLine();
                        }
                        currentLine = reader.readLine();
                    }
                    if (currentLine != null) writer.write(currentLine + '\n');
                }
                writer.close();
                reader.close();
                temp.renameTo(input);
            }
            else System.out.println ("No s'ha pogut eliminar el problema ja que no existeix a la base de dades");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean afegirJugadorProblema(String FEN, String nickname, int puntuacio){
        try{
            if (hiHaProblema(FEN)){
                File temp = new File("localData/problemestemp.txt");
                File input = new File ("localData/problemes2.txt");
                BufferedReader reader = new BufferedReader(new FileReader(input));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp,true));
                String currentLine;
                boolean trobat = false;
                while ((currentLine = reader.readLine()) != null){
                    if (currentLine.contains(FEN)){
                        writer.write(currentLine + '\n');
                        for (int i = 0; i < 3; i++){
                            currentLine = reader.readLine();
                            writer.write(currentLine + '\n');
                        }
                        while(!currentLine.contains("Fi")){
                            currentLine = reader.readLine();
                            String [] parts = currentLine.split("/");
                            if (!trobat && (currentLine.contains("Fi") || puntuacio > Integer.parseInt(parts[1]))) {
                                writer.write(nickname + '/' + puntuacio + '\n');
                                trobat = true;
                            }
                            writer.write(currentLine + '\n');
                        }
                        currentLine = reader.readLine();
                    }
                    if (currentLine != null) writer.write(currentLine + '\n');
                }
                writer.close();
                reader.close();
                temp.renameTo(input);
                return trobat;
            }
            else {
                System.out.println("No s'ha pogut eliminar el problema ja que no existeix a la base de dades");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarJugadorProblema(String FEN, String nickname){
        try{
            if (hiHaProblema(FEN)){
                File temp = new File("localData/problemestemp.txt");
                File input = new File ("localData/problemes2.txt");
                BufferedReader reader = new BufferedReader(new FileReader(input));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp,true));
                String currentLine;
                boolean trobat = false;
                while ((currentLine = reader.readLine()) != null){
                    if (currentLine.contains(FEN)){
                        writer.write(currentLine + '\n');
                        //currentLine = reader.readLine();
                        while(!currentLine.contains("Fi")){
                            currentLine = reader.readLine();
                            if (!currentLine.contains(nickname)) writer.write(currentLine + '\n');
                            if (currentLine.contains(nickname)) trobat = true;
                        }
                        currentLine = reader.readLine();
                    }
                    if (currentLine != null) writer.write(currentLine + '\n');
                }
                writer.close();
                reader.close();
                temp.renameTo(input);
                if (!trobat){
                    System.out.println("No existeix el jugador " + nickname + " per aquest problema");
                    return false;
                }
                return true;
            }
            else{
                System.out.println("No s'ha pogut eliminar el problema ja que no existeix a la base de dades");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void carregarProblemes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
            Problema aux;
            String fen;
            boolean valid;
            int pasos;
            Color color;
            String s = reader.readLine();
            while (s != null){
                fen = s;
                s = reader.readLine();
                if (s.equals("true")) valid = true;
                else valid = false;
                pasos = Integer.parseInt(reader.readLine());
                s = reader.readLine();
                if (s.equals("blanc")) color = Color.blanc;
                else color = Color.negre;
                aux = new Problema(fen, new Tema(pasos,color),valid);

                s = reader.readLine();
                Map<String,Integer> r = new HashMap<String,Integer>();
                while (!s.contains("Fi")){
                    String [] parts = s.split("/");
                    r.put(parts[0],Integer.parseInt(parts[1]));
                    s = reader.readLine();
                }
                aux.setRanking(r);

                problemes.add(aux);
                s = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Problema carregaProblema(String FEN){
        try{
            if (hiHaProblema(FEN)) {
                BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
                String fen = reader.readLine();
                Problema aux;
                boolean valid;
                int pasos;
                Color color;
                boolean trobat = false;
                String s = reader.readLine();

                while (s != null) {
                    if (fen.contains(FEN)){
                        fen = s;
                        s = reader.readLine();
                        if (s.equals("true")) valid = true;
                        else valid = false;
                        pasos = Integer.parseInt(reader.readLine());
                        s = reader.readLine();
                        if (s.equals("blanc")) color = Color.blanc;
                        else color = Color.negre;
                        aux = new Problema(fen, new Tema(pasos,color),valid);

                        s = reader.readLine();
                        Map<String,Integer> r = new HashMap<String,Integer>();
                        while (!s.contains("Fi")){
                            String [] parts = s.split("/");
                            r.put(parts[0],Integer.parseInt(parts[1]));
                            s = reader.readLine();
                        }
                        aux.setRanking(r);
                        return aux;
                    }
                    s = reader.readLine();
                }
                reader.close();
            }
            else System.out.println ("No s'ha pogut carregar el problema ja que no existeix a la base de dades");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void hihaUsuari(String nickname){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/usuaris.txt"));
            String s = reader.readLine();
            boolean trobat = false;

            while (s != null && !trobat){
                if (s.contains(nickname)) trobat = true;
            }

            reader.close();

            if (!trobat){
                BufferedWriter writer = new BufferedWriter(new FileWriter("localData/usuaris.txt"));
                writer.write(nickname);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hiHaProblema(String FEN) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
            String fen = reader.readLine();
            boolean trobat = false;
            while (fen != null && !trobat) {
                if (fen.contains(FEN)) trobat = true;
                fen = reader.readLine();
            }
            reader.close();
            return trobat;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}

