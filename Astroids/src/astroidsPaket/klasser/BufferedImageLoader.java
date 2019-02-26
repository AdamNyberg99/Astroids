package astroidsPaket.klasser;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image = null;
	
	/**
	 * 
	 * @param filnamn tar in namnet/ path:en för bildfilen
	 * @return bilden
	 * @throws IOException
	 * Tar in filnamn för bilden laddar in den och returnar bilden som en bufferedImage.
	 */
	public BufferedImage loadImage(String filnamn) throws IOException {
		image = ImageIO.read(new File(filnamn));
		return image;
	}

}
