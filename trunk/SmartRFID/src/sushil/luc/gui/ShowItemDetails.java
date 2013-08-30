package sushil.luc.gui;


import com.ugrokit.api.Ugi;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import sushil.luc.item.Item;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.utils.DateUtil;
import android.app.ActionBar;

public class ShowItemDetails extends UgroKitActivity{
	
	private TextView Item_ID;
	private TextView Item_Name;
	private TextView Item_RFID;
	private TextView Item_Location;
	private TextView Item_Status;
	private TextView Item_Quantity;
	private TextView Item_Deliverydate;
	private TextView Item_StopRent;
	private TextView Item_ReturnDate;
	private ActionBar actionbar;

	
	private int positionInTicketListview;
	private int positionInItemListview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		
		// init the action bar and assign the current status
		actionbar = getActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		actionbar.setSubtitle(currentStatus);
		
		Ticket current = TicketManagerAssembler.ticketlist.get(positionInTicketListview);
		
		Item i = current.getItems().get(positionInItemListview);
		
		Item_ID = (TextView) findViewById(R.id.item_id);
		Item_Name = (TextView) findViewById(R.id.item_name);
		Item_RFID = (TextView) findViewById(R.id.item_rfid);
		Item_Location = (TextView) findViewById(R.id.item_location);
		Item_Status = (TextView) findViewById(R.id.item_status);
		Item_Quantity= (TextView) findViewById(R.id.item_quantity);
		Item_Deliverydate= (TextView) findViewById(R.id.item_deliverydate);
		Item_StopRent= (TextView) findViewById(R.id.item_stopRent);
		Item_ReturnDate= (TextView) findViewById(R.id.item_returnDate);

		Item_ID.setText("Item ID : "+i.getItemID());
		Item_Name.setText("Itemname : "+i.getItemName());
		Item_RFID.setText("RFID : "+i.getRFID());
		Item_Location.setText("Location : "+i.getWarehouseLocation());
		Item_Status.setText("Status : "+i.getStatus().toString());
        if(i.getQuantity()!=null)
		    Item_Quantity.setText("Quantity : "+i.getQuantity().toString());
		if (i.getDeliveryDate()!=null)
			Item_Deliverydate.setText("Delivery Date : "+DateUtil.formatDate(i.getDeliveryDate().toString()));
		else
			Item_Deliverydate.setVisibility(View.INVISIBLE);
		if (i.getStopRentDate()!=null)
			Item_StopRent.setText("Stop Rent Date : "+DateUtil.formatDate(i.getStopRentDate().toString()));
		else
			Item_StopRent.setVisibility(View.INVISIBLE);
		if (i.getReturnDateDate()!=null)
			Item_ReturnDate.setText("Return Date : "+DateUtil.formatDate(i.getReturnDateDate().toString()));
		else
			Item_ReturnDate.setVisibility(View.INVISIBLE);
		
	}
	
}
	
	public void onResume()
	{
		// stop the inventory if not yet stopped and stop all modes. We don't need it here
		super.onResume();
		super.stopAllModes();
		super.calculateStatus();
	}
	
    @Override
		/**
		 * If the connection from the ugrokit changes, this methode gives feedback to the user
		 */
		public void connectionStateChanged(Ugi.ConnectionStates connectionState) {
			super.connectionStateChanged(connectionState);
			// update the Status
			super.calculateStatus();
			notifiySatusUpdate();
		}
		
		/**
		 * Update the status bar
		 */
		public void notifiySatusUpdate()
		{
			if (actionbar!=null)
				actionbar.setSubtitle(currentStatus);
		}

}
