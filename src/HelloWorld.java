import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloWorld extends AbstractHandler {
	Hashtable<String, BufferedImage> ht = new Hashtable<String, BufferedImage>();

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String baseLink = "http://wallpaperswide.com/download/";
		response.setContentType("image/jpg;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		FileHandler myFileHandler = new FileHandler();
		ScalarHandler s = new ScalarHandler();

		String fileName = myFileHandler.getFileName(request);

		BufferedImage myRamImage;
		BufferedImage myHddImage;
		BufferedImage myOriginalImage;
		BufferedImage myEditedImage;

		int width = 50, height = 50;
		boolean isScale = false, isGray = false;

		Enumeration<String> parametersNames = request.getParameterNames();

		while (parametersNames.hasMoreElements()) {
			String name = (String) parametersNames.nextElement();
			String value = request.getParameter(name).toString();

			try {
				if (name.equals("color") && value.equals("gray")) {
					isGray = true;
				}
				if (name.equals("width") || name.equals("height")) {
					isScale = true;
					if (name.equals("width")) {
						width = Integer.parseInt(value);
					} else {
						height = Integer.parseInt(value);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		myHddImage = myFileHandler.controlImage(fileName, width, height, isScale, isGray);
		myRamImage = ht.get(getKey(fileName, width, height, isScale, isGray));
		if (myRamImage != null) {
			System.out.println("ramden aldiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
			response.setHeader("Content-Type", "image/jpg");
			ImageIO.write((BufferedImage) myRamImage, "jpg", response.getOutputStream());
		} else if (myHddImage != null) {
			System.out.println("hddden aldiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
			response.setHeader("Content-Type", "image/jpg");
			ImageIO.write(myHddImage, "jpg", response.getOutputStream());
			ht.put(getKey(fileName, width, height, isScale, isGray), myHddImage); // ramde
			// yoksa
			// diskten
			// rame
			// aldık.

		} else {
			System.out.println("webden aldiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
			myOriginalImage = myFileHandler.getImageFromWeb(baseLink + fileName + ".jpg");
			myEditedImage = myOriginalImage;
			if (isScale)
				myEditedImage = s.createResizedCopy(myEditedImage, width, height, true);
			if (isGray)
				myEditedImage = s.convertGrayScale(myEditedImage);

			response.setHeader("Content-Type", "image/jpg");
			ImageIO.write(myEditedImage, "jpg", response.getOutputStream());
			baseRequest.setHandled(true);

			ht.put(getKey(fileName, width, height, isScale, isGray), myEditedImage); // hddde
																					// de
																					// yoksa
																					// webden
																					// alıp
																					// rame
																					// yazdık

			try {
				myFileHandler.saveOriginalImage(myOriginalImage, fileName);
				if (isScale && isGray)
					myFileHandler.saveGrayAndScaledImage(myEditedImage, width, height, fileName);
				else if (isScale)
					myFileHandler.saveScaledImage(myEditedImage, width, height, fileName);
				else if (isGray)
					myFileHandler.saveGrayImage(myEditedImage, fileName);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new HelloWorld());
		server.start();
		server.join();
	}

	private String getKey(String fileName, int width, int height, boolean isScale, boolean isGray) {

		if (isScale && isGray)
			return fileName + width + "x" + height + "toGray";
		else if (isScale)
			return fileName + width + "x" + height;
		else if (isGray)
			return fileName + "toGray";
		else
			return fileName;
	}
}