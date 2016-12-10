package es.carlosrolindez.navigatorenh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;



public class InOutListLoader extends AsyncTaskLoader<ArrayList<InOut>> {
	private ArrayList<InOut> inOutList;
	private String filterString;
	private int loaderMode;

	
	

	
	public InOutListLoader(Context ctx,int id, Bundle filter)
	{
		super(ctx);
		filterString = filter.getString(NavisionTool.QUERY,"").toUpperCase(Locale.US);
		loaderMode = id;		
	}
	
	@Override
	public ArrayList<InOut> loadInBackground()
	{
		InOut inOut; 	
		inOutList = new ArrayList<>();
		if (NavisionTool.readMode()==NavisionTool.MODE_EMULATOR)
		{
	    	switch (loaderMode)
	    	{
    		case NavisionTool.LOADER_PRODUCT_IN_OUT:   
    		default:

    			inOut = new InOut(); 	
    			inOut.document = "OC-142035";
    			inOut.source = "High Hit";
    			inOut.quantity = "1500.0";
    			inOut.date = "02/01/2015";		    	
		    	inOut.programmed = false;
		    	inOut.inMode = true; // purchase
		    	inOutList.add(inOut);	
 
		    	inOut = new InOut(); 	
    			inOut.document = "141988";
    			inOut.source = "50302";
    			inOut.quantity = "100.0";
    			inOut.date = "02/12/2014";		    	
		    	inOut.programmed = false;
		    	inOut.inMode = true; // fabrication
		    	inOutList.add(inOut);	

		    	inOut = new InOut(); 	
    			inOut.document = "140036";
    			inOut.source = "50302";
    			inOut.quantity = "100.0";
    			inOut.date = "02/02/2015";		    	
		    	inOut.programmed = true;
		    	inOut.inMode = true; // planned fabrication
		    	inOutList.add(inOut);	

	
		    	inOut = new InOut(); 	
    			inOut.document = "PO-142563";
    			inOut.source = "Music in Every";
    			inOut.quantity = "100.0";
    			inOut.date = "12/12/2014";		    	
		    	inOut.programmed = false;
		    	inOut.inMode = false; // sale
		    	inOutList.add(inOut);	

		    	inOut = new InOut(); 	
    			inOut.document = "transfer";
    			inOut.source = "50302";
    			inOut.quantity = "5.0";
    			inOut.date = "02/12/2014";		    	
		    	inOut.programmed = false; // transfer
		    	inOutList.add(inOut);	
		    	
		    	inOut = new InOut(); 	
    			inOut.document = "142222";
    			inOut.source = "DIS49";
    			inOut.quantity = "5.0";
    			inOut.date = "05/12/2014";		    	
		    	inOut.programmed = false;
		    	inOut.inMode = false; // in fabrication
		    	inOutList.add(inOut);	

		    	inOut = new InOut(); 	
    			inOut.document = "140048";
    			inOut.source = "DIS49";
    			inOut.quantity = "5.0";
    			inOut.date = "05/02/2015";		    	
		    	inOut.programmed = true;
		    	inOut.inMode = false; // in planned fabrication		    	
		    	inOutList.add(inOut);	

		 		break;

	    	}		    	

	    	return inOutList;
			
		}
		else
		{

			/*  Connection */
		    Connection conn = NavisionTool.openConnection();
		    if (conn!=null) 
		    {
    			SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0",Locale.US);
    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
			    
			    try 
			    {
			    	ResultSet result;
			    	switch (loaderMode)
			    	{
	
		    		case NavisionTool.LOADER_PRODUCT_IN_OUT:
		    		default:
		    			result = NavisionTool.queryListPurchase(filterString);
						if (result!=null) {
                            while (result.next()) {
                                inOut = new InOut();
                                inOut.date = dateFormat.format(sourceFormat.parse(result.getString(1)));
                                inOut.document = result.getString(2);
                                inOut.source = NavisionTool.queryProviderName(result.getString(3));
                                inOut.quantity = result.getString(4);
                                inOut.inMode = true; // purchase
                                inOutList.add(inOut);
                            }
                        }
		    			result = NavisionTool.queryListFabrication(filterString);
                        if (result!=null) {
                            while (result.next()) {
                                inOut = new InOut();
                                inOut.date = dateFormat.format(sourceFormat.parse(result.getString(1)));
                                inOut.document = result.getString(2);
                                inOut.source = filterString;
                                inOut.programmed = result.getString(3).contains("2");
                                inOut.quantity = result.getString(4);
                                inOut.inMode = true; // fabrication
                                inOutList.add(inOut);
                            }
                        }

		    			result = NavisionTool.queryListSales(filterString);
                        if (result!=null) {
                            while (result.next()) {
                                inOut = new InOut();
                                inOut.date = dateFormat.format(sourceFormat.parse(result.getString(1)));
                                inOut.document = result.getString(2);
                                inOut.source = result.getString(3);
                                inOut.quantity = result.getString(4);
                                inOut.inMode = false; // sales
                                inOutList.add(inOut);
                            }
                        }
		    			result = NavisionTool.queryListTransfer(filterString);
                        if (result!=null) {
                            while (result.next()) {
                                inOut = new InOut();
                                inOut.date = dateFormat.format(sourceFormat.parse(result.getString(1)));
                                inOut.document = result.getString(2);
                                inOut.source = "transferencia";
                                inOut.quantity = result.getString(3);
                                inOut.inMode = false; // transfer
                                inOutList.add(inOut);
                            }
                        }
		    			result = NavisionTool.queryListInFabrication(filterString);
                        if (result!=null) {
                            while (result.next()) {
                                inOut = new InOut();
                                inOut.date = dateFormat.format(sourceFormat.parse(result.getString(1)));
                                inOut.document = result.getString(2);
                                inOut.source = NavisionTool.queryTargetFabrication(inOut.document);
                                inOut.programmed = result.getString(3).contains("2");
                                inOut.quantity = result.getString(4);
                                inOut.inMode = false; // In fabrication
                                inOutList.add(inOut);
                            }
                        }
					 	break;
					        
				    }		    		     	
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();

				}
	
			    NavisionTool.closeConnection();
			    return inOutList;
		    } 
		}
	    return null;
	}
	
	
	@Override
	public void deliverResult(ArrayList<InOut> arrayList)
	{
		inOutList = arrayList;
		
		if (isStarted())
		{
			super.deliverResult(arrayList);
		}
	}
	
	@Override
	protected void onStartLoading()
	{

		if (inOutList != null)
		{
			deliverResult(inOutList);
		}
		else
		{
			forceLoad();
		}
		
	}
	
	@Override
	protected void onStopLoading()
	{
		cancelLoad();
	}
	
	@Override
	protected void onReset()
	{
		if (inOutList!=null)
	    {
			inOutList.clear();
			inOutList = null;
    	}
		onStopLoading();
	}
	
	@Override
	public void onCanceled(ArrayList<InOut> arrayList)
	{
		super.onCanceled(arrayList);
	}
	
}
