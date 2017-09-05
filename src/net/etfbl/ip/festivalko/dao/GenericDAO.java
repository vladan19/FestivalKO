package net.etfbl.ip.festivalko.dao;

import java.io.Serializable;

public interface GenericDAO<T, PK extends Serializable> extends Serializable {
	T save(T t);
	
	T findById(PK primaryKey);
	
	void delete(T t);
	
	boolean exists(PK primaryKey);
}
