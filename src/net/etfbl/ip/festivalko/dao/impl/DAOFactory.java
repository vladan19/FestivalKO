package net.etfbl.ip.festivalko.dao.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.etfbl.ip.festivalko.dao.GenereDAO;
import net.etfbl.ip.festivalko.dao.UserDAO;

@ManagedBean
@ApplicationScoped
public class DAOFactory {
	private static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
	
	private DataSource dataSource;
	
	private static DAOFactory daoFactory;
	
	private DAOFactory() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/festivalko");
		}catch(NamingException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	public static DAOFactory getInstance() {
		if(daoFactory == null) {
			daoFactory = new DAOFactory();
		}
		return daoFactory;
	}
	
	@Produces
	@Alternative
	public UserDAO getUserDAO() {
		return new MyUserDAO(dataSource);
	}
	
	@Produces
	@Alternative
	public GenereDAO getGenereDAO() {
		return new MyGenereDAO(dataSource);
	}
}
