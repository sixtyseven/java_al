import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int INIT_CAPACITY = 8;
    private int count = 0;
    private LineSegment[] segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1] == points[i]) {
                throw new IllegalArgumentException();
            }
        }

        segs = new LineSegment[INIT_CAPACITY];

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = 1; j < points.length - 2; j++) {
                for (int k = 2; k < points.length - 1; k++) {
                    for (int l = 3; l < points.length; l++) {
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[l]);
                        if ((slope1 == slope2) && (slope1 == slope3)) {
                            count++;
                            add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    private void add(LineSegment item) {
        if (count == segs.length) resize(2 * segs.length);    // double size of array if necessary
        segs[count++] = item;                            // add item
    }


    // the line segments
    public LineSegment[] segments() {
        return segs;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < count; i++)
            copy[i] = segs[i];
        segs = copy;
    }


    public static void main(String[] args) {
        //todo test the app
    }
}
