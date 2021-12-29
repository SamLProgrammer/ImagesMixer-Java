package models;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImagesManager {
	
	public BufferedImage catchImageFromURL(String string) { 
		File file = new File(string);
		BufferedImage img = null;
		try {
			 img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}
