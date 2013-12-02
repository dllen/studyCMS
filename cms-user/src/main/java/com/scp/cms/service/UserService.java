package com.scp.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.scp.cms.dao.IGroupDao;
import com.scp.cms.dao.IRoleDao;
import com.scp.cms.dao.IUserDao;
import com.scp.cms.model.CmsException;
import com.scp.cms.model.Group;
import com.scp.cms.model.Role;
import com.scp.cms.model.User;

@Service("userService")
public class UserService implements IUserService {

	private IUserDao userDao;

	private IGroupDao groupDao;

	private IRoleDao roleDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void add(User user, Integer[] rids, Integer[] gids) {
		
		User tu = userDao.loadByUsername(user.getUsername());
		
		if(tu!=null) throw new CmsException("添加的用户名已经存在，请重新添加..");
		
		for(Integer rid : rids){
			//1.检查角色对象是否存在
			Role role = roleDao.load(rid);
			if(role==null) throw new CmsException("绑定用户的角色不存在");
			//2.检查用户角色对象是否存在
		}
		

	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	public void update(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub

	}

	public void updateStatus(Integer id) {
		// TODO Auto-generated method stub

	}

	public User load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Role> listUserRoles(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> listUserGroups(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
