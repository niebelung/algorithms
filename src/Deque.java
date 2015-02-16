import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
	public Deque()                           // construct an empty deque
	{
        _last= null;
        _first = null;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return _first == null && _last == null;

	}
	public int size()                        // return the number of items on the deque
	{}
	public void addFirst(Item item)          // add the item to the front
	{
		if(item == null) throw new java.lang.NullPointerException("attempted to add null item");

		Node<Item> it = new Node<Item>();
		if(isEmpty())
		{
			it.next = null;
			it.prev = null;
			_first = it;
			_last = it;
		}
		else
		{
			Node<Item> oldfirst = _first;
			_first = it;
			_first.data = item;
			_first.next = oldfirst;
			oldfirst.prev = _first;
			_first.prev  = null;
		}
		_cnt++;
	}

	public void addLast(Item item)           // add the item to the end
	{
		if(item == null) throw new java.lang.NullPointerException("attempted to add null item");

		Node<Item> it = new Node<Item>();

		if(isEmpty())
		{
			it.next = null;
			it.prev = null;
			_first = it;
			_last = it;
		}
		else
		{
			Node<Item> oldlast = _last;
			_last = it;
			_last.data = item;
			_last.next = null;
			oldlast.next  = _last;
			_last.prev = oldlast;
		}
		_cnt++;
    }

	public Item removeFirst()                // remove and return the item from the front
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = _first.data;
        _first = _first.next;
		_cnt--;
        return item;
	}

	public Item removeLast()                 // remove and return the item from the end
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("attempted to remove from an empty queue");

        Item item = _last.data;
        _last = _last.prev;
		_cnt--;
        return item;

	}


	private class Node<Item>
	{
		Item data;
		Node<Item> next;
		Node<Item> prev;
		Node()
		{
			data = null;
			next = null;
			prev = null;
		}
	}

	private Node<Item> _first;
	private Node<Item> _last;
	private int _cnt = 0;

	public class DequeIterator<Item> implements Iterator<Item>
	{
		private Deque<Item> _deque;
		private Node<Item> _current = null;

		public DequeIterator(Deque<Item> d) {
			_deque = d;
			_current = _deque._first;
		}
		@Override
		public boolean hasNext() {
			return _deque.

		}
		@Override
		public Item next() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove() method unsupported");
		}
	}

	@Override
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{}

	public static void main(String[] args)   // unit testing
{}
}
