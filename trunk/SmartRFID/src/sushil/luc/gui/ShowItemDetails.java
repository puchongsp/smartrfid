package sushil.luc.gui;

import sushil.luc.item.Item;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ShowItemDetails extends Activity{
	
	private TextView Item_ID;
	private TextView Item_Name;
	private TextView Item_RFID;
	private TextView Item_Location;
	private TextView Item_Status;
	
	private int positionInTicketListview;
	private int positionInItemListview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.show_item_details);
	
	Bundle extras = getIntent().getExtras();
	if (extras != null) {
		positionInTicketListview = extras.getInt("positionInTicketList",-1);
		positionInItemListview = extras.getInt("positionInItemListview",-1);
	 }
	
	if (positionInTicketListview == -1 || positionInItemListview ==-1)
		finish();
	else
	{
		
		Ticket current = TicketManagerAssembler.ticketlist.get(positionInTicketListview);
		
		Item i = current.getItems().get(positionInItemListview);
		
		Item_ID = (TextView) findViewById(R.id.item_id);
		Item_Name = (TextView) findViewById(R.id.item_name);
		Item_RFID = (TextView) findViewById(R.id.item_rfid);
		Item_Location = (TextView) findViewById(R.id.item_location);
		Item_Status = (TextView) findViewById(R.id.item_status);
		
		Item_ID.setText("Item ID : "+i.getItemID());
		Item_Name.setText("Itemname : "+i.getItemName());
		Item_RFID.setText("RFID : "+i.getRFID());
		Item_Location.setText("Location : "+i.getWarehouseLocation());
		Item_Status.setText("Status : "+i.getStatus().toString());
		
	}
}

}
