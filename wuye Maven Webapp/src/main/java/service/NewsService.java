package service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;

import entity.News;
import entity.NewsBackModel;
@Service
public interface NewsService {
	public News getOne(String oid);
	public boolean addNew(News news) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid);
	public NewsBackModel getList(Map mSearch) throws SQLException;
}
