import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> within = new ArrayList<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                within.add(p);
            }
        }
        return within;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        Point2D candidate = pointSet.first();
        for (Point2D point : pointSet) {
            if (p.distanceTo(point) < p.distanceTo(candidate)) {
                candidate = point;
            }
        }
        return candidate;
    }

    public static void main(String[] args) {

    }
}
