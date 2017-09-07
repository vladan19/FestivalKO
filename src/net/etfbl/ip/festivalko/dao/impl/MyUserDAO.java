package net.etfbl.ip.festivalko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import net.etfbl.ip.festivalko.dao.UserDAO;
import net.etfbl.ip.festivalko.model.User;
import net.etfbl.ip.festivalko.model.UserType;
import net.etfbl.ip.festivalko.utility.PasswordUtility;

public class MyUserDAO extends BaseDAO<User, Integer> implements UserDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MyUserDAO.class.getName());
	
	private static final String ID = "id";
	private static final String FIRST_NAME = "first_name";
	private static final String LAST_NAME = "last_name";
	private static final String JMBG = "jmbg";
	private static final String EMAIL = "email";
	private static final String USER_TYPE = "user_type";
	private static final String PICTURE_URI = "picture_uri";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password_hash";
	private static final String REGISTRATION_DATE = "registration_date";
	private static final String ACTIVATED = "activated";
	private static final String ACTIVATION_DATE = "activation_date";

    MyUserDAO(DataSource dataSource) {
        super(dataSource);
    }

	@Override
	public User save(User user) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO " + getTableName() +" (first_name, last_name, jmbg, email, user_type, picture_uri, username, password_hash, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getJmbg());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getType().getValue());
            preparedStatement.setString(6, user.getPictureUri());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setString(8, PasswordUtility.generate(user.getPassword()));
            user.setRegistationDate(LocalDateTime.now());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(user.getRegistationDate()));

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            return user;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
	}

	@Override
	User create(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt(ID));
		user.setFirstName(resultSet.getString(FIRST_NAME));
		user.setLastName(resultSet.getString(LAST_NAME));
		user.setJmbg(resultSet.getString(JMBG));
		user.setEmail(resultSet.getString(EMAIL));
		user.setType(UserType.valueOf(resultSet.getInt(USER_TYPE)));
		user.setPictureUri(resultSet.getString(PICTURE_URI));
		user.setUsername(resultSet.getString(USERNAME));
		user.setPassword(resultSet.getString(PASSWORD));
		user.setRegistationDate(resultSet.getTimestamp(REGISTRATION_DATE) == null ? null : resultSet.getTimestamp(REGISTRATION_DATE).toLocalDateTime());
		user.setActivated(resultSet.getBoolean(ACTIVATED));
		user.setActivationDate(resultSet.getTimestamp(ACTIVATION_DATE) == null ? null : resultSet.getTimestamp(ACTIVATION_DATE).toLocalDateTime());
		return user;
	}

	@Override
	String getTableName() {
		return "user";
	}
}
