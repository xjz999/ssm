package entity;

import java.util.List;

public class NewsBackModel {
	
	int pageSize;
	int pageIndex;
	int recCount;
	List<News> list;
	
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
	public List<News> getList() {
		return list;
	}
	public void setList(List<News> list) {
		this.list = list;
	}
}
