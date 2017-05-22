import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by williamjones on 5/21/17.
 */
public class Telnet {


    public String  where() throws IOException {
        Socket pingsocket=null;
        PrintWriter mywriter=null;
        BufferedReader myreader=null;
        try{
            pingsocket=new Socket("0.0.0.0", 5555);
            mywriter=new PrintWriter(pingsocket.getOutputStream(), true);
            myreader=new BufferedReader((new InputStreamReader(pingsocket.getInputStream())));


        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        mywriter.println("where");
        StringBuilder response=new StringBuilder();
        String line;
        while((line=myreader.readLine())!=null)
        {
            response.append(line);
        }
        //get stuff from myreader .readline()

        mywriter.close();
        myreader.close();
        pingsocket.close();
        return response.toString();

    }

    public String speed(int speed1, int speed2) throws IOException
    {
        Socket pingsocket=null;
        PrintWriter mywriter=null;
        BufferedReader myreader=null;
        try{
            pingsocket=new Socket("0.0.0.0", 5555);
            mywriter=new PrintWriter(pingsocket.getOutputStream(), true);
            myreader=new BufferedReader((new InputStreamReader(pingsocket.getInputStream())));


        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        mywriter.println("speed "+speed1+" "+speed2);

        StringBuilder response=new StringBuilder();
        String line;
        while((line=myreader.readLine())!=null)
        {
            response.append(line);
        }
        //get stuff from myreader .readline()

        mywriter.close();
        myreader.close();
        pingsocket.close();
        return response.toString();
    }

    public boolean shutdown() throws IOException
    {
        Socket pingsocket=null;
        PrintWriter mywriter=null;
        BufferedReader myreader=null;
        try{
            pingsocket=new Socket("0.0.0.0", 5555);
            mywriter=new PrintWriter(pingsocket.getOutputStream(), true);
            myreader=new BufferedReader((new InputStreamReader(pingsocket.getInputStream())));


        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        mywriter.println("shutdown");
        mywriter.close();
        myreader.close();
        pingsocket.close();
        return true;
    }
}