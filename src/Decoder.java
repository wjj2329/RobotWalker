import org.json.JSONObject;

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
      }
}
