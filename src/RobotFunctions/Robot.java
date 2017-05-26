package RobotFunctions;

import Map.Coordinate;
import Map.Orientation;
import Map.TerrainMap;

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
        arr[0] = goalMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();
        arr[1] = goalMap.getMyMap()[currentCenterPosition.getX()][currentCenterPosition.getY()].getWeight();
        System.out.println("Current Center Position X: " + currentCenterPosition.getX());
        System.out.println("Current Center Position Y: " + currentCenterPosition.getY());
        System.out.println("Delta X: " + goalMap.getMyMap()[currentCenterPosition.getX()]
                [currentCenterPosition.getY()].getΔX());
        System.out.println("Delta Y: " + goalMap.getMyMap()[currentCenterPosition.getY()]
                [currentCenterPosition.getY()].getΔY());
        return arr;
    }

    public void rotateMe() // Need to add a Telnet as a parameter, but it won't allow it
    {

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
