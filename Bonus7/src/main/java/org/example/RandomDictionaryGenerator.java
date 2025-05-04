package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomDictionaryGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();

    // Genereaza un fisier cu un numar dat de cuvinte random
    public static void generateWords(String filename, int numberOfWords, int maxLength) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < numberOfWords; i++) {
                int length = random.nextInt(maxLength - 2) + 3; // lungime intre 3 si maxLength
                StringBuilder word = new StringBuilder();
                for (int j = 0; j < length; j++) {
                    word.append(ALPHABET.charAt(random.nextInt(ALPHABET.length()))); // alege o litera random
                }
                writer.write(word.toString()); // scrie cuvantul in fisier
                writer.newLine(); // adauga newline dupa fiecare cuvant
            }
            System.out.println("Am generat " + numberOfWords + " cuvinte in " + filename);
        } catch (IOException e) {
            e.printStackTrace(); // afiseaza exceptia in caz de eroare
        }
    }
}
