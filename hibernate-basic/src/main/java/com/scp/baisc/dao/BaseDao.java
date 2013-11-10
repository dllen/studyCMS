package com.scp.baisc.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.scp.basic.model.Pager;
import com.scp.basic.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Class<T> clz;

	public Class<T> getClz() {
		if (clz == null) {
			clz = ((Class<T>) (((ParameterizedType) (this.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (!"desc".equals(order))
				hql += " asc";
			else
				hql += " desc";
		}

		return hql;
	}

	@SuppressWarnings("rawtypes")
	private void setAlisParameter(Query query, Map<String, Object> alias) {
		if (alias != null) {
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object val = alias.get(key);

				if (val instanceof Collection) {
					query.setParameterList(key, (Collection) val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}

	private void setParameter(Query query, Object[] args) {

		if (args != null && args.length > 0) {
			int index = 0;
			for (Object object : args) {
				query.setParameter(index++, object);
			}
		}
	}

	private void setPagers(Query query,
			@SuppressWarnings("rawtypes") Pager pager) {

		Integer pageSize = SystemContext.getPageSize();

		Integer pageOffset = SystemContext.getPageOffset();

		if (pageOffset == null || pageOffset < 0)
			pageOffset = 0;

		if (pageSize == null || pageSize <= 0)
			pageSize = 15;

		pager.setOffset(pageOffset);

		pager.setSize(pageSize);

		query.setFirstResult(pageOffset).setMaxResults(pageSize);

	}

	private String getCountHql(String hql, boolean isHql) {

		String e = hql.substring(hql.indexOf("form"));

		String b = "select count(*) ";

		String c = e + b;

		if (isHql)
			c.replaceAll("fetch", "");

		return c;

	}

	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	@Override
	public T load(int id) {
		return (T) getSession().load(clz, id);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] { arg });
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {

		hql = initSort(hql);

		Query query = getSession().createQuery(hql);

		this.setAlisParameter(query, alias);

		this.setParameter(query, args);

		return query.list();
	}

	@Override
	public List<T> listByAlias(String hql, Map<String, Object> alias) {

		return this.list(hql, null, alias);
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	@Override
	public Pager<T> find(String hql, Object arg) {

		return this.find(hql, new Object[] { arg });
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	@Override
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql, true);
		cq = initSort(cq);
		Query cQuery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		setAlisParameter(query, alias);
		setAlisParameter(cQuery, alias);
		setParameter(query, args);
		setParameter(cQuery, args);

		Pager<T> pager = new Pager<T>();

		setPagers(query, pager);

		pager.setDatas(query.list());

		Long total = (Long) cQuery.uniqueResult();

		pager.setTotal(total);

		return pager;
	}

	@Override
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {

		return this.find(hql, null, alias);
	}

	@Override
	public Object queryObject(String hql, Object[] args) {

		return this.queryObject(hql, args, null);
	}

	@Override
	public Object queryObject(String hql, Object arg) {

		return this.queryObject(hql, new Object[] { arg });
	}

	@Override
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAlisParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	@Override
	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {

		return this.queryObject(hql, null, alias);
	}

	@Override
	public Object queryObject(String hql) {

		return this.queryObject(hql, null);
	}

	@Override
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[] { arg });

	}

	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);

	}

	@Override
	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz,
			boolean isEntity) {
		return this.listBySql(sql, args, null, clz, isEntity);
	}

	@Override
	public List<Object> listBySql(String sql, Object arg, Class<Object> clz,
			boolean isEntity) {
		return this.listBySql(sql, new Object[] { arg }, clz, isEntity);
	}

	@Override
	public List<Object> listBySql(String sql, Class<Object> clz,
			boolean isEntity) {

		return this.listBySql(sql, null, clz, isEntity);
	}

	@Override
	public List<Object> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity) {
		sql = initSort(sql);

		SQLQuery sqlQuery = getSession().createSQLQuery(sql);

		setAlisParameter(sqlQuery, alias);

		setParameter(sqlQuery, args);

		if (isEntity) {
			sqlQuery.addEntity(clz);
		} else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}

		return sqlQuery.list();
	}

	@Override
	public List<Object> listBySql(String sql, Object arg,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity) {

		return this.listBySql(sql, new Object[] { arg }, alias, clz, isEntity);
	}

	@Override
	public List<Object> listByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean isEntity) {
		return this.listBySql(sql, null, alias, clz, isEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Object[] args,
			Class<Object> clz, boolean isEntity) {

		return this.findBySql(sql, args, null, clz, isEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz,
			boolean isEntity) {

		return this.findBySql(sql, new Object[] { arg }, clz, isEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Class<Object> clz,
			boolean isEntity) {

		return this.findBySql(sql, null, clz, isEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity) {
		String cq = getCountHql(sql, false);
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		Query cquery = getSession().createSQLQuery(cq);
		setAlisParameter(sq, alias);
		setAlisParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager pager = new Pager();
		setPagers(sq, pager);
		if (isEntity) {
			sq.addEntity(clz);
		} else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List datas = sq.list();
		pager.setDatas(datas);
		long total = (Long) cquery.uniqueResult();
		pager.setTotal(total);
		return pager;
	}

	@Override
	public Pager<Object> findBySql(String sql, Object arg,
			Map<String, Object> alias, Class<Object> clz, boolean isEntity) {

		return this.findBySql(sql, new Object[] { arg }, alias, clz, isEntity);
	}

	@Override
	public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean isEntity) {

		return this.findBySql(sql, null, alias, clz, isEntity);
	}

}
