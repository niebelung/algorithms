import java.util.Arrays;


public class Fast
{
   public static void main(String[] args)
   {
	   if(args.length != 1)
	   {
		   throw new IllegalArgumentException("One argument required!");
	   }
	   String path = args[0];

	   In in = new In(args[0]);
	   int N = in.readInt();
	   Point [] points  = new Point[N];
	   Point [] pointsBySlope  = new Point[N];

	   StdDraw.setXscale(0, 32768);
	   StdDraw.setYscale(0, 32768);
	   StdDraw.setPenRadius(.02);

	   for(int i = 0; i < N; i++)
	   {
		   int x = in.readInt();
		   int y = in.readInt();
		   points[i] = new Point(x, y);
		   pointsBySlope[i] = points[i];
		   points[i].draw();
	   }
	   StdDraw.setPenRadius(.005);

	   Arrays.sort(points);

	   for(int i = 0; i < N; i++)
	   {
		   Arrays.sort(pointsBySlope,points[i].SLOPE_ORDER);
		   int first = 0, last = 0;
		   double slope = points[i].slopeTo(pointsBySlope[0]);
		   for(int j = 1;j < N;j++)
		   {
			   double slope_ = points[i].slopeTo(pointsBySlope[j]);
			   if(slope != slope_ || j == N - 1)
               {
            	   last = (j < N - 1) ? j : (slope != slope_) ? j : j+1;
            	   if((last - first >= 3))
            	   {
            		   Arrays.sort(pointsBySlope,first,last);
            		   if(points[i].compareTo(pointsBySlope[first]) < 0)
            		   {
            			   points[i].drawTo(pointsBySlope[last - 1]);
            			   System.out.printf("%s", points[i].toString());
            			   for(int pos = first; pos < last;++pos)
            			   {
            				   System.out.printf(" -> %s",pointsBySlope[pos].toString());
            		       }
            			   System.out.printf("\n");
            		   }
            	   }
				   slope = slope_;
				   first = last;
               }
               else
               {
            	   last = j;
               }
		   }
	   }
   }

}
