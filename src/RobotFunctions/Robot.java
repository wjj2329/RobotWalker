package RobotFunctions;

import Map.Coordinate;
import Map.Orientation;
import Map.TerrainMap;
import Map.Vector;
import TelnetFunctions.Telnet;

import java.io.IOException;

/**
 * Created by williamjones on 5/15/17.
 * RobotFunctions.RobotFunctions: Class designed to encapsulate our physical robot.
 * 🤖
 */
public class Robot
{
    /**
     * GOAL map of the world the robot is in.
     */
    private TerrainMap goalMap;

    /**
     * The obstacle field
     */
    private TerrainMap obstacleMap;

    /**
     * Random field is necessary to move the robot out of
     *  spots where it can freeze.
     */
    private TerrainMap randomMap;

    /**
     * The combined map
     */
    private TerrainMap combinedMap;

    /**
     * Where the robot currently is in our map
     */
    private Coordinate currentCenterPosition;

    /**
     * Two doubles
     */
    private Orientation orientation;

    /**
     * We have some sexy corners
     */
    private Coordinate corner1;
    private Coordinate corner2;
    private Coordinate corner3;
    private Coordinate corner4;

    /**
     * Public default constructor for RobotFunctions.RobotFunctions.
     */
    public Robot()
    {

    }

    /**
     * Needs to return deltaX and deltaY
     */
    public double[] calculateSpeeds()
    {
        double[] arr = new double[2];
        // Should be combinedMap, not goalMap, ultimately.
        arr[0] = combinedMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();
        arr[1] = combinedMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();

        if (PhysUtils.NONCRUCIAL_PRINTS)
        {
            System.out.println("Current Center Position X: " + currentCenterPosition.getX());
            System.out.println("Current Center Position Y: " + currentCenterPosition.getY());
        }
        return arr;
    }

    public void atEdge(Telnet t, String json) throws IOException
    {
        System.out.println("My current x is " + currentCenterPosition.getX());
        System.out.println("My current y is " + currentCenterPosition.getY());
        if (currentCenterPosition.getX() < 270 || currentCenterPosition.getX() > 1660)
        {
            System.out.println("We've hit the edge.");
            if (!PhysUtils.ALREADY_ROTATED)
            {
                PhysUtils.ALREADY_ROTATED = true;
                t.sendSpeed(0, 0);
                // recalculate maps
                System.out.println("Calling setMyMapsFromJson in atEdge method");
                Decoder.setMyMapsFromJson(this, json, true);
            }
        }
        else
        {
            PhysUtils.ALREADY_ROTATED = false;
        }
    }

    /**
     * Function for rotating our robot!
     * @param t The current telnet connection
     */
    public void rotateMe(Telnet t) throws IOException, InterruptedException
    {
        if (!PhysUtils.MOVE_ROBOT)
        {
            return;
        }
        // 1. Figure out the angles
        double currentAngle = PhysUtils.robotCurrentAngle(orientation);
        currentAngle = Math.toDegrees(currentAngle);
        // Normalize
        if (currentAngle < 0)
        {
            currentAngle += 360;
        }
        //System.out.println("My initial angle is: " + currentAngle);
        // Robot needs to face the vector angle -- needs to be pretty similar
        // vector is at the current position of the robot

        double degreeOfVector = combinedMap.getMyMap()
            [currentCenterPosition.getX()][currentCenterPosition.getY()].getAngle().degree;

        //System.out.println("My modified angle is: " + degreeOfVector);

        //System.out.println("My currentAngle is: " + currentAngle);
        //System.out.println("My degreeOfVector is: " + degreeOfVector);
//        if (true)
//        {
//            return; // take this out ASAP
//        }
        System.out.println("BEFORE THE WHILE: My robot's current angle is " + currentAngle);
        System.out.println("BEFORE THE WHILE: Robot needs to be at " + degreeOfVector);

        if (currentAngle > degreeOfVector + PhysUtils.ROTATION_ERROR ||
                currentAngle < degreeOfVector - PhysUtils.ROTATION_ERROR)
        {
            // rotate; else, don't rotate at all, so we don't need a block there.
            // first, stop the robot.
            t.sendSpeed(0, 0);
            // Chill for a second bro
            //Thread.sleep(2000);
            // second, make it move.
            while (currentAngle > degreeOfVector + PhysUtils.ROTATION_ERROR ||
                    currentAngle < degreeOfVector - PhysUtils.ROTATION_ERROR)
            {
                //System.out.println("My currentAngle is: " + currentAngle);
                //System.out.println("My degreeOfVector is: " + degreeOfVector);
                // Right now, we're just doing a basic unintelligent rotate. We can modify this later if we want.
                //if (currentAngle - degreeOfVector < 180)
                double normalizedAngle = currentAngle - degreeOfVector;
                if (normalizedAngle < 0)
                {
                    normalizedAngle += 360;
                }
                if (normalizedAngle < 180)
                {
                    t.sendSpeed(0,6);
                }
                else
                {
                    t.sendSpeed(6, 0);
                }
                //t.sendSpeed(-3, 3);
                String responseFromServer = t.sendWhere();
                if(responseFromServer.equals("None") || responseFromServer.equals("") ||
                        responseFromServer.equals("\n"))
                {
                    continue;
                }
                Decoder.setMyMapsFromJson(this, responseFromServer, false);
                currentAngle = Math.toDegrees(PhysUtils.robotCurrentAngle(orientation));
                if (currentAngle < 0)
                {
                    currentAngle += 360;
                }
                degreeOfVector = combinedMap.getMyMap()
                        [currentCenterPosition.getX()][currentCenterPosition.getY()].getAngle().degree;
            }
            // Don't want it to keep rotating!
            //t.sendSpeed(0, 0);
            // slow down son
            //Thread.sleep(1000);
        }
    }

    //<editor-fold desc="Collapsed getters and setters">
    /**
     * Getters and setters:
     */
    public TerrainMap getGoalMap()
    {
        return goalMap;
    }

    public void setGoalMap(TerrainMap goalMap)
    {
        this.goalMap = goalMap;
    }

    public TerrainMap getObstacleMap()
    {
        return obstacleMap;
    }

    public void setObstacleMap(TerrainMap obstacleMap)
    {
        this.obstacleMap = obstacleMap;
    }

    public TerrainMap getRandomMap()
    {
        return randomMap;
    }

    public void setRandomMap(TerrainMap randomMap)
    {
        this.randomMap = randomMap;
    }

    public Coordinate getCurrentCenterPosition()
    {
        return currentCenterPosition;
    }

    public void setCurrentCenterPosition(Coordinate currentCenterPosition)
    {
        this.currentCenterPosition = currentCenterPosition;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
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

    public TerrainMap getCombinedMap()
    {
        return combinedMap;
    }

    public void setCombinedMap(TerrainMap combinedMap)
    {
        this.combinedMap = combinedMap;
    }

    //</editor-fold>

}
