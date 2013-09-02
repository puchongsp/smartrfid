package sushil.luc.network;

/**
 * MIME types that web service API is going to return
 * either JSON or XML
 * in our case, we are using JSON
 */
public enum MIMETypes {
	
	APPLICATION_JSON("application/json");
	//APPLICATION_XML("application/xml");
	
	private final String name;
	
	private MIMETypes(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
