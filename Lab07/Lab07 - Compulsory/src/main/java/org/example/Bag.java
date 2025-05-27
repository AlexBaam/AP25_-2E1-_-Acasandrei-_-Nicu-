package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private final List<Tile> letters = new ArrayList<>();

    public Bag() {
        Random r = new Random();
        for (char c = 'a'; c <= 'z'; c++) { //fiecare litera apare pe 10 piese
            for(int i=0; i<10; i++) {
                letters.add(new Tile(c, r.nextInt(10)+1)); //punem toate piesele, fiecare avand un punctaj random de la 1-10
            }
        }
    }

    /*
    * synchronized - un modificator de metoda care indica faptul ca metoda este sincronizata
                   - doar un singur thread poate executa aceasta metoda in acelasi timp pentru un anumit obiect (sau clasa, daca este static).

    * */

    public synchronized List<Tile> extractTiles(int howMany) {
        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (letters.isEmpty()) { //verificam ca sacul sa nu fie gol
                break;
            }
            Random r = new Random();
            //nextInt(val) -> returneaza un numar intreg aleatoriu intre 0 si val-1
            int index = r.nextInt(letters.size()); // extrage aleatoriu o litera
            extracted.add(letters.remove(index)); // punem piesa in lista extrasa si o eliminam din letters
        }
        return extracted;
    }

    public boolean isEmpty() {
        return letters.isEmpty();
    }
}
