package sushil.luc.gui;


import android.app.ActionBar.Tab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.widget.Toast;

public class MyTabsListener<T extends Fragment> implements android.app.ActionBar.TabListener
{
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
	private static String log ="MyTabListener";
	private static boolean logging =true;


    public  MyTabsListener(Activity activity, String tag, Class<T> clz)
    {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
    	Toast.makeText(MainActivity.appContext, "Reselected!", Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onTabUnselected(final Tab tab, final FragmentTransaction ft)
    {
        if (mFragment != null)
        {
            ft.detach(mFragment);
        }
    }
    
    public T getFragment()
    {
    	return (T) mFragment;
    }

}
