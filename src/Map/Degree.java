package Map;

/**
 * Created by williamjones on 5/23/17.
 */
public class Degree {
    public Degree(double degree)
    {
        this.degree=degree;
    }
    public double degree;
    public void  Add(Degree d)
    {
        this.degree=(d.degree+this.degree)%360;
    }


}
