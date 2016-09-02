package by.topolev.contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.contacts.entity.Address;

public class AddressDaoJdbc extends AbstractDaoJDBC implements AddressDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(AddressDaoJdbc.class);
	
	Address createAddressFromResultSet(ResultSet resultSet) throws SQLException{
		Address address = new Address();
		address.setCountry(resultSet.getString("country"));
		address.setCity(resultSet.getString("city"));
		address.setStreet(resultSet.getString("street"));
		address.setBuild(resultSet.getInt("build"));
		address.setFlat(resultSet.getInt("flat"));
		address.setIndex(resultSet.getInt("index"));
		return address;
	}
	
	@Override
	public Address getAddressByIdContact(int id) {
		Connection connection = null;
		Address address = null;
		try {
			connection = getConnection();
			PreparedStatement statmentAddress = connection.prepareStatement("SELECT * FROM address WHERE id_contact = ?");
			statmentAddress.setInt(1, id);
			ResultSet result = statmentAddress.executeQuery();
			result.next();
			return createAddressFromResultSet(result);
		} catch (SQLException e) {
			LOG.info("Problem with database connection");
			LOG.debug("Problem with database connection");
		}
		return address;
	}
	
	public static void main(String[] arg){
		AddressDao addresDao = new AddressDaoJdbc();
		System.out.println(addresDao.getAddressByIdContact(1));
	}

}
