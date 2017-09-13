package net.etfbl.ip.festivalko.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import net.etfbl.ip.festivalko.dao.GenericDAO;
import net.etfbl.ip.festivalko.model.Model;

abstract class BaseDAO<T extends Model<PK>, PK extends Serializable> implements GenericDAO<T, PK> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());
	
	transient DataSource dataSource;
	
	BaseDAO(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, e.getMessage(), e);
                }
            }
        }
    }
	
	abstract T create(ResultSet resultSet) throws SQLException;

    abstract String getTableName();

    @Override
    public T findById(PK primaryKey) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T t = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE id = ?");
            preparedStatement.setObject(1, primaryKey);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                t = create(resultSet);
            }

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
    }


    @Override
    public void delete(T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM" + getTableName() + " WHERE id = ?");
            preparedStatement.setObject(1, t.getPrimaryKey());
            preparedStatement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        } finally {
            close(preparedStatement, connection);
        }
    }

    @Override
    public boolean exists(PK primaryKey) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM " + getTableName() + " WHERE id = ?) AS result");
            preparedStatement.setObject(1, primaryKey);
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
	public List<T> findAll() {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " + getTableName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(create(resultSet));
            }

            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
	}

	@Override
	public void deleteAll(List<T> ts) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM " + getTableName() + " WHERE id IN (" + getIds(ts) + ")");
            setIds(preparedStatement, ts, 1);
            preparedStatement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getSQLState(), e);
        } finally {
            close(preparedStatement, connection);
        }
	}

	@Override
	public String getIds(List<T> ts) {
		String result = "";
		for(int i=0; i<ts.size()-1; ++i) {
			result += "?, ";
		}
		result += "?";
		return result;
	}

	@Override
	public void setIds(PreparedStatement preparedStatement, List<T> ts, int startFrom) throws SQLException{
		for(T t: ts) {
			preparedStatement.setObject(startFrom++, t.getPrimaryKey());
		}
	}
}
