package com.jieehd.villain.toolkit;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class KernelTab extends PreferenceFragment {
	
    JSONObject json;
    public final static String URL = "http://dl.dropbox.com/u/44265003/update.json";
    public static Context cx;

	public class DisplayUi extends TabActivity {
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.kernel);
	        
	        final String kernelVersion = System.getProperty("os.version");
	        
	        Preference kernel_ver = (Preference) findPreference("kernel_version");
	        kernel_ver.setSummary(kernelVersion);
	        
	        Preference kernel_is_custom = (Preference) findPreference("kernel_custom");
	        
	        if (kernelVersion.toLowerCase().contains("ninphetamin3")) {
	        	kernel_is_custom.setSummary("VillainROM supported kernel!\nClick here to check for updates.");
	        	kernel_is_custom.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
					@Override
					public boolean onPreferenceClick(Preference preference) {
						// TODO Auto-generated method stub
						new Read().execute();
						return false;
					}       		
	        	});
	        	
	        } else {
	        	kernel_is_custom.setSummary("Not a VillainROM supported kernel." + "\n" + "What does this mean?");
	        	kernel_is_custom.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
					@Override
					public boolean onPreferenceClick(Preference preference) {
						// TODO Auto-generated method stub
						AlertDialog alertDialog = new AlertDialog.Builder(KernelTab.DisplayUi.this).create();
						alertDialog.setTitle("Why is my kernel unsupported?");
						alertDialog.setMessage("Unfortunately due to hosting and compatibility, not all kernels for your device can be supported in this application. While we would like to include as many as possible, it is not viable to include all custom kernels for a myriad of devices. Sorry!");
						alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						      public void onClick(DialogInterface dialog, int which) {
	
						       //here you can add functions
						      }
	
						});
						alertDialog.show();
						return false;
					}
	        		
	        	});
	        
	        }
	    }
	    
	    public class Display {
	      	public String mKernel;
	      	public String mUrl;
	
	      	public Display (String rom, String downurl) {
	      		mKernel = rom;
	      		mUrl = downurl;
	      	}
	      }
	    
	    // A class that will run Toast messages in the main GUI context
	    private class ToastMessageTask extends AsyncTask<String, String, String> {
	     String toastMessage;
	
		     @Override
		     protected String doInBackground(String... params) {
		         toastMessage = params[0];
		         return toastMessage;
		     }
		     
		     @SuppressWarnings("unused")
		     protected void OnProgressUpdate(String... values) { 
		         super.onProgressUpdate(values);
		     }
		    // This is executed in the context of the main GUI thread
		     protected void onPostExecute(String result){
		            Toast toast = Toast.makeText(cx.getApplicationContext(), result, Toast.LENGTH_SHORT);
		            toast.show();
		     }
	    }
	    
	    public JSONObject getKernelV() throws ClientProtocolException, IOException, JSONException{
			HttpClient client = new DefaultHttpClient();
	    	StringBuilder url = new StringBuilder(URL);
	    	HttpGet get = new HttpGet(url.toString());
	    	HttpResponse r = client.execute(get);
	    	int status = r.getStatusLine().getStatusCode();
	    	if (status == 200) {
	    		HttpEntity e = r.getEntity();
	    		String data = EntityUtils.toString(e);
	    		JSONObject stream = new JSONObject(data);
	    		JSONObject tweaks = stream.getJSONObject("avail-tweaks");
	    		return tweaks;
	    	} else {
	    		return null;
	    	}
	    }
	    
	    
	    public class Read extends AsyncTask<String, Integer, Display> {
			@Override
			protected Display doInBackground(String... params) {
				final String device = params[0];
				// TODO Auto-generated method stub
				try {
					json = getKernelV();
		  			String downurl = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("url");
		  			String build = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("rom");
		  			return new Display(downurl, build);
	
				} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new ToastMessageTask().execute("A server issue occured, please try again.");
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new ToastMessageTask().execute("Error whilst reading content.");
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new ToastMessageTask().execute("No content for your device.");
				}
	
				return null;
			}
	    }
	    
    }
}