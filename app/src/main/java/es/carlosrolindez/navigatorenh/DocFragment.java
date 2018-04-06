package es.carlosrolindez.navigatorenh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

public class DocFragment extends Fragment
{
	private ArrayList<FileDescription> fileDescriptionList;
	private ListView list;
	private DocListAdapter docListAdapter;
	private boolean progressAllowed;
	private boolean progressPending;
	
	public static DocFragment newInstance() 
	{
		DocFragment  fragment = new DocFragment();
		fragment.fileDescriptionList=null;
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
    	return  inflater.inflate(R.layout.doc_layout, container, false);    
    }

    @Override    
    public void onActivityCreated(Bundle savedInstanceState) 
    {	
    	super.onActivityCreated(savedInstanceState);
    
    	

    	if (savedInstanceState != null) 
    		fileDescriptionList = savedInstanceState.getParcelableArrayList(NavisionTool.DOC_LIST_KEY);
   	
 	    list=(ListView)getActivity().findViewById(R.id.doc_list);    

 	    docListAdapter = new DocListAdapter(getActivity(),fileDescriptionList);

	    list.setAdapter(docListAdapter);
	    list.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    	{ 			
	   			FileDescription fileDescription = (FileDescription) parent.getItemAtPosition(position);

    			AsyncFileAccess copyAsyncTask = new AsyncFileAccess();
    			copyAsyncTask.execute(fileDescription.fileName, fileDescription.type, FileTool.getAddress(), FileTool.getDomain(), FileTool.getUsername(), FileTool.getPassword());
	    	}
		});
 
        progressAllowed = true;
        if (progressPending) showProgress (true);
        
	    if (fileDescriptionList!=null) 
       		showResultSet(fileDescriptionList);
    }
    

	
    @Override
    public void onSaveInstanceState(Bundle savedState) 
	{
	    super.onSaveInstanceState(savedState);
	    savedState.putParcelableArrayList(NavisionTool.DOC_LIST_KEY, fileDescriptionList);
	}   

    public void showProgress(boolean progress)
	{
    	if (progressAllowed)
    	{
        	if (progress)    getActivity().findViewById(R.id.loadingPanel_docList).setVisibility(View.VISIBLE);
        	else getActivity().findViewById(R.id.loadingPanel_docList).setVisibility(View.GONE);
        	progressPending = false;
    	}
    	else progressPending = progress;
	}

	void showResultSet(ArrayList<FileDescription> fileDescriptionListLoaded)
	{
		if (fileDescriptionListLoaded == null) 
			fileDescriptionList = null;
		else
		{
			fileDescriptionList = new ArrayList<>();
			for (FileDescription item : fileDescriptionListLoaded)
			{
					fileDescriptionList.add(item);
			}
		}

		if (docListAdapter!=null) 
			docListAdapter.showResultSet(fileDescriptionList);

	}
	public class AsyncFileAccess extends AsyncTask<String, Integer, File> 
	{
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getActivity());
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage("Loading");
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);   
			dialog.setMax(100);
			dialog.setProgress(100);
			dialog.show();
	    }
		
	    @Override 
	    protected File doInBackground(String... strPCPath /* path, address, domain, username, password */) {
	    	 
		    SmbFile smbFolderToDownload;   
		    
		    try 
		    {
		        File localFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/Temporalfolder");

		        // create sdcard path if not exist.
		        if (!localFilePath.isDirectory()) 
		        {
		            localFilePath.mkdir();
		        }
		        try 
		        {         
		        	int index = 0;
		        	for (String typeItem : FileDescription.typeList)
		        	{
		        		if (typeItem.equals(strPCPath[1]))
		        			break;
		        		index++;
		        	}
		        	
					NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(strPCPath[3], strPCPath[4], strPCPath[5]);

					String urlPath =  "smb://" + strPCPath[2] + '/' + FileDescription.pathList[index] + '/';		
					
		            smbFolderToDownload = new SmbFile(urlPath , auth);
		            
					for(SmbFile smbFileToDownload : smbFolderToDownload.listFiles()) 
					{
					    if(smbFileToDownload.getName().contains(strPCPath[0]))
						{
			                InputStream inputStream = smbFileToDownload.getInputStream();

			                File localFile = new File(Environment.getExternalStorageDirectory(/*Environment.DIRECTORY_DOWNLOADS*/),"/Navitron/"+smbFileToDownload.getName());
			                long fileLength = smbFileToDownload.length();
			                
			                OutputStream out = new FileOutputStream(localFile);
			                
			                byte buf[] = new byte[16 * 1024];
			                int len;
			                int cycles = 0;
			                while ((len = inputStream.read(buf)) > 0) 
			                {
			                    out.write(buf, 0, len);
			                    cycles++;
			                    int percentage = (100*cycles)/((int)(fileLength / (16*1024)) + 1);
			                    publishProgress(percentage);
			                }
			                out.flush();
			                out.close();
			                inputStream.close();
			                return localFile;

					    }
					
					}
					return null;

		            /*smbFileToDownload = new SmbFile(url , auth);
		            String smbFileName = smbFileToDownload.getName();
	                InputStream inputStream = smbFileToDownload.getInputStream();

	                File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/Temporalfolder/"+smbFileName);
	                long fileLength = smbFileToDownload.length();
	                
	                OutputStream out = new FileOutputStream(localFile);
	                
	                byte buf[] = new byte[16 * 1024];
	                int len;
	                int cicles = 0;
	                while ((len = inputStream.read(buf)) > 0) 
	                {
	                    out.write(buf, 0, len);
	                    cicles++;
	                    int percentage = (100*cicles)/((int)(fileLength / (16*1024)) + 1);
	                    publishProgress(percentage);
	                }
	                out.flush();
	                out.close();
	                inputStream.close();
	                return localFile;*/
		        }
		        catch (Exception e) 
		        {
		            e.printStackTrace();
			        return null;
		        }
		    } 
		    catch (Exception e) 
		    {
		        e.printStackTrace();
		        return null;
		    }   

	    }
	    
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	        int progress = values[0];
	        dialog.setProgress(progress);
	    }
	    
	   
	    @Override protected void onPostExecute(File file) {
	    	dialog.dismiss();
			if (file== null) return;
	    	//Uri uri = Uri.fromFile(file);
			Uri uri = FileProvider.getUriForFile(getContext(),"es.carlosrolindez.navigatorenh.fileprovider",file);
					Uri.fromFile(file);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			// Check what kind of file you are trying to open, by comparing the url with extensions.
			// When the if condition is matched, plugin sets the correct intent (mime) type, 
			// so Android knew what application to use to open the file
			if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
			    // Word document
			    intent.setDataAndType(uri, "application/msword");
			} else if(file.toString().toLowerCase().contains(".pdf")) {
			    // PDF file
			    intent.setDataAndType(uri, "application/pdf");
			} else if(file.toString().toLowerCase().contains(".xls") || file.toString().toLowerCase().contains(".xlsx")) {
			    // Excel file
			    intent.setDataAndType(uri, "application/vnd.ms-excel");
			} else if(file.toString().toLowerCase().contains(".zip") || file.toString().toLowerCase().contains(".rar"))  {
			    // ZIP Files
			    intent.setDataAndType(uri, "application/zip");
			} else if(file.toString().toLowerCase().contains(".rtf")) {
			    // RTF file
			    intent.setDataAndType(uri, "application/rtf");
			} else if(file.toString().toLowerCase().contains(".gif")) {
			    // GIF file
			    intent.setDataAndType(uri, "image/gif");
			} else if(file.toString().toLowerCase().contains(".jpg") || file.toString().toLowerCase().contains(".jpeg") || file.toString().toLowerCase().contains(".png")) {
			    // JPG file
			    intent.setDataAndType(uri, "image/jpeg");
			} else if(file.toString().toLowerCase().contains(".txt")) {
			    // Text file
			    intent.setDataAndType(uri, "text/plain");
			} else {
			    //if you want you can also define the intent type for any other file
			    
			    //additionally use else clause below, to manage other unknown extensions
			    //in this case, Android will show all applications installed on the device
			    //so you can choose which application to use
			    intent.setDataAndType(uri, "*/*");
			}
	        
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			startActivity(intent);
	    }

	    @Override
	    protected void onCancelled() {
	    	 dialog.dismiss();
	    }


	}

}
    
