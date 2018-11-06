package bean;

import java.util.List;

public class PostBean {
	private int idx;
	private String postCode;
	private String categoryCode;
	private String title;
	private String contents;
	private String urlkey;
	private String changeflag;
	private String priority;
	private String summary;
	private String image;
	private boolean isPrePost;
	private boolean isNextPost;
	private int prePostIdx;
	private String prePost;
	private String prePostDate;
	private int nextPostIdx;
	private String nextPost;
	private String nextPostDate;
	private boolean isPreNextPostView;
	private boolean isViewRecently;
	private List<ListItemBean> recentlyList;

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

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

	public String getChangeflag() {
		return changeflag;
	}

	public void setChangeflag(String changeflag) {
		this.changeflag = changeflag;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public boolean getisPrePost() {
		return isPrePost;
	}

	public void setPrePost(boolean isPrePost) {
		this.isPrePost = isPrePost;
	}

	public boolean getisNextPost() {
		return isNextPost;
	}

	public void setNextPost(boolean isNextPost) {
		this.isNextPost = isNextPost;
	}

	public int getPrePostIdx() {
		return prePostIdx;
	}

	public void setPrePostIdx(int prePostIdx) {
		this.prePostIdx = prePostIdx;
	}

	public String getPrePost() {
		return prePost;
	}

	public void setPrePost(String prePost) {
		this.prePost = prePost;
	}

	public String getPrePostDate() {
		return prePostDate;
	}

	public void setPrePostDate(String prePostDate) {
		this.prePostDate = prePostDate;
	}

	public int getNextPostIdx() {
		return nextPostIdx;
	}

	public void setNextPostIdx(int nextPostIdx) {
		this.nextPostIdx = nextPostIdx;
	}

	public String getNextPost() {
		return nextPost;
	}

	public void setNextPost(String nextPost) {
		this.nextPost = nextPost;
	}

	public String getNextPostDate() {
		return nextPostDate;
	}

	public void setNextPostDate(String nextPostDate) {
		this.nextPostDate = nextPostDate;
	}

	public boolean getisPreNextPostView() {
		return isPreNextPostView;
	}

	public void setPreNextPostView(boolean isPreNextPostView) {
		this.isPreNextPostView = isPreNextPostView;
	}

	public boolean getisViewRecently() {
		return isViewRecently;
	}

	public void setViewRecently(boolean isViewRecently) {
		this.isViewRecently = isViewRecently;
	}

	public List<ListItemBean> getRecentlyList() {
		return recentlyList;
	}

	public void setRecentlyList(List<ListItemBean> recentlyList) {
		this.recentlyList = recentlyList;
	}

}
