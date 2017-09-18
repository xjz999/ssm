package entity;

import java.util.List;

public class QueryParam {
	private String paramName;
	private Object paramValue;
	private int paramType;//0String 1Int 2boolean
	private boolean matchType;//匹配方式是%查询true，还是=查询 false
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Object getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public int getParamType() {
		return paramType;
	}
	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	public boolean getMatchType() {
		return matchType;
	}
	public void setMatchType(boolean matchType) {
		this.matchType = matchType;
	}
	public QueryParam(String name,Object  value,int pType,boolean mType){
		this.paramName = name;
		this.paramValue  = value;
		this.paramType = pType;
		this.matchType = mType;
	}
}
