package es.carlosrolindez.navigatorenh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




class InOutFragmentAdapter extends FragmentPagerAdapter {

    private int mCount;
    InFragment inFragment;
    OutFragment outFragment;

    public InOutFragmentAdapter(FragmentManager fm,InFragment inFragment, OutFragment outFragment) 
    {
        super(fm);
        mCount = 2;
        this.inFragment = inFragment;
        this.outFragment = outFragment;
    }

    @Override
    public Fragment getItem(int position) {

    	switch (position) 
    	{
    		case 0: 	return outFragment;
    		case 1:		
    		default:	return inFragment;
    			
    	}
        
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      	switch (position) 
    	{
    		case 0: 	return "Outcome";
    		case 1: 	
    		default:	return "Income";

    			
    	}
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}