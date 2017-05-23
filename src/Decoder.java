import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by williamjones on 5/21/17.
 * JSON decoder
 */
public class Decoder
{
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
            double time = singMeASongOfJSON.getDouble("time");

            // Please bear in mind that one obstacle will actually become our goal!
            ArrayList<Obstacle> allMyObstacles = new ArrayList<>();
            // get all the keys
            // timestamp
            final Iterator<String> keys = singMeASongOfJSON.keys();
            Gson yourMomUsesGson = new Gson();
            // This does get rid of the robot and time objects right??
            while (keys.hasNext())
            {
                  final String key = keys.next();
                  allMyObstacles.add(yourMomUsesGson.fromJson(singMeASongOfJSON.get(key).toString(),
                          Obstacle.class));
            }

            // I don't know what to do with these obstacles!
            // Oh, right. Can't test this until we get the server running. Hmm.
      }
}
