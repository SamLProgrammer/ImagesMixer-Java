package models;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FilterManager {

	private ArrayList<Filter> filtersList;
	
	private static final String FIRST_FILTER_NAME = "RANDOM - CHOOSER - FILTER";
	private static final String SECOND_FILTER_NAME = "VERTICAL - COLLAGE";
	private static final String THIRD_FILTER_NAME = "HORIZONTAL - COLLAGE";
	private static final String FOURTH_FILTER_NAME = "CLOCK - ROTATION";
	private static final String FIFTH_FILTER_NAME = "AGAINST - CLOCK - ROTATION";
	private static final String SIXTH_FILTER_NAME = "LIGHT - IMAGE";
	private static final String SEVENTH_FILTER_NAME = "DARK - IMAGE";
	private static final String EIGHTH_FILTER_NAME = "INVERT";
	private static final String NINETH_FILTER_NAME = "CAOS";
	
	public FilterManager() {
		initFiltersList();
	}
	
	public void initFiltersList() {
		filtersList = new ArrayList<>();
		Filter randomChooserImage = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				return imagesList.get((int)(Math.random()*imagesList.size()));
			}
		};
		
		Filter horizontalCollage = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				ArrayList<BufferedImage> auxImagesList = new ArrayList<>();
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < imagesList.size(); i++) {
					BufferedImage auxImage = resizeImage(imagesList.get(i));
					 auxImagesList.add(auxImage);
				}
				
				int counter1 = 0, counter2 = 500/auxImagesList.size();
				ArrayList<BufferedImage> randomImagesList = randomizeList(auxImagesList);
				for (int j = 0; j < randomImagesList.size(); j++) {
					for (int i = counter1; i < counter2; i++) {
						for (int k = 0; k < 500; k++) {
							image.setRGB(i, k, randomImagesList.get(j).getRGB(i, k));
						}
						counter1++;
					}
					counter2 += 500/randomImagesList.size();
				}
				return image;
			}
		};
		
		Filter verticalCollage = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				ArrayList<BufferedImage> auxImagesList = new ArrayList<>();
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < imagesList.size(); i++) {
					BufferedImage auxImage = resizeImage(imagesList.get(i));
					 auxImagesList.add(auxImage);
				}
				int counter1 = 0, counter2 = 500/auxImagesList.size();
				ArrayList<BufferedImage> randomImagesList = randomizeList(auxImagesList);
				for (int j = 0; j < randomImagesList.size(); j++) {
					for (int i = counter1; i < counter2; i++) {
						for (int k = 0; k < 500; k++) {
							image.setRGB(k, i, randomImagesList.get(j).getRGB(k, i));
						}
						counter1++;
					}
					counter2 += 500/randomImagesList.size();
				}
				return image;
			}
		};
		
		Filter clockRotation = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				BufferedImage img = imagesList.get((int)(Math.random()*imagesList.size()));
				BufferedImage auxImage = resizeImage(img);
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						image.setRGB(i, j, auxImage.getRGB(499-j, 499-i));
					}
				}
				return image;
			}
		};
		
		Filter againstClockRotation = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				BufferedImage img = imagesList.get((int)(Math.random()*imagesList.size()));
				BufferedImage auxImage = resizeImage(img);
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						image.setRGB(i, j, auxImage.getRGB(j, i));
					}
				}
				return image;
			}
		};

		Filter lightImage = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				BufferedImage img = imagesList.get((int)(Math.random()*imagesList.size()));
				BufferedImage auxImage = resizeImage(img);
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						int rgb = auxImage.getRGB(i, j);
						int red = (rgb >> 16) & 0xFF;
						int green = (rgb >> 8) & 0xFF;
						int blue = rgb & 0xFF;
						if(red + 50 < 255) {
						red += 50 & 0xFF;
						}
						if(green + 50 < 255) {
						green += 50 & 0xFF;
						}
						if(blue + 50 < 255) {
						blue += 50 & 0xFF;
						}
						rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);
						image.setRGB(i, j, rgb);
					}
				}
				return image;
			}
		};
		
		Filter darkImage = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				BufferedImage img = imagesList.get((int)(Math.random()*imagesList.size()));
				BufferedImage auxImage = resizeImage(img);
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						int rgb = auxImage.getRGB(i, j);
						int red = (rgb >> 16) & 0xFF;
						int green = (rgb >> 8) & 0xFF;
						int blue = rgb & 0xFF;
						if(red - 40 > 0) {
						red -= 40 & 0xFF;
						}
						if(green - 40 > 0) {
						green -= 40 & 0xFF;
						}
						if(blue - 40 > 0) {
						blue -= 40 & 0xFF;
						}
						rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);
						image.setRGB(i, j, rgb);
					}
				}
				return image;
			}
		};
		
		Filter invert = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				BufferedImage img = imagesList.get((int)(Math.random()*imagesList.size()));
				BufferedImage auxImage = resizeImage(img);
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						int rgb = auxImage.getRGB(i, j);
						int red = (rgb >> 16) & 0xFF;
						int green = (rgb >> 8) & 0xFF;
						int blue = rgb & 0xFF;
						
						rgb = ((green&0x0ff)<<16)|((blue&0x0ff)<<8)|(red&0x0ff);
						image.setRGB(i, j, rgb);
					}
				}
				return image;
			}
		};
		
		Filter caosFilter = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				ArrayList<BufferedImage> auxImagesList = new ArrayList<>();
				BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < imagesList.size(); i++) {
					BufferedImage auxImage = resizeImage(imagesList.get(i));
					 auxImagesList.add(auxImage);
				}
				int counter1 = 0, counter2 = 500/auxImagesList.size();
				ArrayList<BufferedImage> randomImagesList = randomizeList(auxImagesList);
				int index = 0;
				for (int j = 0; j < randomImagesList.size(); j++) {
					for (int i = counter1; i < counter2; i++) {
						for (int k = 0; k < 500; k++) {					
								image.setRGB(i, k, randomImagesList.get(index).getRGB(i, k));
							index++;
							if(index > randomImagesList.size()-1) {
								index = 0;
							}
						}
						counter1++;
					}
					counter2 += 500/randomImagesList.size();
				}
				return image;
			}
		};
		
		Filter filter10 = new Filter() {
			public BufferedImage applyFilter(ArrayList<BufferedImage> imagesList) {
				return null;
			}
		};
		
		filtersList.add(randomChooserImage);
		filtersList.add(verticalCollage);
		filtersList.add(horizontalCollage);
		filtersList.add(clockRotation);
		filtersList.add(againstClockRotation);
		filtersList.add(lightImage);
		filtersList.add(darkImage);
		filtersList.add(invert);
		filtersList.add(caosFilter);
		filtersList.add(filter10);
	}
	
	public Filter getIndexFilter(String string) {
		Filter filter = null;
		switch (string) {
		case FIRST_FILTER_NAME:
			filter = filtersList.get(0);
			break;
		case SECOND_FILTER_NAME:
			filter = filtersList.get(1);
			break;
		case THIRD_FILTER_NAME:
			filter = filtersList.get(2);
			break;
		case FOURTH_FILTER_NAME:
			filter = filtersList.get(3);
			break;
		case FIFTH_FILTER_NAME:
			filter = filtersList.get(4);
			break;
		case SIXTH_FILTER_NAME:
			filter = filtersList.get(5);
			break;
		case SEVENTH_FILTER_NAME:
			filter = filtersList.get(6);
		case EIGHTH_FILTER_NAME:
			filter = filtersList.get(7);
			break;
		case NINETH_FILTER_NAME:
			filter = filtersList.get(8);
			break;
		}
		return filter;
	}
	
	private BufferedImage resizeImage(BufferedImage img) {
		BufferedImage auxImage =  new BufferedImage(500,
                500, img.getType());
	 Graphics2D g2d = auxImage.createGraphics();
        g2d.drawImage(img, 0, 0, 500, 500, null);
        g2d.dispose();
        return auxImage;
	}
	
	private ArrayList<BufferedImage> randomizeList(ArrayList<BufferedImage> list) {
		ArrayList<BufferedImage> randomImagesList = new ArrayList<>();
		int randomIndex = 0, listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			randomIndex = (int)(Math.random()*list.size());
			randomImagesList.add(list.get(randomIndex));
			list.remove(randomIndex);
		}
		return randomImagesList;
	}
	
	public String[] getFiltersNames() {
		String[] namesVector = {FIRST_FILTER_NAME,SECOND_FILTER_NAME,THIRD_FILTER_NAME,FOURTH_FILTER_NAME,
								FIFTH_FILTER_NAME,SIXTH_FILTER_NAME,SEVENTH_FILTER_NAME,EIGHTH_FILTER_NAME,
								NINETH_FILTER_NAME};
		return namesVector;
	}
	
	public ArrayList<Filter> getFiltersList() {
		return filtersList;
	}
	
}
