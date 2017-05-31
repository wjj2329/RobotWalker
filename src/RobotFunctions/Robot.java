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
        arr[0] = combinedMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();
        arr[1] = combinedMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();

        return arr;
    }

    public void atEdge(Telnet t, String json) throws IOException
    {
        if (currentCenterPosition.getX() < 250 || currentCenterPosition.getX() > 1680)
        {
            if (!PhysUtils.ALREADY_ROTATED)
            {
                PhysUtils.ALREADY_ROTATED = true;
                t.sendSpeed(0, 0);
                // recalculate maps
                Decoder.setMyMapsFromJson(this, json, true);
                PhysUtils.flipBool();
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
        // Figure out the angles
        double currentAngle = PhysUtils.robotCurrentAngle(orientation);
        currentAngle = Math.toDegrees(currentAngle);
        // Normalize
        if (currentAngle < 0)
        {
            currentAngle += 360;
        }
        // Robot needs to face the vector angle

        double degreeOfVector = combinedMap.getMyMap()
            [currentCenterPosition.getX()][currentCenterPosition.getY()].getAngle().degree;

        if (currentAngle > degreeOfVector + PhysUtils.ROTATION_ERROR + 15 ||
                currentAngle < degreeOfVector - PhysUtils.ROTATION_ERROR - 15)
        {
            // first, stop the robot.
            t.sendSpeed(0, 0);
            // make it move.
            // 20 is too narrow of a factor, but too wide messes it up as well. Find the happy medium.
            // Still need to test this with OBSTACLES! 10 doesn't work
            while (currentAngle > degreeOfVector + PhysUtils.ROTATION_ERROR - 15 || // wider or narrower?
                    currentAngle < degreeOfVector - PhysUtils.ROTATION_ERROR  + 15)
            {
                double normalizedAngle = currentAngle - degreeOfVector;
                if (normalizedAngle < 0)
                {
                    normalizedAngle += 360;
                }

                // edge case, in which I need to choose a direction to turn
                if (currentCenterPosition.getX() < 250 || currentCenterPosition.getX() > 1680 && PhysUtils.USE_SPECIAL)
                {
                    if (PhysUtils.TURN_180)
                    {
                        t.sendSpeed(0, 6);
                    }
                    else
                    {
                        t.sendSpeed(6, 0);
                    }
                }
                else
                {
                    if (normalizedAngle < 180)
                    {
                        t.sendSpeed(0, 6);
                    }
                    else
                    {
                        t.sendSpeed(6, 0);
                    }
                }
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
