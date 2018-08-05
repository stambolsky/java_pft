import java.util.Scanner;

import static java.awt.geom.Point2D.distance;

public class Points {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();
        Point point = new Point(x,y,x2,y2);

        System.out.println(distance(x,y,x2,y2));
        System.out.println(distance(1, 2, 4, 9));
        System.out.println(distance(12, 25, 43, 75));
        System.out.println(distance(5, 20, 36, 89));
    }


}

