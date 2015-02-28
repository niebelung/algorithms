import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] queue_;
    private int cnt_;

    // construct an empty randomized queue
	public RandomizedQueue(){
        cnt_ = 0;
		queue_ = (Item[]) new Object[1] ;
	}
	// is the queue empty?
    public boolean isEmpty(){
        return cnt_ == 0;
    }
    // return the number of items on the queue
    public int size(){
        return cnt_;
    }
    // add the item
    public void enqueue(Item item){
		if(item == null) throw new NullPointerException();
    	if(cnt_ == queue_.length)
		{
			resize(queue_.length * 2);
		}
        queue_[cnt_++] = item;
    }
    // remove and return a random item
    public Item dequeue(){
    	if(isEmpty()) throw new java.util.NoSuchElementException();

		int current = StdRandom.uniform(cnt_);

		Item ret = queue_[current];

		if(cnt_ > 1)
		{
			queue_[current] = null;
			swap(current,cnt_-1);
		}

		cnt_--;

		if(cnt_ * 4 < queue_.length)
		{
			resize(queue_.length / 4);
		}
		return ret;

    }
    // return (but do not remove) a random item
    public Item sample(){
    	if (isEmpty()) throw new java.util.NoSuchElementException();
		return  queue_[StdRandom.uniform(cnt_)];
    }

    private void swap(int first, int second)
    {
    	if(first == second)
    	{
    		return;
    	}
    	Item tmp = queue_[first];
    	queue_[first] = queue_[second];
    	queue_[second] = tmp;
    }

    private void resize(int capacity)
    {
    	if(capacity == 0) return;
    	Item[] copy = (Item[]) new Object[capacity];

    	for(int i = 0; i < cnt_; i++)
    	{
    		copy[i] = queue_[i];
    	}
    	queue_ = copy;
    }

	private class RandomizedQueueIterator implements Iterator<Item>
	{
		private Item[] seq_;
		private int len_;

		public RandomizedQueueIterator()
		{
			seq_ = (Item[]) new Object[cnt_];
			len_ = cnt_;;
			for(int i = 0; i < len_; i++)
			{
				seq_[i] = queue_[i];
			}
		}

		@Override
		public boolean hasNext()
		{
			return len_ != 0;
		}

	    private void resize(int capacity)
	    {
	    	Item[] copy = (Item[]) new Object[capacity];

	    	for(int i = 0; i < len_; i++)
	    	{
	    		copy[i] = seq_[i];
	    	}
	    	seq_ = copy;
	    }
	    private void swap( int first, int second)
	    {
	    	Item tmp = seq_[first];
	    	seq_[first] = seq_[second];
	    	seq_[second] = tmp;
	    }

		@Override
		public Item next()
		{
			if(!hasNext()){
				throw new java.util.NoSuchElementException(
						"attempted to access inexistent element via iterator"
						);
			}
			int current = StdRandom.uniform(len_);

			Item ret = seq_[current];

			seq_[current] = null;

			if(len_ > 1)
			{
                swap(current,len_-1);
			}

			len_--;

			//if(len_ * 4 < seq_.length)
			//{
			//	resize(seq_.length / 4);
			//}

			return ret;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("remove() method unsupported");
		}
	}

	@Override
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new RandomizedQueueIterator();
	}
    // unit testing
    public static void main(String[] args){
		System.out.println("TEST#1 enqueueing");
		int [] testArray1 = {1,2,3,4,5,6};
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

		int cnt = 0;
		for (int item : testArray1)
		{
			rq.enqueue(item);
			System.out.printf("rq.size() -> %d\n", rq.size());
		}
		System.out.println("TEST#2 sampling");
		for(int i = 0; i < rq.size(); ++i)
		{
			System.out.printf("sample#%d : %d\n", i, rq.sample());
		}
		System.out.println("TEST#3 iterating");
		Iterator<Integer> it1 = rq.iterator();
		Iterator<Integer> it2 = rq.iterator();
		while(it1.hasNext() && it2.hasNext())
		{
			System.out.printf("it1.next() -> %d it2.next() -> %d\n",
					          it1.next(), it2.next());
		}
		System.out.println("TEST#4 dequeueing");
		while(!rq.isEmpty())
		{
			System.out.printf("deque()-> %d\n", rq.dequeue());
		}
		System.out.println("TEST#5 enqueueing");
		rq.enqueue(1);


    }
}

