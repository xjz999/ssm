package entity;

public class Art {
	private String oid;
	private String title;
	private String useroid;
	private String photoaddr;
	private String phototime;
	private String story;
	private String url;
	private int orderindex = -1;
	private long createtime;
	private long edittime;
	private int ctype = -1;
	private int awardtype = -1;
	private int aligntype = -1;
	private int checked = -1;
	private int deleted = -1;
	private String author;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUseroid() {
		return useroid;
	}
	public void setUseroid(String useroid) {
		this.useroid = useroid;
	}
	public String getPhotoaddr() {
		return photoaddr;
	}
	public void setPhotoaddr(String photoaddr) {
		this.photoaddr = photoaddr;
	}
	public String getPhototime() {
		return phototime;
	}
	public void setPhototime(String phototime) {
		this.phototime = phototime;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getOrderindex() {
		return orderindex;
	}
	public void setOrderindex(int orderindex) {
		this.orderindex = orderindex;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public long getEdittime() {
		return edittime;
	}
	public void setEdittime(long edittime) {
		this.edittime = edittime;
	}
	public int getCtype() {
		return ctype;
	}
	public void setCtype(int ctype) {
		this.ctype = ctype;
	}
	public int getAwardtype() {
		return awardtype;
	}
	public void setAwardtype(int awardtype) {
		this.awardtype = awardtype;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getAligntype() {
		return aligntype;
	}
	public void setAligntype(int aligntype) {
		this.aligntype = aligntype;
	}
	
	
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password="
//				+ password + "]";
//	}
}
