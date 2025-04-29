package org.example;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {

    final Set<String> words = new HashSet<>();
    /* (~ putina cultura ~)
        final - nu poate sa fie schimbat dupa ce a fost initializat
            (Atentie: nu vorbim despre obiect in sine, ci doar despre variabila words)
            adica nu o sa pot face iar words = new HashSet<>() , ci doar sa adaug cuvinte
        Set - colectie din Java
            - nu permite elemente duplicate
            - nu garanteaza ordinea elementelor
        HashSet - o implementare a interfeței Set
                - nu pastreaza ordinea elementelor
                - ofera performante foarte bune la adaugare/cautare/stergere (datorita folosirii unui "hash table")
     */

    public Dictionary(String filename) {
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream(filename); // deschide fisierul si citeste pe octeti (binar)
            if (is == null) {
                throw new FileNotFoundException("File not found: " + filename);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //converteste octetii in caractere
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase()); //E mai bine sa punem trim si sa ne asiguram ca mereu toate vor avea litere mici
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isWord(String str) {

        return words.contains(str.toLowerCase());
    }
}
