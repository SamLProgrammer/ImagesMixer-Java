package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import controller.Actions;
import models.Node;
import models.Tree;

public class TreePanel extends JPanel{
	
	private JTree jTree;
	private JPopupMenu rightClickMenu;
	private RoundJButton expandBtn, collapseBtn;
	private JMenuItem addNode, addImage, seeImage, addFilter, deleteNode;
	private ArrayList<Integer> expandedItemsList;
	private int hInset;
	
	public TreePanel(ActionListener listener, Tree tree) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	hInset= (int)(screenDimension.getWidth()/40);
		initComponents(listener, tree.getRootNode());
		expandedItemsList = new ArrayList<>();
	}
	
	public void initComponents(ActionListener listener, Node rootNode) {
		setUIManager();
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.decode("#003B46"));
		buttonsPanel.setLayout(new GridLayout(1, 2));
		JPanel expandBtnPanel = new JPanel();
		expandBtnPanel.setLayout(new BorderLayout());
		expandBtnPanel.setOpaque(false);
		expandBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, hInset, 0, hInset/2));
		JPanel collapseBtnPanel = new JPanel();
		collapseBtnPanel.setLayout(new BorderLayout());
		collapseBtnPanel.setOpaque(false);
		collapseBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, hInset/2, 0, hInset));
		expandBtn = new RoundJButton("#BA5536", "/img/expand.png", 10);
		expandBtn.setBackground(Color.decode("#002b33"));
		expandBtn.setText("Expand All");
		collapseBtn = new RoundJButton("#BA5536", "/img/collapse.png", 10);
		collapseBtn.setBackground(Color.decode("#002b33"));
		collapseBtn.setText("<html><p style=\"text-align: center;\">Collapse All</p></html");
		collapseBtn.setForeground(Color.decode("#EEF3F7"));
		expandBtn.setBackground(Color.decode("#002b33"));
		expandBtn.setText("<html><p style=\"text-align: center;\">Expand All</p></html");
		expandBtn.setForeground(Color.decode("#EEF3F7"));
		expandBtnPanel.add(expandBtn);
		collapseBtnPanel.add(collapseBtn);
		buttonsPanel.add(expandBtnPanel);
		buttonsPanel.add(collapseBtnPanel);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		rightClickMenu = new JPopupMenu();
		expandBtn.addActionListener(listener);
		expandBtn.setActionCommand(Actions.EXPAND_TREE_BTN.name());
		collapseBtn.addActionListener(listener);
		collapseBtn.setActionCommand(Actions.COLLAPSE_TREE_BTN.name());
		addNode = new JMenuItem("Add ChildNode");
		addNode.addActionListener(listener);
		addNode.setActionCommand(Actions.ADD_NODE_MENU_ITEM.name());
		addImage = new JMenuItem("Add Image");
		addImage.addActionListener(listener);
		addImage.setActionCommand(Actions.ADD_IMAGE_MENU_ITEM.name());
		seeImage = new JMenuItem("See Image");
		seeImage.addActionListener(listener);
		seeImage.setActionCommand(Actions.SEE_IMAGE_MENU_ITEM.name());
		addFilter = new JMenuItem("Add Filter");
		addFilter.addActionListener(listener);
		addFilter.setActionCommand(Actions.ADD_FILTER_MENU_ITEM.name());
		deleteNode = new JMenuItem("Delete Node");
		deleteNode.addActionListener(listener);
		deleteNode.setActionCommand(Actions.DELETE_NODE_MENU_ITEM.name());
		jTree = new JTree(initModel(rootNode));
		jTree.setBackground(Color.decode("#3a4445"));
		rightClickMenu.add(addNode);
		rightClickMenu.add(addFilter);
		rightClickMenu.add(addImage);
		rightClickMenu.add(seeImage);
		rightClickMenu.add(deleteNode);
		
		MouseListener ml = new MouseAdapter() {
		     public void mousePressed(MouseEvent e) {
		         if(SwingUtilities.isRightMouseButton(e)){
		         int selRow = jTree.getRowForLocation(e.getX(), e.getY());
		         TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
		                 jTree.setSelectionPath(selPath); 
		                 if (selRow>-1){
		                    jTree.setSelectionRow(selRow);
		                    rightClickMenu.show(jTree, e.getX(), e.getY());
		                    DefaultMutableTreeNode selectedNode = 
		         			       (DefaultMutableTreeNode)jTree.getLastSelectedPathComponent();
		                    Node node = (Node)selectedNode.getUserObject();
		                    if(node.isLeaf()) {
		                    	addImage.setEnabled(true);
		                    	addFilter.setEnabled(false);
		                    }
		                    else {
		                    	addImage.setEnabled(false);
		                    	if(node.hasResources()) {
		                    	addFilter.setEnabled(true);
		                    	}
		                    }
		                    if(node.iHaveImage()) {
		                    	seeImage.setEnabled(true);
		                    }
		                    else {
		                    	seeImage.setEnabled(false);
		                    }
		                 }
		     }
		 }
		};
		jTree.addMouseListener(ml);
		jTree.setCellRenderer(new MyTreeCellRenderer());
		add(buttonsPanel);
		add(new JScrollPane(jTree));
	}

	public DefaultMutableTreeNode initModel(Node node) {
		DefaultMutableTreeNode model = new DefaultMutableTreeNode(node,true);
		if(!node.isLeaf()) {
			for (Node auxNode : node.getChilds()) {
				DefaultMutableTreeNode auxModel = new DefaultMutableTreeNode(auxNode, true);
				if(!auxNode.isLeaf()) {
					model.add(initModel(auxNode));
				}
				else {
				model.add(auxModel);
				}
			}
		}
		return model;
	}
	
	public Node getSelectedNode() {
		DefaultMutableTreeNode selectedNode = 
			       (DefaultMutableTreeNode)jTree.getLastSelectedPathComponent();
		return (Node)selectedNode.getUserObject();
	}
	
	public void repaintTree(Node node) {
		jTree.setModel(new DefaultTreeModel(initModel(node)));
	}
	
	public void reloadModel() {
		DefaultTreeModel model = (DefaultTreeModel)jTree.getModel();
		model.reload();
	}
	
	public void updateExtendedItemsList() {
		expandedItemsList.clear();
		for (int i = 0; i < jTree.getRowCount(); i++) {
			if(jTree.isExpanded(i)) {
				expandedItemsList.add(i);
			}
		}
	}
	
	public void expandAll() {
		for (int i = 0; i < jTree.getRowCount(); i++) {
			jTree.expandRow(i);
		}
	}
	
	public void collapseAll() {
		for (int i = 0; i < jTree.getRowCount(); i++) {
			jTree.collapseRow(i);
		}
	}
	
	public void updateTreeExpandedState() {
		for (int i = 0; i < expandedItemsList.size(); i++) {
			jTree.expandRow(expandedItemsList.get(i));
		}
	}
	
	private void setUIManager() {
		UIManager.put("MenuItem.font", new Font("Oswald", Font.PLAIN, 20));
	}
	
}
