package org.example;

import java.awt.*;
import java.io.Serializable;

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
}
