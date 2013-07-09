package sushil.luc.gui;

import sushil.luc.smartrfid.R;
import android.app.Fragment;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemInfoFragment extends Fragment {
	
	private TextView info;
	private TextView itemid;
	private TextView itemname;
	private TextView itemrfid;
	private TextView itemstatus;
	private TextView itemlocation;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	View v = inflater.inflate(R.layout.item_info_fragment, container, false);
    	
    	info = (TextView) v.findViewById(R.id.InfoText);
    	itemid = (TextView) v.findViewById(R.id.item_id);
    	itemname = (TextView) v.findViewById(R.id.item_name);
    	itemrfid = (TextView) v.findViewById(R.id.item_rfid);
    	itemstatus = (TextView) v.findViewById(R.id.item_status);
    	itemlocation = (TextView) v.findViewById(R.id.item_location);
    	
    	itemid.setVisibility(View.INVISIBLE);
    	itemname.setVisibility(View.INVISIBLE);
    	itemrfid.setVisibility(View.INVISIBLE);
    	itemstatus.setVisibility(View.INVISIBLE);
    	itemlocation.setVisibility(View.INVISIBLE);
    	
        return v;
    }
    
    
    public void displayInfo (String ItemId, String ItemName, String ItemRfid, String ItemStatus, String ItemLocation)
    {
    	
    	itemid.setVisibility(View.VISIBLE);
    	itemname.setVisibility(View.VISIBLE);
    	itemrfid.setVisibility(View.VISIBLE);
    	itemstatus.setVisibility(View.VISIBLE);
    	itemlocation.setVisibility(View.VISIBLE);
    	
    	info.setText("Last scanned Item :");
    	itemid.setText("Item Id: "+ItemId);
    	itemname.setText("Item Name: "+ItemName);
    	itemrfid.setText("Item RFID: "+ItemRfid);
    	itemstatus.setText("Status: "+ItemStatus);
    	itemlocation.setText("Item Location: "+ItemLocation);
    }
	
	
}
