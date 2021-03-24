import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KMeans {
    public static final Logger LOGGER = Logger.getLogger(KMeans.class.getName());

    private static final int POINTS_TO_GENERATE[][] = { { 1, 10, 200, 1 }, { 2, 20, 230, 1 }, { 6, 25, 150, 1 },
            { 7, 45, 100, 1 }, { 10, 50, 125, 1 }, { 3, 24, 111, 1 }, { 100, 4, 10, 2 }, { 250, 7, 50, 2 },
            { 243, 5, 68, 2 }, { 210, 2, 90, 2 }, { 200, 1, 95, 2 }, { 215, 0, 68, 2 }, { 56, 200, 1, 3 },
            { 79, 234, 3, 3 }, { 80, 210, 8, 3 }, { 95, 200, 10, 3 }, { 80, 210, 4, 3 }, { 49, 207, 1, 3 }, };

    private static final int NUM_CLUSTERS = 3;
    private List<Point> points;
    private List<Cluster> clusters;

    public KMeans() {
        LOGGER.info("-=-=-=-=-=-=-=-=-= INITIALIZING");
        this.points = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public static void main(String[] args) {
        KMeans kmeans = new KMeans();
        kmeans.init();
        kmeans.calculate();
    }

    public void init() {
        for (int i = 0; i < POINTS_TO_GENERATE.length; i++) {
            Point point = new Point(POINTS_TO_GENERATE[i][0], POINTS_TO_GENERATE[i][1], POINTS_TO_GENERATE[i][2]);
            this.points.add(point);
        }

        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = Point.createRandomPoint();
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        plotClusters();
    }

    private void plotClusters() {
        LOGGER.info("\n\nCLUSTERS:");
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    public void calculate() {
        int iteration = 0;

        while (true) {
            iteration++;
            clearClusters();
            List<Point> lastCentroids = getCentroids();

            assignCluster();
            calculateCentroids();

            List<Point> currentCentroids = getCentroids();

            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i), currentCentroids.get(i));
            }

            LOGGER.info("################# Iteration: ".concat(String.valueOf(iteration)));
            // LOGGER.info("Centroid distances: " + distance);

            if (distance < 0.1)
                break;
        }
        plotClusters();
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<>();
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            centroids.add(new Point(aux.getR(), aux.getG(), aux.getB()));
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min;
        double distance;
        int clusterIndex = 0;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);

                // StringBuilder builder = new StringBuilder();
                // builder.append("Distance from point ");
                // builder.append(point.toString());
                // builder.append(" to point ");
                // builder.append(c.getCentroid().toString());
                // builder.append(" is: ");
                // builder.append(Point.distance(point, c.getCentroid()));
                // LOGGER.info(builder.toString());

                distance = Point.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    clusterIndex = i;
                }
            }

            point.setCluster(clusters.get(clusterIndex));
            clusters.get(clusterIndex).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumR = 0;
            double sumG = 0;
            double sumB = 0;
            List<Point> list = cluster.getPoints();
            int pointsSize = list.size();

            for (Point point : list) {
                sumR += point.getR();
                sumG += point.getG();
                sumB += point.getB();
            }

            if (pointsSize > 0) {
                cluster.getCentroid().setR(sumR / pointsSize);
                cluster.getCentroid().setG(sumG / pointsSize);
                cluster.getCentroid().setB(sumB / pointsSize);
            }
        }
    }
}