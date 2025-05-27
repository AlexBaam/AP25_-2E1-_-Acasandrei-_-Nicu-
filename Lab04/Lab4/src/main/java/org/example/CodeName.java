package org.example;
import java.util.*;

public class CodeName {
    Map<String, Integer> counters;
    String[] baseNames;
    Random random;

    public CodeName(String[] baseNames) {
        this.baseNames = baseNames;
        this.random = new Random();
        this.counters = new HashMap<>();
    }

    public String generator() {
        String newName = generateRandomCodeName(baseNames, counters, random);
        return newName;
    }

    public String generateRandomCodeName(String[] baseNames, Map<String, Integer> counters, Random random) {
        String base = baseNames[random.nextInt(baseNames.length)];
        int count = counters.getOrDefault(base, 0) + 1;
        counters.put(base, count);
        return base + "_" + count;
    }

}
