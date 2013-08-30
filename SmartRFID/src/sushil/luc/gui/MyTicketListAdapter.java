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

public class MyTicketListAdapter extends SimpleAdapter {

	private List<Ticket> alltickets;
	private static String MyLog = "MyTicketListAdapter";
	private static boolean logging =false;
	
	
	public MyTicketListAdapter(Context context, List<? extends Map<String, ?>> data,
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
			/*case Closed:
				res =Color.GREEN;
				if (logging)
					Log.d(MyLog, "Closed == Green");
				break;*/
			case Checked:
				res =Color.GREEN;
				if (logging)
					Log.d(MyLog, "InProgress == Yellow");
				break;
			case Open:
				res =Color.RED;
				if (logging)
					Log.d(MyLog, "Open == RED");
				break;
			default :
				res =Color.LTGRAY;
				if (logging)
					Log.d(MyLog, "default == Grey");
				
		}
    	
    	return res;
    }

}
