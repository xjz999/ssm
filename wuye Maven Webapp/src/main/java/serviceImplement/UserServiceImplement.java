package serviceImplement;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.User;
import service.UserService;

@Component
public class UserServiceImplement implements UserService {

	@Autowired
	private UserDao userDao;
	
	public User selectById(int id) {
		return this.userDao.selectById(id);
	}

}
