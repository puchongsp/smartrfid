package sushil.luc.gui;

import sushil.luc.smartrfid.R;
import android.app.Fragment;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemInfoFragment extends Fragment {
	
	private TextView info;
    private TextView Item_ID;
    private TextView Item_Name;
    private TextView Item_RFID;
    private TextView Item_Location;
    private TextView Item_Status;
	private TextView Item_Category;
	private TextView Item_SubCategory;
	private TextView Item_CreationDate;
	private TextView Item_Type;
	private TextView Item_InventoryTotal;
	private TextView Item_InventoryOnHand;
	private TextView Item_InventoryOut;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	View v = inflater.inflate(R.layout.item_info_fragment, container, false);
    	
    	info = (TextView) v.findViewById(R.id.InfoText);
    	 Item_ID = (TextView) v.findViewById(R.id.item_id);
         Item_Name = (TextView) v.findViewById(R.id.item_name);
         Item_RFID = (TextView) v.findViewById(R.id.item_rfid);
         Item_Location = (TextView) v.findViewById(R.id.item_location);
         Item_Status = (TextView) v.findViewById(R.id.item_status);
         
 		Item_Category= (TextView) v.findViewById(R.id.item_categorie);
 		Item_SubCategory= (TextView) v.findViewById(R.id.item_subcategorie);
 		Item_CreationDate= (TextView) v.findViewById(R.id.item_creationdate);
 		Item_Type= (TextView) v.findViewById(R.id.item_type);
 		Item_InventoryTotal= (TextView) v.findViewById(R.id.item_inventorytotal);
 		Item_InventoryOnHand= (TextView) v.findViewById(R.id.item_inventoryonhand);
 		Item_InventoryOut= (TextView) v.findViewById(R.id.item_inventoryout);
    	
 		Item_ID.setVisibility(View.INVISIBLE);
 		Item_Name.setVisibility(View.INVISIBLE);
 		Item_RFID.setVisibility(View.INVISIBLE);
 		Item_Location.setVisibility(View.INVISIBLE);
 		Item_Status.setVisibility(View.INVISIBLE);
 	
 		Item_Category.setVisibility(View.INVISIBLE);
 		Item_SubCategory.setVisibility(View.INVISIBLE);
 		Item_CreationDate.setVisibility(View.INVISIBLE);
 		Item_Type.setVisibility(View.INVISIBLE);
 		Item_InventoryTotal.setVisibility(View.INVISIBLE);
 		Item_InventoryOnHand.setVisibility(View.INVISIBLE);
 		Item_InventoryOut.setVisibility(View.INVISIBLE);
    	
        return v;
    }
    
    /**
     * 
     * @param ItemId
     * @param ItemName
     * @param ItemRfid
     * @param ItemStatus
     * @param ItemLocation
     * @param Category
     * @param SubCategory
     * @param CreationDate
     * @param Type
     * @param InventoryTotal
     * @param InventoryOnHand
     * @param InventoryOut
     */
    public void displayInfo (String ItemId, String ItemName, String ItemRfid, String ItemStatus, String ItemLocation,
    		String Category, String SubCategory, String CreationDate, String Type, String InventoryTotal, String InventoryOnHand, String InventoryOut)
    {
    	
    	if (ItemId.equals(""))
    	{
    		Item_ID.setVisibility(View.VISIBLE);
	 		Item_Name.setVisibility(View.INVISIBLE);
	 		Item_RFID.setVisibility(View.INVISIBLE);
	 		Item_Location.setVisibility(View.INVISIBLE);
	 		Item_Status.setVisibility(View.INVISIBLE);
	 		Item_Category.setVisibility(View.INVISIBLE);
	 		Item_SubCategory.setVisibility(View.INVISIBLE);
	 		Item_CreationDate.setVisibility(View.INVISIBLE);
	 		Item_Type.setVisibility(View.INVISIBLE);
	 		Item_InventoryTotal.setVisibility(View.INVISIBLE);
	 		Item_InventoryOnHand.setVisibility(View.INVISIBLE);
	 		Item_InventoryOut.setVisibility(View.INVISIBLE);
	 		
	 		Item_Name.setText(ItemName);
    	}
    	else
    	{
	    	Item_ID.setVisibility(View.VISIBLE);
	 		Item_Name.setVisibility(View.VISIBLE);
	 		Item_RFID.setVisibility(View.VISIBLE);
	 		Item_Location.setVisibility(View.VISIBLE);
	 		Item_Status.setVisibility(View.VISIBLE);
	 		Item_Category.setVisibility(View.VISIBLE);
	 		Item_SubCategory.setVisibility(View.VISIBLE);
	 		Item_CreationDate.setVisibility(View.VISIBLE);
	 		Item_Type.setVisibility(View.VISIBLE);
	 		Item_InventoryTotal.setVisibility(View.VISIBLE);
	 		Item_InventoryOnHand.setVisibility(View.VISIBLE);
	 		Item_InventoryOut.setVisibility(View.VISIBLE);
	    	
	    	info.setText("Last scanned Item :");
	    	Item_ID.setText("Item Id: "+ItemId);
	    	Item_Name.setText("Item Name: "+ItemName);
	    	Item_RFID.setText("Item RFID: "+ItemRfid);
	    	Item_Status.setText("Status: "+ItemStatus);
	    	if (ItemLocation!=null)
	    		Item_Location.setText("Item Location: "+ItemLocation);
	    	else
	    		Item_Location.setText("Item Location: ");
	    	if (Category!=null)
	    		Item_Category.setText("Category: "+Category);
	    	else
	    		Item_Category.setText("Category: ");
	    	if (SubCategory!=null)
	    		Item_SubCategory.setText("SubCategory: "+SubCategory);
	    	else
	    		Item_SubCategory.setText("SubCategory: ");
	    	if (CreationDate!=null)
	    		Item_CreationDate.setText("CreationDate: "+CreationDate);
	    	else
	    		Item_CreationDate.setText("CreationDate: ");
	    	if (Type!=null)
	    		Item_Type.setText("Type: "+Type);
	    	else
	    		Item_Type.setText("Type: ");
	
	    	Item_InventoryTotal.setText("Inventory Total: "+InventoryTotal);
	    	Item_InventoryOnHand.setText("Inventory on Hand: "+InventoryOnHand);
	 		Item_InventoryOut.setText("Inventory out: "+InventoryOut);
    	}
    }
	
	
}
