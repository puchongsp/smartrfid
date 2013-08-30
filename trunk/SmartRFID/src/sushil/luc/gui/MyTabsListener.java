package sushil.luc.gui;


import sushil.luc.msc.UgroKitActivity;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class MyTabsListener<T extends Fragment> implements android.app.ActionBar.TabListener
{
    private Fragment mFragment;
    private final UgroKitActivity mActivity;
    private final String mTag;
    private final Class<T> mClass;
	private static String log ="MyTabListener";
	private static boolean logging =true;


    public  MyTabsListener(UgroKitActivity activity, String tag, Class<T> clz)
    {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
    	Toast.makeText(getFragment().getActivity().getApplicationContext(), "Reselected!", Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onTabSelected(final Tab tab, final FragmentTransaction ft)
    {
        mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
        if (mFragment == null)
        {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        }
        else
        {
            ft.attach(mFragment);
        }
        // Start the Rfid scan if certain tabs open
        if (tab.getText().equals(MainActivity.ItemInfoTabName) 
        		|| tab.getText().equals(MainActivity.ReturnItemsTabName)
        		|| tab.getText().equals(MainActivity.RepairItemTabName))
        	{
        		Log.d(log, "onTabSelected");
        		mActivity.StartInventory();
        	}
        	
    }

    @Override
    public void onTabUnselected(final Tab tab, final FragmentTransaction ft)
    {
        if (mFragment != null)
        {
            ft.detach(mFragment);
        }
        
        // deactivate the Rfid scan as soon as the tab changes. next tag might not support the rfid scan and saves energy
    	Log.d(log, "onTabUnselected");
    	mActivity.StopInventory();
    	mActivity.stopAllModes();

    }
    
    public T getFragment()
    {
    	return (T) mFragment;
    }

}
