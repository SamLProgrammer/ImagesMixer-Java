package models;

public class Tree {
	
	Node rootNode;
	ImagesManager imagesManager;
	FilterManager filterManager;
	
	public Tree() {
		rootNode = new Node();
		imagesManager = new ImagesManager();
		filterManager = new FilterManager();
	}
	
	public Node getRootNode() {
		return rootNode;
	}
	
	public void deleteNode(Node fatherNode, Node toDeleteNode) {
		if(sonExists(fatherNode, toDeleteNode)) {
			fatherNode.getChilds().remove(toDeleteNode);
		}
		else {
			for (Node node : fatherNode.getChilds()) {
				deleteNode(node, toDeleteNode);
			}
		}
	}
	
	public boolean sonExists(Node fatherNode, Node sonNode) {
		boolean flag = false;
		for (Node node : fatherNode.getChilds()) {
			if(sonNode.equals(node)) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void addChildNode(Node node) {
		Node newNode = new Node();
		node.addChild(newNode);
	}
	
	public void setFilterToNode(Node node,Filter filter) {
		node.setFilter(filter);
	}

	public ImagesManager getImagesManager() {
		return imagesManager;
	}

	public FilterManager getFilterManager() {
		return filterManager;
	}
}
