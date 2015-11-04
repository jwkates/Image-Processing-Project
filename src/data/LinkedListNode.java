package data;

public class LinkedListNode<T> {

    /**
     *  item references the item stored in the current node.
     *  prev references the previous node in the DList.
     *  next references the next node in the DList.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    public T item;

    public LinkedListNode<T> prev;
    public LinkedListNode<T> next;
    /**
     *  DListNode2() constructor.
     */
    public LinkedListNode() {
        this(null);
    }

    public LinkedListNode(T item) {
        this.item = item;

        prev = null;
        next = null;
    }
}