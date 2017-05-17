/**
 * Created by williamjones on 5/15/17.
 * Terrain Map: Map of the surrounding terrain.
 */
public class TerrainMap
{
    private Vector[][] myMap;

    TerrainMap(Vector[][] myMap)
    {
        this.myMap = myMap;
    }

    public Vector[][] getMyMap()
    {
        return myMap;
    }
}
