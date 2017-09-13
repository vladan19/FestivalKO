package net.etfbl.ip.festivalko.dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> extends Serializable {
	T save(T t);
	
	T findById(PK primaryKey);
	
	void delete(T t);
	
	boolean exists(PK primaryKey);
	
	List<T> findAll();
	
	void deleteAll(List<T> ts);
	
	String getIds(List<T> ts);
	
	void setIds(PreparedStatement preparedStatement, List<T> ts, int startFrom) throws SQLException;
}
