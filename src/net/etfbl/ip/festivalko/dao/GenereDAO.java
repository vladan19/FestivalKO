package net.etfbl.ip.festivalko.dao;

import net.etfbl.ip.festivalko.model.Genere;

public interface GenereDAO extends GenericDAO<Genere, Integer> {

	@Override
	Genere save(Genere t);

	@Override
	Genere findById(Integer primaryKey);

	@Override
	void delete(Genere t);

	@Override
	boolean exists(Integer primaryKey);
	
	boolean existsByName(String name);
	
	boolean update(Genere genere);
	
	Genere findByName(String name);

}
