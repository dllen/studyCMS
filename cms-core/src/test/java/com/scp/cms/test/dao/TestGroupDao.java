package com.scp.cms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.scp.basic.model.Pager;
import com.scp.basic.model.SystemContext;
import com.scp.cms.dao.IGroupDao;
import com.scp.cms.dao.IUserDao;
import com.scp.cms.model.Group;
import com.scp.cms.model.User;
import com.scp.test.util.AbstractDbUnitTestCase;
import com.scp.test.util.EntitiesHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestGroupDao extends AbstractDbUnitTestCase{
	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private IUserDao userDao;
	@Inject
	private IGroupDao groupDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	@Test
	public void testListGroup() {
		List<Group> actuals = Arrays.asList(new Group(1,"财务处"),new Group(2,"计科系"),new Group(3,"宣传部"));
		List<Group> expects = groupDao.listGroup();
		EntitiesHelper.assertGroups(expects, actuals);
	}

	@Test
	public void testFindGroup() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		List<Group> actuals = Arrays.asList(new Group(1,"财务处"),new Group(2,"计科系"),new Group(3,"宣传部"));
		Pager<Group> pages = groupDao.findGroup();
		assertNotNull(pages);
		assertEquals(pages.getTotal(), 3);
		EntitiesHelper.assertGroups(pages.getDatas(), actuals);
	}

	@Test
	public void testDeleteGroupUsers() {
		int gid = 1;
		groupDao.deleteGroupUsers(gid);
		List<User> users = userDao.listGroupUsers(gid);
		assertEquals(users.size(), 0);
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
}
