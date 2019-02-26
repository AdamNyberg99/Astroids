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
	 * Skapar och returnerar en bild som utgörs av ett område/ en ruta på grundbilden image.
	 */
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * width) -width, (row * height) - height, width, height);
		return img;
	}
	
	/**
	 * Tar in hur många rader och kolumner, samt brädden och höjden på varje kolumn för spriteSheeten, genererar en och
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
