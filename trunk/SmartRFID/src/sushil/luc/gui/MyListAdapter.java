package sushil.luc.gui;

import java.util.List;
import java.util.Map;

import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketStatus;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyListAdapter extends SimpleAdapter {

	private List<Ticket> alltickets;
	private static String MyLog = "MyListAdapter";
	
	
	public MyListAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to, List<Ticket> alltickets) {
		super(context, data, resource, from, to);

		this.alltickets =alltickets;
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
    	
    	TicketStatus status = alltickets.get(position).getStatus();

		switch (status)
		{
			case Closed:
				res =Color.GREEN;
				Log.d(MyLog, "Closed == Green");
				break;
			case InProgress:
				res =Color.YELLOW;
				Log.d(MyLog, "InProgress == Yellow");
				break;
			case Open:
				res =Color.RED;
				Log.d(MyLog, "Open == RED");
				break;
		}
    	
    	return res;
    }

}
