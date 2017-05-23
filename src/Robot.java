/**
 * Created by williamjones on 5/15/17.
 * Robot: Class designed to encapsulate our physical robot.
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
    private TerrainMap randomField;

    /**
     * Where the robot currently is in our map
     */
    private Coordinate currentCenterPosition;

    /**
     * I don't know what this is for
     */
    private Coordinate orientation;

    /**
     * We have some sexy corners
     */
    private Coordinate corner1;
    private Coordinate corner2;
    private Coordinate corner3;
    private Coordinate corner4;

    /**
     * Public default constructor for Robot.
     */
    public Robot()
    {

    }

    public int[] calculateSpeeds()
    {
        return null;
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

    public TerrainMap getRandomField()
    {
        return randomField;
    }

    public void setRandomField(TerrainMap randomField)
    {
        this.randomField = randomField;
    }

    public Coordinate getCurrentCenterPosition()
    {
        return currentCenterPosition;
    }

    public void setCurrentCenterPosition(Coordinate currentCenterPosition)
    {
        this.currentCenterPosition = currentCenterPosition;
    }

    public Coordinate getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Coordinate orientation)
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
    //</editor-fold>
}
