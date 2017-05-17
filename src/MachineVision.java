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
     * Coded by ALEX
     * I like trains
     *
     * In the MOVE FUNCTION in the ROBOT CLASS, we compute where to move
     *  based on the coordinates given in the obstacle and goal maps.
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
