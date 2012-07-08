package com.jieehd.villain.toolkit;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

public class FetchFile extends AsyncTask<String, String, String> {
	
	public static Context cx;
	public static final String PATH = "/VillainToolkit/ROMs/";
  
	  protected String doInBackground(String... params) {
		
	    final String ROMname = params[0];
	    final String URL = params[1];
	    	DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
	    	request.setDescription(ROMname);
	    	request.setTitle("VR Toolkit");
	    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    	    request.allowScanningByMediaScanner();
	    	    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	    	}
	    	request.setDestinationInExternalPublicDir(PATH, ROMname + ".zip");

	    	DownloadManager manager = (DownloadManager) ROMTab.cx.getSystemService(Context.DOWNLOAD_SERVICE);
	    	manager.enqueue(request);
		return null;  
	  }
  
  
	  protected void onPostExecute(String... params) {
		  
	  }
}