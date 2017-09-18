package entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CompomentSql{
	public String setParamStr(String sql,List<QueryParam> list){
		if (list.size() > 0){
	    	for (int i=0;i<list.size();i++){
	    		if (list.get(i).getMatchType()){
	    			sql += " and " + list.get(i).getParamName() + " like ?";
	    		}else{
	    			sql += " and " + list.get(i).getParamName() + "=?";
	    		}
	    		
	    	}
	    }
		return sql;
	}
	public String setUpdateStr(String sql,List<QueryParam> list){
		if (list.size() > 0){
	    	for (int i=0;i<list.size();i++){
	    		sql += " ," + list.get(i).getParamName() + "=?";
	    	}
	    }
		return sql;
	}
	public PreparedStatement setPstByList(PreparedStatement pst,List<QueryParam> paramList) throws SQLException{
		if (paramList.size() > 0){
	    	for (int i=0;i<paramList.size();i++){
	    		switch(paramList.get(i).getParamType()){
	    		case 0:
	    			if (paramList.get(i).getMatchType()){
	    				pst.setString(i+1, "%"+(String)paramList.get(i).getParamValue()+"%");
	    			}else{
	    				pst.setString(i+1, (String)paramList.get(i).getParamValue());
	    			}
	    			break;
	    		case 1:
	    			pst.setInt(i+1, (Integer)paramList.get(i).getParamValue());
	    			break;
	    		case 2:
	    			pst.setBoolean(i+1, (Boolean)paramList.get(i).getParamValue());
	    			break;
	    		}
	    	}
	    }
		return pst;
	}
}