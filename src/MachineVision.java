/**
 * Created by Alex on 5/16/17.
 * Class to represent the vision of the robot.
 */
public class MachineVision
{
    /**
     * The goal probably won't end up being a coordinate,
     *  since it will most likely be a circle.
     *  However, this will serve as a decent placeholder.
     */
    private Coordinate goalLocation;

    /**
     * The dimensions of the grid. We cannot assume that it is
     *  square, because we all know what assuming does
     */
    private Dimensions dimensions;

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
     *
     *  Can vectors be affected by multiple obstacle fields?
     *  Are we generating a new obstacle map FOR EACH OBSTACLE?
     *  Right now, I will make the assumption that we are, so I will only pass in one.
     *
     *  s + r = circle of influence; s = degree of potency that we want the field to have
     *
     *  spreadOfField = potency of field; this may need to be computed via a formula
     */
    public TerrainMap generateObstacleMap(int rowDim, int colDim, Obstacle obstacle, double spreadOfField)
    {
        dimensions.setRow(rowDim);
        dimensions.setColumn(colDim);
        double obstacleRadius = obstacle.getRadius();

        // For each vector in the field...
        Vector[][] obstacleGrid = new Vector[dimensions.getRow()][dimensions.getColumn()];
        for (int i = 0; i < obstacleGrid.length; i++)
        {
            for (int j = 0; j < obstacleGrid[i].length; j++)
            {
                // ...set its deltaX and deltaY.
                Vector cur = obstacleGrid[i][j];
                // According to the book, this could very well be the distance from the obstacle to the AGENT.
                // However, if we're pre-computing the field, this makes more sense.
                // Because the agent will only be using the method if it's at the cell containing the
                // vector that we're currently analyzing. So it will in effect be the same thing.

                double distanceFromObstacleToVector = PhysUtils.distance(cur.getLocation(), obstacle.getCenter());
                cur.setAngle(PhysUtils.obstacleAngle(obstacle.getCenter(), cur.getLocation()));
                double θ = cur.getAngle();
                setΔXAndΔY(θ, distanceFromObstacleToVector, spreadOfField, obstacleRadius, computeβ(), cur);
            }
        }
        // generate the new coordinate here, based on previous coordinate.x + deltaX, prev.y + deltaY
        return new TerrainMap(obstacleGrid);
    }

    /**
     * Sets ΔX and ΔY, so the robot knows how to move.
     * Beta = scalar for the strength of this field
     */
    private void setΔXAndΔY(double θ, double distanceFromObstacleToVector, double spreadOfField,
                            double obstacleRadius, double β, Vector cur)
    {
        // This means we're inside of the obstacle. BAD! Get out ASAP!
        if (distanceFromObstacleToVector < obstacleRadius)
        {
            cur.setΔX(-PhysUtils.sign(Math.cos(θ)) * Double.MAX_VALUE);
            cur.setΔY(-PhysUtils.sign(Math.sin(θ)) * Double.MAX_VALUE);
        }
        // This means we're approaching the obstacle. The degree of repulsion is computed here.
        else if (obstacleRadius <= distanceFromObstacleToVector && distanceFromObstacleToVector <= spreadOfField
                + obstacleRadius)
        {
            cur.setΔX(-β*(spreadOfField + distanceFromObstacleToVector - obstacleRadius) * Math.cos(θ));
            cur.setΔY(-β*(spreadOfField + distanceFromObstacleToVector - obstacleRadius) * Math.sin(θ));
        }
        // Outside of the sphere of influence = do nothing.
        else if (distanceFromObstacleToVector > spreadOfField + obstacleRadius)
        {
            cur.setΔX(0);
            cur.setΔY(0);
        }
        else
        {
            // You broke it, son. You failed. why, oh why have you failed me?
            System.out.println("You suck.");
        }
    }

    /**
     * Please code this!
     *  This will be determining our beta scalar. It might have to do with the randomly generated
     *  field map, but I'm not entirely certain.
     *  This will probably need some parameters at some point as well.
     */
    private double computeβ()
    {
        return 1;
    }

    /**
     * Generates map for the random field
     */
    public TerrainMap generateRandomFieldMap()
    {
        return new TerrainMap(new Vector[1][]);
    }

    /**
     * Combines all the maps into one: the overarching potential field!
     * May not want it in this class but I'm putting it here for now
     */
    public TerrainMap generateCombinedMap(TerrainMap goalMap, TerrainMap obstacleMap, TerrainMap randomMap)
    {
        // insert ingenious code to combine the three maps here son
        return new TerrainMap(new Vector[1][]);
    }
}
