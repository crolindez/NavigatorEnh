package es.carlosrolindez.navigatorenh;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;





public class InOutActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<InOut>>
{
    private InOutFragmentAdapter mAdapter;
    private ViewPager mPager;
    private TabPageIndicator mIndicator;

    InFragment inFragment;
    OutFragment outFragment;
    
    private String reference;    
    private String description;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

     //   ActionBar ab = getActionBar();
//		if (ab!=null) ab.
				getActionBar().setDisplayHomeAsUpEnabled(true);

    	Intent myIntent = getIntent();
    	
	    reference = myIntent.getStringExtra(NavisionTool.LAUNCH_REFERENCE);	  	    
	    description = myIntent.getStringExtra(NavisionTool.LAUNCH_DESCRIPTION);	
	    int inoutMode = myIntent.getIntExtra(NavisionTool.LAUNCH_IN_OUT_MODE,NavisionTool.IN_OUT_MODE_IN);
	    
        setContentView(R.layout.simple_tabs);
      	getActionBar().setTitle(reference + " " + description); 
        
        if (savedInstanceState == null)
	    {   
	        inFragment = InFragment.newInstance();
	        outFragment = OutFragment.newInstance();
	    }
        
        mAdapter = new InOutFragmentAdapter(getSupportFragmentManager(),inFragment,outFragment);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);	
 	
	    switch (inoutMode)
	    {
	    case NavisionTool.IN_OUT_MODE_OUT:
	    default:  
	        mIndicator.setCurrentItem(0);
	        break;
	        
	    case NavisionTool.IN_OUT_MODE_IN:
	        mIndicator.setCurrentItem(1);
	        break;
	        
	    }

        if (savedInstanceState == null)
	    {   
	      	LoaderManager lm = getSupportLoaderManager();  
	      	Bundle searchString = new Bundle();
       	    searchString.putString(NavisionTool.QUERY, reference);  
       	    lm.restartLoader(NavisionTool.LOADER_PRODUCT_IN_OUT, searchString, this);	

       	    inFragment.showProgress(true);
       	    outFragment.showProgress(true);
	    }
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedState) 
    {
	   	super.onSaveInstanceState(savedState);
	}   

	@Override
	public Loader<ArrayList<InOut>> onCreateLoader(int id, Bundle filter)
	{
		return new InOutListLoader(this,id,filter);
	}
	
	@Override
	public void onLoadFinished(Loader<ArrayList<InOut>> loader,ArrayList<InOut> inOutList)
	{   			
//        setProgressBarIndeterminateVisibility(false);
//        setProgressBarVisibility(false);
		if (inFragment!=null) inFragment.showProgress(false);
   	    if (outFragment!=null) outFragment.showProgress(false);
   	    
	    if (inOutList==null)
	    {	
	   		LayoutInflater inflater = getLayoutInflater();
	    	View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout_root));

	    	TextView text = (TextView) layout.findViewById(R.id.text_layout);
	    	text.setText("SQL server not found");

	    	Toast toast = new Toast(getApplicationContext());
	    	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	    	toast.setDuration(Toast.LENGTH_LONG);
	    	toast.setView(layout);
	    	toast.show();			    	
	    }
	    else
	    {
	        switch(loader.getId())
			{	
				case NavisionTool.LOADER_PRODUCT_IN_OUT:
				    {
				    	inFragment.showResultSet(inOutList);			    	
				    	outFragment.showResultSet(inOutList);
				    }
					break;
			}
	    }
	}
	
	@Override 
	public void onLoaderReset(Loader<ArrayList<InOut>> loader)
	{

	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id)
        {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
