/* RunIterator.java */

package data;

/**
 *  The RunIterator class iterates over a RunLengthEncoding and allows other
 *  classes to inspect the runs in a run-length encoding, one run at a time.
 *  A newly constructed RunIterator "points" to the first run in the encoding
 *  used to construct it.  Each time next() is invoked, it returns a run
 *  (represented as an array of four ints); a sequence of calls to next()
 *  returns run in consecutive order until every run has been returned.
 *
 *  Client classes should never call the RunIterator constructor directly;
 *  instead they should invoke the iterator() method on a RunLengthEncoding
 *  object, which will construct a properly initialized RunIterator for the
 *  client.
 *
 *  Calls to hasNext() determine whether another run is available, or whether
 *  the iterator has reached the end of the run-length encoding.  When
 *  a RunIterator reaches the end of an encoding, it is no longer useful, and
 *  the next() method may throw an exception; thus it is recommended to check
 *  hasNext() before each call to next().  To iterate through the encoding
 *  again, construct a new RunIterator by invoking iterator() on the
 *  RunLengthEncoding and throw the old RunIterator away.
 *
 *  A RunIterator is not guaranteed to work if the underlying RunLengthEncoding
 *  is modified after the RunIterator is constructed.  (Especially if it is
 *  modified by setPixel().)
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<T> implements Iterator<T> {

    /**
     *  Define any variables associated with a RunIterator object here.
     *  These variables MUST be private.
     */

    private LinkedList<T> list;
    private LinkedListNode<T> head;
    private LinkedListNode<T> currentNode;

    /**
     *  RunIterator() constructs a new iterator starting with a specified run.
     *
     */
    public LinkedListIterator(LinkedList<T> list) {
        this.list = list;
        head = list.head;
        currentNode = list.head;
    }

    /**
     *  hasNext() returns true if this iterator has more runs.  If it returns
     *  false, then the next call to next() may throw an exception.
     *
     *  @return true if the iterator has more elements.
     */
    public boolean hasNext() {
        // Replace the following line with your solution.
        return currentNode.next != head;
    }
    
    /**
     * next() returns the next item in the list.
     */
    public T next() {
        // Construct a new array of 4 ints, fill in its values, and return it.
        // Don't forget to advance the RunIterator's pointer so that the next
        // call to next() will return the subsequent run.

        if (hasNext()) {
            currentNode = currentNode.next;

            T item = currentNode.item;
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void set(T item) {
        currentNode.item = item;
    }

    public void insertBefore(T item) {
        list.insertBefore(currentNode, item);
    }

    public void insertAfter(T item) {
        list.insertAfter(currentNode, item);
    }

    /**
     *  remove() would remove from the underlying run-length encoding the run
     *  identified by this iterator, but we are NOT implementing it.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }


}




