package com.scp.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.scp.basic.dao.BaseDao;
import com.scp.basic.model.Pager;
import com.scp.cms.model.Group;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {
	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	@Override
	public Pager<Group> findGroup() {
		return this.find("from Group");
	}

	@Override
	public void deleteGroupUsers(int gid) {
		this.updateByHql("delete UserGroup ug where ug.group.id=?", gid);
	}
}
