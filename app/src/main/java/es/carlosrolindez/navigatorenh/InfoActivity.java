package es.carlosrolindez.navigatorenh;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<Product>>
{
    private InfoFragment infoFragment;
    private ProductListFragment productListFragment;
    private int infoMode;
    private String reference;    
    private String description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);


    	String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);


 //       ActionBar ab = getActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);

    	Intent myIntent = getIntent();
    	
	    reference = myIntent.getStringExtra(NavisionTool.LAUNCH_REFERENCE);	  	    
	    description = myIntent.getStringExtra(NavisionTool.LAUNCH_DESCRIPTION);	
	    infoMode = myIntent.getIntExtra(NavisionTool.LAUNCH_INFO_MODE,NavisionTool.INFO_MODE_SUMMARY);




        setContentView(R.layout.frame_container_layout);	 
        
	    if (savedInstanceState == null)
	    {   	        
	      	LoaderManager lm = getSupportLoaderManager();  
	      	Bundle searchString = new Bundle();
       	    searchString.putString(NavisionTool.QUERY, reference);  	  

       	    switch (infoMode)
		    {     
		    case NavisionTool.INFO_MODE_SUMMARY:
		    default: 
	      	    infoFragment = InfoFragment.newInstance();  		
		        if (infoFragment!=null)
	    			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, infoFragment).commit();
	       	    lm.restartLoader(NavisionTool.LOADER_PRODUCT_INFO, searchString, this);	     
	       	    infoFragment.showProgress(true);
		        break;
		    case NavisionTool.INFO_MODE_BOM:
		    	productListFragment = ProductListFragment.newInstance();		    	
		        if (productListFragment!=null)
		        	getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, productListFragment).commit();
	   	    	lm.restartLoader(NavisionTool.LOADER_PRODUCT_BOM, searchString, this);		        
	   	    	break;
		    case NavisionTool.INFO_MODE_IN_USE:
		    	productListFragment = ProductListFragment.newInstance();		    	
		        if (productListFragment!=null)
		        	getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, productListFragment).commit(); 
	   	    	lm.restartLoader(NavisionTool.LOADER_PRODUCT_IN_USE, searchString, this);
	   	    	break;
		    }	        
   	    }
	    switch (infoMode)
	    {     
	    case NavisionTool.INFO_MODE_SUMMARY:
	    default: 
	      	getActionBar().setTitle(reference + " " + description);
	      	break;
	    case NavisionTool.INFO_MODE_BOM:
	      	getActionBar().setTitle("BOM " + reference);    
	        break;
	    case NavisionTool.INFO_MODE_IN_USE:
	      	getActionBar().setTitle(reference + " used in:");    
	        break;
	    }
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedState) 
    {	
	   	super.onSaveInstanceState(savedState);
	}   
    
	@Override
	public Loader<ArrayList<Product>> onCreateLoader(int id, Bundle filter)
	{
		return new ProductListLoader(this,id,filter);
	}
	
	@Override
	public void onLoadFinished(Loader<ArrayList<Product>> loader,ArrayList<Product> productList)
	{   			
   	    if (infoFragment!=null) infoFragment.showProgress(false);
		if (productListFragment!=null) productListFragment.showProgress(false);
		
	    if (productList==null)
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
			case NavisionTool.LOADER_PRODUCT_INFO:
			default:
				infoFragment.showResultSet(productList);
				Product item = productList.iterator().next();
				description = item.description;
 //               ActionBar ab=getActionBar();
//				if (ab!=null) ab
						getActionBar().setTitle(reference + " " + description);
				break;
				
			case NavisionTool.LOADER_PRODUCT_BOM:				
			case NavisionTool.LOADER_PRODUCT_IN_USE:
				productListFragment.showResultSet(productList);
			    break;
			}
	    }
	}
	
	@Override 
	public void onLoaderReset(Loader<ArrayList<Product>> loader)
	{
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
	    if ((infoMode==NavisionTool.INFO_MODE_BOM)||(infoMode==NavisionTool.INFO_MODE_IN_USE))
	    {
	    	MenuItem menuItem;
	    	menuItem = menu.findItem(R.id.action_plus_one);
	    	menuItem.setVisible(false);	 	    	
	    	menuItem = menu.findItem(R.id.action_docs);
	    	menuItem.setVisible(false);	 
	    	menuItem = menu.findItem(R.id.action_zoom_up);
	    	menuItem.setVisible(false);
	    	menuItem = menu.findItem(R.id.action_zoom_down);
	    	menuItem.setVisible(false);	    	
	    }
	    
	    return super.onCreateOptionsMenu(menu);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        char character;

    	
        switch(id)
		{
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
		case R.id.action_docs:
			intent = new Intent (this, DocActivity.class);
		    	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference);   
		    	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, "");  
				intent.putExtra(NavisionTool.LAUNCH_INFO_MODE, NavisionTool.INFO_MODE_SUMMARY);
				startActivity(intent);   
            break;
		case R.id.action_plus_one:
	        intent = new Intent (this, InfoActivity.class);
			character = reference.charAt(reference.length()-1);
			if ( (character>='0') && (character<'9'))
			{
				character++;
		    	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference.substring(0, reference.length()-1) + character);   
		    	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, "");  
				startActivity(intent);   
			}
            break;
/*		case R.id.action_plus_ten:
	        intent = new Intent (this, InfoActivity.class);
			character = reference.charAt(reference.length()-2);
			if ( (character>='0') && (character<'9'))
			{
				character++;
		    	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference.substring(0, reference.length()-2) + character + reference.charAt(reference.length()-1));   
		    	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, "");  
				intent.putExtra(NavisionTool.LAUNCH_INFO_MODE, NavisionTool.INFO_MODE_SUMMARY);
				startActivity(intent);   
			}
            break;*/
		case R.id.action_zoom_up:
	        intent = new Intent (this, InfoActivity.class);
	    	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference);   
	    	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, description); 
			intent.putExtra(NavisionTool.LAUNCH_INFO_MODE, NavisionTool.INFO_MODE_IN_USE);
        	startActivity(intent);    
            break;
		case R.id.action_zoom_down:
	        intent = new Intent (this, InfoActivity.class);
	    	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference);   
	    	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, description); 
			intent.putExtra(NavisionTool.LAUNCH_INFO_MODE, NavisionTool.INFO_MODE_BOM);
        	startActivity(intent);    
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
