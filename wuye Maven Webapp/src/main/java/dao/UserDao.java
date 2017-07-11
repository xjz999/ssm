package dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.User;

@Component
public class UserDao extends SqlSessionDaoSupport {
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	public User selectById(int id){
		return this.getSqlSession().selectOne("UserMapper.selectById", id);
	}
}
