package net.etfbl.ip.festivalko.dao;

import net.etfbl.ip.festivalko.model.User;

public interface UserDAO extends GenericDAO<User, Integer> {

	@Override
	User save(User t);

	@Override
	User findById(Integer primaryKey);

	@Override
	void delete(User t);

	@Override
	boolean exists(Integer primaryKey);
}
