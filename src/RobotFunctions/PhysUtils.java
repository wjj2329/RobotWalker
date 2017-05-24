package RobotFunctions;

import Map.Coordinate;

/**
 * Created by williamjones on 5/16/17.
 * Utility class for physics methods.
 */
public class PhysUtils
{

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
