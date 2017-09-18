package service;

import java.sql.SQLException;
import java.util.Map;

import entity.Competition;
import entity.CompetitionBackModel;

public interface CompetitionService {
	public Competition getOne(String oid) throws SQLException;
	public boolean addNew(Competition user) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public CompetitionBackModel getList(Map mSearch) throws SQLException;
}
