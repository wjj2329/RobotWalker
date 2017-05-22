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
    private Coordinate currentPosition;

    /**
     * Public default constructor for Robot.
     */
    public Robot() {

    }

    public  int[] calculateSpeeds()
    {
        return null;
    }

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
}
