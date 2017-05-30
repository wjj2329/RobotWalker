package TestCase;


import RobotFunctions.Decoder;
import RobotFunctions.Robot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by williamjones on 5/24/17.
 */
public class TestCaseForMap {

    /**
     * What a great movie.
     */
    private Robot auntFanny;

    /**
     * A test json. :3
     */
    private static final String goalJson = "{\"robot\": "
            + "{\"orientation\": [0.5, 0.8], "
            + "\"corners\": [[995.0, 262.0], [922.0, 297.0], [882.0, 226.0], [952.0, 190.0]], "
            + "\"center\": [937.75, 243.75]}, "
            + "\"time\": 2011196.65, "
            + "\"23\": "
            + "{\"orientation\": [0.7, -0.6], "
            + "\"corners\": [[787.0, 371.0], [836.0, 430.0], [776.0, 477.0], [726.0, 418.0]], "
            + "\"center\": [781.25, 424.0]}}";

    private static final String obstacleJson = "{\"robot\": "
            + "{\"orientation\": [0.48, 0.88], "
            + "\"corners\": [[995.0, 264.0], [924.0, 299.0], [882.0, 226.0], [954.0, 190.0]], "
            + "\"center\": [938.75, 244.75]}, "
            + "\"time\": 2011162.04, "
            + "\"23\": " // changed from 20 to 23. Shouldn't make a difference. But theoretically will fix bug
            + "{\"orientation\": [-0.29, -0.96], "
            + "\"corners\": [[547.0, 308.0], [625.0, 288.0], [648.0, 363.0], [570.0, 383.0]], "
            + "\"center\": [597.5, 335.5]}, "
            + "\"21\": "
            + "{\"orientation\": [0.17, -0.99], "
            + "\"corners\": [[694.0, 562.0], [774.0, 574.0], [762.0, 655.0], [680.0, 643.0]], "
            + "\"center\": [727.5, 608.5]}"
            + "}";

    /**
     * Initial setup before running the test
     */
    @Before
    public void setUp()
    {
        auntFanny = new Robot();
        Decoder.firstTime = true;
    }

    /**
     * Deconstruction after each individual test
     */
    @After
    public void tearDown()
    {
        auntFanny = null;
    }

    /**
     * Master testing function
     */
    @Test
    public void testingMaster() throws Exception
    {
        goalMapTest();
        obstacleMapTest();
        //System.out.println("Testing Robot's navigation through our potential fields...");
    }

    /**
     * In which we build the map
     */
    private void goalMapTest() throws Exception
    {
        Decoder.setMyMapsFromJson(auntFanny, goalJson, false);

        assertNotNull(auntFanny.getGoalMap());
        assertNotNull(auntFanny.getObstacleMap());
        assertNotNull(auntFanny.getRandomMap());
        assertNotNull(auntFanny.getCombinedMap());

        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getObstacleMap()));
        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getRandomMap()));
        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getCombinedMap()));

//        System.out.println("Printing results to files...");
//        PrintWriter out1 = new PrintWriter("obstacleMap.txt");
//        PrintWriter out2 = new PrintWriter("goalMap.txt");
//        PrintWriter out3 = new PrintWriter("randomMap.txt");
//        PrintWriter out4 = new PrintWriter("combinedMap.txt");
//        out1.println("Viewing generated obstacle map...\n");
//        out1.println(auntFanny.getObstacleMap().toString());
//        out2.println("Viewing generated goal map...\n");
//        out2.println(auntFanny.getGoalMap().toString());
//        out3.println("Viewing generated random map...\n");
//        out3.println(auntFanny.getRandomMap().toString());
//        out4.println("Viewing generated combined map...\n");
//        out4.println(auntFanny.getCombinedMap().toString());
//        out1.close();
//        out2.close();
//        out3.close();
//        out4.close();
    }

    /**
     * Tests our map of obstacles
     */
    private void obstacleMapTest() throws Exception
    {
        System.out.println("Testing obstacle map...");
        Decoder.firstTime = true;
        // System.out.println("Boolean value of firstTime: " + Decoder.firstTime);
        auntFanny = new Robot();
        Decoder.setMyMapsFromJson(auntFanny, obstacleJson, false);

        // Yay code duplication lol
        assertNotNull(auntFanny.getGoalMap());
        assertNotNull(auntFanny.getObstacleMap());
        assertNotNull(auntFanny.getRandomMap());
        assertNotNull(auntFanny.getCombinedMap());

        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getObstacleMap()));
        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getRandomMap()));
        assertFalse(auntFanny.getGoalMap().equals(auntFanny.getCombinedMap()));

        System.out.println("Printing results of obstacle/goal test to files...");
        PrintWriter out1 = new PrintWriter("obstacleMap2.txt");
        PrintWriter out2 = new PrintWriter("goalMap2.txt");
        PrintWriter out3 = new PrintWriter("randomMap2.txt");
        PrintWriter out4 = new PrintWriter("combinedMap2.txt");
        out1.println("Viewing generated obstacle map...\n");
        out1.println(auntFanny.getObstacleMap().toString());
        out2.println("Viewing generated goal map...\n");
        out2.println(auntFanny.getGoalMap().toString());
        out3.println("Viewing generated random map...\n");
        out3.println(auntFanny.getRandomMap().toString());
        out4.println("Viewing generated combined map...\n");
        out4.println(auntFanny.getCombinedMap().toString());
        out1.close();
        out2.close();
        out3.close();
        out4.close();
    }

}
