package service;
import org.springframework.stereotype.Service;

import entity.Art;
@Service
public interface ArtService {
	public Art selectById(String oid);
}
