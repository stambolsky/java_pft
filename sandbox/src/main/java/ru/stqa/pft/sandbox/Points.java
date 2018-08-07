package ru.stqa.pft.sandbox;

class Points {
    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(60,100);


        System.out.println(p1.distance(p2));
        System.out.println(p1.distance(20,55));
        System.out.println(p1.distance(0.5,1.5));



    }
}