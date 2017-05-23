/**
 * Created by Alex on 5/17/17.
 * Data package for an obstacle in the terrain map.
 * We might need one of these for a GOAL object, too.
 *
 * where to get the ARUCO libraries?
 * install pyserial?
 *
 * coordinates are openCV image coordinates, tied to the size of the screen and has the
 *  weird upper LH is 0,0 configuration
 * Should be x, y.
 *
 * What do we do with the orientation variable?
 *
 * what physically represents a goal state?
 */
public class Obstacle
{
    /**
     * Remember, we do indeed have a center, but
     *  the obstacle is actually a square!
     */
    private Coordinate center;

    /**
     * These represent the four corners of the obstacle.
     */
    private Coordinate corner1, corner2, corner3, corner4;

    /**
     * Not 100% sure if this should be a coordinate
     */
    private Coordinate orientation;

    /**
     * Radius of the obstacle
     * Transient because we don't get it from the JSON
     *  (thus helping effective GSON parsing)
     */
    private transient double radius;

    /**
     * Default constructor for Obstacle
     */
    public Obstacle(Coordinate center, Coordinate corner1, Coordinate corner2, Coordinate corner3,
                    Coordinate corner4, Coordinate orientation, double radius)
    {
        this.center = center;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.orientation = orientation;
        this.radius = radius;
    }

    /**
     * Getters and setters
     */
    public Coordinate getCenter()
    {
        return center;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public Coordinate getCorner1()
    {
        return corner1;
    }

    public Coordinate getCorner2()
    {
        return corner2;
    }

    public Coordinate getCorner3()
    {
        return corner3;
    }

    public Coordinate getCorner4()
    {
        return corner4;
    }

    public Coordinate getOrientation()
    {
        return orientation;
    }
}
