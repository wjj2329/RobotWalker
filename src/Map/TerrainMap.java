package Map;

/**
 * Created by williamjones on 5/15/17.
 * Terrain Map: Map of the surrounding terrain.
 */
public class TerrainMap
{
    private Vector[][] myMap;
    private Dimensions dimensions;

    public TerrainMap(Vector[][] myMap)
    {
        this.myMap = myMap;
    }

    public Vector[][] getMyMap()
    {
        return myMap;
    }

    public Dimensions getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions)
    {
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        StringBuilder ss=new StringBuilder();
        for (int i=0; i<myMap.length; i++)
        {
            for (int j=0; j<myMap.length; j++)
            {
                ss.append(myMap[i][j].toString());
            }
        }
       return ss.toString();
    }
}
