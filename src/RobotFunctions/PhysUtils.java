package RobotFunctions;

import Map.Coordinate;
import Map.Goal;
import Map.Orientation;
import Map.Vector;

/**
 * Created by williamjones on 5/16/17.
 * Utility class for physics methods.
 */
public class PhysUtils
{
    /**
     * CHANGE THIS BACK TO 2000 IF YOU'RE NOT RUNNING JUNIT!!!!!
     * And back to 1000 if you are!
     */
    public static final int sizeOfOurGrid = 2000;
    public static final int radius=200;
    public static final int MAX_WEIGHT=7;
    public static final int STRENGTH_OF_SPHERE=radius;
    public static final int ARUCO_STOP_RADIUS = 20;
    /**
     * The error for rotation, so it won't have to be exactly to any value.
     */
    public static final int ROTATION_ERROR = 20;

    public static double distance(Coordinate point1, Coordinate point2)
    {
        double first=point1.getX()-point2.getX();
        double second=point1.getY()-point2.getY();
        return Math.sqrt((first*first)+(second*second));
    }

    public static double angle(Coordinate point1, Coordinate point2)
    {
         return Math.atan((point2.getY()-point1.getY())/(point2.getX()-point1.getX()));
    }

    /**
     * Dude my angle method is cooler
     * The one question is: With the obstacle coordinate...It won't really be a
     *  coordinate; it will be a circle of some sort most likely.
     *  So, how are we going to pass in those coordinates?
     *
     *  Returns the arctangent in the correct quadrant.
     */
    public static double obstacleAngle(Coordinate locationOfObstacle, Coordinate currentPosition)
    {
        double y = (double) locationOfObstacle.getY() - currentPosition.getY();
        double x = (double) locationOfObstacle.getX() - currentPosition.getX();

        return Math.atan2(y, x);
    }

    /**
     * Potential replacement to obstacleAngle
     */
    public static double robotCurrentAngle(Orientation orientation)
    {
        double x = orientation.getX();
        double y = orientation.getY();
        return Math.acos(x) * sign(y);
    }

    /**
     * Computes the new angle
     * @param point current position in the 2d array we are examining
     * @param goalPoint the center of the goal
     * @param cell the computed other point in the triangle
     * @return theta, our new angle
     */
    public static double computeNewAngleInDegrees(Coordinate cell, Coordinate goalPoint, Coordinate point)
    {
        double opposite = Math.abs(cell.getX() - goalPoint.getX());
        //double adjacent = Math.abs(cell.getX() - point.getX());
        double hypotenuse = Math.abs(goalPoint.getX() - point.getY()); // consider switching x/y?
//        double opposite = point.getY() - goalPoint.getY();
//        double adjacent = cell.getX() - point.getX();
//        if (opposite < 0)
//        {
//            opposite += (2 * Math.PI);
//        }
//        if (adjacent < 0)
//        {
//            adjacent += (2 * Math.PI);
//        }
        //System.out.println("The first result: " + Math.toDegrees(Math.atan2(opposite, adjacent)));
        //System.out.println("The second result: " + Math.toDegrees(Math.atan2(adjacent, opposite)));
        //return Math.toDegrees(Math.atan2(adjacent, opposite)) + 90;

        // FIX THIS

        return Math.toDegrees(Math.asin(opposite / hypotenuse));
    }

    /**
     * Sign: Returns the sign, either + or -, of a number.
     * If it is zero, we will still be multiplying it
     *  by negative infinity. So keep it as 1.
     */
    public static int sign(double param)
    {
        if (param < 0)
        {
            return -1;
        }
        return 1;
    }

}
