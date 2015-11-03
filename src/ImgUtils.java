/* ImageUtils.java */

/* DO NOT CHANGE THIS FILE. */

/* You may want to insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* please remove any statement you have added. */

/**
 *  The ImgUtils class reads and writes TIFF file, converting to and from
 *  pixel arrays in PixImg format or run-length encodings in
 *  RunLengthEncoding format.  Methods are also included for displaying images
 *  in PixImg format.
 *

 **/

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.media.jai.JAI;
import javax.media.jai.RenderedImageAdapter;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  ImgUtils contains utilities for reading, writing, and displaying images.
 * 
 *  It uses JAI to read and write TIFF files, as the standard libraries cannot
 *  read them.
 * 
 *  All image data is in RGB format (see BufferedImage.getRGB).
 */
public class ImgUtils {

  /**
   *  buffer2PixImg() converts a BufferedImage to a PixImg.
   *  @param bImage the image to convert.
   *  @return a PixImg with the same pixels as the BufferedImage.
   */
  private static PixImg buffer2PixImg(BufferedImage bImage) {
    PixImg pImage = new PixImg(bImage.getWidth(), bImage.getHeight());
    for (int x = 0; x < bImage.getWidth(); x++) {
      for (int y = 0; y < bImage.getHeight(); y++) {
        Color color = new Color(bImage.getRGB(x, y));
        pImage.setPixel(x, y, (short) color.getRed(), (short) color.getGreen(),
                        (short) color.getBlue());
      }
    }
    return pImage;
  }

  /**
   *  PixImg2buffer() converts a PixImg to a BufferedImage.
   *  @param pImage the image to convert.
   *  @return a BufferedImage with the same pixels as the PixImg.
   */
  static BufferedImage pixImg2buffer(PixImg pImage) {
    BufferedImage bImage = new BufferedImage(pImage.getWidth(),
                                             pImage.getHeight(),
                                             BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < bImage.getWidth(); x++) {
      for (int y = 0; y < bImage.getHeight(); y++) {
        bImage.setRGB(x, y, new Color(pImage.getRed(x, y),
                                      pImage.getGreen(x, y),
                                      pImage.getBlue(x, y)).getRGB());
      }
    }
    return bImage;
  }

  /**
   *  readTIFF() reads an image from a file and formats it as a BufferedImage.
   *  @param filename the name of the file to read.
   *  @return a BufferedImage of the file
   */
  private static BufferedImage readTIFF(String filename) {
    return (new RenderedImageAdapter(JAI.create("fileload", filename)))
           .getAsBufferedImage();
  }

  /**
   *  readTIFFPix() reads an image from a file and formats it as a PixImg.
   *  @param filename the name of the file to read.
   *  @return a PixImg of the file
   */
  public static PixImg readTIFFPix(String filename) {
    return buffer2PixImg(readTIFF(filename));
  }

  /**
   *  readTIFFRLE() reads an image from a file and formats it as a run-length
   *  encoding.
   *  @param filename the name of the file to read.
   *  @return a RunLengthEncoding of the file.
   */
  public static RunLengthEncoding readTIFFRLE(String filename) {
    return new RunLengthEncoding(readTIFFPix(filename));
  }

  /**
   *  writeTIFF() writes a BufferedImage to a specified file in TIFF format.
   *  @param image the input BufferedImage.
   *  @param filename the output filename.
   */
  private static void writeTIFF(BufferedImage image, String filename) {
    JAI.create("filestore", image, filename, "tiff");
  }

  /**
   *  writeTIFF() writes a PixImg to a specified file in TIFF format.
   *  @param image the input PixImg.
   *  @param filename the output filename.
   */
  public static void writeTIFF(PixImg image, String filename) {
    writeTIFF(pixImg2buffer(image), filename);
  }

  /**
   *  writeTIFF() writes a run-length encoding to a specified file in TIFF
   *  format.
   *  @param rle the input run-length encoded image.
   *  @param filename the output filename.
   */
  public static void writeTIFF(RunLengthEncoding rle, String filename) {
    writeTIFF(rle.toPixImg(), filename);
  }

  /**
   *  displayFrame displays a JFrame and pauses until the window is closed.
   *  @param frame a JFrame to display.
   */
  private static void displayFrame(final JFrame frame) {
    try {
      synchronized (ImgUtils.class) {
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
              synchronized (ImgUtils.class) {
                ImgUtils.class.notify();
                frame.dispose();
              }
            }
          });
        frame.pack();
        frame.setVisible(true);
        ImgUtils.class.wait();
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted Exception in displayFrame().");
      e.printStackTrace();
    }
  }

  /**
   *  displayTIFFs displays a sequence of PixImgs and pauses until the window
   *  is closed.
   *  @param images an array of PixImgs to display.
   */
  public static void displayTIFFs(PixImg[] images) {
    JFrame frame = new JFrame();
    Box box = Box.createHorizontalBox();
    for (int i = 0; i < images.length; i++) {
      box.add(new JLabel(new ImageIcon(pixImg2buffer(images[i]))));
      if (i < images.length - 1) {
        box.add(Box.createHorizontalStrut(10));
      }
    }
    frame.add(box);
    displayFrame(frame);
  }

  /**
   *  displayTIFF displays a PixIamge and pauses until the window is closed.
   *  @param image the PixImg to display.
   */
  public static void displayTIFF(PixImg image) {
    displayTIFFs(new PixImg[] { image });
  }
}
