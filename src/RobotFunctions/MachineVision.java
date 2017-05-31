package RobotFunctions;

import Map.*;

import java.util.ArrayList;
import java.util.Random;

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
     * The obstacles
     */
    private ArrayList<Obstacle> obstacles;

    /**
     * Generates map for the goal
     * Doing this in traditional grid format, not the inverted graphics grid.
     * This should be fine as long as we stay consistent.
     */
    public TerrainMap generateGoalMap(int rowDim, int colDim, Goal goal, double spreadOfField, Robot r)
    {
        if (PhysUtils.USE_SPECIAL)
        {
            return generateGoalMapSpecial(rowDim, colDim, r);
        }
        dimensions = new Dimensions(rowDim, colDim);
        Vector[][] goalGrid = new Vector[dimensions.getRow()][dimensions.getColumn()];

        for (int i = 0; i < goalGrid.length; i++)
        {
            for (int j = 0; j < goalGrid[i].length; j++)
            {
                goalGrid[i][j] = new Vector(new Coordinate(i, j));
                Vector cur = goalGrid[i][j];
                double deltaX = goal.getCenter().getX() - cur.getLocation().getX();
                double deltaY = goal.getCenter().getY() - cur.getLocation().getY();
                cur.setΔX(deltaX);
                cur.setΔY(deltaY);
                int xlengths = Math.abs(goal.getCenter().getX() - i);
                int ylengths = Math.abs(goal.getCenter().getY() - j);//a little warning
                Coordinate point = new Coordinate(goal.getCenter().getX(), cur.getLocation().getY());
                double angleToGoal = PhysUtils.computeNewAngleInDegrees(cur.getLocation(), goal.getCenter(), point);
                cur.setAngle(new Degree(angleToGoal));
                if (xlengths + ylengths > PhysUtils.radius)//greater then sphere of influence
                {
                    cur.setWeight(PhysUtils.MAX_WEIGHT);
                    cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.AWAY);
                }
                else
                {
                    if (i < goal.getCenter().getX() - PhysUtils.ARUCO_STOP_RADIUS ||
                            i > goal.getCenter().getX() + PhysUtils.ARUCO_STOP_RADIUS
                            || j < goal.getCenter().getY() - PhysUtils.ARUCO_STOP_RADIUS
                            || j > goal.getCenter().getY() + PhysUtils.ARUCO_STOP_RADIUS)
                    {
                        double strength = xlengths + ylengths;
                        strength /= PhysUtils.STRENGTH_OF_SPHERE;
                        double weight = PhysUtils.MAX_WEIGHT * strength;
                        cur.setWeight((int) Math.round(weight));
                        cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.APPROACHING);
                    }
                    else
                    {
                        cur.setWeight(0);
                        cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.CENTER);
                    }
                }

            }
        }

        return new TerrainMap(goalGrid);
    }

    /**
     * Helps the robot know what to do on the SPECIAL field
     * (╯°□°)╯
     */
    private static boolean seen0 = false;
    private boolean seen180 = false;
    public TerrainMap generateGoalMapSpecial(int rowDim, int colDim, Robot r)
    {
        dimensions = new Dimensions(rowDim, colDim);
        Vector[][] toReturn = new Vector[dimensions.getRow()][dimensions.getColumn()];
        for (int i = 0; i < toReturn.length; i++)
        {
            for (int j = 0; j < toReturn[i].length; j++)
            {
                toReturn[i][j] = new Vector(new Coordinate(i, j));
                Vector cur = toReturn[i][j];

                cur.setWeight(4);
                if (PhysUtils.ROTATE)
                {
                    if (!seen0)
                    {
                        System.out.println("I rotate 0");
                        seen0 = true;
                    }
                    cur.setAngle(new Degree(0));
                }
                else
                {
                    if (!seen180)
                    {
                        System.out.println("I rotate 180");
                        seen180 = true;
                    }
                    cur.setAngle(new Degree(180));
                }
                toReturn[i][j] = cur;
            }
        }

        if (PhysUtils.ROTATE)
        {
            PhysUtils.ROTATE = false;
        }
        else
        {
            PhysUtils.ROTATE = true;
        }
        return new TerrainMap(toReturn);
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
     *  Orientation of an obstacle is NOT RELEVANT. Only the robot.
     */
    public TerrainMap generateObstacleMap(int rowDim, int colDim, Obstacle obstacle, double spreadOfField)
    {
        System.out.println("generateObstacleMap called");
        if (dimensions == null)
            dimensions = new Dimensions(rowDim, colDim);
        dimensions.setRow(rowDim);
        dimensions.setColumn(colDim);

        // For each vector in the field...
        Vector[][] obstacleGrid = new Vector[dimensions.getRow()][dimensions.getColumn()];
        for (int i = 0; i < obstacleGrid.length; i++)
        {
            for (int j = 0; j < obstacleGrid[i].length; j++)
            {
                obstacleGrid[i][j] = new Vector(new Coordinate(i, j));
                // ...set its deltaX and deltaY.
                Vector cur = obstacleGrid[i][j];
                double deltaX = obstacle.getCenter().getX() - cur.getLocation().getX();
                double deltaY = obstacle.getCenter().getY() - cur.getLocation().getY();
                cur.setΔX(-deltaX); // Should these be negative?
                cur.setΔY(-deltaY); // They have to be repulsive in some way.

                Coordinate point = new Coordinate(obstacle.getCenter().getX(),
                        cur.getLocation().getY());
                double angleToGoal = PhysUtils.computeNewAngleInDegrees(cur.getLocation(),
                        obstacle.getCenter(), point);
                cur.setAngle(new Degree((angleToGoal + 180) % 360));

                int xlengths=Math.abs(obstacle.getCenter().getX()-i);
                int ylengths=Math.abs(obstacle.getCenter().getY()-j);
                if (xlengths + ylengths > PhysUtils.radius)//greater then sphere of influence
                {
                    cur.setWeight(0);
                }
                else
                {
                    if (i < obstacle.getCenter().getX() - PhysUtils.ARUCO_STOP_RADIUS ||
                            i > obstacle.getCenter().getX() + PhysUtils.ARUCO_STOP_RADIUS
                            || j < obstacle.getCenter().getY() - PhysUtils.ARUCO_STOP_RADIUS
                            || j > obstacle.getCenter().getY() + PhysUtils.ARUCO_STOP_RADIUS)
                    {
                        double strength=xlengths+ylengths;
                        strength/=PhysUtils.STRENGTH_OF_SPHERE;
                        double weight=PhysUtils.MAX_WEIGHT*(1 - strength);
                        cur.setWeight((int)Math.round(weight));
                    }
                    else
                    {
                        cur.setWeight(PhysUtils.MAX_WEIGHT);
                    }
                }
                obstacleGrid[i][j] = cur;
            }
        }
        // generate the new coordinate here, based on previous coordinate.x + deltaX, prev.y + deltaY

        return new TerrainMap(obstacleGrid);
    }

    /**
     * Generates map for the random field
     */
    public TerrainMap generateRandomFieldMap()
    {
        TerrainMap newmap=new TerrainMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid2]);
        Random myrandom=new Random();
        for (int i=0; i<newmap.getMyMap().length; i++)
        {
            for(int j=0; j<newmap.getMyMap()[i].length; j++)
            {
                newmap.getMyMap()[i][j]=new Vector(new Coordinate(i, j));
                newmap.getMyMap()[i][j].setAngle(new Degree(0));
                newmap.getMyMap()[i][j].setΔX(0);
                newmap.getMyMap()[i][j].setΔY(0);//this is experimental I have no idea what it should actually be
            }
        }


        return newmap;
    }

    /**
     * Combines all the maps into one: the overarching potential field!
     * May not want it in this class but I'm putting it here for now
     */
    public TerrainMap generateCombinedMap(TerrainMap goalMap, TerrainMap obstacleMap, TerrainMap randomMap)
    {
        if (obstacleMap.getMyMap() == null || obstacleMap.getMyMap()[0][0] == null)
        {
            obstacleMap.setMyMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid2]);
            for (int k = 0; k < obstacleMap.getMyMap().length; k++)
            {
                for (int l = 0; l < obstacleMap.getMyMap()[k].length; l++)
                {
                    Vector myNew = new Vector(new Coordinate(0, 0));
                    myNew.setΔX(0);
                    myNew.setΔY(0);
                    obstacleMap.getMyMap()[k][l] = myNew;
                }
            }
        }
        TerrainMap toReturn = new TerrainMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid2]);
        toReturn.fillWithZeroes();
        for(int i=0; i<goalMap.getMyMap().length; i++)
        {
            for(int j=0; j<goalMap.getMyMap()[i].length; j++)
            {
                if (obstacleMap.getMyMap()[i][j].getAngle() == null)
                {
                    obstacleMap.getMyMap()[i][j].setAngle(goalMap.getMyMap()[i][j].getAngle());
                }
                if (goalMap.getMyMap()[i][j].getAngle() == null)
                {
                    goalMap.getMyMap()[i][j].setAngle(obstacleMap.getMyMap()[i][j].getAngle());
                }
                boolean weightsZero = goalMap.getMyMap()[i][j].getWeight() == 0
                        || obstacleMap.getMyMap()[i][j].getWeight() == 0;
                if (!weightsZero)
                {
                    Degree newAngle = new Degree(obstacleMap.getMyMap()[i][j].getAngle().degree -
                            goalMap.getMyMap()[i][j].getAngle().degree);
                    if (newAngle.degree < 0)
                    {
                        newAngle = new Degree(newAngle.degree + 360);
                        newAngle = new Degree(360 - newAngle.degree);
                    }
                    double anglesSummed = goalMap.getMyMap()[i][j].getWeight() +
                            obstacleMap.getMyMap()[i][j].getWeight();
                    double angle1Pull = goalMap.getMyMap()[i][j].getWeight() / anglesSummed;
                    double angle2Pull = obstacleMap.getMyMap()[i][j].getWeight() / anglesSummed;
                    double additionFactor = angle1Pull * newAngle.degree;
                    // add it to angle 1
                    toReturn.getMyMap()[i][j].setAngle(new Degree(obstacleMap.getMyMap()[i][j].getAngle().degree
                        + additionFactor));

                    // Computing weight formula
                    double newWeight = PhysUtils.computeNewWeight(goalMap.getMyMap()[i][j],
                            obstacleMap.getMyMap()[i][j], newAngle);

                    toReturn.getMyMap()[i][j].setWeight((int) Math.round(newWeight));
                    // We can add the random map in later if we so desire.
                }
                else
                {
                    toReturn.getMyMap()[i][j].setAngle(goalMap.getMyMap()[i][j].getAngle());
                    toReturn.getMyMap()[i][j].setWeight(goalMap.getMyMap()[i][j].getWeight());
                }
            }
        }
        return toReturn;
    }

    public ArrayList<Obstacle> getObstacles()
    {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles)
    {
        this.obstacles = obstacles;
    }

}
