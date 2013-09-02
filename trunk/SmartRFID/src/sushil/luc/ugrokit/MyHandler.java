package sushil.luc.ugrokit;

import android.content.Context;

import com.ugrokit.api.UgiTag;

import sushil.luc.gui.TagNewItemActivity;
import sushil.luc.gui.Ticket_showItems;
import sushil.luc.item.Item;
import sushil.luc.ticket.Ticket;

/**
 * If a Tag was found this handler calls the correct Manager, which uses the tag in the way as expect
 *
 * @author Luc
 */
public class MyHandler {

    private static UgiTag lastTag;
    private TicketScanManager ts_manager;
    private NewItemManager ni_manager;
    private boolean modeTicketItemScan;
    private boolean modeNewItem;

    /**
     * Init all the different handlers
     *
     * @param con
     */
    public MyHandler(Context con) {
        ts_manager = new TicketScanManager(con);
        ni_manager = new NewItemManager(con);
        lastTag = null;
    }

    /**
     * If you are in a Ticket and you want to scan the items
     * Turn on the mode where you get a dialog to check if really found the correct item
     *
     * @param mode true = on, false =off
     */
    public void modeTicketItemScan(boolean mode, Ticket t, Ticket_showItems activity) {
        ts_manager.reset();
        ts_manager.init(t, activity);
        modeTicketItemScan = mode;
    }

	

    /**
     * Tag new Items
     *
     * @param mode     true = on, false =off
     * @param activity
     * @param i
     */
    public void modeNewItem(boolean mode, TagNewItemActivity activity, Item i) {
        ni_manager.reset();
        ni_manager.init(i, activity);
        modeNewItem = mode;
    }
	

    /**
     * If a new tag was found tell the handler. The handler calls the function of the correct Manager
     *
     * @param current: the new Tag
     */
    public void setTag(UgiTag current) {
        lastTag = current;

        if (modeTicketItemScan)
            TicketScan(current);

        if (modeNewItem)
            NewItemScan(current);


    }


    /**
     * Give the ticket manager the new tag
     *
     * @param tag
     */
    private void TicketScan(UgiTag tag) {
        if (ts_manager.stillopen || ts_manager.initial) {
            ts_manager.handleTag(tag);
        } else
            modeTicketItemScan = false;
    }
	


    /**
     * Give the New Item Manager the new tag
     *
     * @param tag
     */
    private void NewItemScan(UgiTag tag) {

        ni_manager.handleTag(tag);
    }
 
}
