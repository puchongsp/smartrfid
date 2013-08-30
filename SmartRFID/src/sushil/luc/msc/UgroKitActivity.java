package sushil.luc.msc;


import sushil.luc.ugrokit.MyHandler;
import com.ugrokit.api.Ugi;
import com.ugrokit.api.UgiInventoryDelegate;
import com.ugrokit.api.UgiTag;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Activity which implement the Ugrokit functionality. All Activites which want to use the Ugrokit should extend this class
 * @author Luc
 *
 */
public class UgroKitActivity extends Activity implements
		Ugi.ConnectionStateListener, 
		UgiInventoryDelegate,
		UgiInventoryDelegate.InventoryTagSubsequentFindsListener 
		{

	private Ugi.InventoryTypes inventoryType = Ugi.InventoryTypes.LOCATE_DISTANCE;
	
	private String LogTag = "UgroKitActivity";
	protected String currentStatus;
	protected Boolean isConnected;
	protected String TagId;
	protected  MyHandler mHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		mHandler = new MyHandler(this);
		
		Ugi.singleton().activityOnCreate(this, true);
		Ugi.singleton().addConnectionStateListener(this);
		calculateStatus();
	}

	@Override
	public void onDestroy() {
		Ugi.singleton().removeConnectionStateListener(this);
		Ugi.singleton().activityOnDestroy(this);
		super.onDestroy();
	}

	@Override
	public void onPause() {
		Ugi.singleton().activityOnPause(this);
		super.onPause();
	}

	@Override
	public void onResume() {
		Ugi.singleton().activityOnResume(this);
		isConnected = false;
		calculateStatus();
		super.onResume();
	}

	
	// Callback when connection state has changed
	
	@Override
	public void connectionStateChanged(Ugi.ConnectionStates connectionState) {
		if (connectionState == Ugi.ConnectionStates.NOT_CONNECTED) {
			Toast.makeText(this, "Not Connected + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
		} else if (connectionState == Ugi.ConnectionStates.CONNECTING) {
			Toast.makeText(this, "Connecting + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
		} else if (connectionState == Ugi.ConnectionStates.INCOMPATIBLE_READER) {
			Toast.makeText(this, "Incompatible + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
		} else { 
			if (connectionState == Ugi.ConnectionStates.CONNECTED)
			{
				Toast.makeText(this, "Connected + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
				isConnected = true;
			}
		}
		calculateStatus();
	}


	@Override
	public void inventoryTagSubsequentFinds(UgiTag tag, int foundnb) {
		//Log.d(LogTag, "inventoryTagSubsequentFinds "+tag.getEpc().toString());
		mHandler.setTag(tag);
	}
	
	/**
	 * Starts the inventory if the ugrokit is connected and the inventory is not yet started.
	 */
	public void StartInventory ()
	{
		if (!Ugi.singleton().getInStartInventory())
		{
			Log.d(LogTag, "StartInventory inside");
			Ugi.singleton().startInventory(this, inventoryType);
		}
		
		Log.d(LogTag, "StartInventory. Inventory running :  "+Ugi.singleton().getInStartInventory());
		// update the statusbar
		calculateStatus();	
	}
	
	/**
	 * Stops the inventory if it is actually running
	 */
	public void StopInventory()
	{
		if (Ugi.singleton().getInStartInventory())
		{
			Log.d(LogTag, "StopInventory inside");
			Ugi.singleton().stopInventory(this);
		}
		
		Log.d(LogTag, "StopInventory. Inventory running : "+Ugi.singleton().getInStartInventory());
		// update the statusbar
		calculateStatus();
	}
	
	/**
	 * Displays a popup with information about the battery status
	 * @param view : View in which the popup should been displayed
	 */
	 public void doBattery(View view) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Battery Level");
		    Ugi.BatteryInfo d = Ugi.singleton().getBatteryInfo();
		    if (d != null) {
		      builder.setMessage("Charger: " + (d.externalPowerIsConnected
		                                ? (d.isCharging ? "connected, charging" : "connected, fully charged")
		                                : "not connected") +
		                         "Battery level: " + d.percentRemaining + "% (approx)\n" +
		                         "Minutes scanning: TBD\n" +
		                         "Voltage: " + d.voltage);
		    } else {
		      builder.setMessage("Can't read battery level");
		    }
		    builder.setNeutralButton("Ok", null);
		    builder.show();
		  }
	 
	 /**
	  * Calculates the current status. Ugrokit connected or not. RFID Scan on or not.
	  * Triggers on his own the statusbar to update
	  */
	 public void calculateStatus()
	 {
		String status = "UGrokIt: ";

		// check the ugrokit connection
		if (Ugi.singleton().getIsConnected())
			status = status + "Connected";
		else
			status = status + "Not Connected";

		// check the RFID scan 
		if (Ugi.singleton().getInStartInventory())
			status += " | RFID Scan: On";
		else
			status += " | RFID Scan: Off";
		// update the variable and trigger the notify function
		currentStatus = status;
		notifiySatusUpdate();
	}
	 
	 /**
	  * Should be implemented by the real Activites which use the ugrokit.
	  * Basically set the currentStatus String at the statusbar
	  */
	 public void notifiySatusUpdate()
	 {
		 
	 }
	 
	 /**
	  * Stops all handler, if they are still turned on
	  */
	 public void stopAllModes()
	 {
		 mHandler.modeNewItem(false, null, null);
		 mHandler.modeTicketItemScan(false, null, null);
	//	 mHandler.modeTruckerCheck(false);
	 }
}
