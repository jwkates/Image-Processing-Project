package data;

import data.Pixel;

/**
 * Created by Jack Kates on 10/15/15.
 */
public class Run {

    private int runLength;
    private Pixel pixel;

    public Run() {
        this(0, null);
    }

    public Run(int runLength, Pixel pixel) {
        this.runLength = runLength;
        this.pixel = pixel;
    }

    public Run(int runLength, int red, int green, int blue) {
        this(runLength, new Pixel(red, green, blue));
    }

    public int getRunLength() {
        return runLength;
    }

    public Pixel getPixel() {
        return pixel;
    }

    public short getRed() {
        return pixel.getRed();
    }

    public short getGreen() {
        return pixel.getGreen();
    }

    public short getBlue() {
        return pixel.getBlue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Run run = (Run) o;

        if (pixel != null ? !pixel.equals(run.pixel) : run.pixel != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format("Run[%d,%d,%d,%d]", runLength, pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
}
