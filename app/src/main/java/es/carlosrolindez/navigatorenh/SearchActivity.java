package es.carlosrolindez.navigatorenh;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


// TODO Improve position of progress Layout
// TODO Access to purchase/sales order
// TODO Publish?


public class SearchActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<Product>>
{
	
	private ProductListFragment searchFragment;
//	private LoaderManager lm;
	private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
    	
    	super.onCreate(savedInstanceState);
 
        setContentView(R.layout.frame_container_layout);
    	
        loadPreferences();


    	

    	
	    if (savedInstanceState == null)
	    {
        	searchFragment = ProductListFragment.newInstance();
    		if (searchFragment!=null)
    			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, searchFragment).commit();
        }
	    
    	Intent intent = getIntent();
    	
    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
    	{
	    	String query=intent.getStringExtra(SearchManager.QUERY);
			ActionBar ab = getActionBar();
	      	if (ab!=null) ab.setTitle(query);
    	}
    	else if (intent.getStringExtra(NavisionTool.LAUNCH_REFERENCE)!=null)
    	{
    		String reference = intent.getStringExtra(NavisionTool.LAUNCH_REFERENCE);	  	    
    		int infoMode = intent.getIntExtra(NavisionTool.LAUNCH_INFO_MODE,NavisionTool.SEARCH_MODE_BOM);
			ActionBar ab = getActionBar();
		    switch (infoMode)
		    {
		    case NavisionTool.SEARCH_MODE_BOM:
                if (ab!=null) ab.setTitle("BOM " + reference);
		        break;
		        
		    case NavisionTool.SEARCH_MODE_IN_USE:
                if (ab!=null) ab.setTitle(reference + " used in:");
		        break;
		    }	        
    	}

 	    
    }

	@Override
	protected void onNewIntent(Intent intent) 
	{ 
		LoaderManager lm;
        ActionBar ab=getActionBar();
		
        setContentView(R.layout.frame_container_layout);       
        loadPreferences();
    	lm = getSupportLoaderManager();  
    	setIntent(intent);
	    searchView.setIconified(true);
    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
    	{
	    	searchFragment = ProductListFragment.newInstance();
	    	getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
	    	
	    	String query=intent.getStringExtra(SearchManager.QUERY);	    	
	      	if (ab!=null) ab.setTitle(query);
	      	 
		    Bundle searchString = new Bundle();
		    searchString.putString(NavisionTool.QUERY, query);  	

		    lm.restartLoader(NavisionTool.LOADER_PRODUCT_SEARCH, searchString, this);	
		    searchFragment.showProgress(true);
    	}
    	else if (intent.getStringExtra(NavisionTool.LAUNCH_REFERENCE)!=null)
    	{
	    	searchFragment = ProductListFragment.newInstance();
	        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit(); 
	        
    		String reference = intent.getStringExtra(NavisionTool.LAUNCH_REFERENCE);	  	    
    		int infoMode = intent.getIntExtra(NavisionTool.LAUNCH_INFO_MODE,NavisionTool.SEARCH_MODE_BOM);

	      	Bundle searchString = new Bundle();
       	    searchString.putString(NavisionTool.QUERY, reference);  	  
 
		    switch (infoMode)
		    {
		    case NavisionTool.SEARCH_MODE_BOM:
                if (ab!=null) ab.setTitle("BOM " + reference);
	       	    lm.restartLoader(NavisionTool.LOADER_PRODUCT_SEARCH_BOM, searchString, this);	
	       	    searchFragment.showProgress(true);
		        break;
		        
		    case NavisionTool.SEARCH_MODE_IN_USE:
                if (ab!=null) ab.setTitle(reference + " used in:");
	       	    lm.restartLoader(NavisionTool.LOADER_PRODUCT_SEARCH_IN_USE, searchString, this);	
	       	    searchFragment.showProgress(true);
		        break;
		    }	        
    	}
    	else
        if (ab!=null) ab.setTitle("Navigator");
    }
	
	private void loadPreferences()
	{
	    PreferenceManager.setDefaultValues(this, R.xml.preferences, false); 
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    	boolean emulator =  sharedPref.getBoolean("emulator_mode", true);
    	if (emulator)
    	{
    		NavisionTool.changeMode(NavisionTool.MODE_EMULATOR);  	
    	}
    	else
    	{
    		NavisionTool.changeMode(NavisionTool.MODE_REAL);
    		NavisionTool.setServerConnection( sharedPref.getString("db_name", ""),
    			sharedPref.getString("db_port_number", ""), 
    			sharedPref.getString("db_ip_address", ""), 
    			sharedPref.getString("domain_name", ""), 
    			sharedPref.getString("user_name", ""), 
    			sharedPref.getString("password", ""));
    	}  
		FileTool.setServerConnection(sharedPref.getString("server_address", ""), 
    			sharedPref.getString("domain_name", ""), 
    			sharedPref.getString("user_name", ""), 
    			sharedPref.getString("password", ""));
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuItem searchMenuItem;
        getMenuInflater().inflate(R.menu.search_menu, menu);
        
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchMenuItem = menu.findItem(R.id.action_search);
	    searchView = (SearchView) searchMenuItem.getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//	    searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
	    searchView.setIconifiedByDefault(true); 
	    searchView.setIconified(true);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) 
                {
                    if (searchView != null) 
                    {
                        searchView.setQuery("", false);

                    }// end if
                }// end if

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) 
            {
                /**
                 * hides and then unhides search tab to make sure
                 * keyboard disappears when query is submitted
                 */
                if (searchView != null) {
                    searchView.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    
	    return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//    	    searchView.clearFocus();
 	    	Intent intent = new Intent (this, SettingsActivity.class);
        	startActivity(intent);    
            return true;
        }
        return super.onOptionsItemSelected(item);
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
	    searchFragment.showProgress(false);
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
    	searchFragment.showResultSet(productList);
	}
	
	@Override 
	public void onLoaderReset(Loader<ArrayList<Product>> loader)
	{

	}
}
