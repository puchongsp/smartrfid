package sushil.luc.database;

public interface DatabaseConnection {

	public DatabaseConnection getInstance();
	public void disconnect();
}
