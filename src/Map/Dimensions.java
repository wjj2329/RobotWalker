package Map;

/**
 * Created by Alex on 5/17/17.
 * Used to represent dimensions of the grid.
 */
public class Dimensions
{
    private int row;
    private int column;

    public Dimensions(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }
}
