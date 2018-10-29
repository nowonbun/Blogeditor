package bean;

public class PostBean {
	private String categoryCode;
	private String title;
	private String contents;
	private String urlkey;
	private String changefleg;
	private String priority;
	private byte[] image;

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUrlkey() {
		return urlkey;
	}

	public void setUrlkey(String urlkey) {
		this.urlkey = urlkey;
	}

	public String getChangefleg() {
		return changefleg;
	}

	public void setChangefleg(String changefleg) {
		this.changefleg = changefleg;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
