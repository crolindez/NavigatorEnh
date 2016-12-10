package es.carlosrolindez.navigatorenh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductListFragment extends Fragment 
{
	private ListView list;
	private ProductListAdapter listAdapter;
	private ArrayList<Product> productList;
	private boolean progressAllowed;
	private boolean progressPending;
	
	public static ProductListFragment newInstance() 
	{
		ProductListFragment  fragment = new ProductListFragment();
		fragment.progressAllowed = false;
		fragment.progressPending = false;
		return fragment;
	}

	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	 	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        return inflater.inflate(R.layout.list_layout, container, false);         
    }

    @Override    
    public void onActivityCreated(Bundle savedInstanceState) 
    {
      	super.onActivityCreated(savedInstanceState);

      	if (savedInstanceState != null) 
      	{
        	productList = savedInstanceState.getParcelableArrayList(NavisionTool.PRODUCT_LIST_KEY);
      	}
      	else
      	{
      		productList=null;
      	}
		
    	list=(ListView)getActivity().findViewById(R.id.list);  
    	listAdapter = new ProductListAdapter(getActivity(),productList);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(onItemClickListener);  
	    
        progressAllowed = true;
        if (progressPending) showProgress (true);
        

        if (productList!=null) 
        {
       		showResultSet(productList);
        }
    }
    
	OnItemClickListener onItemClickListener = new OnItemClickListener() 
	{
		@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    	{ 			
	    	Intent intent = new Intent (view.getContext(), InfoActivity.class);
	        Product product = (Product)parent.getItemAtPosition(position);
        	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, product.reference);        	
        	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, product.description);  
        	intent.putExtra(NavisionTool.LAUNCH_INFO_MODE, NavisionTool.INFO_MODE_SUMMARY);
        	startActivity(intent);
        	
//        	Toast.makeText(view.getContext(), "List Item", Toast.LENGTH_SHORT).show();
    	}
	};
    
    
    public void onSaveInstanceState(Bundle savedState) 
    {
        super.onSaveInstanceState(savedState);
        savedState.putParcelableArrayList(NavisionTool.PRODUCT_LIST_KEY, productList);

    }  
   
    public void showProgress(boolean progress)
	{
    	if (progressAllowed)
    	{
        	if (progress)    getActivity().findViewById(R.id.loadingPanel_list).setVisibility(View.VISIBLE);
        	else getActivity().findViewById(R.id.loadingPanel_list).setVisibility(View.GONE);
        	progressPending = false;
    	}
    	else progressPending = progress;
	}

	void showResultSet(ArrayList<Product> productListLoaded)
	{
		if (productListLoaded == null) 
			productList = null;
		else
		{
	   		productList = new ArrayList<>();
			for (Product item : productListLoaded)
			{
				productList.add(item);
			}
		}
		if (listAdapter!=null) 
			listAdapter.showResultSet(productList);
	}
}
