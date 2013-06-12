package sushil.luc.database;

public class LocalDatabaseConnection implements DatabaseConnection{
	
	private LocalDatabaseConnection ()
	{
		
	}
	
	@Override
	public DatabaseConnection getInstance() {
		// TODO Auto-generated method stub
		return new LocalDatabaseConnection();
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

}
