import java.util.Comparator;


public class Point implements Comparable<Point> {
	// compare points by slope to this point
	public final Comparator<Point> SLOPE_ORDER= new Comparator<Point>()
    {
        @Override
        public int compare(Point p0, Point p1)
        {
            return Double.compare(slopeTo(p0), slopeTo(p1));
        }
    };

    //construct the point (x, y)
    public Point(int x_, int y_)
    {
    	x = x_;
    	y = y_;
    }
    // draw this point
    public   void draw()
    {
        StdDraw.point(x, y);
    }

    // draw the line segment from this point to that point
    public   void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    @Override
	public String toString()
	{
        return String.format("(%5d, %5d)",this.x,this.y);
	}

	// is this point lexicographically smaller than that point?
    @Override
    public    int compareTo(Point that)
    {
        if(this.y < that.y)
        {
        	return -1;
        }
        else if(this.y > that.y)
        {
        	return 1;
        }
        else
        {
        	if(this.x < that.x)
        	{
        		return -1;
        	}
        	else if(this.x > that.x)
        	{
        		return 1;
        	}
        	else
        	{
        		return 0;
        	}
        }
    }
    //the slope between this point and that point
    public double slopeTo(Point that)
    {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        if (dy == 0)
        {
        	if(dx == 0)
        	{
        		return Double.NEGATIVE_INFINITY;
        	}
        	return new Double(+0.0);
        }
        else
        {
        	if (dx == 0)
        	{
        		return Double.POSITIVE_INFINITY;
        	}
        	return dy/dx;
        }
    }


    private int x;
    private int y;
}


