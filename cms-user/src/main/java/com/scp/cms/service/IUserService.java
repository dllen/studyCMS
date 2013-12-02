package com.scp.cms.service;

import java.util.List;

import com.scp.cms.model.Group;
import com.scp.cms.model.Role;
import com.scp.cms.model.User;

public interface IUserService {

	/**
	 * 添加用户，判断用户名是否存在，存在抛出异常
	 * 
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void add(User user, Integer[] rids, Integer[] gids);

	/**
	 * 删除要级联删除角色和组 如果用户下面存在文章，不能删除
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 如果角色不存在rids--->删除 如果角色存在rids----->不操作 如果rids不存在用户中--->添加
	 * 
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void update(User user, Integer[] rids, Integer[] gids);

	/**
	 * 更新状态
	 * 
	 * @param id
	 */
	public void updateStatus(Integer id);

	public User load(Integer id);

	public List<User> findUser();

	public List<Role> listUserRoles(Integer id);

	public List<Group> listUserGroups(Integer id);

}
