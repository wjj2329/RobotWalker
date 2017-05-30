package Map;

import Map.Coordinate;
import Map.Degree;

/**
 * Created by williamjones on 5/15/17.
 * Class designed to represent an arrow with varying strength.
 */
public class Vector {
    /**
     * Index of the vector in the 2D array
     */
    private Coordinate location;

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int weight = 0;

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
    private Degree angle;

    /**
     * Center = inside of the goal/obstacle
     * Approaching = nearby
     * Away = not even close
     */
    public enum PROXIMITY_TO_OBJECT
    {
        CENTER, APPROACHING, AWAY
    }

    /**
     * Proximity to goal/obstacles
     */
    private PROXIMITY_TO_OBJECT goalProximity = PROXIMITY_TO_OBJECT.AWAY;
    private PROXIMITY_TO_OBJECT obstacleProximity = PROXIMITY_TO_OBJECT.AWAY;

    /**
     * Public constructor for Map.Vector
     */
    public Vector(Coordinate location, double length, Degree angle)
    {
        this.location = location;
        this.ΔX = length;
        this.angle = angle;
    }

    /**
     * Polymorphic constructor for Map.Vector.
     *  The thing is, we may not know what
     *  the angle or ΔX are when we first
     *  start out, and we might have to compute them later.
     */
    public Vector(Coordinate location)
    {
        this.location = location;
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

    public Degree getAngle()
    {
        return angle;
    }

    public void setΔX(double ΔX)
    {
        this.ΔX = ΔX;
    }

    public void setAngle(Degree angle)
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

    public PROXIMITY_TO_OBJECT getGoalProximity()
    {
        return goalProximity;
    }

    public void setGoalProximity(PROXIMITY_TO_OBJECT goalProximity)
    {
        this.goalProximity = goalProximity;
    }

    public PROXIMITY_TO_OBJECT getObstacleProximity()
    {
        return obstacleProximity;
    }



    public void setObstacleProximity(PROXIMITY_TO_OBJECT obstacleProximity)
    {
        this.obstacleProximity = obstacleProximity;
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        s.append("ΔX ");
        s.append(ΔX);
        s.append(" ΔY ");
        s.append(ΔY);
        s.append("  ");
        s.append("Type in relation to GOAL: " + goalProximity.toString());
        s.append(" ");
        s.append("Location Coordinate ");
        s.append(location.getX());
        s.append(", ");
        s.append(location.getY() + " ");
        s.append("Type in relation to OBSTACLE: " + obstacleProximity.toString());
        s.append(" ");
        s.append("\n");
        // s.append("Angle of ");
        //s.append(angle.degree);
        return s.toString();
    }

}
