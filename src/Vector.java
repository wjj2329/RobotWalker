/**
 * Created by williamjones on 5/15/17.
 * Class designed to represent an arrow with varying strength.
 */
public class Vector
{
    /**
     * Index of the vector in the 2D array
     */
    private Coordinate location;

    /**
     * Length: represents the strength of the arrow,
     *  which will determine how much the robot is
     *  attracted or repelled.
     */
    private double length;

    /**
     * Angle: represents the direction.
     */
    private double angle;

    /**
     * Public constructor for Vector
     */
    public Vector(Coordinate location, double length, double angle)
    {
        this.location = location;
        this.length = length;
        this.angle = angle;
    }

    /**
     * Getters, and potentially setters iff
     *  they turn out to be necessary
     */
    public Coordinate getLocation()
    {
        return location;
    }

    public double getLength()
    {
        return length;
    }

    public double getAngle()
    {
        return angle;
    }
}
