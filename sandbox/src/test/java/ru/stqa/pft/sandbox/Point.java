package ru.stqa.pft.sandbox;

public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double distance(double x, double y) {
        double pow = (Math.pow(this.x - x, 2)) + (Math.pow(this.y - y, 2));
        return Math.sqrt(pow);
    }

    double distance(Point p) {
        return distance(p.x, p.y);

    }

}
