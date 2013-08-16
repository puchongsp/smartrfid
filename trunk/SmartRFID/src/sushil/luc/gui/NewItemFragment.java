package sushil.luc.gui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.smartrfid.R;

public class NewItemFragment extends Fragment {

    private static ListView mListView;
    private View view;
    private static MyItemListAdapter adapter;
    private static List<Map<String, String>> groupData;
    private static List<Item> newItems;
    private static Context context;
   // private L

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.new_item_fragment, container, false);
      // loadNewItems();
      //  setRetainInstance(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity().getApplicationContext();
        loadNewItems();
    }

    private void loadNewItems(){
        ItemService itemService = new ItemService();
        //TODO connect to database-> done
        newItems = itemService.getNewItems("NewItemFragment");
        String KEY_LABEL ="Big Text";
        String KEY_HELP ="Help Text";
        Map<String, String> group;
        groupData = new ArrayList<Map<String, String>>();

        for (int i =0; i<newItems.size();i++) {
            Item tmp = newItems.get(i);

            group = new HashMap<String, String>();

            group.put( KEY_LABEL,  tmp.getItemName() );
            group.put( KEY_HELP, "Location "+tmp.getWarehouseLocation() );


            groupData.add(group);
        }

        mListView = (ListView) view.findViewById(R.id.new_item_list_view);

        adapter = new MyItemListAdapter( getActivity().getApplicationContext(), groupData, android.R.layout.simple_list_item_2,
                new String[] { KEY_LABEL, KEY_HELP },
                new int[]{ android.R.id.text1, android.R.id.text2 } , newItems);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(), TagNewItemActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }
    
    
    public static void updateView(List<Item> updateNewItems)
    {
    	String KEY_LABEL ="Big Text";
        String KEY_HELP ="Help Text";
        Map<String, String> group;
        groupData = new ArrayList<Map<String, String>>();

        for (int i =0; i<updateNewItems.size();i++) {
            Item tmp = updateNewItems.get(i);

            group = new HashMap<String, String>();

            group.put( KEY_LABEL,  tmp.getItemName() );
            group.put( KEY_HELP, "Location "+tmp.getWarehouseLocation() );


            groupData.add(group);
        }
    	
        adapter = new MyItemListAdapter( context, groupData, android.R.layout.simple_list_item_2,
                new String[] { KEY_LABEL, KEY_HELP },
                new int[]{ android.R.id.text1, android.R.id.text2 } , newItems);
        
    	mListView.setAdapter(adapter);
    	
    }
}
