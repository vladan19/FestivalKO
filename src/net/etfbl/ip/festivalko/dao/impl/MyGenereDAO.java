package net.etfbl.ip.festivalko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import net.etfbl.ip.festivalko.dao.GenereDAO;
import net.etfbl.ip.festivalko.model.Genere;

public class MyGenereDAO extends BaseDAO<Genere, Integer> implements GenereDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MyGenereDAO.class.getName());
	
	private static final String ID = "id";
	private static final String NAME = "name";

	MyGenereDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Genere save(Genere genere) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO " + getTableName() +" (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, genere.getName());
			
			preparedStatement.execute();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if(resultSet.next()) {
				genere.setId(resultSet.getInt(1));
			}
			
			return genere;
		} catch (SQLException e) {
			 LOGGER.log(Level.WARNING, e.getMessage(), e);
	         return null;
		} finally {
			close(resultSet, preparedStatement, connection);
		}
	}

	@Override
	Genere create(ResultSet resultSet) throws SQLException {
		Genere genere = new Genere(resultSet.getInt(ID), resultSet.getString(NAME));
		return genere;
	}

	@Override
	String getTableName() {
		return "genere";
	}

	@Override
	public boolean existsByName(String name) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM " + getTableName() + " WHERE name = ?) AS result");
            preparedStatement.setObject(1, name);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next() && resultSet.getBoolean("result");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
	}

	@Override
	public boolean update(Genere genere) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE " + getTableName() + " SET name = ? WHERE id = ?");
            preparedStatement.setString(1, genere.getName());
            preparedStatement.setInt(2, genere.getId());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
	}

	@Override
	public Genere findByName(String name) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Genere g = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE name = ?");
            preparedStatement.setObject(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                g = create(resultSet);
            }

            return g;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
	}

}
