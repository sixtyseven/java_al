import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int INIT_CAPACITY = 2;
    private int count = 0;
    private LineSegment[] segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] rawPoints) {
        if (rawPoints == null) {
            throw new IllegalArgumentException();
        }

        Point[] points = new Point[rawPoints.length];
        for (int i = 0; i < rawPoints.length; i++) {
            if (rawPoints[i] == null) {
                throw new IllegalArgumentException();
            }
            points[i] = rawPoints[i];
        }


        Arrays.sort(points);
//        for (Point p : points) {
//            StdOut.println(p);
//        }
//        StdOut.println("======");
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segs = new LineSegment[INIT_CAPACITY];

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[l]);
                        if ((slope1 == slope2) && (slope1 == slope3)) {
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
        LineSegment[] ls = new LineSegment[count];
        for (int i = 0; i < count; i++)
            ls[i] = segs[i];
        return ls;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < count; i++)
            copy[i] = segs[i];
        segs = copy;
    }

    private class SlopeNode implements Comparable<BruteCollinearPoints.SlopeNode> {
        private double slope;
        private Point[] pointsPair;

        public SlopeNode(double slope, Point[] points) {
            this.slope = slope;
            this.pointsPair = points;
        }

        public int compareTo(BruteCollinearPoints.SlopeNode that) {
            if (slope < that.slope) {
                return -1;
            }
            if (slope > that.slope) {
                return 1;
            }
            return 0;
        }

        public String toString() {
            return "(" + slope + ", " + pointsPair[0].toString() + ", " + pointsPair[1].toString() + ")";
        }
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.segments().length);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
