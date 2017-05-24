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
}
