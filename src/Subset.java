import java.util.Iterator;


public class Subset {
   public static void main(String[] args)
   {
       int k = Integer.parseInt(args[0]);

	   RandomizedQueue<String> q = new RandomizedQueue<String>();
       for(int i = 0;
    		   i < k && !StdIn.isEmpty();
    		   i++){
    	   q.enqueue(StdIn.readString());
       }
	   Iterator<String> it = q.iterator();
	   while(!q.isEmpty()){
	       System.out.printf("%s\n",q.dequeue());
	   }

   }

}



