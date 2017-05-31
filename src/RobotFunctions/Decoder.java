package RobotFunctions;

import Map.*;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by williamjones on 5/21/17.
 * JSON decoder
 */
public class Decoder
{
      /**
       * Our machine vision class used to create maps and such
       */
      private static MachineVision machineVision;

      /**
       * So we don't recompute!
       */
      public static boolean firstTime = true;

      /**
       * Default constructor
       */
      public Decoder()
      {
            machineVision = new MachineVision();
      }

      /**
       * Comes from WHERE command
       * @param r: our robot
       * @param json: the commands in json format, to be converted
       *            into Java objects.
       */
      public static boolean setMyMapsFromJson(Robot r, String json, boolean callingFromAtEdge)
      {
            if (machineVision == null)
            {
                  machineVision = new MachineVision();
            }
            JSONObject singMeASongOfJSON = new JSONObject(json);
            if(!singMeASongOfJSON.has("robot"))
            {
                  System.out.println("ROBOT NOT FOUND");
                  return false;
            }
            JSONObject robot = singMeASongOfJSON.getJSONObject("robot");
            JSONArray centerCoordinates = robot.getJSONArray("center");
            double x = centerCoordinates.getDouble(0);
            double y = centerCoordinates.getDouble(1);
            JSONArray orientationCoordinates = robot.getJSONArray("orientation");
            double a = orientationCoordinates.getDouble(0);
            double b = orientationCoordinates.getDouble(1);

            r.setOrientation(new Orientation(a, b));
            r.setCurrentCenterPosition(new Coordinate((int)x, (int)y));
            double elapsedTime = singMeASongOfJSON.getDouble("time");

            // Obstacle computation.
            if (firstTime)
            {
                  // One obstacle will actually become our goal!
                  ArrayList<Obstacle> allMyObstacles = new ArrayList<>();

                  final Iterator<String> keys = singMeASongOfJSON.keys();
                  while (keys.hasNext())
                  {
                        final String key = keys.next();
                        if (!key.equals("robot") && !key.equals("time"))
                        {
                              // this broke eh
                              JSONObject jason = (JSONObject) singMeASongOfJSON.get(key);
                              JSONArray orientation = jason.getJSONArray("orientation");
                              double xCoord = orientation.getDouble(0);
                              double yCoord = orientation.getDouble(1);
                              Coordinate res = new Coordinate((int) xCoord, (int) yCoord);
                              JSONArray corners = jason.getJSONArray("corners");
                              JSONArray corner1 = corners.getJSONArray(0);
                              JSONArray corner2 = corners.getJSONArray(1);
                              JSONArray corner3 = corners.getJSONArray(2);
                              JSONArray corner4 = corners.getJSONArray(3);
                              Coordinate corner1Coordinate = new Coordinate((int) corner1.getDouble(0),
                                      (int) corner1.getDouble(1));
                              Coordinate corner2Coordinate = new Coordinate((int) corner2.getDouble(0),
                                      (int) corner2.getDouble(1));
                              Coordinate corner3Coordinate = new Coordinate((int) corner3.getDouble(0),
                                      (int) corner3.getDouble(1));
                              Coordinate corner4Coordinate = new Coordinate((int) corner4.getDouble(0),
                                      (int) corner4.getDouble(1));

                              JSONArray center = jason.getJSONArray("center");
                              Coordinate centerCoord = new Coordinate((int) center.getDouble(0),
                                      (int) center.getDouble(1));
                              Obstacle newObstacle = new Obstacle(centerCoord, corner1Coordinate, corner2Coordinate,
                                      corner3Coordinate, corner4Coordinate, res,
                                      PhysUtils.distance(centerCoord, corner1Coordinate));
                              // It's a square, so let's make the radius just from center to one corner
                              newObstacle.setRadius(PhysUtils.distance(newObstacle.getCenter(), newObstacle.getCorner1()));
                              newObstacle.setId(Integer.parseInt(key));
                              allMyObstacles.add(newObstacle);
                        }
                  }
                  machineVision.setObstacles(allMyObstacles);

                processObstacles(allMyObstacles, r, PhysUtils.USE_SPECIAL);
                  r.setRandomMap(machineVision.generateRandomFieldMap());
                  // This will only work if we've set this variables for the robot.
                  r.setCombinedMap(machineVision.generateCombinedMap(r.getGoalMap(), r.getObstacleMap(),
                          r.getRandomMap()));
                  firstTime = false;
            }
            else if (PhysUtils.USE_SPECIAL && callingFromAtEdge)
            {
                // Recalculate the combining of the obstacles and the goal map
                processObstacles(machineVision.getObstacles(), r, PhysUtils.USE_SPECIAL);
                r.setCombinedMap(machineVision.generateCombinedMap(r.getGoalMap(), r.getObstacleMap(),
                        r.getRandomMap()));
            }
            return true;
      }

      /**
       * Takes care of the obstacles
       * @param obstacles the obstacles
       */
      private static void processObstacles(ArrayList<Obstacle> obstacles, Robot r, boolean special)
      {
          ArrayList<TerrainMap> obstacleMaps = new ArrayList<>();
          if (!special)
          {
              System.out.println("Please select a GOAL among the following options:");
              for (int j = 0; j < obstacles.size(); j++)
              {
                  System.out.println(obstacles.get(j).getId() + " ");
              }
          }
            // Always remember to change this variable to false if you're not running a Junit test
            boolean testing = false;
            int choice;
            if (special)
            {
                choice = -1;
            }
            else if (!testing)
            {
                  Scanner scanner = new Scanner(System.in);
                  choice = Integer.parseInt(scanner.next());
            }
            else
            {
                  choice = 23;
            }
            if (obstacles.size() == 0 && choice == -1)
            {
                r.setGoalMap(machineVision.generateGoalMap(PhysUtils.sizeOfOurGrid,
                        PhysUtils.sizeOfOurGrid2, new Goal(null),250, r));
            }

            for (int i = 0; i < obstacles.size(); i++)
            {
                  Obstacle cur = obstacles.get(i);
                  if (choice == -1)
                  {
                      cur.setRadius(PhysUtils.distance(cur.getCenter(), cur.getCorner1()));
                      obstacleMaps.add(machineVision.generateObstacleMap(PhysUtils.sizeOfOurGrid,
                              PhysUtils.sizeOfOurGrid2, cur,250));

                      r.setGoalMap(machineVision.generateGoalMap(PhysUtils.sizeOfOurGrid,
                              PhysUtils.sizeOfOurGrid2, new Goal(cur),250, r));
                  }
                  else if (cur.getId() != choice)
                  {
                        // I generate a SEPARATE obstacle map for each one.
                        cur.setRadius(PhysUtils.distance(cur.getCenter(), cur.getCorner1()));
                        obstacleMaps.add(machineVision.generateObstacleMap(PhysUtils.sizeOfOurGrid,
                                PhysUtils.sizeOfOurGrid2, cur,250));
                  }
                  else
                  {
                        Goal g = new Goal(cur);
                        r.setGoalMap(machineVision.generateGoalMap(PhysUtils.sizeOfOurGrid,
                                PhysUtils.sizeOfOurGrid2, g,250, r));
                  }
            }
            TerrainMap sol = combineObstacleMaps(obstacleMaps);
            if (sol.getMyMap() == null)
            {
                  r.setObstacleMap(null);
            }
            else
            {
                  r.setObstacleMap(sol);
            }
      }

      /**
       * Combines all the obstacle maps into one.
       */
      private static TerrainMap combineObstacleMaps(ArrayList<TerrainMap> obstacleMaps)
      {
            Vector[][] combinedObstacleMap = new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid2];
            TerrainMap temp = new TerrainMap(combinedObstacleMap);
            temp.fillWithZeroes();
            combinedObstacleMap = temp.getMyMap();
            if (obstacleMaps.size() == 0)
            {
                return temp;
            }
          if (obstacleMaps.size() == 1)
            {
                return obstacleMaps.get(0);
            }

            Vector[][] firstMap = obstacleMaps.get(0).getMyMap();
            for (int i = 0; i < combinedObstacleMap.length; i++)
            {
                for (int j = 0; j < combinedObstacleMap[i].length; j++)
                {
                    combinedObstacleMap[i][j].setAngle(firstMap[i][j].getAngle());
                    combinedObstacleMap[i][j].setWeight(firstMap[i][j].getWeight());
                }
            }

            for (int iter = 1; iter < obstacleMaps.size(); iter++)
            {
                // For each obstacle map, factor it in.
                TerrainMap currentMap = obstacleMaps.get(iter);
                for (int i = 0; i < currentMap.getMyMap().length; i++)
                {
                    for (int j = 0; j < currentMap.getMyMap()[i].length; j++)
                    {
                        // Use combinedObstacleMap and currentMap
                        // currentMap = goalMap
                        // combinedObstacleMap = obstacleMap
                        if (currentMap.getMyMap()[i][j].getAngle() == null)
                        {
                            currentMap.getMyMap()[i][j].setAngle(combinedObstacleMap[i][j].getAngle());
                        }
                        if (combinedObstacleMap[i][j].getAngle() == null)
                        {
                            combinedObstacleMap[i][j].setAngle(currentMap.getMyMap()[i][j].getAngle());
                        }
                        boolean weightsZero = combinedObstacleMap[i][j].getWeight() == 0
                                || currentMap.getMyMap()[i][j].getWeight() == 0;
                        if (!weightsZero)
                        {
                            Degree newAngle = new Degree(combinedObstacleMap[i][j].getAngle().degree -
                                    currentMap.getMyMap()[i][j].getAngle().degree);
                            if (newAngle.degree < 0)
                            {
                                newAngle = new Degree(newAngle.degree + 360);
                                newAngle = new Degree(360 - newAngle.degree);
                            }
                            double anglesSummed = currentMap.getMyMap()[i][j].getWeight() +
                                    combinedObstacleMap[i][j].getWeight();
                            double angle1Pull = currentMap.getMyMap()[i][j].getWeight() / anglesSummed;
                            double angle2Pull = combinedObstacleMap[i][j].getWeight() / anglesSummed;
                            double additionFactor = angle1Pull * newAngle.degree;
                            // add it to angle 1
                            combinedObstacleMap[i][j].setAngle(new Degree
                                    (combinedObstacleMap[i][j].getAngle().degree + additionFactor));

                            // Computing weight formula
                            double newWeight = PhysUtils.computeNewWeight(currentMap.getMyMap()[i][j],
                                    combinedObstacleMap[i][j], newAngle);

                            combinedObstacleMap[i][j].setWeight((int) Math.round(newWeight));
                        }
                        else
                        {
                            combinedObstacleMap[i][j].setAngle(currentMap.getMyMap()[i][j].getAngle());
                            combinedObstacleMap[i][j].setWeight(currentMap.getMyMap()[i][j].getWeight());
                        }
                    }
                }
            }
            return new TerrainMap(combinedObstacleMap);
      }

}
