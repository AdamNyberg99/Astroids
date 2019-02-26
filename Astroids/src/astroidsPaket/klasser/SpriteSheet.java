package astroidsPaket.klasser;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage image;

	/**
	 * Tar in en bufferedImage
	 * @param image
	 */
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Skapar och returnerar en bild som utg�rs av ett omr�de/ en ruta p� grundbilden image.
	 */
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * width) -width, (row * height) - height, width, height);
		return img;
	}
	
	/**
	 * Tar in hur m�nga rader och kolumner, samt br�dden och h�jden p� varje kolumn f�r spriteSheeten, genererar en och
	 * returnerar en array med bufferedImages
	 */
	public BufferedImage[] generateArray(int numbOfColls, int numbOfRows, int width, int height) {
		BufferedImage[] picArray = new BufferedImage[numbOfColls*numbOfRows];
		int arrayPos = 0;
		for(int i = 1; i <= numbOfColls; i++) {
			for(int j = 1; j <= numbOfRows; j++) {
				picArray[arrayPos] = grabImage(i, j, width, height);
				arrayPos++;
			}
		}
		return picArray;
		
	}

}
