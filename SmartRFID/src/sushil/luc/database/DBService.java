package sushil.luc.database;

import java.util.HashMap;
import java.util.List;

public interface DBService {

    /**
     * @param sql
     * @return List of selected values in (Key, value) format, where key = field or a row
     */
	public List<HashMap<String,String>> select(String sql);
	
	public boolean update(String sql);
	
	public boolean delete(String sql);

    /**
     *
     * @param sql
     * @return id or recently inserted data
     */
	public long insert(String sql);
}
