package org.example;

public class PairStructure<T, U> {
    private T firstLocation;
    private U secondLocation;

    public PairStructure(T firstLocation, U secondLocation) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
    }

    public T getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(T firstLocation) {
        this.firstLocation = firstLocation;
    }

    public U getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(U secondLocation) {
        this.secondLocation = secondLocation;
    }

    @Override
    public String toString() {
        return "Pair: \n" +
                "First Location: " + firstLocation +
                "\n Second Location: " + secondLocation;
    }
}
