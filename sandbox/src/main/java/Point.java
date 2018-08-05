
public class Point {
    double x;
    double y;
    double x2;
    double y2;

    public Point(double x, double y, double x2, double y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2= y2;
    }

    public static double distance(double x, double y, double x2, double y2){
        double pow = (Math.pow(x2 - x, 2)) + (Math.pow(y2 - y,2));
        return Math.sqrt(pow);
    }
}