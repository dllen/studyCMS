package com.scp.basic.dao;

import org.springframework.stereotype.Repository;

import com.scp.basic.dao.BaseDao;
import com.scp.basic.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

}
