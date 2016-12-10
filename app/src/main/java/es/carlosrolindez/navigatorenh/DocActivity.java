package es.carlosrolindez.navigatorenh;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;





public class DocActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<FileDescription>>
{

    DocFragment docFragment;
    
    private String reference;    
    private String description;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

    	Intent myIntent = getIntent();
    	
	    reference = myIntent.getStringExtra(NavisionTool.LAUNCH_REFERENCE);	  	    
	    description = myIntent.getStringExtra(NavisionTool.LAUNCH_DESCRIPTION);	
	    
        setContentView(R.layout.frame_container_layout);
      	getActionBar().setTitle("Docs for " + reference + " " + description);

        if (savedInstanceState == null)
	    {   
        	docFragment = DocFragment.newInstance();
    		if (docFragment!=null)
    			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, docFragment).commit();
 
	      	LoaderManager lm = getSupportLoaderManager();  
	      	Bundle searchString = new Bundle();
       	    searchString.putString(NavisionTool.QUERY, reference);  
       	    lm.restartLoader(NavisionTool.LOADER_PRODUCT_DOC, searchString, this);	

       	    docFragment.showProgress(true);

	    }
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedState) 
    {
	   	super.onSaveInstanceState(savedState);
	}   

	@Override
	public Loader<ArrayList<FileDescription>> onCreateLoader(int id, Bundle filter)
	{
		return new DocListLoader(this,id,filter);
	}
	
	@Override
	public void onLoadFinished(Loader<ArrayList<FileDescription>> loader,ArrayList<FileDescription> fileDescriptorList)
	{   			
//        setProgressBarIndeterminateVisibility(false);
//        setProgressBarVisibility(false);
		if (docFragment!=null) docFragment.showProgress(false);
   	    
	    if (fileDescriptorList==null)
	    {	
	   		LayoutInflater inflater = getLayoutInflater();
	    	View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout_root));

	    	TextView text = (TextView) layout.findViewById(R.id.text_layout);
	    	text.setText("No Document found");

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
				case NavisionTool.LOADER_PRODUCT_DOC:
				    {
				    	docFragment.showResultSet(fileDescriptorList);			    	
				    }
					break;
			}
	    }
	}
	
	@Override 
	public void onLoaderReset(Loader<ArrayList<FileDescription>> loader)
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
