/**
 * Created by Alex on 5/16/17.
 * Class to represent the vision of the robot.
 */
public class MachineVision
{
    /**
     * Generates map for the goal
     */
    public TerrainMap generateGoalMap()
    {
        return new TerrainMap(new Vector[1][]);
    }

    /**
     * Generates map for the potential field
     */
    public TerrainMap generateObstacleMap()
    {
        return new TerrainMap(new Vector[1][]);
    }

    /**
     * Generates map for the random field
     */
    public TerrainMap generateRandomFieldMap()
    {
        return new TerrainMap(new Vector[1][]);
    }

}
