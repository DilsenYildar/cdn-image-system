import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.imageio.ImageIO;

public class FileHandler {
	public static String TUMBNAILS = "./img/tumbnails/";

	/**
	 * Control the file in given path.
	 * 
	 * @param path
	 *            Path of an image file.
	 * @return Return true if file exist.
	 */
	private boolean fileExist(String filePath) {
		File myFile = new File(filePath);
		if (myFile.exists() && !myFile.isDirectory())
			return true;
		return false;
	}

	/**
	 * @throws IOException
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public BufferedImage controlImage(String fileName, int width, int height, boolean isScale, boolean isGray)
			throws IOException {
		String path;
		if (isScale && isGray) {
			path = "./img/tumbnails/" + fileName + width + "x" + height + "toGray" + ".jpg";
			System.out.println(fileExist(path) + "isScale&isGray");
		} else if (isScale) {
			path = "./img/tumbnails/" + fileName + width + "x" + height + ".jpg";
			System.out.println(fileExist(path) + "isScale");
		} else if (isGray) {
			path = "./img/tumbnails/" + fileName + "toGray.jpg";
			System.out.println(fileExist(path) + "isGray");
		} else {
			path = "./img/" + fileName + ".jpg";
			System.out.println(fileExist(path) + "notMarkup");
		}
		if (fileExist(path))
			return ImageIO.read(new File(path));
		else
			return null;
	}

	/**
	 * get file name from request
	 * 
	 * 
	 * 
	 */
	public String getFileName(HttpServletRequest request) {
		String path = request.getPathInfo().toString();
		// path = "." + path;
		return path.substring(5, path.indexOf(".jpg"));
	}

	/**
	 * Get image given URL
	 * 
	 * @throws IOException
	 * 
	 * 
	 * 
	 */
	public BufferedImage getImageFromWeb(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		BufferedImage srcImage = ImageIO.read(is);

		is.close();
		return srcImage;
	}

	/**
	 * Get the request path
	 * 
	 * 
	 * 
	 * 
	 */
	public String getPath(HttpServletRequest request) {
		String path = request.getPathInfo().toString();
		return path = "." + path;
	}

	/**
	 * Save the scaled buffered image to given path.
	 * 
	 * @param image
	 *            Buffered Image of an image file.
	 * @param width
	 *            Width of new image.
	 * @param height
	 *            Height of new image.
	 * @param filename
	 *            Name on which new file is expected to be found.
	 * @return path of geneterated tumbnail.
	 * @throws Exception
	 */
	public String saveScaledImage(BufferedImage image, int width, int height, String fileName) throws Exception {
		File tosave = new File(TUMBNAILS + fileName + width + "x" + height + ".jpg");
		ImageIO.write(image, "jpg", tosave);
		return tosave.getAbsolutePath();
	}

	/**
	 * Save the original buffered image to given path.
	 * 
	 * @param image
	 *            Buffered Image of an image file.
	 * @param width
	 *            Width of new image.
	 * @param height
	 *            Height of new image.
	 * @param filename
	 *            Name on which new file is expected to be found.
	 * @return path of geneterated tumbnail.
	 * @throws Exception
	 */
	public String saveOriginalImage(BufferedImage image, String fileName) throws Exception {
		File tosave = new File("./img/" + fileName + ".jpg");
		ImageIO.write(image, "jpg", tosave);
		return tosave.getAbsolutePath();
	}

	/**
	 * Save the gray buffered image to given path.
	 * 
	 * @param image
	 *            Buffered Image of an image file.
	 * @param filename
	 *            Name on which new file is expected to be found.
	 * @return path of geneterated tumbnail.
	 * @throws Exception
	 */
	public String saveGrayImage(BufferedImage image, String fileName) throws Exception {
		File tosave = new File(TUMBNAILS + fileName + "toGray.jpg");
		ImageIO.write(image, "jpg", tosave);
		return tosave.getPath();
	}

	/**
	 * Save the gray and scale buffered image to given path.
	 * 
	 * @param image
	 *            Buffered Image of an image file.
	 * @param width
	 *            Width of new image.
	 * @param height
	 *            Height of new image.
	 * @param filename
	 *            Name on which new file is expected to be found.
	 * @return path of geneterated tumbnail.
	 * @throws Exception
	 */
	public String saveGrayAndScaledImage(BufferedImage image, int width, int height, String fileName) throws Exception {
		File tosave = new File(TUMBNAILS + fileName + width + "x" + height + "toGray.jpg");
		ImageIO.write(image, "jpg", tosave);
		return tosave.getPath();
	}

	/**
	 * Save the merge buffered image to given path.
	 * 
	 * @param image
	 *            Buffered Image of an image file.
	 * @param filename
	 *            Target file name.
	 * @return Path of new image file.
	 * @throws Exception
	 */
	public String saveMerged(BufferedImage image, String fileName) throws Exception {
		File tosave = new File(TUMBNAILS + fileName + "Merge.jpg");
		ImageIO.write(image, "jpg", tosave);
		return tosave.getAbsolutePath();
	}
}
