package com.scp.baisc.dao;

import java.util.List;
import java.util.Map;

import com.scp.basic.model.Pager;

public interface IBaseDao<T> {

	public T add(T t);

	public void update(T t);

	public void delete(int id);

	public T load(int id);

	public List<T> list(String hql, Object[] args);

	public List<T> list(String hql, Object arg);

	public List<T> list(String hql);

	public List<T> list(String hql, Object[] args, Map<String, Object> alias);

	public List<T> listByAlias(String hql, Map<String, Object> alias);

	public Pager<T> find(String hql, Object[] args);

	public Pager<T> find(String hql, Object arg);

	public Pager<T> find(String hql);

	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias);

	public Pager<T> findByAlias(String hql, Map<String, Object> alias);

	public Object queryObject(String hql, Object[] args);

	public Object queryObject(String hql, Object arg);

	public Object queryObject(String hql);

	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias);

	public Object queryObjectByAlias(String hql, Map<String, Object> alias);

	public void updateByHql(String hql, Object[] args);

	public void updateByHql(String hql, Object arg);

	public void updateByHql(String hql);

	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz,
			boolean isEntity);

	public List<Object> listBySql(String sql, Object arg, Class<Object> clz,
			boolean isEntity);

	public List<Object> listBySql(String sql, Class<Object> clz, boolean isEntity);

	public List<Object> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity);

	public List<Object> listBySql(String sql, Object arg, Map<String, Object> alias,
			Class<Object> clz, boolean isEntity);

	public List<Object> listByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean isEntity);

	public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz,
			boolean isEntity);

	public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz,
			boolean isEntity);

	public Pager<Object> findBySql(String sql, Class<Object> clz, boolean isEntity);

	public Pager<Object> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity);

	public Pager<Object> findBySql(String sql, Object arg,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity);

	public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean isEntity);

}
