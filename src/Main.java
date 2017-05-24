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
            String s=connection.sendWhere();
            System.out.println(s);
            Decoder.setMyMapsFromJson(r, s);

           Thread.sleep(3000);
           int directions[] = r.calculateSpeeds();
           String speedResult = connection.sendSpeed(directions[0], directions[1]);
           if(directions[0] == 0 && directions[1] == 0)
           {
               connection.shutdown();
               return;
           }

        }
    }
}
