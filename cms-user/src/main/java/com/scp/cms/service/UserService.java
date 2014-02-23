package com.scp.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.scp.basic.model.Pager;
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

	private void addUserRole(User user, Integer rid) {

		// 1.检查角色对象是否存在
		Role role = roleDao.load(rid);
		if (role == null)
			throw new CmsException("绑定用户的角色不存在");
		// 2.检查用户角色对象是否存在
		userDao.addUserRole(user, role);
	}

	private void addUserGroup(User user, Integer gid) {
		Group group = groupDao.load(gid);
		if (group == null)
			throw new CmsException("添加的组不存在");
		userDao.addUserGroup(user, group);
	}

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

		if (tu != null)
			throw new CmsException("添加的用户名已经存在，请重新添加..");

		userDao.add(user);

		for (Integer rid : rids) {
			addUserRole(user, rid);
		}

		for (Integer gid : gids) {
			addUserGroup(user, gid);
		}

	}

	public void delete(Integer id) {
		// TODO 删除用户文章不？
		userDao.deleteUserGroups(id);
		userDao.deleteUserRoles(id);
		userDao.delete(id);

	}

	public void update(User user, Integer[] rids, Integer[] gids) {
		List<Integer> erids = userDao.listUserRoleIds(user.getId());

		List<Integer> egids = userDao.listUserGroupIds(user.getId());

		for (Integer rid : rids) {
			if (!erids.contains(rid)) {
				addUserRole(user, rid);
			}
		}

		for (Integer gid : gids) {
			if (!egids.contains(gid)) {
				addUserGroup(user, gid);
			}
		}

		for (Integer erid : erids) {
			if (!ArrayUtils.contains(rids, erid)) {
				userDao.deleteUserRole(user.getId(), erid);
			}
		}

		for (Integer egid : egids) {
			if (!ArrayUtils.contains(gids, egid)) {
				userDao.deleteUserGroup(user.getId(), egid);
			}
		}
	}

	public void updateStatus(Integer id) {
		User u = userDao.load(id);
		if (u == null)
			throw new CmsException("用户不存在");
		if (u.getStatus() == 0)
			u.setStatus(1);
		else
			u.setStatus(0);
		userDao.update(u);
	}

	public User load(Integer id) {
		return userDao.load(id);
	}

	public Pager<User> findUser() {

		return userDao.findUser();
	}

	public List<Role> listUserRoles(Integer id) {

		return userDao.listUserRoles(id);
	}

	public List<Group> listUserGroups(Integer id) {

		return userDao.listUserGroups(id);
	}

}
