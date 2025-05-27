package org.example;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Trie {
    private TrieNode root;

    // Constructor care initializeaza radacina trie-ului
    public Trie() {
        root = new TrieNode();
    }

    // Adauga un cuvant in Trie
    public void addWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(c, k -> new TrieNode()); // adauga nod daca nu exista
        }
        current.setEndOfWord(true); // marcheaza sfarsitul cuvantului
    }

    // Cauta un cuvant in Trie
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.getChildren().get(c); // navigheaza pe litere
            if (current == null) {
                return false; // daca nu exista litera, cuvantul nu exista
            }
        }
        return current.isEndOfWord(); // verifica daca este un cuvant complet
    }

    // DFS Iterator (recursiv) pentru toate cuvintele din Trie
    public void dfs(TrieNode node, StringBuilder currentWord) {
        if (node.isEndOfWord()) {
            System.out.println(currentWord.toString()); // afiseaza cuvantul
        }

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            currentWord.append(entry.getKey()); // adauga litera
            dfs(entry.getValue(), currentWord); // continua recursiv
            currentWord.deleteCharAt(currentWord.length() - 1); // revine la starea anterioara
        }
    }

    // Metoda pentru a porni DFS-ul de la radacina
    public void printAllWords() {
        dfs(root, new StringBuilder());
    }

    // Creeaza un graf de prefixe folosind JGraphT
    public Graph<String, DefaultEdge> buildGraph() {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Radacina va fi nodul gol ("")
        buildGraphRecursive(root, "", graph);
        return graph;
    }

    // Functie recursiva pentru a construi graful
    private void buildGraphRecursive(TrieNode node, String currentWord, Graph<String, DefaultEdge> graph) {
        graph.addVertex(currentWord); // adauga nodul curent

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            String nextWord = currentWord + entry.getKey(); // concateneaza litera
            graph.addVertex(nextWord); // adauga copilul
            graph.addEdge(currentWord, nextWord); // adauga muchie intre noduri

            buildGraphRecursive(entry.getValue(), nextWord, graph); // recursiv copil
        }
    }

    // Colecteaza toate cuvintele din Trie intr-un set
    private void collectAllWords(TrieNode node, StringBuilder currentWord, Set<String> results) {
        if (node.isEndOfWord()) {
            results.add(currentWord.toString()); // adauga cuvantul gasit
        }

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            currentWord.append(entry.getKey()); // adauga litera
            collectAllWords(entry.getValue(), currentWord, results); // recursiv copil
            currentWord.deleteCharAt(currentWord.length() - 1); // elimina ultima litera pentru backtracking
        }
    }

    // Cauta toate cuvintele care incep cu un anumit prefix
    public List<String> lookupPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            current = current.getChildren().get(c); // navigheaza pe prefix
            if (current == null) {
                return results; // daca prefixul nu exista
            }
        }
        dfs(current, new StringBuilder(prefix), results); // cauta cuvintele pornind de la nodul gasit
        return results;
    }

    // Versiune modificata a DFS-ului pentru a adauga cuvinte in lista
    private void dfs(TrieNode node, StringBuilder currentWord, List<String> results) {
        if (node.isEndOfWord()) {
            results.add(currentWord.toString()); // adauga cuvantul
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            currentWord.append(entry.getKey());
            dfs(entry.getValue(), currentWord, results);
            currentWord.deleteCharAt(currentWord.length() - 1); // backtrack
        }
    }

    // Getter pentru radacina Trie-ului
    public TrieNode getRoot() {
        return root;
    }

}
