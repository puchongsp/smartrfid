package sushil.luc.ugrokit;

import sushil.luc.gui.TagNewItemActivity;
import sushil.luc.gui.Ticket_showItems;
import sushil.luc.item.Item;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.ticket.Ticket;
import android.content.Context;
import com.ugrokit.api.UgiTag;
/**
 * If a Tag was found this handler calls the correct Manager, which uses the tag in the way as expect
 * @author Luc
 *
 */
public class MyHandler {
	
	private static UgiTag lastTag; 
	//private UgiTag ticketScanLastTag;
	private TicketScanManager ts_manager;
	//private ItemInfoManager ii_manager;
	private NewItemManager ni_manager;
	//private TruckerCheckManager tc_manager;
	//private ReturnItemManager ri_manager;
	private boolean modeTicketItemScan;
	//private boolean modeItemInfo;
	private boolean modeNewItem;
	//private boolean modeTruckerCheck;
	//private boolean modeReturnItem;
	
	/**
	 * Init all the different handlers
	 * @param con
	 */
	public MyHandler(Context con)
	{
		ts_manager = new TicketScanManager(con);
	//	ii_manager = new ItemInfoManager();
		ni_manager = new NewItemManager(con);
		//tc_manager = new TruckerCheckManager(con);
	//	ri_manager = new ReturnItemManager(con);
		lastTag=null;
	}
	
	/**
	 * If you are in a Ticket and you want to scan the items
	 * Turn on the mode where you get a dialog to check if really found the correct item
	 * @param mode true = on, false =off
	 */
	public void modeTicketItemScan( boolean mode, Ticket t, Ticket_showItems activity)
	{
		ts_manager.reset();
		ts_manager.init(t, activity);
		modeTicketItemScan = mode;
	}
	
	/*public void modeItemInfo( boolean mode)
	{
		modeItemInfo = mode;
	}*/
	/**
	 * Turn on the Trucker scan Mode
	 * @param mode true = on, false =off
	 */
/*	public void modeTruckerCheck( boolean mode)
	{
		tc_manager.reset();
		modeTruckerCheck = mode;
	}*/
	
	/**
	 * Tag new Items 
	 * @param mode true = on, false =off
	 * @param activity
	 * @param i
	 */
	public void modeNewItem( boolean mode, TagNewItemActivity activity, Item i)
	{
		ni_manager.reset();
		ni_manager.init(i, activity);
		modeNewItem = mode;
	}
	
/*	public void modeReturnItem( boolean mode)
	{
		ri_manager.reset();
		modeReturnItem = mode;
	}*/
	/**
	 * If a new tag was found tell the handler. The handler calls the function of the correct Manager
	 * @param current: the new Tag
	 */
	public void setTag(UgiTag current)
	{
		lastTag = current;
		
		if (modeTicketItemScan)
			TicketScan(current);
	/*	if (modeItemInfo)
			ItemInfoScan(current);*/
		if (modeNewItem)
			NewItemScan(current);
/*		if (modeTruckerCheck)
			TruckerCheckScan(current);*/
	/*	if (modeReturnItem)
			ReturnItemScan(current);*/
			
	}
	/*
	public String getLastEpc()
	{
		return lastTag.getEpc().toString();		
	}*/
	
	/**
	 * Give the ticket manager the new tag
	 * @param tag
	 */
	private void TicketScan(UgiTag tag)
	{
		if (ts_manager.stillopen || ts_manager.initial)
		{
			ts_manager.handleTag(tag);
		}
		else
			modeTicketItemScan=false;
	}
	
	/*private void ItemInfoScan (UgiTag tag)
	{
		ii_manager.handleTag(tag);
	}*/
	
	/**
	 * Give the New Item Manager the new tag
	 * @param tag
	 */
	private void NewItemScan (UgiTag tag)
	{
		
		ni_manager.handleTag(tag);
	}
	/**
	 * Give the Trucker Check manager the new tag
	 * @param tag
	 */
/*	private void TruckerCheckScan (UgiTag tag)
	{
		tc_manager.handleTag(tag);
	}*/
	
	/*private void ReturnItemScan (UgiTag tag)
	{
		ri_manager.handleTag(tag);
	}*/
}
