package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

/**
 * @author hassan and deep
 *
 */
public class Hash implements Serializable {
	/**
	 * serialVersionUID for serializing
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * array of bytes
	 */
	private byte[] hash;

	/**wrapper for ha mehtod
	 * @param img SerialImage
	 */
	public Hash(SerialImage img) {
		ha(img);
	}

	/**getting the has for the image
	 * @param img SsialImage
	 */
	public void ha(SerialImage img) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img.getImage(), "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] ha = os.toByteArray();
		hash = md.digest(ha);
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Hash))
			return false;
		Hash h = (Hash) o;
		for (int i = 0; i < hash.length; i++) {
			if (hash[i] != h.hash[i])
				return false;
		}
		return true;
	}

}
