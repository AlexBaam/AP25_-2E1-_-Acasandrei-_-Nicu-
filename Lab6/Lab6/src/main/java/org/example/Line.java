package org.example;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Line implements Serializable {
    protected Point start;
    protected Point end;
    protected Color color;

    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public double getDistance(){
        //double length = sqrt((end.x - start.x)*(end.x - start.x) + (end.y - start.y)*(end.y - start.y)); // Distanta euclidiana
        //return length;
        return Math.sqrt((end.x - start.x)*(end.x - start.x) + (end.y - start.y)*(end.y - start.y));
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line line)) return false;

        return (Objects.equals(start, line.start) && Objects.equals(end, line.end)) ||
                (Objects.equals(start, line.end) && Objects.equals(end, line.start));
    }

    @Override
    public int hashCode() {
        // Order doesn't matter: sum is commutative
        return Objects.hash(start) + Objects.hash(end);
    }
}
