package sushil.luc.gui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.smartrfid.R;

public class ReturnItemFragment extends Fragment {


    private ListView returnItemListView;
    private View view;
    private ItemService itemService;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.return_items_fragment, container, false);
        
        itemService = new ItemService();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateItems();
    }
/**
 * Set a new returned Item and update the view
 * @param item
 */
    public void returnItem(Item item){
    	// set an item to returned
        itemService.returnItem(item);
        populateItems();
    }

    /**
     * update the view
     */
    private void populateItems() {
    	// get all the returned items from the database
        List<Item> returnedItems = itemService.getReturnedItems();

        String KEY_LABEL ="Big Text";
        String KEY_HELP ="Help Text";
        Map<String, String> group;
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();

        for (int i =0; i < returnedItems.size();i++) {
            Item tmp = returnedItems.get(i);

            group = new HashMap<String, String>();

            group.put( KEY_LABEL,  tmp.getItemID() );
            group.put( KEY_HELP, "Desc "+tmp.getItemName() );

            groupData.add(group);
        }

        returnItemListView = (ListView) view.findViewById(R.id.return_item_list_view);
        // Create an adapter which adapts the color according to the item status
        ReturnItemListAdapter adapter = new ReturnItemListAdapter( getActivity().getApplicationContext(), groupData, android.R.layout.simple_list_item_2,
                new String[] { KEY_LABEL, KEY_HELP },
                new int[]{ android.R.id.text1, android.R.id.text2 } , returnedItems);

        returnItemListView .setAdapter(adapter);

        returnItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(), ReturnedItemOptionsActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }
 
}
