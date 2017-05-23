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
      public static void setMyMapsFromJson(Robot r, String json)
      {
            // TerrainMap  CREATE THE THREE MAPS FOR THE ROBOT
            // Robot orientation, corners, center, and time; obstacle locations, corners, and center
            JSONObject singMeASongOfJSON = new JSONObject(json);
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

            // Please bear in mind that one obstacle will actually become our goal!
            ArrayList<Obstacle> allMyObstacles = new ArrayList<>();

            final Iterator<String> keys = singMeASongOfJSON.keys();
            Gson yourMomUsesGson = new Gson();
            // This does get rid of the robot and time objects right??
            while (keys.hasNext())
            {
                  final String key = keys.next();
                  Obstacle newObstacle = yourMomUsesGson.fromJson(singMeASongOfJSON.get(key).toString(),
                          Obstacle.class);
                  // It's a square, so let's make the radius just from center to one corner
                  newObstacle.setRadius(PhysUtils.distance(newObstacle.getCenter(), newObstacle.getCorner1()));
                  // Ok, so the number of the obstacle should be the key right?
                  newObstacle.setId(Integer.parseInt(key));
                  allMyObstacles.add(newObstacle);
            }
            machineVision.setObstacles(allMyObstacles);
            processObstacles(allMyObstacles);
      }

      /**
       * Takes care of the obstacles
       * @param obstacles the obstacles
       */
      private static void processObstacles(ArrayList<Obstacle> obstacles)
      {
            System.out.println("Please select a GOAL among the following options:");
            for (int j = 0; j < obstacles.size(); j++)
            {
                  System.out.println(obstacles.get(j).getId() + " ");
            }
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.next());

            for (int i = 0; i < obstacles.size(); i++)
            {
                  Obstacle cur = obstacles.get(i);
                  if (cur.getId() != choice)
                  {
                        machineVision.generateObstacleMap(1000, 1000, cur, 250);
                  }
                  else
                  {
                        Goal g = new Goal(cur);
                        machineVision.generateGoalMap(1000, 1000, g, 250);
                  }
                  // I think we need to adjust the spread based on how well the robot avoids the obstacles
            }
      }
}
