package es.carlosrolindez.navigatorenh;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class InOutListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<InOut> mInOutList;
	
	public InOutListAdapter(Activity activity,ArrayList<InOut> inOutList)
	{
		mInOutList = inOutList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount()
	{
		if (mInOutList == null)
			return 0;
		else
			return mInOutList.size();
	}
	
	@Override
	public Object getItem(int position)
	{
		if (mInOutList == null)
			return 0;
		else			
			return mInOutList.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
			return position;
	}
	
	public ArrayList<InOut>getProductList() 
	{
		return mInOutList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{	
		final InOut inOut;
		
		float quantityValue;
		
	    if (mInOutList == null)
	    	return null;
		View localView = convertView;
		if (localView==null)
		{
			localView = inflater.inflate(R.layout.inout_row, parent, false);
		}
		
		TextView document = (TextView)localView.findViewById(R.id.document_inout);
		TextView date = (TextView)localView.findViewById(R.id.date_inout);
		TextView quantity = (TextView)localView.findViewById(R.id.quantity_inout);
		TextView source = (TextView)localView.findViewById(R.id.source_inout);
		
		inOut = mInOutList.get(position);
		
		document.setText(inOut.document);
		date.setText(inOut.date);		
		source.setText(inOut.source);

		quantityValue = Float.parseFloat(inOut.quantity);
		quantity.setText(String.format(Locale.US,"%.1f un.",quantityValue));

        if (inOut.programmed)
            drawResourceInView(R.drawable.stock_bg,localView);
        else
            drawResourceInView(R.drawable.cost_bg,localView);
        return localView;
	}

    private void drawResourceInView(int resource, View viewer)
    {
        viewer.setBackground(viewer.getResources().getDrawable(resource,null));
    }

	public  void showResultSet(  ArrayList<InOut> inOutList)
	{
		mInOutList = inOutList;
	    notifyDataSetChanged();		
	}

	
}
