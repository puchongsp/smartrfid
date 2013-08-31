package sushil.luc.ugrokit;

import java.util.ArrayList;

import sushil.luc.smartrfid.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ugrokit.api.UgiEpc;
import com.ugrokit.api.UgiTag;

public class TruckerCheckManager {

	/**
	 * Not needed in this project
	 * 
	 */
	/*private Context con;
	private Dialog dialog;
	private String LogTag = "TruckerCheck";
	public boolean initial;
	private ListView d_tags;
	private ArrayList<String> taglist;
	private ArrayList<String> notallowedtaglist;
	private ArrayAdapter<String> adapter;

	// private Ticket deliveryTicket;

	public TruckerCheckManager(Context con) {
		this.con = con;
		this.initial = true;
		this.taglist = null;
		this.adapter = null;
		this.d_tags = null;
		this.notallowedtaglist = new ArrayList<String>();
	}

	public void handleTag(UgiTag tag) {
		if (initial) {
			this.initial = false;
			Log.d(LogTag, "First time startup");
			showDialog(tag);
		} else {
			checkTag(tag);
		}
	}

	private String getTagName(UgiTag tag) {

		String name = tag.getEpc().toString();
		name = name.replaceFirst("^0*", "");
		return "TestName" + name;
	}

	public void showDialog(final UgiTag tag) {
		dialog = new Dialog(this.con);
		dialog.setContentView(R.layout.dialog_trucker_check);
		dialog.setTitle("Trucker Check");

		// set the custom dialog components - text, image and button
		d_tags = (ListView) dialog.findViewById(R.id.discovered_tags);

		initList();

		Button dialogChancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
		dialogChancelButton.setText(" Cancel ");
		// if button is clicked, close the custom dialog
		dialogChancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(LogTag, "Cancel Button");

				reset();
				dialog.dismiss();
			}
		});

		Button dialogOkButton = (Button) dialog.findViewById(R.id.dialogButtonOk);
		dialogOkButton.setText(" Ok "); 
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(LogTag, "Ok Button");

				//reset();
				dialog.dismiss();
			}
		});

		Button dialogRetestButton = (Button) dialog.findViewById(R.id.dialogButtonRestart);
		dialogRetestButton.setText(" Retest "); 
		// if button is clicked, close the custom dialog
		dialogRetestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(LogTag, "Retest Button");

			//	reset();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public void reset() {
		this.initial = true;
		this.taglist = null;
		this.adapter = null;
		this.d_tags = null;
	}

	private void initList() {

		String[] dummy = new String[] { "TestName1", "TestName2", "TestName3" };
		taglist = new ArrayList<String>();
		for (String a : dummy) {
			taglist.add(a);
		}

		adapter = new ArrayAdapter<String>(con, android.R.layout.simple_list_item_1, taglist);
		d_tags.setAdapter(adapter);

	}

	private void checkTag(UgiTag tag) {
		String a = getTagName(tag);
		// Log.d(LogTag, "addTagtoList");
		if (!notallowedtaglist.contains(a))
		{
			if ((!taglist.contains(a))) {
				taglist.add(a);
				notallowedtaglist.add(a);
				adapter.notifyDataSetChanged();
				// Log.d(LogTag, "addTagtoList yayayayay");
			}
			else
				Toast.makeText(con, "Found Item "+a, Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(con, "Found Item "+a, Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * public void setTicket (Ticket t) { deliveryTicket =t; }
	 */


	/*
	 * private class TruckerAdapter extends SimpleAdapter {
	 * 
	 * //private List<Item> allItems; private static String MyLog =
	 * "TruckerAdapter";
	 * 
	 * 
	 * public TruckerAdapter(Context context, List<? extends Map<String, ?>>
	 * data, int resource, String[] from, int[] to, List<Item> allItems) {
	 * super(context, data, resource, from, to);
	 * 
	 * this.allItems =allItems; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { View row = super.getView(position, convertView, parent);
	 * 
	 * row.setBackgroundColor(getColor(position));
	 * 
	 * return row; }
	 * 
	 * private int getColor (int position) { int res =-1;
	 * 
	 * /*ItemStatus status = allItems.get(position).getStatus();
	 * 
	 * switch (status) { case Collected: res =Color.GREEN; Log.d(MyLog,
	 * "Closed == Green"); break; case Available: res =Color.RED; Log.d(MyLog,
	 * "Open == RED"); break; case Repair: res = Color.DKGRAY; Log.d(MyLog,
	 * "Repair == DKGRAY"); break; default: res =Color.BLUE; Log.d(MyLog,
	 * "Repair, RentToClient, Transport == Blue");
	 * 
	 * }
	 * 
	 * return res; }}
	 */
}
