package RobotFunctions;

import Map.*;

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
    // 1920 x 1080, buffer of 30
    public static final int sizeOfOurGrid = 1930;
    public static final int sizeOfOurGrid2 = 1090;
    public static final int buffer = 100;
    public static final int radius=300; // sphere of influence eh.
    public static final int MAX_WEIGHT=5;
    public static final int STRENGTH_OF_SPHERE=radius;
    public static final int ARUCO_STOP_RADIUS = 50;
    public static final boolean MOVE_ROBOT = true;
    public static final boolean NONCRUCIAL_PRINTS = true;
    public static boolean ROTATE = true;
    public static boolean ALREADY_ROTATED = true;
    public static final boolean USE_SPECIAL = true;
    /**
     * The error for rotation, so it won't have to be exactly to any value.
     * It works at 30, without the 180/180 thing. Also snakes at 15 with it.
     *
     * every other ones
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
        double opposite = cell.getX() - goalPoint.getX();
        double adjacent = goalPoint.getY() - cell.getY();

        return Math.toDegrees(Math.abs(Math.atan2(adjacent, opposite) - Math.PI));
    }

    public static double computeNewWeight(Vector vec1, Vector vec2, Degree newAngle)
    {
        return Math.sqrt(Math.pow(vec1.getWeight(), 2) + Math.pow(vec2.getWeight(), 2)
                + (2 * vec1.getWeight() * vec2.getWeight() * Math.cos(newAngle.degree)));
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
