import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by williamjones on 5/21/17.
 * Telnet client.
 */
public class Telnet
{
    private Socket pingsocket;
    private PrintWriter mywriter;
    private BufferedReader myreader;

    Telnet() throws IOException {
        pingsocket=new Socket("localhost", 55555);
        mywriter=new PrintWriter(pingsocket.getOutputStream(), true);
        myreader=new BufferedReader((new InputStreamReader(pingsocket.getInputStream())));
    }


    public String sendWhere() throws IOException
    {
        mywriter.println("where");
        StringBuilder response=new StringBuilder();
        String line;
        while(myreader.ready())
        {
            line=myreader.readLine();
            response.append(line);
        }
        //get stuff from myreader .readline()


        return response.toString();

    }

    public String sendSpeed(double speed1, double speed2) throws IOException
    {

        mywriter.println("speed "+speed1+" "+speed2);

        StringBuilder response=new StringBuilder();
        String line;
        while(myreader.ready())
        {
            line=myreader.readLine();
            response.append(line);
        }
        //get stuff from myreader .readline()

        mywriter.close();
        myreader.close();

        return response.toString();
    }

    public boolean shutdown() throws IOException
    {

        mywriter.println("shutdown");
        mywriter.close();
        myreader.close();
        pingsocket.close();
        return true;
    }
}