package org.example;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    // Fiecare nod are copii (urmatoarele litere) si un flag pentru a marca finalul unui cuvant
    private Map<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;

    // Constructor care initializeaza copii si setaza isEndOfWord la false
    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false; // initial nu este un cuvant complet
    }

    // Getter pentru copii
    public Map<Character, TrieNode> getChildren() {
        return children; // returneaza harta cu copii
    }

    // Verifica daca nodul este final de cuvant
    public boolean isEndOfWord() {
        return isEndOfWord; // returneaza daca este final de cuvant
    }

    // Seteaza daca nodul este final de cuvant
    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord; // actualizeaza flag-ul
    }
}
