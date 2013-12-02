package com.scp.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
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
import org.springframework.stereotype.Repository;

import com.scp.basic.model.Pager;
import com.scp.basic.model.SystemContext;

@SuppressWarnings("unchecked")
@Repository("baseDao")
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

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
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

		String e = hql.substring(hql.indexOf("from"));

		String b = "select count(*) ";

		String c = b + e;

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
		return (T) getSession().load(getClz(), id);
	}

	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] { arg });
	}

	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {

		hql = initSort(hql);

		Query query = getSession().createQuery(hql);

		this.setAlisParameter(query, alias);

		this.setParameter(query, args);

		return query.list();
	}

	public List<T> listByAlias(String hql, Map<String, Object> alias) {

		return this.list(hql, null, alias);
	}

	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	public Pager<T> find(String hql, Object arg) {

		return this.find(hql, new Object[] { arg });
	}

	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql, true);
		// cq = initSort(cq);
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

	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {

		return this.find(hql, null, alias);
	}

	public Object queryObject(String hql, Object[] args) {

		return this.queryObject(hql, args, null);
	}

	public Object queryObject(String hql, Object arg) {

		return this.queryObject(hql, new Object[] { arg });
	}

	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAlisParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {

		return this.queryObject(hql, null, alias);
	}

	public Object queryObject(String hql) {

		return this.queryObject(hql, null);
	}

	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[] { arg });

	}

	public void updateByHql(String hql) {
		this.updateByHql(hql, null);

	}


	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Class<?> clz, boolean isEntity) {
		return this.listBySql(sql, args, null, clz, isEntity);
	}

	
	public <N extends Object> List<N> listBySql(String sql, Object arg,
			Class<?> clz, boolean isEntity) {
		return this.listBySql(sql, new Object[] { arg }, clz, isEntity);
	}

	
	public <N extends Object> List<N> listBySql(String sql, Class<?> clz,
			boolean isEntity) {

		return this.listBySql(sql, null, clz, isEntity);
	}

	
	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {
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

	
	public <N extends Object> List<N> listBySql(String sql, Object arg,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {

		return this.listBySql(sql, new Object[] { arg }, alias, clz, isEntity);
	}

	
	public <N extends Object> List<N> listByAliasSql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {
		return this.listBySql(sql, null, alias, clz, isEntity);
	}

	
	public <N extends Object> Pager<N> findBySql(String sql, Object[] args,
			Class<?> clz, boolean isEntity) {

		return this.findBySql(sql, args, null, clz, isEntity);
	}

	
	public <N extends Object> Pager<N> findBySql(String sql, Object arg,
			Class<?> clz, boolean isEntity) {

		return this.findBySql(sql, new Object[] { arg }, clz, isEntity);
	}

	
	public <N extends Object> Pager<N> findBySql(String sql, Class<?> clz,
			boolean isEntity) {

		return this.findBySql(sql, null, clz, isEntity);
	}

	
	public <N extends Object> Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {
		sql = initSort(sql);
		String cq = getCountHql(sql, false);
		SQLQuery sq = getSession().createSQLQuery(sql);
		Query cquery = getSession().createSQLQuery(cq);
		setAlisParameter(sq, alias);
		setAlisParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<N> pager = new Pager<N>();
		setPagers(sq, pager);
		if (isEntity) {
			sq.addEntity(clz);
		} else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas = sq.list();
		pager.setDatas(datas);
		long total = ((BigInteger) cquery.uniqueResult()).longValue();
		pager.setTotal(total);
		return pager;
	}

	
	public <N extends Object> Pager<N> findBySql(String sql, Object arg,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {

		return this.findBySql(sql, new Object[] { arg }, alias, clz, isEntity);
	}

	
	public <N extends Object> Pager<N> findByAliasSql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean isEntity) {

		return this.findBySql(sql, null, alias, clz, isEntity);
	}

}
