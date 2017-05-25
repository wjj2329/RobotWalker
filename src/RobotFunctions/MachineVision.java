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
     */
    public TerrainMap generateGoalMap(int rowDim, int colDim, Goal goal, double spreadOfField, Robot r)
    {
        dimensions = new Dimensions(rowDim, colDim);
        double goalRadius = goal.getRadius();
        Orientation orientation = r.getOrientation();
        Vector[][] goalGrid = new Vector[dimensions.getRow()][dimensions.getColumn()];
        for (int i = 0; i < goalGrid.length; i++)
        {
            for (int j = 0; j < goalGrid[i].length; j++)
            {
                goalGrid[i][j] = new Vector(new Coordinate(i, j));
                Vector cur = goalGrid[i][j];
                double distanceFromGoalToVector = PhysUtils.distance(new Coordinate(i, j), goal.getCenter());
                cur.setAngle(new Degree(PhysUtils.modifiedAngle(orientation)));
                //  changed y-coordinate to negative to flip graph
                Degree θ = cur.getAngle();
                setΔXAndΔYAcceptField(θ, distanceFromGoalToVector, spreadOfField, goalRadius, computeα(), cur);
            }
        }

        return new TerrainMap(goalGrid);
    }

    /**
     * Helps the robot know how to move.
     * (╯°□°)╯
     */
    private void setΔXAndΔYAcceptField(Degree θ, double distanceFromGoalToVector, double spread,
                                       double goalRadius, double α, Vector cur)
    {
        // If we're inside of the goal, don't move any longer.
        if (distanceFromGoalToVector < goalRadius)
        {
            cur.setΔX(0);
            cur.setΔY(0);
            cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.CENTER);
        }
        // If we're close to the field, make a strong potential field.
        else if (goalRadius <= distanceFromGoalToVector && distanceFromGoalToVector <= spread + goalRadius)
        {
            // ∆x=α(d−r)cos(θ) and ∆y=α(d−r)sin(θ)
            cur.setΔX(α * (distanceFromGoalToVector - goalRadius) * Math.cos(θ.degree));
            cur.setΔY(α * (distanceFromGoalToVector - goalRadius) * Math.sin(θ.degree));
            cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.APPROACHING);
        }
        // If we're outside the goal altogether, make a very strong potential field of influence.
        else if (distanceFromGoalToVector > spread + goalRadius)
        {
            //if d > s + r, then ∆x = αs cos(θ) and ∆y = αs sin(θ)
            cur.setΔX(α * spread * Math.cos(θ.degree));
            cur.setΔY(α * spread * Math.sin(θ.degree));
            cur.setGoalProximity(Vector.PROXIMITY_TO_OBJECT.AWAY);
        }
        // you failed
        else
        {
            System.out.println("it broke -__- ");
            cur.setΔX(α * spread * Math.cos(θ.degree));
            cur.setΔY(α * spread * Math.sin(θ.degree));
        }
    }

    /**
     * So that way we can adjust our value
     *  based on this scalar.
     */
    private double computeα()
    {
        return 1;
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
        dimensions.setRow(rowDim);
        dimensions.setColumn(colDim);
        double obstacleRadius = obstacle.getRadius();

        // For each vector in the field...
        Vector[][] obstacleGrid = new Vector[dimensions.getRow()][dimensions.getColumn()];
        for (int i = 0; i < obstacleGrid.length; i++)
        {
            for (int j = 0; j < obstacleGrid[i].length; j++)
            {
                obstacleGrid[i][j] = new Vector(new Coordinate(i, j));
                // ...set its deltaX and deltaY.
                Vector cur = obstacleGrid[i][j];
                // According to the book, this could very well be the distance from the obstacle to the AGENT.
                // However, if we're pre-computing the field, this makes more sense.
                // Because the agent will only be using the method if it's at the cell containing the
                // vector that we're currently analyzing. So it will in effect be the same thing.

                double distanceFromObstacleToVector = PhysUtils.distance(new Coordinate(i, j), obstacle.getCenter());
                cur.setAngle(new Degree(PhysUtils.obstacleAngle(obstacle.getCenter(), new Coordinate(i, j))));
                Degree θ = cur.getAngle();
                setΔXAndΔYRejectField(θ, distanceFromObstacleToVector, spreadOfField, obstacleRadius, computeβ(), cur);
                obstacleGrid[i][j] = cur;
            }
        }
        // generate the new coordinate here, based on previous coordinate.x + deltaX, prev.y + deltaY

        return new TerrainMap(obstacleGrid);
    }

    /**
     * Sets ΔX and ΔY, so the robot knows how to move.
     * Beta = scalar for the strength of this field
     */
    private double smallest = Double.MAX_VALUE;
    private void setΔXAndΔYRejectField(Degree θ, double distanceFromObstacleToVector, double spreadOfField,
                                       double obstacleRadius, double β, Vector cur)
    {
        //System.out.println("POOOOOOOO");
        // This means we're inside of the obstacle. BAD! Get out ASAP!
        if (distanceFromObstacleToVector < obstacleRadius)
        {
            //System.out.println("First if");
            cur.setΔX(-PhysUtils.sign(Math.cos(θ.degree)) * Double.MAX_VALUE);
            cur.setΔY(-PhysUtils.sign(Math.sin(θ.degree)) * Double.MAX_VALUE);
            cur.setObstacleProximity(Vector.PROXIMITY_TO_OBJECT.CENTER);
            //System.out.println("Is this set correctly? " + cur.getObstacleProximity().toString());
        }
        // This means we're approaching the obstacle. The degree of repulsion is computed here.
        else if (obstacleRadius <= distanceFromObstacleToVector && distanceFromObstacleToVector <= spreadOfField
                + obstacleRadius)
        {
            //System.out.println("second if");
            cur.setΔX(-β*(spreadOfField + distanceFromObstacleToVector - obstacleRadius) * Math.cos(θ.degree));
            cur.setΔY(-β*(spreadOfField + distanceFromObstacleToVector - obstacleRadius) * Math.sin(θ.degree));
            cur.setObstacleProximity(Vector.PROXIMITY_TO_OBJECT.APPROACHING);
        }
        // Outside of the sphere of influence = do nothing.
        else if (distanceFromObstacleToVector > spreadOfField + obstacleRadius)
        {
            //System.out.println("third if");
            cur.setΔX(0);
            cur.setΔY(0);
            cur.setObstacleProximity(Vector.PROXIMITY_TO_OBJECT.AWAY);
        }
        else
        {
            // You broke it, son. You failed. why, oh why have you failed me?
            System.out.println("You suck.");
            cur.setΔX(0);
            cur.setΔY(0);
        }
        //System.out.println("weedle");
    }

    /**
     * Please code this!
     *  This will be determining our beta scalar. It might have to do with the randomly generated
     *  field map, but I'm not entirely certain.
     *  This will probably need some parameters at some point as well.
     *  "allows the agent to scale the strength of this field" ?
     *
     * Test value: 1.
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
        TerrainMap newmap=new TerrainMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid]);
        Random myrandom=new Random();
        for (int i=0; i<newmap.getMyMap().length; i++)
        {
            for(int j=0; j<newmap.getMyMap().length; j++)
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
            obstacleMap.setMyMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid]);
            for (int k = 0; k < obstacleMap.getMyMap().length; k++)
            {
                for (int l = 0; l < obstacleMap.getMyMap()[k].length; l++)
                {
                    Vector myNew = new Vector(new Coordinate(0, 0));
                    myNew.setΔX(0);
                    myNew.setΔY(0);
                    obstacleMap.getMyMap()[k][l] = myNew;
                    //System.out.println("My new's delta x is " + myNew.getΔX());
                }
            }
        }
        TerrainMap toReturn = new TerrainMap(new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid]);
        toReturn.fillWithZeroes();
        for(int i=0; i<goalMap.getMyMap().length; i++)
        {
            for(int j=0; j<goalMap.getMyMap()[i].length; j++)
            {
                //goalMap.getMyMap()[i][j].getAngle().Add(obstacleMap.getMyMap()[i][j].getAngle());
                //goalMap.getMyMap()[i][j].getAngle().Add(randomMap.getMyMap()[i][j].getAngle());  //adds the two together includes mod
//                if (i < 50 && j < 50)
//                {
//                    System.out.println("Old x: " + goalMap.getMyMap()[i][j].getΔX());
//                    System.out.println("New x: " + (goalMap.getMyMap()[i][j].getΔX()+
//                            obstacleMap.getMyMap()[i][j].getΔX()
//                            +randomMap.getMyMap()[i][j].getΔX()));
//                    System.out.println("Old y: " + goalMap.getMyMap()[i][j].getΔY());
//                    System.out.println("New y: " + (goalMap.getMyMap()[i][j].getΔY()+
//                            obstacleMap.getMyMap()[i][j].getΔY()
//                            +randomMap.getMyMap()[i][j].getΔY()));
//                }
                toReturn.getMyMap()[i][j].setΔX(goalMap.getMyMap()[i][j].getΔX()+obstacleMap.getMyMap()[i][j].getΔX()
                        +randomMap.getMyMap()[i][j].getΔX());
                toReturn.getMyMap()[i][j].setΔY(goalMap.getMyMap()[i][j].getΔY()+obstacleMap.getMyMap()[i][j].getΔY()
                        +randomMap.getMyMap()[i][j].getΔY());
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
