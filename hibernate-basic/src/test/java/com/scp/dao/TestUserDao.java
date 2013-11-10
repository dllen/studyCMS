package com.scp.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scp.basic.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.scp.test.util.AbstractDbUnitTestCase;
import com.scp.test.util.EntitiesHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class TestUserDao extends AbstractDbUnitTestCase {

	@Inject
	private IUserDao userDao;

	@Before
	public void setUp() throws DataSetException, SQLException, IOException {
		this.backupAllTable();
	}

	@Test
	// @DatabaseSetup("t_user.xml")
	public void testLoad() throws DatabaseUnitException, SQLException {

		IDataSet dataSet = createDateSet("t_user");

		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dataSet);

		User u = userDao.load(1);

		EntitiesHelper.assertUser(u);

	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException,
			SQLException {
		this.resumeTable();
	}

}
