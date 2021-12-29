package models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface Filter {

	 BufferedImage applyFilter(ArrayList<BufferedImage> imagesList);
	
}
