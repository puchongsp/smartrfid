package sushil.luc.msc;

import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
// OLD Implementation for the NFC adapter of the Phone
public class RFIDActivity extends Activity {

	private static String log = "RFIDActivity";
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	protected String TagId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mAdapter=null;
		mPendingIntent =null;
		 startDetection(this);
	}
	
	
	public void onNewIntent(Intent intent) {
		Log.d(log, "onNewIntent");
		TagId = getTagId(intent);
	}
	
	public String getTagId (Intent intent)
	{
		String action = intent.getAction();
		Log.d(log, "new TAG");
		Log.d(log, "Action "+action);
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) 
	    		|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) 
	    		|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			
		    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		    if (tag!=null)
		    	return ByteArrayToHexString(tag.getId());	
	    }
	    
	    return null;
	}
	
	private String ByteArrayToHexString(byte [] inarray) 
    {
	    int i, j, in;
	    String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	    String out= "";
	
	    for(j = 0 ; j < inarray.length ; ++j) 
	        {
	        in = (int) inarray[j] & 0xff;
	        i = (in >> 4) & 0x0f;
	        out += hex[i];
	        i = in & 0x0f;
	        out += hex[i];
	        }
	    return out;
    }
	
	@Override
		public void onPause() {
		    super.onPause();
		    if (mAdapter != null) 
		    	mAdapter.disableForegroundDispatch(this);
		}
	@Override
		public void onResume ()
		{
			super.onResume();
			if (mAdapter != null) 
				mAdapter.enableForegroundDispatch(this, mPendingIntent, null,null);
			
		}

		private void startDetection(Activity activity)
		{
			Context context = getApplicationContext();
		    NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
		    mAdapter = manager.getDefaultAdapter();
		  
		    if (mAdapter == null)
		    {
		    	showWarningAndTerminate("Your Android phone does not proivde NFC capabilities.");
		    }          
		    else if (!mAdapter.isEnabled())
		    {
		    	showWarningAndTerminate("Please enable NFC reader first.");
		    }
		    
		    mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);    	
		}

		private void showWarningAndTerminate(String messageText)
		{
			Toast.makeText(this, messageText, Toast.LENGTH_LONG).show();
		}
}
