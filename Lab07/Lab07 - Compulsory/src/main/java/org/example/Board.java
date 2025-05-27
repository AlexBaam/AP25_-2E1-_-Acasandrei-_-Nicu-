package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<String> words = new ArrayList<>();

    // sincronizeaza la adaugarea cuvintelor -> sa nu apara conflicte
    public synchronized void addWord(Player player, String word) {
        words.add(word);
        System.out.println(player.getName() + ": " + word);
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    @Override
    public String toString() {
        return words.toString();
    }
}
