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
 */

public class CtrlPersistence {

    public Domain.Huma[] GetUsuaris() {
        return null;
    }


    public List<Problema> GetProblemes() {
        return carregarProblemes();
    }

    List<Problema> problemes = new ArrayList<>();

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

    public void guardarProblema(Problema p) {
        try {
            if (!hiHaProblema(p.GetFEN())) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/problemes2.txt"), true));
                writer.append(p.GetFEN() + '\n' + p.GetValid() + '\n');                            //Guardo codi FEN i validesa
                writer.append(String.valueOf(p.getTema().getMovimentsFinsMat()) + '\n');    //Guardo numero moviments
                if (p.getTema().getCol() == Color.blanc) {
                    writer.append("blanc"+'\n');    //Guardo color blanc
                }
                else{
                    writer.append("negre"+'\n');    //Guardo color negre
                }
                Map<String,Integer> ranking = p.getRanking();
                ranking = sortByValue(ranking);
                for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
                    writer.append(entry.getKey() + "/" + entry.getValue() + '\n');
                }
                writer.append("Fi\n");
                writer.close();
            } else System.out.println("No s'ha pogut guardar el problema ja que el problema està repetit");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

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

    private List<Problema> carregarProblemes() {
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
        return problemes;
    }
    /*
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        list = Collections.reverse(list);
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }*/
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue
    (Map<K, V> map) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<K, V> comparingByValue().reversed())
                // Type here -----^ reversed() here -------^
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /*
    static <K,V extends Comparable<? super V>>
    List<Map.Entry<K, V>> sortByValue(Map<K,V> map) {

        List<Map.Entry<K,V>> sortedEntries = new ArrayList<Map.Entry<K,V>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<K,V>>() {
                    @Override
                    public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }*/
}

