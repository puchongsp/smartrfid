package sushil.luc.ticket;


public enum TicketStatus {
	Open("Open",0),
    Closed("Closed",1),
    Checked("Checked",2),
    Staged("Staged",3);

	
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
