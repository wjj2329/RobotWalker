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
      public static boolean setMyMapsFromJson(Robot r, String json)
      {
            if (machineVision == null)
            {
                  machineVision = new MachineVision();
            }
            // System.out.println("Meet my neighbor, SpongeBob. HI");
            // Map.TerrainMap  CREATE THE THREE MAPS FOR THE ROBOT
            //System.out.println("This is it " + json);
            // RobotFunctions.RobotFunctions orientation, corners, center, and time; obstacle locations, corners, and center
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

            // rounding...
            r.setOrientation(new Coordinate((int)a, (int)b));
            r.setCurrentCenterPosition(new Coordinate((int)x, (int)y));
            // do we need this? :o
            double elapsedTime = singMeASongOfJSON.getDouble("time");

            // Obstacle computation. We can do this later, technically.
            if (firstTime) // What if I comment this out?
            {
                  //System.out.println("I should see this twice ONLY if I'm testing Junit.");
                  // Please bear in mind that one obstacle will actually become our goal!
                  ArrayList<Obstacle> allMyObstacles = new ArrayList<>();

                  final Iterator<String> keys = singMeASongOfJSON.keys();
                  Gson yourMomUsesGson = new Gson();
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
                              // The thing is, I have to do more than just this. It returns a lot of arrays and stuff
                              // inside of it. I have to understand what keys really are.
                              // Aha. We can't DIRECTLY serialize it. We have to get the thingy

                              // It's a square, so let's make the radius just from center to one corner
                              newObstacle.setRadius(PhysUtils.distance(newObstacle.getCenter(), newObstacle.getCorner1()));
                              // Ok, so the number of the obstacle should be the key right?
                              newObstacle.setId(Integer.parseInt(key));
                              allMyObstacles.add(newObstacle);
                        }
                  }
                  machineVision.setObstacles(allMyObstacles);
                  processObstacles(allMyObstacles, r);
                  r.setRandomMap(machineVision.generateRandomFieldMap());
                  // This will only work if we've set this variables for the robot.
                  r.setCombinedMap(machineVision.generateCombinedMap(r.getGoalMap(), r.getObstacleMap(),
                          r.getRandomMap()));
                  firstTime = false;
            }
            return true;
      }

      /**
       * Takes care of the obstacles
       * @param obstacles the obstacles
       */
      private static void processObstacles(ArrayList<Obstacle> obstacles, Robot r)
      {
            System.out.println("Please select a GOAL among the following options:");
            ArrayList<TerrainMap> obstacleMaps = new ArrayList<>();
            for (int j = 0; j < obstacles.size(); j++)
            {
                  System.out.println(obstacles.get(j).getId() + " ");
            }
            // Always remember to change this variable to false if you're not running a Junit test
            boolean testing = false;
            int choice = -1;
            if (!testing)
            {
                  Scanner scanner = new Scanner(System.in);
                  choice = Integer.parseInt(scanner.next());
            }
            else
            {
                  choice = 23;
            }

            for (int i = 0; i < obstacles.size(); i++)
            {
                  Obstacle cur = obstacles.get(i);
                  if (cur.getId() != choice)
                  {
                        // Okay, this might be a problem: I generate a SEPARATE obstacle map for each one.
                        cur.setRadius(PhysUtils.distance(cur.getCenter(), cur.getCorner1()));
                        obstacleMaps.add(machineVision.generateObstacleMap(PhysUtils.sizeOfOurGrid,
                                PhysUtils.sizeOfOurGrid, cur,250));
                  }
                  else
                  {
                        Goal g = new Goal(cur);
                        r.setGoalMap(machineVision.generateGoalMap(PhysUtils.sizeOfOurGrid,
                                PhysUtils.sizeOfOurGrid, g,250));
                  }
                  // I think we need to adjust the spread based on how well the robot avoids the obstacles
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
            Vector[][] combinedObstacleMap = new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid];
            for (int i = 0; i < obstacleMaps.size(); i++)
            {
                  TerrainMap cur = obstacleMaps.get(i);
                  Vector[][] curMap = cur.getMyMap();
                  for (int j = 0; j < curMap.length; j++)
                  {
                        for (int k = 0; k < curMap[j].length; k++)
                        {
                              // Add the vectors
                              //combinedObstacleMap[j][k] += curMap[j][k];
                              combinedObstacleMap[j][k] = new Vector(new Coordinate(j, k));
                              Vector here = combinedObstacleMap[j][k];
                              here.setΔX(here.getΔX() + curMap[j][k].getΔX());
                              here.setΔY(here.getΔY() + curMap[j][k].getΔY());
                              // something about proximity type. won't be 100% accurate
                              here.setObstacleProximity(curMap[j][k].getObstacleProximity());
                        }
                  }
            }
            return new TerrainMap(combinedObstacleMap);
      }

}
