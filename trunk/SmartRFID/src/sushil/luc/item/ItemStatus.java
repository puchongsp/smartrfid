package sushil.luc.item;


public enum ItemStatus {
	Available("Available",0),
	Staged("IsStaged",1),
	Checked("IsChecked",2),
	Returned ("Returned", 3);
		
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
