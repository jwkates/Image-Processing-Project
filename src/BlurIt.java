/* BlurItItIt.java */

/* DO NOT CHANGE THIS FILE. */

/* You may want to insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* please remove any statement you have added. */

/**
 *  The BlurIt class is a program that reads an image file in TIFF format, BlurIts
 *  it with a 3x3 box Blurring kernel, writes the Blurred image as a TIFF file,
 *  and displays both images.
 *
 *  The BlurIt program takes up to two parameters.  The first parameter is
 *  the name of the TIFF-format file to read.  (The output image file is
 *  constructed by adding "BlurIt_" to the beginning of the input filename.)
 *  An optional second parameter specifies the number of iterations of the
 *  box BlurItring operation.  (The default is one iteration.)  For example, if
 *  you run
 *
 *         java BlurIt engine.tiff 5
 *
 *  then BlurIt will read engine.tiff, perform 5 iterations of Blurring, and
 *  write the Blurred image to BlurIt_engine.tiff .
 *
 */

public class BlurIt {
  
  /**
   *  BlurItFile() reads a TIFF image file, BlurIts it, write the Blurred image to
   *  a new TIFF image file, and displays both images.
   *
   *  @param filename the name of the input TIFF image file.
   *  @param numIterations the number of iterations of BlurItring to perform.
   */
  private static void BlurItFile(String filename, int numIterations) {
    System.out.println("Reading image file " + filename);
    PixImg image = ImgUtils.readTIFFPix(filename);

    System.out.println("BlurItring image file.");
    PixImg Blurred = image.sobelEdges();

    String BlurItname = "blurred_" + filename;
    System.out.println("Writing Blurred image file " + BlurItname);
    TIFFEncoder.writeTIFF(Blurred, BlurItname);
    /*
    TIFFEncoder.writeTIFF(new RunLengthEncoding(edges), "rle" + BlurItname);
    */

    System.out.println("Displaying input image and Blurred image.");
    System.out.println("Close the image to quit.");
    ImgUtils.displayTIFFs(new PixImg[] { image, Blurred });
  }

  /**
   *  main() reads the command-line arguments and initiates the BlurItring.
   *
   *  The first command-line argument is the name of the image file.
   *  An optional second argument is number of iterations of BlurItring.
   *
   *  @param args the usual array of command-line argument Strings.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("usage:  java BlurIt imagefile [iterations]");
      System.out.println("  imagefile is an image in TIFF format.");
      System.out.println("  interations is the number of BlurItring iterations" +
                         " (default 1).");
      System.out.println("The Blurred image is written to BlurIt_imagefile.");
      System.exit(0);
    }

    int numIterations = 1;
    if (args.length > 1) {
      try {
        numIterations = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        System.err.println("The second argument must be a number.");
        System.exit(1);
      }
    }

    BlurItFile(args[0], numIterations);
  }
}