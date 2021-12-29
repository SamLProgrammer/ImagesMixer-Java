package models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Node {
	
	private BufferedImage img;
	
	private Filter filter;
	
	String imgName, filterName;
	
	private ArrayList<Node> childs;
	
	public Node() {
		childs = new ArrayList<>();
	}
	
	public boolean isLeaf() {
		return childs.size() == 0;
	}
	
	public boolean hasResources(){
		boolean flag = false;
		for (Node node : childs) {
			if(node.getImg() != null) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void applyFilter() {
		if(hasResources()) {
		ArrayList<BufferedImage> imagesList = new ArrayList<>();
		for (Node node : childs) {
			if(node.getImg() != null) {
				imagesList.add(node.getImg());
			}
		}
		img = this.filter.applyFilter(imagesList);
		}
	}
	
	public void addChild(Node node) {
		childs.add(node);
	}
	
	public boolean iHaveImage() {
		return img != null;
	}
	//============================================ GETTERS && SETTERS ==========================================

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public ArrayList<Node> getChilds() {
		return childs;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
