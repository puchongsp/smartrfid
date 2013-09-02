package sushil.luc.gui;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ugrokit.api.Ugi;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.utils.DateUtil;

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
	private Item currentItem;
	private Ticket currentTicket;
	private Button btnUnCheck;

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
		{
			finish();
		}
		else
		{
			
			// init the action bar and assign the current connection status
			actionbar = getActionBar();
			actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
			actionbar.setSubtitle(currentStatus);
			
			// get the previously selected ticket 
			currentTicket = TicketManagerAssembler.ticketlist.get(positionInTicketListview);
			
			currentItem =currentTicket.getItems().get(positionInItemListview);
			
			Item_ID = (TextView) findViewById(R.id.item_id);
			Item_Name = (TextView) findViewById(R.id.item_name);
			Item_RFID = (TextView) findViewById(R.id.item_rfid);
			Item_Location = (TextView) findViewById(R.id.item_location);
			Item_Status = (TextView) findViewById(R.id.item_status);
			Item_Quantity= (TextView) findViewById(R.id.item_quantity);
			Item_Deliverydate= (TextView) findViewById(R.id.item_deliverydate);
			Item_StopRent= (TextView) findViewById(R.id.item_stopRent);
			Item_ReturnDate= (TextView) findViewById(R.id.item_returnDate);
			
			btnUnCheck = (Button) findViewById(R.id.btn_UnCheck);
			btnUnCheck.setText("Uncheck Item");
			btnUnCheck.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Undo the status change Set it back to Available
						currentItem.setStatus(ItemStatus.Available);
                        initButton();
                        Item_Status.setText("Status : "+currentItem.getStatus().toString());
				}
			});
			
			Item_ID.setText("Item ID : "+currentItem.getItemID());		
			Item_Name.setText("Itemname : "+currentItem.getItemName());
			if (currentItem.getRFID()!=null)
				Item_RFID.setText("RFID : "+currentItem.getRFID());
			else
				Item_RFID.setText("RFID : not yet assigned");
			if(currentItem.getWarehouseLocation()!=null)
				Item_Location.setText("Location : "+currentItem.getWarehouseLocation());
			else
				Item_Location.setText("Location : ");
			if(currentItem.getStatus()!=null)
				Item_Status.setText("Status : "+currentItem.getStatus().toString());
			else
				Item_Status.setText("Status : ");
	        if(currentItem.getQuantity()!=null)
			    Item_Quantity.setText("Quantity : "+currentItem.getQuantity().toString());
	        else
	        	 Item_Quantity.setText("Quantity : ");
			if (currentItem.getDeliveryDate()!=null)
				Item_Deliverydate.setText("Delivery Date : "+DateUtil.formatDate(currentItem.getDeliveryDate().toString()));
			else
				Item_Deliverydate.setText("Delivery Date : ");
			if (currentItem.getStopRentDate()!=null)
				Item_StopRent.setText("Stop Rent Date : "+DateUtil.formatDate(currentItem.getStopRentDate().toString()));
			else
				Item_StopRent.setText("Stop Rent Date : ");
			if (currentItem.getReturnDateDate()!=null)
				Item_ReturnDate.setText("Return Date : "+DateUtil.formatDate(currentItem.getReturnDateDate().toString()));
			else
				Item_ReturnDate.setText("Return Date : ");
			
		}
	}

	/**
	 * According to the ItemStatus the button is visible or not
	 */
    private void initButton()
    {
        if (currentItem.getStatus().equals(ItemStatus.Checked))
        {
            btnUnCheck.setVisibility(View.VISIBLE);
        }
        else
        {
            btnUnCheck.setVisibility(View.INVISIBLE);
        }
    }

	public void onResume()
	{
		// stop the inventory if not yet stopped and stop all modes. We don't need it here
		super.onResume();
		super.stopAllModes();
		super.calculateStatus();

        initButton();
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
