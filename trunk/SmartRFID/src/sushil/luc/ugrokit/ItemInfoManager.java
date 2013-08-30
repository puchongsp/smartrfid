package sushil.luc.ugrokit;

import android.util.Log;
import android.widget.Toast;

import com.ugrokit.api.UgiTag;
/**
 * Is not used anymore, because there is a problem with Activity and Fragment
 * Solved in the MainActivity class
 * @author Luc
 *
 */
public class ItemInfoManager {
	
/*	private boolean init;
	private UgiTag lastTag;
	private String DebugTag = "ItemInfoManager";
	//private Item item;
	
	public ItemInfoManager()
	{
		init = true;
		lastTag = null;
	}
	private void reset()
	{
		init = true;
		lastTag = null;
	}
	
	public void handleTag (UgiTag tag)
	{
		if (init)
		{
			init = false;
			lastTag = tag;
			Log.d(DebugTag, "Found Item "+tag.getEpc());
			reset();
			//item = service.getItemInfo(lastTag.getEpc());
		}
	}
	
	/* public Item getItem ()
	 {
		 reset();
		 return item;
	 }*/
}
