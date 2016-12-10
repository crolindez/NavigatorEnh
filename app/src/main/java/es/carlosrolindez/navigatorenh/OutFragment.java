package es.carlosrolindez.navigatorenh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class OutFragment extends Fragment
{
	private ArrayList<InOut> inOutList;
	private ListView list;
	private InOutListAdapter listAdapter;
	private boolean progressAllowed;
	private boolean progressPending;
	
	public static OutFragment newInstance() 
	{
		OutFragment  fragment = new OutFragment();
		fragment.inOutList=null;
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
    	return  inflater.inflate(R.layout.out_layout, container, false);    
    }

    @Override    
    public void onActivityCreated(Bundle savedInstanceState) 
    {	
    	super.onActivityCreated(savedInstanceState);

    	if (savedInstanceState != null) 
    		inOutList = savedInstanceState.getParcelableArrayList(NavisionTool.IN_OUT_LIST_KEY);
   	
 	    list=(ListView)getActivity().findViewById(R.id.out_list);    

	    listAdapter = new InOutListAdapter(getActivity(),inOutList);

	    list.setAdapter(listAdapter);
 
        progressAllowed = true;
        if (progressPending) showProgress (true);
        
	    if (inOutList!=null) 
       		showResultSet(inOutList);
    }
    

	
    @Override
    public void onSaveInstanceState(Bundle savedState) 
	{
	    super.onSaveInstanceState(savedState);
	    savedState.putParcelableArrayList(NavisionTool.IN_OUT_LIST_KEY, inOutList);
	}   

    public void showProgress(boolean progress)
	{
    	if (progressAllowed)
    	{
        	if (progress)    getActivity().findViewById(R.id.loadingPanel_outList).setVisibility(View.VISIBLE);
        	else getActivity().findViewById(R.id.loadingPanel_outList).setVisibility(View.GONE);
        	progressPending = false;
    	}
    	else progressPending = progress;
	}

	void showResultSet(ArrayList<InOut> inOutListLoaded)
	{
		if (inOutListLoaded == null) 
			inOutList = null;
		else
		{
			inOutList = new ArrayList<>();
			for (InOut item : inOutListLoaded)
			{
				if (!item.inMode)
				{
					inOutList.add(item);
				}
			}
		}

		if (listAdapter!=null) 
			listAdapter.showResultSet(inOutList);

	}
}
    
