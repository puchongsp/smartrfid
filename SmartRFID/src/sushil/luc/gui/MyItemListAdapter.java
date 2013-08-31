package sushil.luc.gui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;

public class MyItemListAdapter extends SimpleAdapter {

	private List<Item> allItems;
	private static String MyLog = "MyItemListAdapter";
	 
	
	
	public MyItemListAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to, List<Item> allItems) {
		super(context, data, resource, from, to);

		this.allItems =allItems;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View row = super.getView(position, convertView, parent);

        row.setBackgroundColor(getColor(position));
       
        return row;
    }
    
    private int getColor (int position)
    {
    	int res =-1;
    	
    	ItemStatus status = allItems.get(position).getStatus();
    	
    	if (status!=null)
    	{
		switch (status)
		{
			case Checked:
				res =Color.GREEN;
				Log.d(MyLog, "Checked == Green");
				break;
			case Available:
				res =Color.RED;
				Log.d(MyLog, "Available == RED");
				break;
            case Returned:
                res = Color.DKGRAY;
                Log.d(MyLog, "Returned == DKGRAY");
                break;
			default:
				res =Color.BLUE;
				Log.d(MyLog, "Repair, RentToClient, Transport == Blue");
			
		}
    	}
    	return res;
    }
    

}
