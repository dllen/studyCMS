package com.scp.dao;

import org.scp.basic.model.User;
import org.springframework.stereotype.Repository;

import com.scp.baisc.dao.BaseDao;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

}
