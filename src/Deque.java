import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
	public Deque()                           // construct an empty deque
	{
        last_= null;
        first_ = null;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return first_ == null && last_ == null;

	}
	public int size()                        // return the number of items on the deque
	{
		return cnt_;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if(item == null) throw new java.lang.NullPointerException("attempted to add null item");

		Node it = new Node();
		if(isEmpty())
		{
			it.next = null;
			it.prev = null;
			it.data = item;
			first_ = it;
			last_ = it;
		}
		else
		{
			Node oldfirst = first_;
			first_ = it;
			first_.data = item;
			first_.next = oldfirst;
			oldfirst.prev = first_;
			first_.prev  = null;
		}
		cnt_++;
		//TEST
		System.out.printf("first %d last %d\n", first_.data,last_.data);
	}

	public void addLast(Item item)           // add the item to the end
	{
		if(item == null) throw new java.lang.NullPointerException("attempted to add null item");

		Node it = new Node();

		if(isEmpty())
		{
			it.next = null;
			it.prev = null;
			it.data = item;
			first_ = it;
			last_ = it;
		}
		else
		{
			Node oldlast = last_;
			last_ = it;
			last_.data = item;
			last_.next = null;
			oldlast.next  = last_;
			last_.prev = oldlast;
		}
		cnt_++;
		//TEST
		System.out.printf("first %d last %d\n", first_.data,last_.data);
    }

	public Item removeFirst()                // remove and return the item from the front
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = first_.data;

        first_ = first_.next;
        if (first_ == null){
        	last_ = null;
        }
        else{
        	first_.prev = null;
        }
		cnt_--;
        return item;
	}

	public Item removeLast()                 // remove and return the item from the end
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = last_.data;
        last_ = last_.prev;
        if (last_ == null){
        	first_ = null;
        }
        else{
        	last_.next = null;
        }
		cnt_--;
        return item;

	}

	private class Node
	{
		Item data;
		Node next;
		Node prev;
		Node()
		{
			data = null;
			next = null;
			prev = null;
		}
	}

	private Node first_;
	private Node last_;
	private int cnt_ = 0;

	private class DequeIterator implements Iterator<Item>
	{
		private Node current_;

		public DequeIterator()
		{
			current_ = new Node();
			current_.next = first_;
		}

		@Override
		public boolean hasNext()
		{
			return current_.next != null;
		}

		@Override
		public Item next()
		{
			if(current_.next == null){
				throw new java.util.NoSuchElementException(
						"attempted to access inexistent element via iterator"
						);
			}
			current_ = current_.next;
			return current_.data;
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
		return new DequeIterator();
	}

	// unit testing
	public static void main(String[] args){
		System.out.println("TEST#1");
		int [] testArray1 = {1,2,3,4,5,6};

		Deque<Integer> deque = new Deque<Integer>();

		int cnt = 0;
		for(int item : testArray1)
		{
			if((cnt++ % 2) == 0)
			{
				System.out.printf("deque.addFirst(%d)\n",item);
				deque.addFirst(item);
			}
			else
			{
				System.out.printf("deque.addLast(%d)\n",item);
				deque.addLast(item);
			}
		}

		Iterator<Integer> it1 = deque.iterator();
		Iterator<Integer> it2 = deque.iterator();
		while(it1.hasNext() && it2.hasNext())
		{
			System.out.printf("it1.next() = %d\n",it1.next());
			System.out.printf("it2.next() = %d\n",it2.next());
		}

		while(!deque.isEmpty())
		{
			if((cnt++ % 2) == 0)
			{
				System.out.printf("deque.removeLast() -> %d\n",deque.removeLast());
			}
			else
			{
				System.out.printf("deque.removeFirst() -> %d\n",deque.removeFirst());
			}
		}
		System.out.printf(
				"deque.iterator().hasNext() -> %b,deque.size() -> %d, deque.isEmpty() -> %b\n",
				deque.iterator().hasNext(), deque.size(), deque.isEmpty());
		try{
			//deque.removeFirst();
			//deque.removeLast();
			//int foo = deque.iterator().next();
		}
		catch (UnsupportedOperationException e){
			System.out.println(e.getMessage());
		}
		catch(java.util.NoSuchElementException e){
			System.out.println(e.getMessage());
		}
	}

}
