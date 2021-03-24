import java.util.Random;

public class Point {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    private double r;
    private double g;
    private double b;
    private Cluster cluster;

    public Point(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    protected static double distance(Point p, Point centroid) {
        return Math.sqrt(Math.pow(p.getR() - centroid.getR(), 2) + Math.pow(p.getG() - centroid.getG(), 2)
                + Math.pow(p.getB() - centroid.getB(), 2));
    }

    protected static Point createRandomPoint() {
        double r = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        double g = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        double b = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        return new Point(r, g, b);
    }

    @Override
    public String toString() {
        return "Point [r=" + r + ", g=" + g + ", b=" + b + "], cluster=" + (cluster == null ? "" : cluster.toString());
    }

}
