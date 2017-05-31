package Map;

import Map.Coordinate;
import Map.Obstacle;

/**
 * Created by Alex on 5/23/17.
 * Class representing a goal on the obstacle field.
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

    private Coordinate corner1;
    private Coordinate corner2;
    private Coordinate corner3;
    private Coordinate corner4;


    public Goal(Obstacle o)
    {
        if (o != null)
        {
            radius = o.getRadius();
            center = o.getCenter(); // type of copy?
            corner1 = o.getCorner1();
            corner2 = o.getCorner2();
            corner3 = o.getCorner3();
            corner4 = o.getCorner4();
        }
    }

    public double getRadius()
    {
        return radius;
    }

    public Coordinate getCenter()
    {
        return center;
    }

    public Coordinate getCorner1()
    {
        return corner1;
    }

    public void setCorner1(Coordinate corner1)
    {
        this.corner1 = corner1;
    }

    public Coordinate getCorner2()
    {
        return corner2;
    }

    public void setCorner2(Coordinate corner2)
    {
        this.corner2 = corner2;
    }

    public Coordinate getCorner3()
    {
        return corner3;
    }

    public void setCorner3(Coordinate corner3)
    {
        this.corner3 = corner3;
    }

    public Coordinate getCorner4()
    {
        return corner4;
    }

    public void setCorner4(Coordinate corner4)
    {
        this.corner4 = corner4;
    }
}
