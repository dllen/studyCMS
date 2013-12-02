package com.scp.cms.dao;

import java.util.List;

import com.scp.basic.dao.IBaseDao;
import com.scp.basic.model.Pager;
import com.scp.cms.model.Group;

public interface IGroupDao extends IBaseDao<Group>{
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int gid);
}
