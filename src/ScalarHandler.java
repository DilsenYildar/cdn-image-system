import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ScalarHandler {

    /**
     * Takes original {@link Image} and scales it according to the scaledWidth
     * and scaledHeight parameters. If original image contains alpha color code
     * and you want to save them, please set preserveAlpha parameter true.
     * 
     * @param originalImage
     *            Image to scale
     * @param scaledWidth
     *            Target image's width
     * @param scaledHeight
     *            Target image' height
     * @param preserveAlpha
     *            condition to care alpha color code.
     * @return Scaled {@link Image} object.
     */
    public BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight,  boolean preserveAlpha) {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    /**
     * Converts given image to gray scale.
     * 
     * @param originalImage
     *            to transform into gray scale.
     * @return gray scaled copy of original image.
     */
    public BufferedImage convertGrayScale(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(originalImage.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(red + green + blue,

                        red + green + blue, red + green + blue);

                newImg.setRGB(j, i, newColor.getRGB());
            }
        }
        return newImg;
    }

    /**
     * Merges given to images into one.
     * 
     * @param firstImage
     *            First image to tile.
     * @param secondImage
     *            Second image to tile.
     * @return merged image.
     */
    public BufferedImage merge(BufferedImage firstImage, BufferedImage secondImage) {
        BufferedImage newImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(firstImage, 0, 0, null);
        g.drawImage(secondImage, 50, 0, null);
        return newImage;
    }
}