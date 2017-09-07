package entity;

import java.util.List;

public class ArtBackModel {
	
	int pageSize;
	int pageIndex;
	int recCount;
	List<Art> list;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getRecCount() {
		return recCount;
	}
	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}
	public List<Art> getList() {
		return list;
	}
	public void setList(List<Art> list) {
		this.list = list;
	}
}
