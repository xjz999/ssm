package entity;

public class User {
	private String oid;
	private String truename;
	private String loginname;
	private String password;
	private int sex = -1;
	private String email;
	private String mobile;
	private int memlevel = -1;
	private String portrait;
	private long createtime;
	private long edittime;
	private String qqtoken;
	private String wechattoken;
	private String weibotoken;
	private int regtype = -1;
	private String regphoto;
	private String backgroundimg;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getMemlevel() {
		return memlevel;
	}
	public void setMemlevel(int memlevel) {
		this.memlevel = memlevel;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
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
	public String getQqtoken() {
		return qqtoken;
	}
	public void setQqtoken(String qqtoken) {
		this.qqtoken = qqtoken;
	}
	public String getWechattoken() {
		return wechattoken;
	}
	public void setWechattoken(String wechattoken) {
		this.wechattoken = wechattoken;
	}
	public String getWeibotoken() {
		return weibotoken;
	}
	public void setWeibotoken(String weibotoken) {
		this.weibotoken = weibotoken;
	}
	public int getRegtype() {
		return regtype;
	}
	public void setRegtype(int regtype) {
		this.regtype = regtype;
	}
	public String getRegphoto() {
		return regphoto;
	}
	public void setRegphoto(String regphoto) {
		this.regphoto = regphoto;
	}
	public String getBackgroundimg() {
		return backgroundimg;
	}
	public void setBackgroundimg(String backgroundimg) {
		this.backgroundimg = backgroundimg;
	}
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password="
//				+ password + "]";
//	}
	
	
}
