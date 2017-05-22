import java.io.IOException;

/**
 * Created by williamjones on 5/15/17.
 * Main class.
 */
public class Main
{
    public static void main(String argv[]) throws IOException {
        Robot r = new Robot();
        System.out.println("Hi. My name is Robot \uD83E\uDD16");
        Telnet connection=new Telnet();
        while(true)
        {
            String action =connection.where();

        }
    }
}
