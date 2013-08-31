package sushil.luc.gui;

import java.util.ArrayList;
import java.util.List;

import com.ugrokit.api.UgiTag;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RepairItemFragment extends Fragment{
	
	private TextView ItemName;
	private TextView ItemInfo;
	private TextView HistoryInfo;
	private EditText NewItemHistory;
	private Button btnSave;
	private Button btnFixed;
	private Button btnCancel;
	private ListView ItemHistory;
	private ItemService itemservice;
	
	private static Item currentItem; 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.repair_item_fragment, container, false);
    	
    	ItemName = (TextView) view.findViewById(R.id.ItemName);
    	ItemInfo  = (TextView) view.findViewById(R.id.ItemInfo);
    	HistoryInfo  = (TextView) view.findViewById(R.id.HistoryInfo);
    	NewItemHistory = (EditText) view.findViewById(R.id.NewItemHistory);
    	ItemHistory  = (ListView) view.findViewById(R.id.ItemHistory);
    	
    	btnSave = (Button)  view.findViewById(R.id.savehistory);
    	btnSave.setText("Save History");
    	btnFixed = (Button)  view.findViewById(R.id.FixedItem);
    	btnFixed.setText("Fixed Item");
    	btnCancel = (Button)  view.findViewById(R.id.Cancel);
    	btnCancel.setText("Cancel");
    	
    	itemservice = new ItemService();
    	
    	btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO save ItemHistory with itemService
				
				Toast.makeText(getActivity(), "History saved", Toast.LENGTH_SHORT).show();
			}
		});
    	
    	btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reset();
			}
		});
    	
    	btnFixed.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				itemservice.sendToWarehouse(currentItem);
				reset();
			}
		});
			
    	
    	return view;
	}
	
	public void onResume()
	{
		initScreen();
		super.onResume();
	}
	
	public void setItem( Item i )
	{
		currentItem=i;
		initScreen();
	}
	
	private void initScreen()
	{
    	if (currentItem==null)
    	{
			ItemName.setText("Please scan an RFID Tag");
	    	ItemInfo.setVisibility(View.INVISIBLE);
	    	NewItemHistory.setVisibility(View.INVISIBLE);
	    	btnSave.setVisibility(View.INVISIBLE);
	    	btnFixed.setVisibility(View.INVISIBLE);
	    	btnCancel.setVisibility(View.INVISIBLE);
			ItemHistory.setVisibility(View.INVISIBLE);
			HistoryInfo.setVisibility(View.INVISIBLE);
    	}
    	else
    	{
    		ItemInfo.setVisibility(View.VISIBLE);
    		NewItemHistory.setVisibility(View.VISIBLE);
        	btnSave.setVisibility(View.VISIBLE);
        	btnFixed.setVisibility(View.VISIBLE);
        	btnCancel.setVisibility(View.VISIBLE);
        	ItemHistory.setVisibility(View.VISIBLE);
        	HistoryInfo.setVisibility(View.VISIBLE);
        	
        	ItemName.setText("Item ID: "+currentItem.getItemID());
        	ItemInfo.setText("Desc: "+currentItem.getItemName()+ " RFID Nb: "+ currentItem.getRFID());
        	NewItemHistory.setHint("Insert a new history entry here");
        	//TODO get the ItemHistory 
        	
        	ArrayList<String> data = new ArrayList<String>();
        	
        	data.add("foo");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
        	data.add("bar bar bar bar bar bar bar bar barbar bar bar bar bar bar barbar bar bar barbarbarbarbar bar  bar");
           
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, data);
            ItemHistory.setAdapter(arrayAdapter);
        
    
    	}
	}
	
	private void reset()
	{
		currentItem=null;
		initScreen();
		((UgroKitActivity) getActivity()).StartInventory();
	}
	
}
