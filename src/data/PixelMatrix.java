package data;

/**
 * Created with IntelliJ IDEA.
 * User: jkates
 * Date: 10/14/15
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PixelMatrix {

    private Pixel[][] pixels;

    private int width;
    private int height;


    public PixelMatrix(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new Pixel[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel();
            }
        }
    }

    public Pixel getPixel(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return pixels[x][y];
        } else {
            return Pixel.zeroPixel();
        }
    }

    public Pixel neighborhoodAverage(int x, int y) {
        // Sum of neighbors in each color
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                Pixel pixel = getPixel(i, j);

                redSum += pixel.getRed();
                greenSum += pixel.getGreen();
                blueSum += pixel.getBlue();
            }
        }

        // Average pixel has the total values divided by the # of neighbors as its components
        int neighbors = numberOfNeighbors(x, y);

        return new Pixel(redSum / neighbors, greenSum / neighbors, blueSum / neighbors);
    }

    public Pixel convolve(int x, int y, short[][] kernel) {
        int redTotal = 0;
        int greenTotal = 0;
        int blueTotal = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Kernel (i,j) entry is convolved with matrix (x + i - 1, y + j - 1) entry.
                redTotal += kernel[i][j] * getPixelReflected(x + i - 1, y + j - 1).getRed();
                greenTotal += kernel[i][j] * getPixelReflected(x + i - 1, y + j - 1).getGreen();
                blueTotal += kernel[i][j] * getPixelReflected(x + i - 1, y + j - 1).getBlue();
            }
        }

        return new Pixel(redTotal, greenTotal, blueTotal);
    }

    private Pixel getPixelReflected(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            // Pixel in the matrix, return the pixel
            return getPixel(x, y);
        } else if (x < 0 && y < 0) {
            // Return top left corner
            return getPixel(0, 0);
        } else if (x >= getWidth() && y < 0) {
            // Return top right corner
            return getPixel(getWidth() - 1, 0);
        } else if (x < 0 && y >= getHeight()) {
            // Return bottom left corner
            return getPixel(0, getHeight() - 1);
        } else if (x >= getWidth() && y >= getHeight()) {
            // Return bottom right corner
            return getPixel(getWidth() - 1, getHeight() - 1);
        } else if (x < 0) {
            // Reflect to left edge
            return getPixel(0, y);
        } else if (x >= getWidth()) {
            // Reflect to right edge
            return getPixel(getWidth() - 1, y);
        } else if (y < 0) {
            // Reflect to top edge
            return getPixel(x, 0);
        } else if (y >= getHeight()) {
            return getPixel(x, getHeight() - 1);
        } else {
            return Pixel.zeroPixel();
        }

    }

    private int numberOfNeighbors(int x, int y) {
        if (x > 0 && x < getWidth() - 1 && y > 0 && y < getHeight() - 1) {
            // Interior pixel
            return 9;
        } else if (x == 0 && y == 0) {
            // Top left corner
            return 4;
        } else if (x == getWidth() - 1 && y == 0) {
            // Top right corner
            return 4;
        } else if (x == 0 && y == getHeight() - 1) {
            // Bottom left corner
            return 4;
        } else if (x == getWidth() - 1 && y == getHeight() - 1) {
            // Bottom right corner
            return 4;
        } else {
            // Other pixels are on edge but not corner
            return 6;
        }
    }

    public void setPixel(int x, int y, short red, short green, short blue) {
        getPixel(x, y).setValues(red, green, blue);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
