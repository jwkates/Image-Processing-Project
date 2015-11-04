/* RunIterator.java */

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

import data.*;

import java.util.NoSuchElementException;

public class RunIterator extends LinkedListIterator {

  /**
   *  Define any variables associated with a RunIterator object here.
   *  These variables MUST be private.
   */

  private LinkedListIterator<Run> runIterator;

  /**
   *  RunIterator() constructs a new iterator starting with a specified run.
   *
   */
  // Unlike all the other methods we have asked you to write, the RunIterator()
  // constructor does not have a predefined signature, because no outside
  // class should ever call this constructor except the iterator() method in
  // the RunLengthEncoding class.  The correct way for outside classes to
  // get access to a RunIterator is to call the iterator() method on a
  // RunLengthEncoding object.  You are welcome to add any parameters to the
  // constructor that you want so that your RunLengthEncoding.iterator()
  // implementation can construct a RunIterator that points to the first run of
  // the encoding.
  RunIterator(LinkedList<Run> runList) {
    super(runList);
  }

  public int[] next() {
    // Construct a new array of 4 ints, fill in its values, and return it.
    // Don't forget to advance the RunIterator's pointer so that the next
    // call to next() will return the subsequent run.

    if (hasNext()) {
      Run run = runIterator.next();
      Pixel pixel = run.getPixel();

      // Return a four-element array representing this run.
      int[] runArray = {run.getRunLength(), pixel.getRed(), pixel.getGreen(), pixel.getBlue()};
      return runArray;
    } else {
      throw new NoSuchElementException();
    }
  }
}
