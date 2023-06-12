import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private static final int INIT_CAPACITY = 8;
    private int count = 0;
    private LineSegment[] segs;
    private SlopeNode[] sameSlopeSlopeNodes;
    private int slopeNodeCount;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        SlopeNode[] slopeNodes;

        for (int i = 0; i < points.length - 4; i++) {
            slopeNodes = new SlopeNode[points.length - i - 1];

            for (int j = i + 1; j < points.length; j++) {
                double slopeIJ = points[i].slopeTo(points[j]);
                slopeNodes[j - i - 1] = new SlopeNode();
                slopeNodes[j - i - 1].slope = slopeIJ;
                Point[] slopeNodesPoints = {points[i], points[j]};
                slopeNodes[j - i - 1].pointsPair = slopeNodesPoints;
            }
            Arrays.sort(slopeNodes);

            for (int k = 0; k < slopeNodes.length; ) {
                int sameSlopeCount = 0;
                while (slopeNodes[k] == slopeNodes[k++]) {
                    sameSlopeCount++;
                }
                if (sameSlopeCount >= 4) {
                    addSlopeNode(slopeNodes[k]);
                }
            }
        }

        Arrays.sort(segs);

        LineSegment[] tempSegs = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            tempSegs[i] = segs[i];
        }


        segs = new LineSegment[INIT_CAPACITY];

    }

    // helper linked list class
    private class SlopeNode implements Comparable<SlopeNode> {
        private double slope;
        private Point[] pointsPair;

        public int compareTo(SlopeNode that) {
            if (slope < that.slope) {
                return -1;
            }
            if (slope > that.slope) {
                return 1;
            }
            return 0;
        }
    }

    private static class ByEndPoint implements Comparator<SlopeNode> {
        public int compare(SlopeNode a, SlopeNode b) {
            return a.pointsPair[1].compareTo(b.pointsPair[1]);
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

    private void addSlopeNode(SlopeNode item) {
        if (slopeNodeCount == sameSlopeSlopeNodes.length)
            resizeSlopeNodes(2 * sameSlopeSlopeNodes.length);    // double size of array if necessary
        sameSlopeSlopeNodes[slopeNodeCount++] = item;                            // add item
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
            copy[i] = sameSlopeSlopeNodes[i];
        }
        sameSlopeSlopeNodes = copy;
    }


    // the line segments
    public LineSegment[] segments() {
        return segs;
    }

    public static void main(String[] args) {


    }
}
