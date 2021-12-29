package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import models.Node;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {
	
	private int iconWidth;
	private int iconHeight;

    MyTreeCellRenderer() {
    	Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	iconWidth = (int)(screenDimension.getWidth()/35);
    	iconHeight = (int)(screenDimension.getHeight()/35);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
    	DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
    	
    	renderer.setFont(new Font("Oswald", Font.PLAIN, 23));
        Object object = ((DefaultMutableTreeNode) value).getUserObject();
            renderer.setBackgroundNonSelectionColor(Color.decode("#3a4445"));
            renderer.setBackgroundSelectionColor(Color.decode("#8D230F"));
            renderer.setForeground(Color.decode("#F4EADE"));
        if (object instanceof Node) {
            Node node = (Node) object;
            if (node.isLeaf()) {
            	if(node.getImg() != null) {
            	BufferedImage img = resizeImage(node.getImg());
                renderer.setIcon(new ImageIcon(img));
                renderer.setText(node.getImgName());
            	}
            	else {
            		ImageIcon myIcon = new ImageIcon(getClass().getResource("/img/image3.png"));
            		Image img = myIcon.getImage();
            		Image newimg = img.getScaledInstance(iconWidth, iconHeight,  java.awt.Image.SCALE_SMOOTH);
            		ImageIcon newIcon = new ImageIcon(newimg);
            		renderer.setIcon(newIcon);
            		renderer.setText("Empty Node");
            	}
            }
            else {
            	ImageIcon myIcon = new ImageIcon(getClass().getResource("/img/folder3.png"));
        		Image img = myIcon.getImage();
        		Image newimg = img.getScaledInstance(iconWidth, iconHeight,  java.awt.Image.SCALE_SMOOTH);
        		ImageIcon newIcon = new ImageIcon(newimg);
        		renderer.setIcon(newIcon);
        		renderer.setFont(new Font("Oswald", Font.BOLD, 20));
        		renderer.setForeground(Color.decode("#EFB509"));
        		renderer.setText(node.getFilterName());
            }
        }
        return renderer;
    }
    
	private BufferedImage resizeImage(BufferedImage img) {
		BufferedImage auxImage =  new BufferedImage(iconWidth,
                iconHeight, img.getType());
	 Graphics2D g2d = auxImage.createGraphics();
        g2d.drawImage(img, 0, 0, iconWidth, iconHeight, null);
        g2d.dispose();
        return auxImage;
	}
}