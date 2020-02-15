package com.sp.sbbs;

public class Board {
    private int num, listNum;
    private String userName, userId;
    private String subject, content;
    private String created;
    private int hitCount;
    
    private int groupCategoryNum, categoryNum;
    private String groupCategory, category;
    private int parent;
    
	private int replyCount;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getGroupCategoryNum() {
		return groupCategoryNum;
	}

	public void setGroupCategoryNum(int groupCategoryNum) {
		this.groupCategoryNum = groupCategoryNum;
	}

	public int getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(int categoryNum) {
		this.categoryNum = categoryNum;
	}

	public String getGroupCategory() {
		return groupCategory;
	}

	public void setGroupCategory(String groupCategory) {
		this.groupCategory = groupCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	@Override
	public String toString() {
		return "Board [num=" + num + ", listNum=" + listNum + ", userName=" + userName + ", userId=" + userId
				+ ", subject=" + subject + ", content=" + content + ", created=" + created + ", hitCount=" + hitCount
				+ ", groupCategoryNum=" + groupCategoryNum + ", categoryNum=" + categoryNum + ", groupCategory="
				+ groupCategory + ", category=" + category + ", parent=" + parent + ", replyCount=" + replyCount
				+ ", getNum()=" + getNum() + ", getListNum()=" + getListNum() + ", getUserName()=" + getUserName()
				+ ", getUserId()=" + getUserId() + ", getSubject()=" + getSubject() + ", getContent()=" + getContent()
				+ ", getCreated()=" + getCreated() + ", getHitCount()=" + getHitCount() + ", getGroupCategoryNum()="
				+ getGroupCategoryNum() + ", getCategoryNum()=" + getCategoryNum() + ", getGroupCategory()="
				+ getGroupCategory() + ", getCategory()=" + getCategory() + ", getParent()=" + getParent()
				+ ", getReplyCount()=" + getReplyCount() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
