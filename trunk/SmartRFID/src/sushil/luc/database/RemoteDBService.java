package sushil.luc.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class RemoteDBService implements DBService{

	private Connection connection;

	public RemoteDBService()
	{
        try {
            connection = RemoteDatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public List<HashMap<String, String>> select(String sql) {
        // TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    /**
     * Returns id of inserted row
     */
	public long insert(String sql) {
        int id = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            id = rs.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
