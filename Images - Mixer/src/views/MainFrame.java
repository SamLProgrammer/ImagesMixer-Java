package views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.Node;
import models.Tree;

public class MainFrame extends JFrame{

	ImagePanel imagePanel;
	TreePanel treePanel;
	
	public MainFrame(ActionListener listener, Tree tree) {
		setLayout(new GridLayout(1, 2));
//		ImageIcon myIcon = new ImageIcon(getClass().getResource("/img/jpgmixerlogo.jpg"));
		setIconImage(new ImageIcon(getClass().getResource("/img/JPGMixerLogo.jpg")).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents(tree, listener);
		locate();
		setVisible(true);
	}
	
	private void initComponents(Tree tree, ActionListener listener) {
		imagePanel = new ImagePanel();
		treePanel = new TreePanel(listener, tree);
		add(treePanel);
		add(imagePanel);
	}
	
	private void locate() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(dim.getWidth()/2), (int)(dim.getHeight()/2));
		setLocation((int)(dim.getWidth()/2-this.getWidth()/2), (int)(dim.getHeight()/2 - this.getHeight()/2));
	}
	
	public Node getSelectedNode() {
		return treePanel.getSelectedNode();
	}
	
	public void updateTreePanel(Node node) {
		treePanel.repaintTree(node);
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	public void reloadModelTree() {
		treePanel.reloadModel();
	}
	
	public void saveTreeExtendedState() {
		treePanel.updateExtendedItemsList();
	}
	
	public void loadTreeExtendedState() {
		treePanel.updateTreeExpandedState();
	}
	
	public void collapseTree() {
		treePanel.collapseAll();
	}
	
	public void expandTree() {
		treePanel.expandAll();
	}
}
