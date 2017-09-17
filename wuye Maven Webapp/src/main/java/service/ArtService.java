package service;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Service;

import entity.Art;
import entity.ArtBackModel;
@Service
public interface ArtService {
	public Art getOne(String oid) throws SQLException;
	public boolean addNew(Art art) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public ArtBackModel getList(Map mSearch) throws SQLException;
}
