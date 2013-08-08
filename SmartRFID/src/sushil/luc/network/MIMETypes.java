package sushil.luc.network;

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
