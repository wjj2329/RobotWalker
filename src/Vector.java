/**
 * Created by williamjones on 5/15/17.
 * Class designed to represent an arrow with varying strength.
 */
public class Vector {
    /**
     * Index of the vector in the 2D array
     */
    private Coordinate location;

    /**
     * ΔX: represents the strength of the arrow in the X direction,
     * which will determine how much the robot is
     * attracted or repelled.
     */
    private double ΔX;

    /**
     * Same as ΔX, but for the Y-coordinate.
     * :D
     */
    private double ΔY;

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
        this.ΔX = length;
        this.angle = angle;
    }

    /**
     * Polymorphic constructor for Vector.
     *  The thing is, we may not know what
     *  the angle or ΔX are when we first
     *  start out, and we might have to compute them later.
     */
    public Vector(Coordinate location)
    {

    }

    /**
     * Getters, and potentially setters iff
     *  they turn out to be necessary
     */
    public Coordinate getLocation()
    {
        return location;
    }

    public double getΔX()
    {
        return ΔX;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setΔX(double ΔX)
    {
        this.ΔX = ΔX;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public double getΔY()
    {
        return ΔY;
    }

    public void setΔY(double ΔY)
    {
        this.ΔY = ΔY;
    }
}
