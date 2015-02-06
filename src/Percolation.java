public class Percolation {

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
    	if(N <= 0) throw new IllegalArgumentException("Grid size may not be less than zero!");

    	grid_ = new boolean [N][N];

    	for(boolean row [] : grid_)
    	{
    		for(boolean item : row)
    		{
    			item = false;
    		}
    	}

    	qu_ = new QuickUnionUF(N*N);
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        Coords c = new Coords(i, j);
        check(c);

        if(!grid_[c.i][c.j])
        {
            grid_[c.i][c.j] = true;
            traverseNeighbors(c);
        }
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        Coords c = new Coords(i, j);
        check(c);
        return grid_[c.i][c.j];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        Coords c = new Coords(i, j);
        check(c);

        int elemIdx = convertCartesianToOneD(c);

        for (int idx = 0; idx < grid_.length; ++i)
        {
        	if(!grid_[0][idx])
        	{
        		continue;
        	}

        	int curIdx = convertCartesianToOneD(new Coords(0,idx));

        	if(qu_.connected(elemIdx,curIdx))
        	{
        		//If in the bottom row
        		if(c.i == grid_.length - 1)
        		{
        			// We just have discovered that the system percolates
        			percolates_ = true;
        		}
        		return true;
        	}
        }

        return false;
    }

    public boolean percolates()             // does the system percolate?
    {
        return percolates_;
    }

    public static void main(String[] args)   // test client (optional)
	/**
	 * @param args
	 */
    {
		// TODO Auto-generated method stub

    }

   private boolean [][] grid_;
   private boolean percolates_ = false;
    private QuickUnionUF qu_;

    //Class representing zero-based cartesian coords
    // Constructed by one-based coords
    private class Coords
    {
    	public int i;
    	public int j;

    	public Coords(int _i, int _j)
    	{
    		i = _i - 1;
    		j = _j - 1;
    	}
    }


    private int convertCartesianToOneD(Coords coords)
    {
    	return grid_.length * (coords.i - 1) + coords.j - 1;
    }

    private void unionWith(Coords coords)
    {
        //TODO
    }

    private boolean isElementExistent(Coords coords)
    {
        if (coords.i < 0 ||
            coords.i > grid_.length - 1 ||
        	coords.j < 0 ||
        	coords.j > grid_.length-1)
        {
        	return false;
        }
        return true;
    }

    private int traverseNeighbors(Coords coords)
    {
        //TODO
    }

    private void check(Coords coords)
    {
    	if (!isElementExistent(coords))
		{
        	throw new IllegalArgumentException();
		}
    }

}
