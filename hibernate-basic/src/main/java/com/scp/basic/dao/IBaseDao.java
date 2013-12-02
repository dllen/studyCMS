package com.scp.basic.dao;


public interface IBaseDao<T> {

	public T add(T t);

	public void update(T t);

	public void delete(int id);

	public T load(int id);

	

}
