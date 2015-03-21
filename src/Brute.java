import java.util.Arrays;


public class Brute
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

	   StdDraw.setXscale(0, 32768);
	   StdDraw.setYscale(0, 32768);
	   StdDraw.setPenRadius(.02);

	   for(int i = 0; i < N; i++)
	   {
		   int x = in.readInt();
		   int y = in.readInt();
		   points[i] = new Point(x, y);
		   points[i].draw();
	   }

	   for(int i = 0; i < N; i++)
	   {
		   for(int j = i + 1; j < N; j++)
		   {
			   for(int k = j + 1; k < N; k++)
			   {
				   for(int l = k + 1; l < N; l++)
				   {
					   if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
							   points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
					   {
						   Point [] arr = {points[i],points[j],points[k],points[l]};
						   Arrays.sort(arr);
						   System.out.printf("%s -> %s -> %s -> %s\n",
								             arr[0].toString(), arr[1].toString(),
								             arr[2].toString(),arr[3].toString());
						   StdDraw.setPenRadius(.005);
						   arr[0].drawTo(arr[3]);
					   }
				   }
			   }
		   }
	   }
   }

}
