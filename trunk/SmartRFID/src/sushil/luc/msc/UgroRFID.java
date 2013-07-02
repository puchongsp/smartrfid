package sushil.luc.msc;

import com.ugrokit.api.Ugi;
import com.ugrokit.api.UgiEpc;
import com.ugrokit.api.UgiInventoryDelegate;
import com.ugrokit.api.UgiTag;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;

public class UgroRFID extends Activity implements UgiInventoryDelegate,UgiInventoryDelegate.InventoryTagFoundListener,
UgiInventoryDelegate.InventoryTagSubsequentFindsListener{
	
	private byte[] tagid;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  Ugi.singleton().openConnection();
		  Ugi.singleton().activityOnCreate(this, true);
		  Ugi.singleton().startInventory(this,Ugi.InventoryTypes.LOCATE_DISTANCE);
		  tagid =null;
		}

		@Override public void onDestroy() {			
		  Ugi.singleton().activityOnDestroy(this);
		  Ugi.singleton().closeConnection();
		  super.onDestroy();
		}

		@Override public void onPause() {
		  Ugi.singleton().activityOnPause(this);
		  super.onPause();
		}

		@Override public void onResume() {
		  Ugi.singleton().activityOnResume(this);
		  super.onResume();
		}
		
		@Override 
		public void inventoryTagFound(UgiTag tag) {
			  // tag was found for the first time
			tagid = tag.getTidBytes();				
			}
		
		@Override 
		public void inventoryTagSubsequentFinds(UgiTag tag, int count) {
			  // tag found count more times
			tagid = tag.getTidBytes();
			}
		
		public byte[] getTag()
		{
			return this.tagid;
		}
}
