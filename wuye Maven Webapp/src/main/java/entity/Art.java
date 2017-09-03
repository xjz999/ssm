package entity;

public class Art {
	private String oid;
	private String title;
	private String useroid;
	private String addr;
	private String phototime;
	private String story;
	private String url;
	private int orderindex;
	private long createtime;
	private long edittime;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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
	
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password="
//				+ password + "]";
//	}
}
