package Persistence;

import java.io.*;
import java.util.*;

/**
 * @class CtrlPersistence
 * \brief Classe encarregada de gestionar el .txt que actua com a base de dades
 *        Per cada problema s'encarregade guardar:
 *              - Codi FEN
 *              - Validesa (true/false)
 *              - Numero de passos fins el mat
 *              - Color del jugador que realitza el mat
 *              - Creador
 *              - Ranking
 */

public class CtrlPersistence {

    public CtrlPersistence() {}

    public List<Object[]> GetProblemes() {return carregarProblemes();}

    /**
     * Guarda el problema si no existeix a la base de dades o si existeix pero no es valid en la base de dades i si en
     * el parametre passat, altrament no fa res
     * @param FEN Codi FEN del problema a guardar
     * @param validesa Indica si el problema és vàlid o no
     * @param njugades Nombre de jugades amb el qual s'aconsegueix el mat
     * @param color Color del jugador que aconsegueix el mat
     * @param creador Creador del problema
     */


    public void guardarProblema(String FEN, boolean validesa, int njugades, String color, String creador, String dificultat, String login) {
        try {
            boolean hiHaProblema = hiHaProblema(FEN);
            if (!hiHaProblema) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/problemes2.txt"), true));
                writer.append(FEN + '\n' + validesa + '\n');       //Guardo codi FEN i validesa
                writer.append(String.valueOf(njugades) + '\n');    //Guardo numero moviments
                writer.append(color + '\n');
                writer.append(creador + '\n');
                writer.append(dificultat + '\n');
                writer.append("Fi\n");
                writer.close();
                return;
            }
            else if (hiHaProblema && validesa) {
                File input = new File("localData/problemes2.txt");
                BufferedReader reader = new BufferedReader(new FileReader(input));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(FEN)) {
                        currentLine = reader.readLine();    //Valid
                        String valid = currentLine;
                        currentLine = reader.readLine();    //Passos
                        currentLine = reader.readLine();    //Color
                        currentLine = reader.readLine();    //Creador
                        if ((valid.contains("false") || !shaJugat(FEN)) && (currentLine.contains("root") || currentLine.contains(login))) {
                            eliminarProblema(FEN);
                            guardarProblema(FEN, validesa, njugades,color,creador, dificultat,login);
                        }
                    }
                }
                reader.close();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un problema de la base de dades si existeix, altrement llença un missatge d'error
     * @param FEN Codi FEN del problema a eliminar
     */

    public boolean eliminarProblema(String FEN){
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
                    if (currentLine != null) writer.append(currentLine + '\n');
                }
                writer.close();
                reader.close();
                temp.renameTo(input);
                return true;
            }
            else return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
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
                        for (int i = 0; i < 5; i++){
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
            else return false;
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
                if (!trobat) return false;
                return true;
            }
            else return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private List<Object[]> carregarProblemes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
            String fen;
            boolean valid;
            int pasos;
            String color;
            String creador;
            String s = reader.readLine();
            String dificultat;
            List<Object[]> problemes = new ArrayList<>();
            while (s != null){
                fen = s;
                s = reader.readLine();
                if (s.equals("true")) valid = true;
                else valid = false;
                pasos = Integer.parseInt(reader.readLine());
                s = reader.readLine();
                color = s;
                s = reader.readLine();
                creador = s;
                s = reader.readLine();
                dificultat = s;
                s = reader.readLine();

                List<Object[]> r = new ArrayList<>();
                while (s != null && !s.contains("Fi")){
                    String [] parts = s.split("/");
                    Object [] player = new Object[] {
                            parts[0],
                            Integer.parseInt(parts[1])
                    };
                    r.add(player);
                    s = reader.readLine();
                }

                Object[] aux = new Object[] {
                        fen,
                        valid,
                        pasos,
                        color,
                        r,
                        creador,
                        dificultat
                };

                problemes.add(aux);
                s = reader.readLine();
            }
            return problemes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] carregaProblema(String FEN){
        try{
            if (hiHaProblema(FEN)) {
                BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
                String fen = reader.readLine();
                boolean valid;
                int pasos;
                String color;
                String creador;
                String dificultat;
                String s = reader.readLine();

                while (s != null) {
                    if (fen.contains(FEN)){
                        fen = s;
                        s = reader.readLine();
                        if (s.equals("true")) valid = true;
                        else valid = false;
                        pasos = Integer.parseInt(reader.readLine());
                        s = reader.readLine();
                        color = s;
                        s = reader.readLine();
                        creador = s;
                        s = reader.readLine();
                        dificultat = s;

                        s = reader.readLine();
                        List<Object[]> r = null;
                        while (!s.contains("Fi")){
                            String [] parts = s.split("/");
                            Object [] player = new Object[] {
                                    parts[0],
                                    Integer.parseInt(parts[1])
                            };
                            r.add(player);
                            s = reader.readLine();
                        }
                        Object[] aux = new Object[] {
                                fen,
                                valid,
                                pasos,
                                color,
                                r,
                                creador,
                                dificultat
                        };
                        return aux;
                    }
                    s = reader.readLine();
                }
                reader.close();
            }
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean InsertaUsuari(String nickName){
        try{
            if (!hihaUsuari(nickName)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/usuaris.txt"), true));
                writer.append(nickName+'\n');
                writer.close();
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public boolean hihaUsuari(String nickname){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/usuaris.txt"));
            String s = reader.readLine();
            while (s != null){
                if (s.contains(nickname)) return true;
                s = reader.readLine();
            }

            reader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
    /**
     * pre: El problema es troba a la base de dades
     */

    private boolean shaJugat(String FEN){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("localData/problemes2.txt"));
            String s = reader.readLine();
            boolean trobat = false;
            while (s != null && !trobat) {
                if (s.contains(FEN)) {
                    s = reader.readLine();
                    s = reader.readLine();
                    s = reader.readLine();
                    s = reader.readLine();
                    s = reader.readLine();
                    if (s.contains("Fi")) return false;
                    else return true;
                }
                s = reader.readLine();
            }
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}