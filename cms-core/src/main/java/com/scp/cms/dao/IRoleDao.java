package com.scp.cms.dao;

import java.util.List;

import com.scp.basic.dao.IBaseDao;
import com.scp.cms.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
