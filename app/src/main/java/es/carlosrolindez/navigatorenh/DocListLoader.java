package es.carlosrolindez.navigatorenh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;



public class DocListLoader extends AsyncTaskLoader<ArrayList<FileDescription>>
{
	private ArrayList<FileDescription> fileList;
	private String filterString;
	private int loaderMode;

	
	public DocListLoader(Context ctx,int id, Bundle filter)
	{
		super(ctx);
		filterString = filter.getString(NavisionTool.QUERY,"").toUpperCase(Locale.US);	
		
		loaderMode = id;
	
	}
	
	@Override
	public ArrayList<FileDescription> loadInBackground()
	{	
		FileDescription fileDescription; 	
		fileList = new ArrayList<>();
		
		if (NavisionTool.readMode()==NavisionTool.MODE_EMULATOR)
		{
	    	switch (loaderMode)
	    	{
    		case NavisionTool.LOADER_PRODUCT_DOC:   
    		default:
    			
    			fileDescription = new FileDescription(); 
    			fileDescription.fileName = "Hola";
    			fileDescription.type = "";
    			fileList.add(fileDescription);	
 
    			fileDescription = new FileDescription(); 
    			fileDescription.fileName = "Adios";
    			fileDescription.type = "";
    			fileList.add(fileDescription);			

		 		break;

	    	}		    	

	    	return fileList;
			
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
	
		    		case NavisionTool.LOADER_PRODUCT_DOC:
		    		default:
		    			result = NavisionTool.queryDocs(filterString);
						if (result==null) break;
					    while(result.next())
					    {
			    			fileDescription = new FileDescription(); 
			      			fileDescription.fileName = result.getString(1);
			      			fileDescription.type = result.getString(2);
							fileList.add(fileDescription);
					    }
					 	break;
					        
				    }		    		     	
				} catch (SQLException e) {
					e.printStackTrace();

				}
	
			    NavisionTool.closeConnection();
			    return fileList;
		    } 
		}
	    return null;
 	}
	
	
	@Override
	public void deliverResult(ArrayList<FileDescription> arrayList)
	{
		fileList = arrayList;
		
		if (isStarted())
		{
			super.deliverResult(arrayList);
		}
	}
	
	@Override
	protected void onStartLoading()
	{

		if (fileList != null)
		{
			deliverResult(fileList);
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
		if (fileList!=null)
	    {
			fileList.clear();
			fileList = null;
    	}
		onStopLoading();
	}
	
	@Override
	public void onCanceled(ArrayList<FileDescription> arrayList)
	{
		super.onCanceled(arrayList);
	}
	
}
