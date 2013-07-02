package sushil.luc.item;
//public class ItemStatus {
	/*private enum ItemStatusType
	{*/
//	public static String AVAILABLE = "Availabe";
//	public static String REPAIR = "Repair";
//	public static String TRANSPORT = "Transport";
//	public static String RENTTOCLIENT = "RentToCustomer";
//	public static String COLLECTED = "Collected";
	
	/*}
	
	*/

public enum ItemStatus {
		Available("Availabe",0),
		Repair("Repair",1),
		Transport("Transport",2),
		RentToCustomer("RentToCustomer",3),
		Collected ("Collected",4);
		
		private String stringvalue;
		private int intValue;
		
		private ItemStatus (String s, int value)
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
