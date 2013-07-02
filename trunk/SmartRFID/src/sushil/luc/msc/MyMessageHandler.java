package sushil.luc.msc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.widget.TextView;

public class MyMessageHandler {
	
	private String TAG_ID;
	private String TAG ="MyMessageHandler";
	
	public MyMessageHandler(Intent intent)
	{
		byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		TAG_ID=ByteArrayToHexString(tagId);
		Log.d(TAG,"Tag Id : "+TAG_ID);
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
	
	public String getTagID()
	{
		return TAG_ID;
	}

}
