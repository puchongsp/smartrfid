package sushil.luc.ugrokit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.smartrfid.R;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
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


/**
 * Is not used anymore, because there is a problem with Activity and Fragment
 * Solved in the MainActivity class
 * @author Luc
 *
 */
public class ReturnItemManager {

/*	private Context con;
	private Dialog dialog;
	private String LogTag = "ReturnItem";
	public boolean initial;
	private ListView d_tags;
	private ArrayList<String> taglist;
	ArrayAdapter<String> adapter;

	public ReturnItemManager(Context con) {
		this.con = con;
		this.initial = true;
		this.taglist = null;
		this.adapter = null;
		this.d_tags = null;
	}

	public void handleTag(UgiTag tag) {
			if (initial) {
				this.initial = false;
				Log.d(LogTag, "First time startup");
				showDialog(tag);
			} else {
				addTagtoList(tag);
			}
		
	}

	private String getTagId(UgiTag tag) {
		// ask database
		String name = tag.getEpc().toString();
		name = name.replaceFirst("^0*", "");
		return name;
	}

	public void showDialog(final UgiTag tag) {
		dialog = new Dialog(this.con);
		dialog.setContentView(R.layout.dialog_return_item);
		dialog.setTitle("Return Tags discovered");

		// set the custom dialog components - text, image and button
		d_tags = (ListView) dialog.findViewById(R.id.discovered_tags);

		initList();

		Button dialogOkButton = (Button) dialog.findViewById(R.id.dialogButtonOk);
		dialogOkButton.setText(" Ok ");
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(LogTag, "Button Ok");

				reset();
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

		// data
		taglist = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(con,
				android.R.layout.simple_list_item_1, taglist);
		d_tags.setAdapter(adapter);
		
	}

	private void addTagtoList(UgiTag tag) {
		String a = getTagId(tag);
		//Log.d(LogTag, "addTagtoList");
		if ((!taglist.contains(a))) {
			taglist.add(a);
			adapter.notifyDataSetChanged();
			//Log.d(LogTag, "addTagtoList yayayayay");
		}
	}*/
}
