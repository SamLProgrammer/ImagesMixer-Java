package views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	BufferedImage img;
	
	public ImagePanel() {
		  try {
			  InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("img/JPGMixerLogo.jpg");
			 img = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
}
