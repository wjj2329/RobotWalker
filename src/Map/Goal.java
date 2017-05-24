package Map;

import Map.Coordinate;
import Map.Obstacle;

/**
 * Created by Alex on 5/23/17.
 */
public class Goal
{
    /**
     * Radius of our goal
     */
    private double radius;

    /**
     * Center of the goal
     */
    private Coordinate center;

    public Goal(Obstacle o)
    {
        radius = o.getRadius();
        center = o.getCenter(); // type of copy?
    }

    public double getRadius()
    {
        return radius;
    }

    public Coordinate getCenter()
    {
        return center;
    }
}
