

import RobotFunctions.Decoder;
import RobotFunctions.PhysUtils;
import RobotFunctions.Robot;
import TelnetFunctions.Telnet;

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
            //Thread.sleep(2000);
            String s=connection.sendWhere();
            if (PhysUtils.NONCRUCIAL_PRINTS)
            {
                System.out.println("S is " + s);
            }

           if(s.equals("None") || s.equals("") || s.equals("\n")) {
               continue;
           }

           System.out.println("Calling setMyMapsFromJson in the Main class");
            boolean success=Decoder.setMyMapsFromJson(r, s, false);
            if(!success)
            {
                connection.sendSpeed(0,0);
            }
            else
            {
                // call the special goal map instead of the other one
                if (PhysUtils.USE_SPECIAL)
                {
                    r.atEdge(connection, s);
                }
                r.rotateMe(connection);
                double directions[] = r.calculateSpeeds();
                if (PhysUtils.NONCRUCIAL_PRINTS)
                {
                    System.out.println("I say go here " + (int) Math.round(directions[0]) + " " +
                            (int) Math.round(directions[1]));
                }
                connection.sendSpeed((int) Math.round(directions[0]), (int) Math.round(directions[1]));
                if (directions[0] == 0 && directions[1] == 0) {
                    //connection.shutdown();
                    if (PhysUtils.MOVE_ROBOT)
                    {
                        return;
                    }
                }
            }

        }
    }

}
