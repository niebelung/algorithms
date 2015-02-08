
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

    	qu_ = new WeightedQuickUnionUF(N*N);
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
    	Coords c = new Coords(i, j);
        check(c);

        if(!grid_[c.i][c.j])
        {
            grid_[c.i][c.j] = true;
            tryUnionWithNeighbors(c);
            //Should be invoked to raised percolates_ flag
            isFull(i,j);
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

        if(!grid_[c.i][c.j])
        {
        	return false;
        }

        int elemIdx = convertCartesianToOneD(c);

        //Traverse top row
        for (int idx = 0; idx < grid_.length; ++idx)
        {
        	if(!grid_[0][idx])
        	{
        		continue;
        	}

        	int curIdx = convertCartesianToOneD(new Coords(1,idx+1));

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
        //Traverse bottom row
        for (int idx = 0; idx < grid_.length; ++idx)
        {
        	if(isFull(grid_.length, idx+1))
        	{
        		return true;
        	}
        }
        return false;
    }

   private boolean [][] grid_;
   private boolean percolates_ = false;
    private WeightedQuickUnionUF qu_;

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
    	public Coords getLeftNeighbor()
    	{
    		return new Coords((i + 0) + 1,(j - 1) + 1);
    	}
    	public Coords getRightNeighbor()
    	{
    		return new Coords((i + 0) + 1,(j + 1) + 1);
    	}
    	public Coords getTopNeighbor()
    	{
    		return new Coords((i - 1) + 1,(j + 0) + 1);
    	}
    	public Coords getBottomNeighbor()
    	{
    		return new Coords((i + 1) + 1,(j + 0) + 1);
    	}

    }


    private int convertCartesianToOneD(Coords coords)
    {
    	return grid_.length * coords.i + coords.j;
    }

    private boolean isElementExistent(Coords coords)
    {
    	if (coords.i < 0 ||
            coords.i > grid_.length - 1 ||
        	coords.j < 0 ||
        	coords.j > grid_.length - 1)
        {
        	return false;
        }

        return true;
    }

    private void tryUnionWithNeighbors(Coords me)
    {
        Coords left = me.getLeftNeighbor();
        if(isElementExistent(left) )
        {
        	if(grid_[left.i][left.j])
        	{
        		qu_.union(convertCartesianToOneD(me), convertCartesianToOneD(left));
        	}
        }
        Coords right = me.getRightNeighbor();
        if(isElementExistent(right))
        {
        	if(grid_[right.i][right.j])
        	{
        		qu_.union(convertCartesianToOneD(me), convertCartesianToOneD(right));
        	}
        }
        Coords top = me.getTopNeighbor();
        if(isElementExistent(top))
        {
        	if(grid_[top.i][top.j])
        	{
        	    qu_.union(convertCartesianToOneD(me), convertCartesianToOneD(top));
        	}
        }
        Coords bottom = me.getBottomNeighbor();
        if(isElementExistent(bottom) )
        {
        	if(grid_[bottom.i][bottom.j])
        	{
        	    qu_.union(convertCartesianToOneD(me), convertCartesianToOneD(bottom));
        	}
        }
    }

    private void check(Coords coords)
    {
    	if (!isElementExistent(coords))
		{
        	throw new java.lang.IndexOutOfBoundsException();
		}
    }

    public static void main(String[] args)   // test client (optional)
	/**
	 * @param args
	 */
    {
        //TEST Invalid constructor arg
        try
        {
    	Percolation p1 = new Percolation(-1);
        }
        catch (IllegalArgumentException e)
        {
    	System.out.printf("Caught %s when passing -1 to Percolation() in test 1\n",
    			          e.getMessage());
        }

        //TEST Construction 2 by 2 grid

        try
        {
    	Percolation p2 = new Percolation(2);
     	p2.open(1, 1);
    	System.out.printf("opened (1,1) isOpen(1,1)=%b isFull(1,1)=%b percolates()=%b\n",
		          p2.isOpen(1, 1), p2.isFull(1,1), p2.percolates());
       	p2.open(2, 2);
    	System.out.printf("opened (2,2) isOpen(2,2)=%b isFull(2,2)=%b percolates()=%b\n",
     			          p2.isOpen(2,2), p2.isFull(2,2), p2.percolates());
     	p2.open(2, 1);
    	System.out.printf("opened (2,1) isOpen(2,1)=%b isFull(2,1)=%b percolates()=%b\n",
    			          p2.isOpen(2,1), p2.isFull(2,1), p2.percolates());

        }
        catch (IllegalArgumentException e)
        {
    	System.out.printf("Caught %s when passing 2 to Percolation() in test 2\n",
    			          e.getMessage());
        }
        catch (java.lang.IndexOutOfBoundsException x)
        {
        	System.out.printf("%s\n","Caught "+ x +" when running test 2");
        }






    }

}
