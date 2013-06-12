package sushil.luc.database;

public class RemoteDatabaseConnection implements DatabaseConnection{

	private RemoteDatabaseConnection()
	{
		
	}
	
	@Override
	public DatabaseConnection getInstance() {
		// TODO Auto-generated method stub
		return new RemoteDatabaseConnection();
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

}
