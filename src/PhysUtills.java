/**
 * Created by williamjones on 5/16/17.
 */
public class PhysUtills
{

    public static double distance (Coordinate point1, Coordinate point2)
    {
        double first=point1.getX()-point2.getX();
        double second=point1.getY()-point2.getY();
        return Math.sqrt((first*first)+(second*second));
    }
    public static double angle(Coordinate point1, Coordinate point2)
    {
         return Math.atan((point2.getY()-point1.getY())/(point2.getX()-point1.getX()));
    }


}
