package service;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;

import entity.Art;
import entity.ArtBackModel;
@Service
public interface ArtService {
	public Art selectById(String oid);
	public Art getOne(String oid);
	public boolean addNew(Art art) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid);
	public ArtBackModel getList(Map mSearch) throws SQLException;
}
