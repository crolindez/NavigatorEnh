package es.carlosrolindez.navigatorenh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



public class ProductListLoader extends AsyncTaskLoader<ArrayList<Product>> {
	private ArrayList<Product> productList;
	private String filterString;
	private int loaderMode;

	
	

	
	public ProductListLoader(Context ctx,int id, Bundle filter)
	{
		super(ctx);
		filterString = filter.getString(NavisionTool.QUERY,"").toUpperCase(Locale.US);
		loaderMode = id;		
	}
	
	@Override
	public ArrayList<Product> loadInBackground()
	{
		Product product; 	
		productList = new ArrayList<>();
		if (NavisionTool.readMode()==NavisionTool.MODE_EMULATOR)
		{
	    	switch (loaderMode)
	    	{
    		case NavisionTool.LOADER_PRODUCT_IN_USE:    		
    		case NavisionTool.LOADER_PRODUCT_SEARCH_IN_USE:
    			product = new Product(); 	
				product.reference = "50302";
				product.description = "iSelect 2.5 pulgadas blanco";	
				product.stock = "333";
				product.cost = "20.3";
		    	product.inProduction = "150";
		    	product.purchase = "500";		    	
		    	product.transfer = "13";
		    	product.sale = "25";
		    	product.usedInProduction = "255";	    			
		    	product.orderPoint = "100";
		    	product.quantity = "1.0";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;
				productList.add(product);	 
				
				product = new Product(); 	 

				product.reference = "50304";
				product.description = "iSelect 5 pulgadas blanco";	
				product.stock = "222";
		    	product.inProduction = "150";
		    	product.purchase = "500";		    	
		    	product.transfer = "13";
		    	product.sale = "25";
		    	product.usedInProduction = "255";	
		    	product.orderPoint = "100";
		    	product.quantity = "1.0";
				product.cost = "40.3";
				product.quantity = "1.5";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;    			
				productList.add(product);	    				
				break;

    		case NavisionTool.LOADER_PRODUCT_BOM:
    		case NavisionTool.LOADER_PRODUCT_SEARCH_BOM:
    			product = new Product(); 	
    			product.reference = "50342";
				product.description = "iSelect 2.5 pulgadas cromo";	
				product.stock = "333";
				product.cost = "20.3";
		    	product.inProduction = "150";
		    	product.purchase = "500";		    	
		    	product.transfer = "13";
		    	product.sale = "25";
		    	product.usedInProduction = "255";	    
		    	product.orderPoint = "100";
				product.quantity = "2.0";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;    			
				productList.add(product);	    				

				product = new Product(); 	
				product.reference = "50344";
				product.description = "iSelect 5 pulgadas cromo";	
				product.stock = "222";
		    	product.inProduction = "150";
		    	product.purchase = "500";		    	
		    	product.transfer = "13";
		    	product.sale = "25";
		    	product.usedInProduction = "255";	    					
		    	product.orderPoint = "100";
		    	product.quantity = "1.0";
				product.cost = "40.3";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;    					
				productList.add(product);	    				
		 		break;

    		case NavisionTool.LOADER_PRODUCT_SEARCH:
    			product = new Product(); 
    			product.reference = "50312";
				product.description = "iSelect 2.5 pulgadas niquel";	
				product.stock = "333";
				product.cost = "20.3";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;
    			
				productList.add(product);	    				

				product = new Product(); 
				product.reference = "50314";
				product.description = "iSelect 5 pulgadas niquel";	
				product.stock = "222";
				product.cost = "40.3";
				product.hasBOM = true;
    			product.inBOM = true;
    			product.itemMode = loaderMode;
    			productList.add(product);	    				
				break;
				
    		case NavisionTool.LOADER_PRODUCT_INFO:
    		default:
		    	product = new Product(); 	
		    	product.reference = filterString;
				product.description = "Description of " + filterString;
		    	
				product.stock ="222";
				product.cost = "40.3";
				product.price = "120.2";
		    	
		    	product.inPlannedProduction = "200";
		    	product.inProduction = "0";
		    	product.purchase = "500";
		    	
		    	product.transfer = "13";
		    	product.sale = "25";
		    	product.usedInPlannedProduction = "150";
		    	product.usedInProduction = "255";
		    	product.orderPoint = "100";
		    	
		    	product.consumeByMonth[0] = "-10.5";
		    	product.consumeByMonth[1] = "-8.5";
		    	product.consumeByMonth[2] = "-12.5";
		    	product.consumeByMonth[3] = "-20.5";
		    	product.consumeByMonth[4] = "-30.5";
		    	product.consumeByMonth[5] = "-15.5";
		    	product.consumeByMonth[6] = "-3.5";
		    	product.consumeByMonth[7] = "-0.5";
		    	product.consumeByMonth[8] = "-8.5";
		    	product.consumeByMonth[9] = "-10.5";
		    	product.consumeByMonth[10] = "-12.5";
		    	product.consumeByMonth[11] = "-12.5";
		    	product.consumeByMonth[12] = "-18.5";
		    	product.consumeByMonth[13] = "-18.5";
		    	product.consumeByMonth[14] = "-30.5";
		    	product.consumeByMonth[15] = "-35.5";
		    	product.consumeByMonth[16] = "-20.5";
		    	product.consumeByMonth[17] = "-20.5";
		    	product.consumeByMonth[18] = "-15.5";
		    	product.consumeByMonth[19] = "-15.5";
		    	product.consumeByMonth[20] = "-35.5";
		    	product.consumeByMonth[21] = "-30.5";
		    	product.consumeByMonth[22] = "-20.5";
		    	product.consumeByMonth[23] = "-10.5";
		    	
		    	
    			product.itemMode = loaderMode;
    			
    			
    			
    			productList.add(product);

	    	}		    	

	    	return productList;
			
		}
		else
		{

			/*  Connection */

		    Connection conn = NavisionTool.openConnection();

		    if (conn!=null) 
		    {    	
			    try 
			    {
			    	ResultSet result;
			    	switch (loaderMode)
			    	{
	
		    		case NavisionTool.LOADER_PRODUCT_IN_USE:
		    		case NavisionTool.LOADER_PRODUCT_SEARCH_IN_USE:
		    			result = NavisionTool.queryListInBOM(filterString);
						if (result==null) break;
					    while(result.next())
					    {
					    	product = new Product(); 	
					    	product.reference = result.getString(1);	
					    	product.quantity = result.getString(2);		
					    	product.description = NavisionTool.queryDescription(product.reference);	
							product.stock = NavisionTool.queryStock(product.reference);
							if (loaderMode == NavisionTool.LOADER_PRODUCT_IN_USE)
							{
								product.inProduction = NavisionTool.queryInProduction(product.reference);
						    	product.purchase = NavisionTool.queryPurchase(product.reference);
						    	
						    	product.transfer = NavisionTool.queryTransfer(product.reference);
						    	product.sale = NavisionTool.querySale(product.reference);
						    	product.usedInProduction = NavisionTool.queryUsedInProduction(product.reference);
						    	product.orderPoint = NavisionTool.queryOrderPoint(product.reference);
					
							}
					    	product.cost = NavisionTool.queryCost(product.reference);
					    	product.inBOM = NavisionTool.queryInBOM(product.reference);
					    	product.hasBOM = true;
			    			product.itemMode = loaderMode;
					    	productList.add(product);
					    }
						break;
	
		    		case NavisionTool.LOADER_PRODUCT_BOM:
		    		case NavisionTool.LOADER_PRODUCT_SEARCH_BOM:		    			
		    			result = NavisionTool.queryListBOM(filterString);
                        if (result==null) break;
		      			while(result.next())
						{					
					    	product = new Product(); 	
					    	product.reference = result.getString(1);	
					    	product.description = result.getString(2) + result.getString(4);	
					    	product.quantity = result.getString(3);		
							product.stock = NavisionTool.queryStock(product.reference);
							if (loaderMode == NavisionTool.LOADER_PRODUCT_BOM)
							{
						    	product.inProduction = NavisionTool.queryInProduction(product.reference);
						    	product.purchase = NavisionTool.queryPurchase(product.reference);
						    	
						    	product.transfer = NavisionTool.queryTransfer(product.reference);
						    	product.sale = NavisionTool.querySale(product.reference);
						    	product.usedInProduction = NavisionTool.queryUsedInProduction(product.reference);
						    	product.orderPoint = NavisionTool.queryOrderPoint(product.reference);
							}
						    	
							product.cost = NavisionTool.queryCost(product.reference);
					    	product.inBOM = true;
					    	product.hasBOM = NavisionTool.queryHasBOM(product.reference);
			    			product.itemMode = loaderMode;
					    	productList.add(product);
						}	    	
				 		break;
	
		    		case NavisionTool.LOADER_PRODUCT_SEARCH:
	    				result = NavisionTool.queryList(filterString);
                        if (result == null) break;
		      			while(result.next())
						{					
					    	product = new Product(); 	
					    	product.reference = result.getString(1);
							product.description = result.getString(2) + result.getString(3);	
							product.stock = NavisionTool.queryStock(product.reference);
							product.cost = result.getString(4);
							product.hasBOM = !(result.getString(5).isEmpty());
			    			product.inBOM = NavisionTool.queryInBOM(product.reference);
			    			product.itemMode = loaderMode;
							productList.add(product);
						}
						break;
		    		case NavisionTool.LOADER_PRODUCT_INFO:
		    		default: 								
				    	product = new Product(); 	
				    	product.reference = filterString;
				    	
						product.stock = NavisionTool.queryStock(filterString);
						product.cost = NavisionTool.queryCost(filterString);
						product.description = NavisionTool.queryDescription(filterString);
				    	
				    	product.inPlannedProduction = NavisionTool.queryInPlannedProduction(filterString);
				    	product.inProduction = NavisionTool.queryInProduction(filterString);
				    	product.purchase = NavisionTool.queryPurchase(filterString);
				    	product.price = NavisionTool.queryRetailPrice(filterString);
				    	
				    	product.transfer = NavisionTool.queryTransfer(filterString);
				    	product.sale = NavisionTool.querySale(filterString);
				    	product.usedInPlannedProduction = NavisionTool.queryUsedInPlannedProduction(filterString);
				    	product.usedInProduction = NavisionTool.queryUsedInProduction(filterString);
				    	product.orderPoint = NavisionTool.queryOrderPoint(filterString);
				    	
		    			product.itemMode = NavisionTool.LOADER_PRODUCT_INFO;

		    			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000",Locale.US);
					    Calendar calendar = Calendar.getInstance();
					    calendar.add(Calendar.MONTH,-Product.NUMBER_OF_MONTHS);
					    String fromDate;
					    String toDate;
					    
					    for (int i=0;i<Product.NUMBER_OF_MONTHS;i++)
					    {
					    	fromDate = dateFormat.format(calendar.getTime());
						    calendar.add(Calendar.MONTH,1);
					    	toDate = dateFormat.format(calendar.getTime());
					    	product.consumeByMonth[i] = NavisionTool.queryConsumeInPeriod(product.reference,fromDate,toDate);
					    }


						productList.add(product);						
					 	break;
					        
				    }		    		     	
				} catch (SQLException e) {
					e.printStackTrace();
				}
	
			    NavisionTool.closeConnection();
			    return productList;
		    } 
		}
	    return null;
	}
	
	
	@Override
	public void deliverResult(ArrayList<Product> arrayList)
	{
	    productList = arrayList;
		
		if (isStarted())
		{
			super.deliverResult(arrayList);
		}
	}
	
	@Override
	protected void onStartLoading()
	{

		if (productList != null)
		{
			deliverResult(productList);
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
		if (productList!=null)
	    {
	    	productList.clear();
		    productList = null;
    	}
		onStopLoading();
	}
	
	@Override
	public void onCanceled(ArrayList<Product> arrayList)
	{
		super.onCanceled(arrayList);
	}
	
}
