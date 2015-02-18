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
	}

	public void addLast(Item item)           // add the item to the end
	{
		if(item == null) throw new java.lang.NullPointerException("attempted to add null item");

		Node it = new Node();

		if(isEmpty())
		{
			it.next = null;
			it.prev = null;
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
    }

	public Item removeFirst()                // remove and return the item from the front
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = first_.data;
        first_ = first_.next;
		cnt_--;
        return item;
	}

	public Item removeLast()                 // remove and return the item from the end
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = last_.data;
        last_ = last_.prev;
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
		private Node current_ = Deque.this.first_;

		@Override
		public boolean hasNext()
		{
			return current_.next != null;
		}

		@Override
		public Item next()
		{
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

	public static void main(String[] args)   // unit testing
{}
}
