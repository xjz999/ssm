package service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;

import entity.News;
import entity.NewsBackModel;
@Service
public interface NewsService {
	public News getOne(String oid) throws SQLException;
	public boolean addNew(News news) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public NewsBackModel getList(Map mSearch) throws SQLException;
}
