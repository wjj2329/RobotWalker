/**
 * Created by Alex on 5/17/17.
 * Data package for an obstacle in the terrain map.
 * We might need one of these for a GOAL object, too.
 */
public class Obstacle
{
    /**
     * Eventually, this probably won't just be a location,
     *  considering the fact that an obstacle is an entire
     *  circle.
     */
    private Coordinate location;

    /**
     * Radius of the obstacle
     */
    private double radius;

    /**
     * Default constructor for Obstacle
     */
    public Obstacle(Coordinate location, double radius)
    {
        this.location = location;
        this.radius = radius;
    }

    /**
     * Getters and setters
     */
    public Coordinate getLocation()
    {
        return location;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }
}
