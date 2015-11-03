package data;

/**
 * Created with IntelliJ IDEA.
 * User: jkates
 * Date: 10/14/15
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pixel {

    private short red;
    private short green;
    private short blue;

    public Pixel() {
        red = 0;
        green = 0;
        blue = 0;
    }

    public Pixel(short red, short green, short blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Pixel(int red, int green, int blue) {
        this((short) red, (short) green, (short) blue);
    }

    public void setValues(short red, short green, short blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Pixel zeroPixel() {
        return new Pixel();
    }

    public void setGrayscaleValue(short value) {
        red = value;
        green = value;
        blue = value;
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pixel)) {
            return false;
        } else {
            Pixel pixel = (Pixel) obj;
            return (getRed() == pixel.getRed() && getGreen() == pixel.getGreen() && getBlue() == pixel.getBlue());
        }
    }


    @Override
    public String toString() {
        return "Pixel{" + "red=" + red +  ", green=" + green + ", blue=" + blue + '}' + "\n";
    }
}
