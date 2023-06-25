import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private static final int INIT_CAPACITY = 2;
    private int count = 0;
    private LineSegment[] segs;
    private SlopeNode[] fourPointsSlopeNodes;
    private int slopeNodeCount;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] rawPoints) {
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
        fourPointsSlopeNodes = new SlopeNode[INIT_CAPACITY];
        SlopeNode[] slopeNodes;

        for (int i = 0; i < points.length - 3; i++) {
            slopeNodes = new SlopeNode[points.length - i - 1];

            for (int j = i + 1; j < points.length; j++) {
                double slopeIJ = points[i].slopeTo(points[j]);
                Point[] slopeNodesPoints = {points[i], points[j]};
                slopeNodes[j - i - 1] = new SlopeNode(slopeIJ, slopeNodesPoints);
            }

            Arrays.sort(slopeNodes);

//            for (SlopeNode s : slopeNodes) {
//                StdOut.println(s);
//            }
//            StdOut.println("====== 2.1");

            for (int k = 0; k < slopeNodes.length - 1; ) {
                int sameSlopeCount = 0;
                int initialIdx = -1;
//                if (slopeNodes[k].slope == 1) {
//                    StdOut.println("====== debug 3");
//                }
                while ((k + 1) <= (slopeNodes.length - 1) && slopeNodes[k].slope == slopeNodes[k + 1].slope) {
                    if (initialIdx == -1) {
                        initialIdx = k;
                    }
                    sameSlopeCount++;
                    k++;
                }

                if (sameSlopeCount >= 2) {
                    Point[] pp = {slopeNodes[initialIdx].pointsPair[0], slopeNodes[k].pointsPair[1]};
                    SlopeNode sn = new SlopeNode(slopeNodes[initialIdx].slope, pp);

                    addFourPointsSlopeNode(sn);
                }

                k++;
            }
        }

        SlopeNode[] sn = getFourPointsSlopeNodes();

//        for (SlopeNode s : sn) {
//            StdOut.println(s);
//        }
//        StdOut.println("====== 3");


        Arrays.sort(sn);
        Arrays.sort(sn, new ByEndPoint());

        if (sn.length == 1) {
            add(new LineSegment(sn[sn.length - 1].pointsPair[0], sn[sn.length - 1].pointsPair[1]));
        } else if (sn.length > 1) {
            for (int i = 0; i < sn.length; ) {
                int count = 0;
                while (i < sn.length - 1 && sn[i].slope == sn[i + 1].slope && sn[i].pointsPair[1].compareTo(sn[i + 1].pointsPair[1]) == 0) {
                    i++;
                    count++;
                }

                if (count > 0) {
                    add(new LineSegment(sn[i - count].pointsPair[0], sn[i].pointsPair[1]));
                } else {
                    add(new LineSegment(sn[i].pointsPair[0], sn[i].pointsPair[1]));
                }
                i++;
            }
        }
    }

    private SlopeNode[] getFourPointsSlopeNodes() {
        SlopeNode sn[] = new SlopeNode[slopeNodeCount];
        for (int i = 0; i < slopeNodeCount; i++) {
            sn[i] = fourPointsSlopeNodes[i];
        }

        return sn;
    }

    // helper linked list class
    private class SlopeNode implements Comparable<SlopeNode> {
        private double slope;
        private Point[] pointsPair;

        public SlopeNode(double slope, Point[] points) {
            this.slope = slope;
            this.pointsPair = points;
        }

        public int compareTo(SlopeNode that) {
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

    private static class ByEndPoint implements Comparator<SlopeNode> {
        public int compare(SlopeNode a, SlopeNode b) {
            return a.pointsPair[1].compareTo(b.pointsPair[1]);
        }
    }

    private static class ByStartPoint implements Comparator<SlopeNode> {
        public int compare(SlopeNode a, SlopeNode b) {
            return a.pointsPair[0].compareTo(b.pointsPair[0]);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    private void add(LineSegment item) {
        if (count == segs.length)
            resize(2 * segs.length);    // double size of array if necessary
        segs[count++] = item;                            // add item
    }

    private void addFourPointsSlopeNode(SlopeNode item) {
        if (slopeNodeCount == fourPointsSlopeNodes.length)
            resizeSlopeNodes(2 * fourPointsSlopeNodes.length);    // double size of array if necessary
        fourPointsSlopeNodes[slopeNodeCount++] = item;                            // add item
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = segs[i];
        }
        segs = copy;
    }

    // resize the underlying array holding the elements
    private void resizeSlopeNodes(int capacity) {
        SlopeNode[] copy = new SlopeNode[capacity];
        for (int i = 0; i < slopeNodeCount; i++) {
            copy[i] = fourPointsSlopeNodes[i];
        }
        fourPointsSlopeNodes = copy;
    }


    // the line segments
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[count];
        for (int i = 0; i < count; i++)
            ls[i] = segs[i];
        return ls;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
