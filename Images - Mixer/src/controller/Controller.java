package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import models.Tree;
import views.FiltersChooserDialog;
import views.MainFrame;

public class Controller implements ActionListener{

	MainFrame mf;
	Tree tree;
	FiltersChooserDialog filtersChooserDialog;
	JFileChooser jfc;
	
	public Controller() {
		tree = new Tree();
		mf = new MainFrame(this, tree);
		filtersChooserDialog = new FiltersChooserDialog(this);
		jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Actions.valueOf(e.getActionCommand())) {
		case ADD_NODE_MENU_ITEM:
			addChildNode();
			break;
		case ADD_IMAGE_MENU_ITEM:
			addImageToNode();
			break;
		case SEE_IMAGE_MENU_ITEM:
			updateShowedImage();
			break;
		case ADD_FILTER_MENU_ITEM:
			filtersChooserDialog.showUp();;
			break;
		case OK_FILTER_CHOOSER_BTN:
			addFilterToNode();
			break;
		case DELETE_NODE_MENU_ITEM:
			deleteNode();
			break;
		case EXPAND_TREE_BTN:
			expandTree();
			break;
		case COLLAPSE_TREE_BTN:
			collapseTree();
			break;
		}
	}
	
	private void deleteNode() {
		mf.saveTreeExtendedState();
		tree.deleteNode(tree.getRootNode(), mf.getSelectedNode());
		mf.updateTreePanel(tree.getRootNode());
		mf.loadTreeExtendedState();
	}
	
	private void updateShowedImage() {
		if(!mf.getSelectedNode().isLeaf()) {
		mf.getSelectedNode().applyFilter();
		}
		mf.getImagePanel().setImg(mf.getSelectedNode().getImg());
		mf.getImagePanel().repaint();
	}

	private void addChildNode() {
		mf.saveTreeExtendedState();
		tree.addChildNode(mf.getSelectedNode());
		mf.updateTreePanel(tree.getRootNode());
		mf.loadTreeExtendedState();
	}
	
	private void addFilterToNode() {
		mf.saveTreeExtendedState();
		mf.getSelectedNode().setFilter(tree.getFilterManager().getIndexFilter(filtersChooserDialog.getSelectedFilter()));
		mf.getSelectedNode().applyFilter();
		mf.getSelectedNode().setFilterName(filtersChooserDialog.getSelectedFilter());
		mf.updateTreePanel(tree.getRootNode());
		mf.loadTreeExtendedState();
		filtersChooserDialog.setVisible(false);
	}
	
	private void addImageToNode() {
		BufferedImage img = null;
		
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes());
		jfc.setFileFilter(imageFilter);
		int returnValue = jfc.showOpenDialog(mf);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			img = tree.getImagesManager().catchImageFromURL(jfc.getSelectedFile().getAbsolutePath());
		}
		try {
		mf.getSelectedNode().setImg(img);
		mf.getSelectedNode().setImgName(jfc.getSelectedFile().getName());
		}catch (Exception e) {
		}
	}
	
	private void collapseTree() {
		mf.collapseTree();
	}
	
	private void expandTree() {
		mf.expandTree();
	}
}