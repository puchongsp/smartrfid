package sushil.luc.ticket;


public enum TicketStatus {
	Open("Open",0),
    Closed("Closed",1),
    InProgress("InProgress",2);

	
	private String stringvalue;
	private int intValue;
	
	private TicketStatus (String s, int value)
	{
		stringvalue =s;
		intValue = value;
	}
	
	@Override
	public String toString()
	{
		return stringvalue;
	}
	

	public int getintValue()
	{
		return intValue;
	}
	
}
