package sushil.luc.msc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

public class RfidService extends Activity{
	
	public void onResume() {
	    super.onResume();
	    
	    Intent intent = getIntent();
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        if (rawMsgs != null) {
	        	NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        }
	    }
	    //process the msgs array
	}
	
}
