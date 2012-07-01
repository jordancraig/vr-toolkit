package com.jieehd.villain.toolkit;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.jieehd.villain.toolkit.utils.ShellCommand;
import com.jieehd.villain.toolkit.utils.ShellCommand.CommandResult;
import com.jieehd.villain.toolkit.utils.Utils;

public class FetchFile extends AsyncTask<String, String, String> {
	
	public static Context cx;
	public static final String PATH = "/VillainToolkit/ROMs/";
  
	  protected String doInBackground(String... params) {
		
	    final String ROMname = params[0];
	    final String URL = params[1];
	    	DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
	    	request.setDescription(ROMname);
	    	request.setTitle("VR Toolkit");
	    	// in order for this if to run, you must use the android 3.2 to compile your app
	    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    	    request.allowScanningByMediaScanner();
	    	    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	    	}
	    	request.setDestinationInExternalPublicDir(PATH, ROMname + ".zip");

	    	// get download service and enqueue file
	    	DownloadManager manager = (DownloadManager) ROMTab.CX.getSystemService(Context.DOWNLOAD_SERVICE);
	    	manager.enqueue(request);
	    	
		return null;  
	  }
  
  
	  protected void onPostExecute(String... params) {
			  ShellCommand cmd = new ShellCommand();
			  CommandResult cmdr = cmd.su.runWaitFor("echo 'install_zip(\"" + ROMTab.PATH + params[0] + ".zip" + "\");'" + " > /cache/recovery/extendedcommand\n");
			  if(!cmdr.success())
				  Log.e(Utils.LOGTAG, "Zip flash command failed");
			  else
				  Log.i(Utils.LOGTAG, "Zip flash command succeeded");
	  }
}