package bean;

import java.util.List;

public class ListBean {
	private String categoryCode;
	private String listTitle;
	private List<ListItemBean> listItem;
	private int listCount;

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}

	public List<ListItemBean> getListItem() {
		return listItem;
	}

	public void setListItem(List<ListItemBean> listItem) {
		this.listItem = listItem;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

}
