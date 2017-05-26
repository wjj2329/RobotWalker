

import RobotFunctions.Decoder;
import RobotFunctions.Robot;

import java.io.IOException;

/**
 * Created by williamjones on 5/15/17.
 * Main class.
 */
public class Main
{
    public static void main(String argv[]) throws IOException, InterruptedException
    {
        Robot r = new Robot();
        System.out.println("Hi. My name is Robot \uD83E\uDD16");
        Telnet connection=new Telnet();

        while(true)
        {
            Thread.sleep(2000);
            String s=connection.sendWhere();
            System.out.println("S is " + s);

           if(s.equals("None") || s.equals("") || s.equals("\n")) {
               continue;
           }

            boolean success=Decoder.setMyMapsFromJson(r, s);
            if(!success)
            {
                connection.sendSpeed(0,0);

            }
            else {
                double directions[] = r.calculateSpeeds();
                System.out.println("I say go here " + (int) Math.round(directions[0]) + " " +
                        (int) Math.round(directions[1]));
                connection.sendSpeed((int) Math.round(directions[0]), (int) Math.round(directions[1]));
                if (directions[0] == 0 && directions[1] == 0) {
                    connection.shutdown();
                    return;
                }
            }

        }
    }

}
