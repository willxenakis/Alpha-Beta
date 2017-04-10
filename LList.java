

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created a Linked List class that implements the List<E> interface.
 * Implemented necessary methods that are used in DrawingPane.
 */
public class LList<E> implements List<E> {

	/** An iterator for an LList. Used in the iterator() method of LList. */
	private class LListIterator implements Iterator<E> {

		private Node<E> currentNode = head;

		@Override
		// Returns true if the next node in the list is not null.
		public boolean hasNext() {
			if (currentNode == null) {
				return false;
			}
			return true;
		}

		@Override
		// Assigns the current node pointer to the next node in the list, and
		// returns the previous current node data.
		public E next() {
			E currNodeData = currentNode.data;
			currentNode = currentNode.nextNode;
			return currNodeData;
		}

	}

	/** This class represents a single node in a list. It is used in LList. */
	private class Node<X> {
		// Represents the data kept in a node
		public X data;

		// A pointer to the next node in the list
		public Node<X> nextNode;
	}

	private Node<E> head;

	@Override
	public int size() {
		Node currentNode = head;
		int count = 0;
		// Iterates through LList counting each time it goes to the next node
		while (currentNode != null) {
			count++;
			currentNode = currentNode.nextNode;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Iterator<E> iterator() {
		return new LListIterator();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean add(E e) {
		// If the LList is empty, add a node with data e as the head
		if (head == null) {
			head = new Node<E>();
			head.data = e;
			head.nextNode = null;
			return true;
		}

		// Appends a node with data e to the LList
		Node<E> end = new Node<E>();
		end.data = e;
		end.nextNode = null;
		Node<E> currentNode = head;
		while (currentNode.nextNode != null) {
			currentNode = currentNode.nextNode;
		}
		currentNode.nextNode = end;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clear() {
		head = null;
	}

	@Override
	public E get(int index) {
		Node<E> currentNode = head;
		if (index == 0) {
			return currentNode.data;
		}
		currentNode = currentNode.nextNode;
		// Go through LList one node at a time, index amount of times
		for (int i = 1; i < index; i++) {
			currentNode = currentNode.nextNode;
		}
		return currentNode.data;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		if (head == null) {
			return null;
		}
		int count = 0;
		Node<E> currNode = head;
		E removedNode;
		// If head is the only node in LList, remove it and return its data
		if (currNode.nextNode == null) {
			removedNode = currNode.data;
			head = null;
			return removedNode;
		}
		if (index == 0) {
			head = head.nextNode;
			return currNode.data;
		}

		// Iterate through LList up to the node before the one wished to remove
		while (count < index - 1) {
			currNode = currNode.nextNode;
			count++;
		}

		// Reorder the .next of the node before the removed node, therefore
		// erasing all pointers to the node
		removedNode = currNode.nextNode.data;
		currNode.nextNode = currNode.nextNode.nextNode;
		return removedNode;
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();

	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();

	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();

	}

}
