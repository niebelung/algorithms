

public class PercolationStats
{

   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid...
   {
       if (T <= 0)
	   {throw new IllegalArgumentException("Experiment series should be larger than zero!");}
       if (N <= 0)
	   {throw new IllegalArgumentException("Grid size should be larger than zero!");}

       T_ = T;
       thresholdSeries_ = new double[T];

       //Random random = new Random();

       for(int k = 0; k < T; ++k)
       {
    	   Percolation percolation = new Percolation(N);

    	   int cnt = 0;
    	   while(! percolation.percolates())
	       {
	    	   int i = StdRandom.uniform(N) + 1;
	    	   int j = StdRandom.uniform(N) + 1;

	    	   if(!percolation.isOpen(i, j))
	    	   {
		    	   percolation.open(i, j);
		    	   cnt++;
	    	   }
	       }
    	   thresholdSeries_[k] = (double)cnt / (N*N);
       }


   }
   public double mean()                      // sample mean of percolation threshold
   {
       return StdStats.mean(thresholdSeries_);
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(thresholdSeries_);
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
       return mean() - 1.96 *stddev()/Math.sqrt(T_);
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
       return mean() + 1.96 *stddev()/Math.sqrt(T_);
   }

   public static void main(String[] args)    // test client (described below)
   {
       //System.out.println("TEST args[" + args.length + "] "+ args[0] + args[1]);

	   if (args.length != 2)
		   {throw new IllegalArgumentException("Should 2 arguments be provided");}

       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);

       if (T <= 0)
	   {throw new IllegalArgumentException("Experiment series should be larger than zero!");}
       if (N <= 0)
	   {throw new IllegalArgumentException("Grid size should be larger than zero!");}

       Stopwatch sw = new Stopwatch();

       PercolationStats stats = new PercolationStats(N,T);



   	   System.out.println("time[s]                 = " + sw.elapsedTime());
	   System.out.println("mean                    = " + stats.mean());
	   System.out.println("stddev                  = " + stats.stddev());
	   System.out.println("95% confidence interval = " +
	                      stats.confidenceLo() + ", " + stats.confidenceHi());;

   }

   //private Percolation percolation_;
   private double thresholdSeries_[];
   private int T_;


}
