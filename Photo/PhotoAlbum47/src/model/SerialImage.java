package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @author hassan and deep
 *
 */
public class SerialImage implements Serializable {

	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * buffered image
	 */
	private transient BufferedImage img;

	/**constructor for SerialImage
	 * @param img BufferedImage
	 */
	public SerialImage(BufferedImage img) {
		this.img = img;
	}

	/**setting the image
	 * @param image BufferedImage
	 */
	public void setImage(BufferedImage image) {
		this.img = image;
	}

	/**getting buffered image
	 * @return buffered image
	 */
	public BufferedImage getImage() {
		return img;
	}

	/**getting javafx image
	 * @return javafx image
	 */
	public Image getFXimg() {
		return SwingFXUtils.toFXImage(img, null);
	}

	/**write image to file
	 * @param s ObjectInputStream
	 * @throws IOException input/output exception
	 * @throws ClassNotFoundException class not found exception
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		img = ImageIO.read(ImageIO.createImageInputStream(s));
	}

	/**read image from file
	 * @param s ObjectOutputStream
	 * @throws IOException input/output exception
	 */
	private void writeObject(ObjectOutputStream s) throws IOException {
		ImageIO.write(img, "jpg", ImageIO.createImageOutputStream(s));
	}

}