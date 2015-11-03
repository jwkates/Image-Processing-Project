/* DList2.java */

package data;

import java.util.Iterator;

/**
 * A DList2 is a mutable doubly-linked list.  Its implementation is
 * circularly-linked and employs a sentinel (dummy) node at the head
 * of the list.
 */

public class LinkedList<T> implements Iterable<T> {

    /**
     * head references the sentinel node.
     * <p/>
     * DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    public LinkedListNode<T> head;
    public int size;

  /* DList2 invariants:
   *  1)  head != null.
   *  2)  For any DListNode2 x in a DList2, x.next != null.
   *  3)  For any DListNode2 x in a DList2, x.prev != null.
   *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
   *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
   *      (denoted by "head"), that can be accessed from the sentinel by
   *      a sequence of "next" references.
   */

    /**
     * DList2() constructor for an empty DList2.
     */
    public LinkedList() {
        head = new LinkedListNode<>();
        head.next = head;
        head.prev = head;
        size = 0;
    }

    /**
     * DList2() constructor for a one-node DList2.
     */
    public LinkedList(T item) {
        this();
        head.next = new LinkedListNode<>(item);
        head.prev = head.next;
        head.next.prev = head;
        head.next.next = head;
        size = 1;
    }

    public void add(T item) {
        // The new item is inserted after the last node
        insertAfter(head.prev, item);
    }

    public void insertAfter(LinkedListNode<T> node, T item) {
        LinkedListNode<T> newNode = new LinkedListNode<>(item);

        newNode.prev = node;
        newNode.next = node.next;

        node.next.prev = newNode;
        node.next = newNode;

        size++;
    }

    public void insertBefore(LinkedListNode<T> node, T item) {
        LinkedListNode<T> newNode = new LinkedListNode<>(item);

        newNode.prev = node.prev;
        newNode.next = node;

        node.prev.next = newNode;
        node.prev = newNode;

        size++;
    }

    @Override
    public LinkedListIterator<T> iterator() {
        return new LinkedListIterator<>(this);
    }

    public int getLength() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * toString() returns a String representation of this DList.
     * <p/>
     * DO NOT CHANGE THIS METHOD.
     *
     * @return a String representation of this DList.
     */
    public String toString() {
        String result = "[  ";
        LinkedListNode current = head.next;
        while (current != head) {
            result += current.item + ", ";
            current = current.next;
        }
        return result + "]";
    }
}
