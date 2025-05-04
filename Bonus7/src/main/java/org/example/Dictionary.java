package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary {

    final Set<String> words = new HashSet<>();
    private Trie trie;
    /* (~ putina cultura ~)
        final - nu poate sa fie schimbat dupa ce a fost initializat
            (Atentie: nu vorbim despre obiect in sine, ci doar despre variabila words)
            adica nu o sa pot face iar words = new HashSet<>() , ci doar sa adaug cuvinte
        Set - colectie din Java
            - nu permite elemente duplicate
            - nu garanteaza ordinea elementelor
        HashSet - o implementare a interfetei Set
                - nu pastreaza ordinea elementelor
                - ofera performante foarte bune la adaugare/cautare/stergere (datorita folosirii unui "hash table")
     */

    public Dictionary(String filename) {
        this.trie = new Trie(); // Initializeaza Trie-ul corect
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filename); // deschide fisierul si citeste pe octeti (binar)
            if (is == null) {
                throw new FileNotFoundException("File not found: " + filename);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // converteste octetii in caractere
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim().toLowerCase();
                words.add(word); // adauga cuvantul in set
                trie.addWord(word); // adauga cuvantul in trie
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verifica daca un cuvant exista in dictionar
    public boolean isWord(String str) {
        return words.contains(str.toLowerCase());
    }

    // Getter pentru Trie
    public Trie getTrie() {
        return trie;
    }

    // Cauta toate cuvintele care incep cu prefixul dat folosind parallelStream
    public Set<String> lookupWithParallelStream(String prefix) {
        return words.parallelStream()
                .filter(word -> word.startsWith(prefix.toLowerCase()))
                .collect(Collectors.toSet());
    }

    // Cauta toate cuvintele care incep cu prefixul dat folosind parallelStream
    public Set<String> lookupPrefixParallel(String prefix) {
        return words.parallelStream()
                .filter(word -> word.startsWith(prefix.toLowerCase()))
                .collect(Collectors.toSet());
    }

    // Creeaza un graf prefix tree folosind JGraphT
    public Graph<String, DefaultEdge> createPrefixGraph() {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        buildGraph(trie.getRoot(), "", graph); // porneste constructia grafului de la radacina Trie-ului
        return graph;
    }

    // Metoda auxiliara care construieste recursiv graful
    private void buildGraph(TrieNode node, String currentPrefix, Graph<String, DefaultEdge> graph) {
        graph.addVertex(currentPrefix); // adauga nodul curent

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            String childPrefix = currentPrefix + entry.getKey();
            graph.addVertex(childPrefix); // adauga copilul
            graph.addEdge(currentPrefix, childPrefix); // adauga muchia intre parinte si copil
            buildGraph(entry.getValue(), childPrefix, graph); // apeleaza recursiv pentru copil
        }
    }

    // Parcurge graful folosind Depth-First Search si afiseaza toate nodurile
    public void dfsGraph(Graph<String, DefaultEdge> graph) {
        DepthFirstIterator<String, DefaultEdge> iterator = new DepthFirstIterator<>(graph);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
