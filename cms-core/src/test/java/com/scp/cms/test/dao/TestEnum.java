package com.scp.cms.test.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.scp.basic.util.EnumUtils;
import com.scp.cms.model.RoleType;
import com.scp.test.util.EntitiesHelper;

public class TestEnum {

	@Test
	public void testEnumList() {
		List<String> actuals = Arrays.asList("ROLE_ADMIN","ROLE_PUBLISH","ROLE_AUDIT");
		List<String> expectes = EnumUtils.enum2Name(RoleType.class);
		EntitiesHelper.assertObjects(expectes, actuals);
	}
	
}
