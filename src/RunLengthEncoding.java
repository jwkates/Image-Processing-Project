/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImg object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImg image) {
 *
 *  that create a run-length encoding of a PixImg having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImg in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImg object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import data.LinkedListIterator;
import data.Pixel;
import data.LinkedList;
import data.Run;

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

    /**
     * Define any variables associated with a RunLengthEncoding object here.
     * These variables MUST be private.
     */

    private LinkedList<Run> list;

    private int width;
    private int height;

    /**
     * RunLengthEncoding() (with two parameters) constructs a run-length
     * encoding of a black PixImg of the specified width and height, in which
     * every pixel has red, green, and blue intensities of zero.
     *
     * @param width  the width of the image.
     * @param height the height of the image.
     */

    public RunLengthEncoding(int width, int height) {
        this.width = width;
        this.height = height;

        this.list = new LinkedList<>();

        int runLength = width * height;

        Run newRun = new Run(runLength, Pixel.zeroPixel());
        list.add(newRun);
    }

    /**
     * RunLengthEncoding() (with six parameters) constructs a run-length
     * encoding of a PixImg of the specified width and height.  The runs of
     * the run-length encoding are taken from four input arrays of equal length.
     * data.Run i has length runLengths[i] and RGB intensities red[i], green[i], and
     * blue[i].
     *
     * @param width      the width of the image.
     * @param height     the height of the image.
     * @param red        is an array that specifies the red intensity of each run.
     * @param green      is an array that specifies the green intensity of each run.
     * @param blue       is an array that specifies the blue intensity of each run.
     * @param runLengths is an array that specifies the length of each run.
     *                   <p/>
     *                   NOTE:  All four input arrays should have the same length (not zero).
     *                   All pixel intensities in the first three arrays should be in the range
     *                   0...255.  The sum of all the elements of the runLengths array should be
     *                   width * height.  (Feel free to quit with an error message if any of these
     *                   conditions are not met--though we won't be testing that.)
     */

    public RunLengthEncoding(int width, int height, int[] red, int[] green,
                             int[] blue, int[] runLengths) {
        this.width = width;
        this.height = height;

        list = new LinkedList<>();

        for (int i = 0; i < runLengths.length; i++) {
            // Get run length for the ith run
            // Add a run with length runLength and the right color
            int runLength = runLengths[i];
            Run newRun = new Run(runLength, red[i], green[i], blue[i]);

            list.add(newRun);
        }
    }

    /**
     * iterator() returns a newly created RunIterator that can iterate through
     * the runs of this RunLengthEncoding.
     *
     * @return a newly created RunIterator object set to the first run of this
     * RunLengthEncoding.
     */
    public RunIterator iterator() {
        // Replace the following line with your solution.
        return new RunIterator(list);
        // You'll want to construct a new RunIterator, but first you'll need to
        // write a constructor in the RunIterator class.
    }

    /**
     * toPixImg() converts a run-length encoding of an image into a PixImg
     * object.
     *
     * @return the PixImg that this RunLengthEncoding encodes.
     */
    public PixImg toPixImg() {
        PixImg out = new PixImg(width, height);

        int count = 0;
        Iterator<Run> iterator = list.iterator();
        while (iterator.hasNext()) {
            Run run = iterator.next();
            // Add as many pixels as the run length dictates
            for (int i = 0; i < run.getRunLength(); i++) {
                // Translate element count to grid position
                int xCoordinate = count % width;
                int yCoordinate = count / width;
                // Set the pixel in the image
                Pixel pixel = run.getPixel();
                out.setPixel(xCoordinate, yCoordinate, pixel.getRed(), pixel.getGreen(), pixel.getBlue());

                count++;
            }
        }

        return out;
    }


    /**
     *  The following methods are required for Part III.
     */

    /**
     * RunLengthEncoding() (with one parameter) is a constructor that creates
     * a run-length encoding of a specified PixImg.
     * <p/>
     * Note that you must encode the image in row-major format, i.e., the second
     * pixel should be (1, 0) and not (0, 1).
     *
     * @param image is the PixImg to run-length encode.
     */
    public RunLengthEncoding(PixImg image) {
        // Your solution here, but you should probably leave the following line
        // at the end.
        width = image.getWidth();
        height = image.getHeight();

        list = new LinkedList<>();

        int numberOfPixels = width * height;

        // Convert PixImg into array of pixels for easier processing
        Pixel[] pixels = new Pixel[numberOfPixels];
        // Go in row-major order
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = image.getRed(x, y);
                int green = image.getGreen(x, y);
                int blue = image.getBlue(x, y);
                pixels[y*width + x] = new Pixel(red, green, blue);
            }
        }

        // Walk through the list, finding runs and adding them to the RunList
        for (int i = 0; i < pixels.length; i++) {
            Pixel pixel = pixels[i];

            // Loop through list while subsequent pixels are equal to count length of run
            int runLength = 1;
            while (i + runLength < pixels.length && pixel.equals(pixels[i + runLength])) {
                runLength++;
            }
            i += runLength - 1;

            Run run = new Run(runLength, pixel);
            list.add(run);
        }

        check();
    }


    /**
     * check() walks through the run-length encoding and prints an error message
     * if two consecutive runs have the same RGB intensities, or if the sum of
     * all run lengths does not equal the number of pixels in the image.
     */
    public void check() {
        Iterator<Run> runIterator = list.iterator();

        if (!runIterator.hasNext()) {
            // If list is empty, return
            return;
        }

        // Load first run
        Run prevRun = runIterator.next();
        int runLengthSum = prevRun.getRunLength();

        while (runIterator.hasNext()) {
            // Iterate through the list, comparing the current run to the previous run
            Run currentRun = runIterator.next();

            if (currentRun.equals(prevRun)) {
                System.err.println("Check failed. Subsequent pixels equal.");
                return;
            }

            runLengthSum += currentRun.getRunLength();
            prevRun = currentRun;

        }

        if (runLengthSum != width * height) {
            System.err.println("Check failed. Incorrect dimensions.");
        }
    }


    /**
     *  The following method is required for Part IV.
     */

    /**
     * setPixel() modifies this run-length encoding so that the specified color
     * is stored at the given (x, y) coordinates.  The old pixel value at that
     * coordinate should be overwritten and all others should remain the same.
     * The updated run-length encoding should be compressed as much as possible;
     * there should not be two consecutive runs with exactly the same RGB color.
     *
     * @param x     the x-coordinate of the pixel to modify.
     * @param y     the y-coordinate of the pixel to modify.
     * @param red   the new red intensity to store at coordinate (x, y).
     * @param green the new green intensity to store at coordinate (x, y).
     * @param blue  the new blue intensity to store at coordinate (x, y).
     */
    public void setPixel(int x, int y, short red, short green, short blue) {
        // Convert (x, y) location into pixel number. First pixel is 1.
        int pixelIndex = y * width + x + 1;

        // Go through the run list to find the run containing the pixel
        LinkedListIterator<Run> iterator = list.iterator();

        Run currentRun = iterator.next();
        int runLengthTotal = currentRun.getRunLength();

        // Iterate until we find the run that contains the target pixel
        while (runLengthTotal < pixelIndex && iterator.hasNext()) {
            currentRun = iterator.next();
            runLengthTotal += currentRun.getRunLength();
        }

        // If the pixel is the same, there is nothing to do
        if (currentRun.getRed() == red && currentRun.getGreen() == green && currentRun.getBlue() == blue) {
            return;
        }

        // If the run is a single pixel, just replace it
        if (currentRun.getRunLength() == 1) {
            iterator.set(new Run(1, red, green, blue));
            return;
        }
        // The run is more than a single pixel
        int offset = pixelIndex - (runLengthTotal - currentRun.getRunLength());

        if (offset == 1) {
            // If the target pixel is at the start of a run, add the new run before
            iterator.insertBefore(new Run(1, red, green, blue));
            iterator.set(new Run(currentRun.getRunLength() - 1, currentRun.getPixel()));
        } else if (offset == currentRun.getRunLength()) {
            // If target pixel is at end, add new run after
            iterator.insertAfter(new Run(1, red, green, blue));
            iterator.set(new Run(currentRun.getRunLength() - 1, currentRun.getPixel()));
        } else {
            // If target pixel is in middle, add new runs on either side
            iterator.insertBefore(new Run(offset - 1, currentRun.getPixel()));
            iterator.insertAfter(new Run(currentRun.getRunLength() - offset, currentRun.getPixel()));
            iterator.set(new Run(1, red, green, blue));
        }


        check();
    }


    /**
     * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
     * You are welcome to add tests, though.  Methods below this point will not
     * be tested.  This is not the autograder, which will be provided separately.
     */


    /**
     * doTest() checks whether the condition is true and prints the given error
     * message if it is not.
     *
     * @param b   the condition to check.
     * @param msg the error message to print if the condition is false.
     */
    private static void doTest(boolean b, String msg) {
        if (b) {
            System.out.println("Good.");
        } else {
            System.err.println(msg);
        }
    }

    /**
     * array2PixImg() converts a 2D array of grayscale intensities to
     * a grayscale PixImg.
     *
     * @param pixels a 2D array of grayscale intensities in the range 0...255.
     * @return a new PixImg whose red, green, and blue values are equal to
     * the input grayscale intensities.
     */
    private static PixImg array2PixImg(int[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        PixImg image = new PixImg(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                        (short) pixels[x][y]);
            }
        }

        return image;
    }

    /**
     * setAndCheckRLE() sets the given coordinate in the given run-length
     * encoding to the given value and then checks whether the resulting
     * run-length encoding is correct.
     *
     * @param rle       the run-length encoding to modify.
     * @param x         the x-coordinate to set.
     * @param y         the y-coordinate to set.
     * @param intensity the grayscale intensity to assign to pixel (x, y).
     */
    private static void setAndCheckRLE(RunLengthEncoding rle,
                                       int x, int y, int intensity) {
        rle.setPixel(x, y,
                (short) intensity, (short) intensity, (short) intensity);
        rle.check();
    }


    /**
     *  getWidth() returns the width of the image that this run-length encoding
     *  represents.
     *
     *  @return the width of the image that this run-length encoding represents.
     */
    public int getWidth() {
        // Replace the following line with your solution.
        return width;
    }

    /**
     * getHeight() returns the height of the image that this run-length encoding
     * represents.
     *
     * @return the height of the image that this run-length encoding represents.
     */
    public int getHeight() {
        // Replace the following line with your solution.
        return height;
    }

    /**
     * toString() returns a String representation of this RunLengthEncoding.
     * <p/>
     * This method isn't required, but it should be very useful to you when
     * you're debugging your code.  It's up to you how you represent
     * a RunLengthEncoding as a String.
     *
     * @return a String representation of this RunLengthEncoding.
     */
    public String toString() {
        // Replace the following line with your solution.
        return list.toString();
    }

    /**
     * main() runs a series of tests of the run-length encoding code.
     */
    public static void main(String[] args) {
        // Be forewarned that when you write arrays directly in Java as below,
        // each "row" of text is a column of your image--the numbers get
        // transposed.

        PixImg image1 = array2PixImg(new int[][]{{0, 3, 6},
                {1, 4, 7},
                {2, 5, 8}});

        System.out.println("Testing one-parameter RunLengthEncoding constructor " +
                "on a 3x3 image.  Input image:");
        System.out.print(image1);
        RunLengthEncoding rle1 = new RunLengthEncoding(image1);
        System.out.println(rle1);
        rle1.check();

        System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
        doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
                "RLE1 has wrong dimensions");

        System.out.println("Testing toPixImg() on a 3x3 encoding.");
        doTest(image1.equals(rle1.toPixImg()),
                "image1 -> RLE1 -> image does not reconstruct the original image");
        System.out.println(rle1.toPixImg());

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 0, 0, 42);
        image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
        System.out.println(image1);
        doTest(rle1.toPixImg().equals(image1),
           /*
                       array2PixImg(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
                "Setting RLE1[0][0] = 42 fails.");

        System.out.println(rle1);
        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 1, 0, 42);
        image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[1][0] = 42 fails.");

        System.exit(0);

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 0, 1, 2);
        image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[0][1] = 2 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 0, 0, 0);
        image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[0][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 2, 2, 7);
        image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[2][2] = 7 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 2, 2, 42);
        image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[2][2] = 42 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 1, 2, 42);
        image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImg().equals(image1),
                "Setting RLE1[1][2] = 42 fails.");


        PixImg image2 = array2PixImg(new int[][]{{2, 3, 5},
                {2, 4, 5},
                {3, 4, 6}});

        System.out.println("Testing one-parameter RunLengthEncoding constructor " +
                "on another 3x3 image.  Input image:");
        System.out.print(image2);
        RunLengthEncoding rle2 = new RunLengthEncoding(image2);
        rle2.check();
        System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
        doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
                "RLE2 has wrong dimensions");

        System.out.println("Testing toPixImg() on a 3x3 encoding.");
        doTest(rle2.toPixImg().equals(image2),
                "image2 -> RLE2 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle2, 0, 1, 2);
        image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
        doTest(rle2.toPixImg().equals(image2),
                "Setting RLE2[0][1] = 2 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle2, 2, 0, 2);
        image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
        doTest(rle2.toPixImg().equals(image2),
                "Setting RLE2[2][0] = 2 fails.");

        PixImg image3 = array2PixImg(new int[][]{{0, 5},
                {1, 6},
                {2, 7},
                {3, 8},
                {4, 9}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on a 5x2 image.  Input image:");
        System.out.print(image3);
        RunLengthEncoding rle3 = new RunLengthEncoding(image3);
        rle3.check();
        System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
        doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
                "RLE3 has wrong dimensions");

        System.out.println("Testing toPixImg() on a 5x2 encoding.");
        doTest(rle3.toPixImg().equals(image3),
                "image3 -> RLE3 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 4, 0, 6);
        image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
        doTest(rle3.toPixImg().equals(image3),
                "Setting RLE3[4][0] = 6 fails.");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 0, 1, 6);
        image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
        doTest(rle3.toPixImg().equals(image3),
                "Setting RLE3[0][1] = 6 fails.");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 0, 0, 1);
        image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
        doTest(rle3.toPixImg().equals(image3),
                "Setting RLE3[0][0] = 1 fails.");


        PixImg image4 = array2PixImg(new int[][]{{0, 3},
                {1, 4},
                {2, 5}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on a 3x2 image.  Input image:");
        System.out.print(image4);
        RunLengthEncoding rle4 = new RunLengthEncoding(image4);
        rle4.check();
        System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
        doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
                "RLE4 has wrong dimensions");

        System.out.println("Testing toPixImg() on a 3x2 encoding.");
        doTest(rle4.toPixImg().equals(image4),
                "image4 -> RLE4 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 2, 0, 0);
        image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle4.toPixImg().equals(image4),
                "Setting RLE4[2][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 1, 0, 0);
        image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle4.toPixImg().equals(image4),
                "Setting RLE4[1][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 1, 0, 1);
        image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
        doTest(rle4.toPixImg().equals(image4),
                "Setting RLE4[1][0] = 1 fails.");
    }
}
