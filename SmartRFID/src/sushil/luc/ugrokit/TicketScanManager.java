package sushil.luc.ugrokit;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ugrokit.api.UgiTag;

import java.util.LinkedList;
import java.util.List;

import sushil.luc.gui.Ticket_showItems;
import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.item.ItemStatus;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketStatus;

public class TicketScanManager extends RFIDManager {

    private Context con;
    public boolean retest;
    private Dialog dialog;
    private UgiTag retestTag;
    private String LogTag = "TicketScanTag";
    public boolean stillopen;
    public boolean initial;
    private Ticket currentticket;
    private Ticket_showItems currentactivity;
    private List<UgiTag> alreadyUsed;
    private ItemService itemservice;


    public TicketScanManager(Context con) {
        this.con = con;
        this.retest = false;
        this.retestTag = null;
        this.stillopen = false;
        this.initial = true;
        this.alreadyUsed = new LinkedList<UgiTag>();
        this.itemservice = new ItemService();
    }

    /**
     * Check what has to be done with the tag
     *
     * @param tag : the new found tag
     */
    public void handleTag(UgiTag tag) {
        // did the user find all the tags?
        if (!currentticket.getStatus().equals(TicketStatus.Open)) {
            // stop the rfid scanner to save energy
            this.currentactivity.StopInventory();
        } else {
            itemservice.fetchItemFromRfid(tag, null, this);

        }
    }

    public void handle2(Item item, UgiTag tag) {

        String name = item.getItemName();
        Log.d(LogTag, "handle Tag " + tag.getEpc().toString());
        String info = name + " (" + tag.getEpc() + ")";
        // If the tag was already handled we don't have to check it again
        if (!alreadyUsed.contains(tag)) {
            // did the user activate the retest mode, to find the correct item
            if (retest) {
                TextView text = (TextView) dialog.findViewById(R.id.text);

                // is the current tag the same as before
                if (retestTag.equals(tag)) {
                    Log.d(LogTag, "The same Tag");
                    text.setText("Found the same item : " + info);
                } else {
                    Log.d(LogTag, "Not the same Tag");
                    text.setText("Found another item : " + info);
                }
            } else {
                // check if the dialog is open and retest is not active
                if (stillopen) {
                    // ignore the tag for now
                } else {
                    // Dialog not yet open, check if the tag is in the item list of the ticket
                    if (currentticket != null && currentticket.checkRFIDInTicket(tag.getEpc().toString())) {
                        this.initial = false;
                        Log.d(LogTag, "First time found, No retest");
                        showDialog(name, tag);
                    } else {
                        Toast.makeText(con, "This item is not in the ticket", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /**
     * Starts the Dialog
     *
     * @param name
     * @param tag
     */
    public void showDialog(final String name, final UgiTag tag) {
        stillopen = true;
        dialog = new Dialog(this.con);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ticket_scan);
        dialog.setTitle("Tag discovered");

        String info = name + " (" + tag.getEpc() + ")";

        // set the custom dialog components - text, image and button
        final TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Found item : " + info);

        final Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
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

        final Button dialogOkButton = (Button) dialog.findViewById(R.id.dialogButtonOk);
        dialogOkButton.setText(" Ok ");
        // if button is clicked, close the custom dialog
        dialogOkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LogTag, "Button Add to Ticket");

                Item currentItem = currentticket.getItem(tag);
                currentItem.setStatus(ItemStatus.Checked);

                // update the views and check if ticket is maybe already fully collected
                currentticket.calcTicketStatus();
                currentactivity.fillItems2List();

                alreadyUsed.add(tag);
                reset();
                dialog.dismiss();
            }
        });


        final Button dialogRetestButton = (Button) dialog.findViewById(R.id.dialogButtonRetest);
        dialogRetestButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retest == false) {
                    retest = true;
                    retestTag = tag;
                    Log.d(LogTag, "Button Retest");
                    text.setText("Please " + name + " scan again.");
                    dialogRetestButton.setText(" Stop Retest ");
                    dialogOkButton.setVisibility(View.INVISIBLE);
                    dialogCancelButton.setVisibility(View.INVISIBLE);
                } else {
                    retest = false;
                    text.setText("Please " + name + " scan again.");
                    dialogRetestButton.setText(" Retest ");
                    dialogOkButton.setVisibility(View.VISIBLE);
                    dialogCancelButton.setVisibility(View.VISIBLE);
                }
            }
        });
        dialog.show();
    }

    /**
     * Init the Manager. This has to be called by the activity which wants to use this manager
     *
     * @param t
     * @param activity
     */
    public void init(Ticket t, Ticket_showItems activity) {
        this.currentticket = t;
        this.currentactivity = activity;
    }

    /**
     * After the dialog is closed some variables should be initialized again
     */
    public void reset() {
        this.retest = false;
        this.retestTag = null;
        this.stillopen = false;
        this.initial = true;
    }
}
