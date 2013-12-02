package com.scp.basic.dao;

import java.util.List;
import java.util.Map;

import com.scp.basic.dao.IBaseDao;
import com.scp.basic.model.Pager;
import com.scp.basic.model.User;

public interface IUserDao extends IBaseDao<User> {

	public List<User> listUser(String string, Object[] objects);

	public	List<User> listUser(String string, Object[] objects, Map<String, Object> alias);

	public Pager<User> findUser(String string, Object[] objects);

	public	Pager<User> findUser(String string, Object[] objects, Map<String, Object> alias);

	public	List<User> listUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	public	List<User> listUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	public	Pager<User> findUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	public	Pager<User> findUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

}
