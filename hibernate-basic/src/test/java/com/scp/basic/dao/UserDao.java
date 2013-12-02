package com.scp.basic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.scp.basic.dao.BaseDao;
import com.scp.basic.model.Pager;
import com.scp.basic.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<User> listUser(String string, Object[] objects) {
		return this.list(string, objects);
	}

	@Override
	public List<User> listUser(String string, Object[] objects,
			Map<String, Object> alias) {
		return this.list(string, objects, alias);
	}

	@Override
	public Pager<User> findUser(String string, Object[] objects) {

		return this.find(string, objects);
	}

	@Override
	public Pager<User> findUser(String string, Object[] objects,
			Map<String, Object> alias) {
		return this.find(string, objects, alias);
	}

	@Override
	public List<User> listUserBySql(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return this.listBySql(string, objects, class1, b);
	}

	@Override
	public List<User> listUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return this.listBySql(string, objects, alias, class1, b);
	}

	@Override
	public Pager<User> findUserBySql(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return this.findBySql(string, objects, class1, b);
	}

	@Override
	public Pager<User> findUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return this.findBySql(string, objects, alias, class1, b);
	}

}
