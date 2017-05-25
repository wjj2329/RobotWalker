package Map;

import RobotFunctions.PhysUtils;

import java.util.Arrays;

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

    public void fillWithZeroes()
    {
        if (myMap == null)
        {
            myMap = new Vector[PhysUtils.sizeOfOurGrid][PhysUtils.sizeOfOurGrid];
        }
        for (int i = 0; i < myMap.length; i++)
        {
            for (int j = 0; j < myMap.length; j++)
            {
                myMap[i][j] = new Vector(new Coordinate(i, j));
                myMap[i][j].setΔX(0);
                myMap[i][j].setΔY(0);
            }
        }
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerrainMap that = (TerrainMap) o;

        if (!Arrays.deepEquals(myMap, that.myMap)) return false;
        return dimensions != null ? dimensions.equals(that.dimensions) : that.dimensions == null;
    }

    @Override
    public int hashCode()
    {
        int result = Arrays.deepHashCode(myMap);
        result = 31 * result + (dimensions != null ? dimensions.hashCode() : 0);
        return result;
    }

    public Vector[][] getMyMap()
    {
        return myMap;
    }

    public void setMyMap(Vector[][] myMap)
    {
        this.myMap = myMap;
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
