package es.carlosrolindez.navigatorenh;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Locale;

public class InfoFragment extends Fragment
{
	private ArrayList<Product> productList;
	private boolean progressAllowed;
	private boolean progressPending;
	
	public static InfoFragment newInstance() 
	{
		InfoFragment  fragment = new InfoFragment();
		fragment.productList = null;
		fragment.progressAllowed = false;
		fragment.progressPending = false;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) 
    {
    	return inflater.inflate(R.layout.general_info_enh_layout, container, false);    
    }

    @Override    
    public void onActivityCreated(Bundle savedInstanceState) 
    {	
    	super.onActivityCreated(savedInstanceState);
    	if (savedInstanceState != null) 
        	productList = savedInstanceState.getParcelableArrayList(NavisionTool.PRODUCT_LIST_KEY);
	    
	    progressAllowed = true;
        if (progressPending) showProgress (true);
 
	    if (productList!=null) 
       		showResultSet(productList);
    }    	
   
    @Override
    public void onSaveInstanceState(Bundle savedState) 
	{
	    super.onSaveInstanceState(savedState);
	    savedState.putParcelableArrayList(NavisionTool.PRODUCT_LIST_KEY, productList);
	}

    private void drawResourceInView(int resource, int viewer)
    {
        getActivity().findViewById(viewer).setBackground(getResources().getDrawable(resource,null));
    }

    public void showProgress(boolean progress)
	{
    	if (progressAllowed )
    	{
        	if (progress) getActivity().findViewById(R.id.loadingPanel_general_info).setVisibility(View.VISIBLE);
        	else getActivity().findViewById(R.id.loadingPanel_general_info).setVisibility(View.GONE);
        	progressPending = false;     	   
        }
    	else progressPending = progress;
	}

	public void showResultSet(ArrayList<Product> productListLoaded)
	{
		Product product = null;
		float stockValue;
		float costValue;
		float priceValue;
		float purchaseValue;
		float inProductionValue;
		float inPlannedProductionValue;
		float saleValue;
		float usedInProductionValue;
		float transferValue;
		float usedInPlannedProductionValue;		
		float orderPointValue;

		
		if (productListLoaded == null) 
		{
			productList = null;
		}
		else
		{
	   		productList = new ArrayList<>();
			for (Product item : productListLoaded)
			{
				if (item.itemMode == NavisionTool.LOADER_PRODUCT_INFO)
				{
					productList.add(item);
					product = item;
				}
			}
			if (product != null && getActivity()!=null)
			{

				TextView stock = (TextView)getActivity().findViewById(R.id.general_info_stock);
				stockValue = Float.parseFloat(product.stock);
				stock.setText(String.format(Locale.US,"%.1f un.",stockValue));
				
				TextView orderPoint = (TextView)getActivity().findViewById(R.id.general_info_order_point);
				orderPointValue = Float.parseFloat(product.orderPoint);
				orderPoint.setText(String.format(Locale.US,"  (%.1f)",orderPointValue));

				TextView price = (TextView)getActivity().findViewById(R.id.general_info_price);
				priceValue = Float.parseFloat(product.price);
				price.setText(String.format(Locale.US,"%.2f �",priceValue));

				TextView cost = (TextView)getActivity().findViewById(R.id.general_info_cost);
				costValue = Float.parseFloat(product.cost);
				if ( (costValue>0) && ( priceValue>0) )
					cost.setText(String.format(Locale.US,"%.2f � (%.2f%%)",costValue,100*(costValue/priceValue)));
				else
					cost.setText(String.format(Locale.US,"%.2f �",costValue));

				
				TextView purchase = (TextView)getActivity().findViewById(R.id.general_info_purchase);
				purchaseValue = Float.parseFloat(product.purchase);
				if (purchaseValue>0)
					purchase.setText(String.format(Locale.US,"%.1f un.",purchaseValue));
				else
					getActivity().findViewById(R.id.purchase_layout).setVisibility(View.GONE);
				
				TextView inProduction = (TextView)getActivity().findViewById(R.id.general_info_in_production);
				inProductionValue = Float.parseFloat(product.inProduction);
				if (inProductionValue>0)
					inProduction.setText(String.format(Locale.US,"%.1f un.",inProductionValue));
				else
					getActivity().findViewById(R.id.production_layout).setVisibility(View.GONE);

				TextView inPlannedProduction = (TextView)getActivity().findViewById(R.id.general_info_in_planned_productions);
				inPlannedProductionValue = Float.parseFloat(product.inPlannedProduction);
				if (inPlannedProductionValue>0)
					inPlannedProduction.setText(String.format(Locale.US,"%.1f un.",inPlannedProductionValue));
				else
					getActivity().findViewById(R.id.in_planned_productions_layout).setVisibility(View.GONE);

				TextView sale = (TextView)getActivity().findViewById(R.id.general_info_sales);
				saleValue = Float.parseFloat(product.sale);
				if (saleValue>0)
					sale.setText(String.format(Locale.US,"%.1f un.",saleValue));
				else
					getActivity().findViewById(R.id.sales_layout).setVisibility(View.GONE);
				
				TextView usedInProduction = (TextView)getActivity().findViewById(R.id.general_info_used_in_production);
				usedInProductionValue = Float.parseFloat(product.usedInProduction);
				if (usedInProductionValue>0)
					usedInProduction.setText(String.format(Locale.US,"%.1f un.",usedInProductionValue));
				else
					getActivity().findViewById(R.id.used_in_production_layout).setVisibility(View.GONE);

				TextView transfer = (TextView)getActivity().findViewById(R.id.general_info_transfer);
				transferValue = Float.parseFloat(product.transfer);
				if (transferValue>0)
					transfer.setText(String.format(Locale.US,"%.1f un.",transferValue));
				else
					getActivity().findViewById(R.id.transfer_layout).setVisibility(View.GONE);

				TextView usedInPlannedProduction = (TextView)getActivity().findViewById(R.id.general_info_used_in_planned_productions);
				usedInPlannedProductionValue = Float.parseFloat(product.usedInPlannedProduction);
				if (usedInPlannedProductionValue>0)
					usedInPlannedProduction.setText(String.format("%.1f un.",usedInPlannedProductionValue));
				else
					getActivity().findViewById(R.id.used_in_planned_productions_layout).setVisibility(View.GONE);


                if ( (stockValue + purchaseValue + inProductionValue) < (saleValue + transferValue + usedInProductionValue))
                    drawResourceInView(R.drawable.consume_bg, R.id.stock_layout);
                else if ( (stockValue + inProductionValue) < (saleValue + transferValue + usedInProductionValue))
                    drawResourceInView(R.drawable.stock_bg, R.id.stock_layout);
                else if ( (stockValue + purchaseValue + inProductionValue) < (saleValue + transferValue + usedInProductionValue + orderPointValue))
                    drawResourceInView(R.drawable.danger_bg, R.id.stock_layout);




				final String reference = product.reference;
				final String description = product.description;		
				
	        	View inLayout = getActivity().findViewById(R.id.in_layout);
	        	inLayout.setOnClickListener(new View.OnClickListener() {      
	        	    @Override
	        	    public void onClick(View v) 
	        	    {
	        	    	Intent intent = new Intent (v.getContext(), InOutActivity.class);
	                	intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference);        	
	                	intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, description);  
	                	intent.putExtra(NavisionTool.LAUNCH_IN_OUT_MODE, NavisionTool.IN_OUT_MODE_IN);
	                	startActivity(intent);
	                }


	        	});

                View outLayout = getActivity().findViewById(R.id.out_layout);

                outLayout.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent (v.getContext(), InOutActivity.class);
                        intent.putExtra(NavisionTool.LAUNCH_REFERENCE, reference);
                        intent.putExtra(NavisionTool.LAUNCH_DESCRIPTION, description);
                        intent.putExtra(NavisionTool.LAUNCH_IN_OUT_MODE, NavisionTool.IN_OUT_MODE_OUT);
                        startActivity(intent);
                    }

                });


                GraphView graphView = new GraphView( getActivity());

                DataPoint[] seriesData = new DataPoint[Product.NUMBER_OF_MONTHS];
                DataPoint[] seriesData2Years = new DataPoint[Product.NUMBER_OF_MONTHS];
                DataPoint[] seriesData1Year = new DataPoint[Product.NUMBER_OF_MONTHS];
                DataPoint[] seriesData6Months = new DataPoint[Product.NUMBER_OF_MONTHS];
                DataPoint[] seriesData3Months = new DataPoint[Product.NUMBER_OF_MONTHS];



                double mean2Years=0.0d, mean1Year=0.0d, mean6Months=0.0d, mean3Months=0.0d;
                int counter;

				for (counter=0; counter < Product.NUMBER_OF_MONTHS; counter++)
				{
                    double monthValue = -Double.parseDouble(product.consumeByMonth[counter]);
                    mean2Years += monthValue;
                    seriesData[counter] = new DataPoint(counter+0.5d,monthValue);
                    if (counter>=(Product.NUMBER_OF_MONTHS / 2))
                    {
                        mean1Year += monthValue;
                        if (counter>= ((Product.NUMBER_OF_MONTHS / 2) + (Product.NUMBER_OF_MONTHS / 4)))
                        {
                            mean6Months += monthValue;
                            if (counter>= ((Product.NUMBER_OF_MONTHS / 2) + (Product.NUMBER_OF_MONTHS / 4) + (Product.NUMBER_OF_MONTHS / 8)))
                            {
                                mean3Months += monthValue;
                            }
                        }
                    }
				}

                mean2Years /= Product.NUMBER_OF_MONTHS;
                mean1Year /= (Product.NUMBER_OF_MONTHS/2);
                mean6Months /= (Product.NUMBER_OF_MONTHS/4);
                mean3Months /= (Product.NUMBER_OF_MONTHS/8);

                for (counter=0; counter < Product.NUMBER_OF_MONTHS; counter++)
                {
                    seriesData2Years[counter] = new DataPoint(counter+0.5d,mean2Years);
                    if (counter>=(Product.NUMBER_OF_MONTHS / 2))
                    {
                        seriesData1Year[counter] = new DataPoint(counter+0.5d,mean1Year);
                        if (counter>= ((Product.NUMBER_OF_MONTHS / 2) + (Product.NUMBER_OF_MONTHS / 4)))
                        {
                            seriesData6Months[counter] = new DataPoint(counter+0.5d,mean6Months);
                            if (counter>= ((Product.NUMBER_OF_MONTHS / 2) + (Product.NUMBER_OF_MONTHS / 4) + (Product.NUMBER_OF_MONTHS / 8)))
                            {
                                seriesData3Months[counter] = new DataPoint(counter+0.5d,mean3Months);
                            }
                            else
                            {
                                seriesData3Months[counter] = new DataPoint(counter + 0.5d, 0);
                            }
                        }
                        else
                        {
                            seriesData6Months[counter] = new DataPoint(counter + 0.5d, 0);
                            seriesData3Months[counter] = new DataPoint(counter + 0.5d, 0);
                        }
                    }
                    else
                    {
                        seriesData1Year[counter] = new DataPoint(counter + 0.5d, 0);
                        seriesData6Months[counter] = new DataPoint(counter + 0.5d, 0);
                        seriesData3Months[counter] = new DataPoint(counter + 0.5d, 0);
                    }


                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(seriesData);
                BarGraphSeries<DataPoint> mean2YearSeries = new BarGraphSeries<>(seriesData2Years);
                BarGraphSeries<DataPoint> mean1YearSeries = new BarGraphSeries<>(seriesData1Year);
                BarGraphSeries<DataPoint> mean6MonthsSeries = new BarGraphSeries<>(seriesData6Months);
                BarGraphSeries<DataPoint> mean3MonthsSeries = new BarGraphSeries<>(seriesData3Months);

                series.setColor(Color.BLACK);
                mean2YearSeries.setColor(Color.BLUE);
                mean1YearSeries.setColor(Color.GREEN);
                mean6MonthsSeries.setColor(Color.GRAY);
                mean3MonthsSeries.setColor(Color.MAGENTA);

                graphView.addSeries(mean2YearSeries);
                graphView.addSeries(mean1YearSeries);
                graphView.addSeries(mean6MonthsSeries);
                graphView.addSeries(mean3MonthsSeries);
                graphView.addSeries(series);

                graphView.setTitle("Consumo");
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(24);
	            LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.graph_view_layout);
				layout.addView(graphView);
				
			}
		}
	}
	
	
}
