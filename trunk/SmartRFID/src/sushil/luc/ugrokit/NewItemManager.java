package sushil.luc.ugrokit;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ugrokit.api.UgiTag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.gui.TagNewItemActivity;
import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.smartrfid.R;

public class NewItemManager extends RFIDManager{

	private Context con;
	private Dialog dialog;
	private String LogTag = "NewItem";
	private boolean initial;
	private boolean stopAdding;
	private ListView d_tags;
	private ArrayList<String> taglist;
	private ArrayAdapter<String> adapter;
	private Item currentItem;
	private TagNewItemActivity currentActivity;
	private List<UgiTag> alreadyTestedTags;
	//private ItemHistory itemHistory;
	private ItemService itemService;

	/**
	 * Init the Manager
	 * @param con
	 */
	public NewItemManager(Context con) {
		this.con = con;
		this.initial = true;
		this.taglist = null;
		this.adapter = null;
		this.dialog =null;
		this.d_tags = null;
		this.stopAdding =false;
		this.currentItem= null;
		this.currentActivity=null;
		this.alreadyTestedTags = new LinkedList<UgiTag>();
		//this.itemHistory = ItemHistory.getInstance();
		this.itemService = new ItemService();
	}

	/**
	 * ask the database if the tag is already connected to an item
	 * 
	 * @param tag
	 * @return false if there is no connection true if there is a connection
	 */
/*	private boolean alreadyexitsts(UgiTag tag) {

		 	//TODO fix concurrency here
	        Item lookupItem = itemService.fetchItemFromRfid(tag.getEpc().toString(),null, this);

	        // lookupItem null means the rfid is not yet assigned to any item yet
	        if (lookupItem == null) {
	        	return false;
	        }
	        else {
	        	return true;
	        }
	}*/
	
	/**
	 * Check what should be done with the Tag
	 * @param tag
	 */
	public void handleTag(UgiTag tag) {
		Log.d(LogTag, "Initial "+initial );
		Log.d(LogTag, "handleTag "+ tag.getEpc());
		// check if we did not yet handle the current Tag
		if (!this.alreadyTestedTags.contains(tag))
		{
			// Tag was not seen before
			Log.d(LogTag, "Not yet in the List "+ tag.getEpc());
			
			itemService.fetchItemFromRfid(tag,null, this);
			/*
			if (alreadyexitsts(tag)) {
				// The Tag is already in use, by another item 
				Toast.makeText(con, "Tag "+tag.getEpc().toString()+" already in use for another item", Toast.LENGTH_SHORT).show();
				this.alreadyTestedTags.add(tag);
			} else {
				// check if the Dialog is currently open
				if (initial) {					
					this.initial = false;
					Log.d(LogTag, "First time startup "+ tag.getEpc());
					showDialog(tag);
				} else {
					Log.d(LogTag, "Not intial "+ tag.getEpc());
					addTagtoList(tag);
				}
			}*/
		}
	}
	
	public void handle2 (Item item, UgiTag tag)
	{
		if (currentItem!=null)
        {
            if (item.getItemID()!=null)
            {
                // The Tag is already in use, by another item
                Toast.makeText(con, "Tag "+tag.getEpc().toString()+" already in use for another item", Toast.LENGTH_SHORT).show();
                this.alreadyTestedTags.add(tag);
            } else {
                // check if the Dialog is currently open
                if (initial) {
                    this.initial = false;
                    Log.d(LogTag, "First time startup "+ tag.getEpc());
                    showDialog(tag);
                } else {
                    Log.d(LogTag, "Not intial "+ tag.getEpc());
                    addTagtoList(tag);
                }
            }
        }
	}
	
	/**
	 * Gets from the given tag the EPC id. Does cut of the leading 0
	 * @param tag
	 * @return
	 */
	/*private String getTagId(UgiTag tag) {
		// ask database
		String name = tag.getEpc().toString();
		name = name.replaceFirst("^0*", "");
		return name;
	}*/

	/**
	 * Displays the Dialog to find all the available not yet used Tags
	 * @param tag 
	 */
	public void showDialog(final UgiTag tag) {
		dialog = new Dialog(this.con);
        dialog.setCancelable(false);
		dialog.setContentView(R.layout.dialog_new_item);
		dialog.setTitle("New Tag discovered");

		// set the custom dialog components - text, image and button
		d_tags = (ListView) dialog.findViewById(R.id.discovered_tags);

		initList();

		Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
		dialogCancelButton.setText(" Cancel ");
		// if button is clicked, close the custom dialog
		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(LogTag, "Button Cancel");

				reset();
				
				dialog.dismiss();
			}
		});


		dialog.show();
	}

	/**
	 * If the Dialog is closed some Data has to be reset to the initial values
	 */
	public void reset() {
		Log.d(LogTag, "reset");

       /* this.initial = true;
        this.taglist = null;
        this.adapter = null;
        this.dialog =null;
        this.d_tags = null;
        this.stopAdding =false;
        this.currentItem= null;
        this.currentActivity=null;
        this.alreadyTestedTags = new LinkedList<UgiTag>();*/

		this.initial = true;
		this.taglist = null;
		this.adapter = null;
		this.d_tags = null;
		this.stopAdding =false;
		//this.dialog =null;
		//this.currentItem= null;
		//this.currentActivity=null;
		this.alreadyTestedTags = new LinkedList<UgiTag>();
	}
	
	/**
	 * Init the List, first. At the start there is not data in there
	 */
	private void initList() {

		// data
		taglist = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(con,
				android.R.layout.simple_list_item_single_choice, taglist);
		d_tags.setAdapter(adapter);

		// on click
		d_tags.setOnItemClickListener(new OnItemClickListener() {

			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				stopAdding=true; 
			    String selected_id = (String) d_tags.getItemAtPosition(position);
			    
			    // if the user clicks on the ID, we have to combine the item and the tag id
			    Log.d(LogTag, "Selected Id was : "+selected_id);

	            currentItem.setRFID(selected_id);
                itemService.tagNewItem(currentItem);
	            // Tell the history
	           // itemHistory.saveToHistory(currentItem);

                ItemService.newItems.remove(currentItem);


	            currentActivity.StopInventory();
	            // tell the view to update. Delete the currently tagged Item


                // Stop the rfid scan to save energy
                currentActivity.updateView();

                  // stop all modes
                  currentActivity.stopAllModes();



	            
	            
	            Log.d(LogTag, "Did it");
			    //reset();
			    dialog.dismiss();
			  }});
	}
	
	/**
	 * Adds the tag to the list of tags, which might be used as to the tag new items
	 * @param tag
	 */
	private void addTagtoList(UgiTag tag) {
		String a = tag.getEpc().toString();
		//Log.d(LogTag, "addTagtoList");
		if ((!taglist.contains(a)) && (!stopAdding)) {
			taglist.add(a);
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * An init function, which has to be called by the activity which starts this Manager
	 * @param i : Item which you want to tag
	 * @param a : the calling Activity
	 */
	public void init(Item i, TagNewItemActivity a)
	{
		this.currentItem= i;
		this.currentActivity=a;
	}
	
}
