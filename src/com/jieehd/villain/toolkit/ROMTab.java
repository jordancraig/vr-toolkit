package com.jieehd.villain.toolkit;


import java.io.File;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jieehd.villain.toolkit.utils.Utils;

@SuppressWarnings("deprecation")
public class ROMTab extends PreferenceFragment {
	
	
    private static final String KEY_BUILD_VERSION = "rom_version_pref";
    private static final String KEY_TEST = "test_pref";	
    public final static String URL = "http://dl.dropbox.com/u/44265003/update.json";
    public final static File sdDir = (Environment.getExternalStorageDirectory());
    public static final String PATH = sdDir + "/VillainToolKit/ROMs/";
    public static final String device = android.os.Build.MODEL.toUpperCase();
    public String version;
    public static MenuItem refresh;
    public static final Utils utils = new Utils();
    public static Dialog dialog;
    static Context cx;
    public static View view;
    public static Activity ac;
    
    JSONObject json;
    TextView tv_display;

    
    
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.d("ROMTab", "Started DisplayUi");
	        addPreferencesFromResource(R.xml.rom);
	        
	        try {
	        	version = utils.new Read().execute().get().toString();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        
	        setStringSummary(KEY_BUILD_VERSION, version);

	        
	        Preference test = (Preference) findPreference(KEY_TEST);
	        test.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					// TODO Auto-generated method stub
			        	dialog = new Dialog(TabDisplay.mContext);	    
			        	dialog.setTitle("Loading..");
			        	dialog.setContentView(R.layout.spinner_dialog);
			        	@SuppressWarnings("unused")
						Spinner spin = (Spinner) view.findViewById(R.id.spinner);
						dialog.show();
						Log.d(Utils.LOGTAG, device);
						Log.d(Utils.LOGTAG, version);
						new Read().execute(device);
					return true;
				}
	        	
	        });
	        
	    }
	    
	    
    

    
	    private void setStringSummary(String preference, String value) {
	        try {
	            findPreference(preference).setSummary(value);
	        } catch (RuntimeException e) {
	            findPreference(preference).setSummary("");
	        }
	    }
	    
	    
	      
	      
	      public class Display {
	      	public String mRom;
	      	public String mChange;
	      	public String mUrl;
	      	public String mBuild;
	
	      	public Display (String rom, String changelog, String downurl, String build) {
	      		mRom = rom;
	      		mChange = changelog;
	      		mUrl = downurl;
	      		mBuild = build;
	      	}
	      }
	      
	      public JSONObject getVersion() throws ClientProtocolException, IOException, JSONException{
	      HttpClient client = new DefaultHttpClient();
	        StringBuilder url = new StringBuilder(URL);
	        HttpGet get = new HttpGet(url.toString());
	        HttpResponse r = client.execute(get);
	        int status = r.getStatusLine().getStatusCode();
	        if (status == 200){
	          HttpEntity e = r.getEntity();
	          String data = EntityUtils.toString(e);
	          JSONObject stream = new JSONObject(data);
	          JSONObject quote = stream.getJSONObject("villain-roms");
	          return quote;
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
	      	  	json = getVersion();
	  			String version = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("version");
	  			String changelog = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("changelog");
	  			String downurl = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("url");
	  			String build = json.getJSONObject("device").getJSONArray(device).getJSONObject(0).getString("rom");
	  			return new Display(version, changelog, downurl, build);
	
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	        	return null;
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
	  	            Toast toast = Toast.makeText(cx, result, Toast.LENGTH_SHORT);
	  	            toast.show();
	  	     }
	  	 }
	      
	      @Override
	      public void onPostExecute(final Display result) {
	    	  
	    	  final String ROM = result.mRom;
	    	  final String CHANGELOG = result.mChange;
	    	  final String BUILD = result.mBuild;
	    	  final String URL = result.mUrl;
	    	  
	    	  cx = cx.getApplicationContext();
	    	  
		      boolean file = new File(PATH).exists(); {
			     if (file == true) {
			      		
			     }else if (file == false){
			    	 
			      	new File(PATH).mkdirs();
			     }
		      }
		    	 
	      	
	    	  
	    	  try {
	    		  dialog.dismiss();
	    		  if (version.equals(ROM)) {    			  
	    			  new ToastMessageTask().execute("No new version!");
	    			  
	    		  } else {
	    			  final AlertDialog newvDialog = new AlertDialog.Builder(TabDisplay.mContext).create();
	    			  newvDialog.setTitle("New version!");
	    			  newvDialog.setMessage("Build: " + BUILD + "\nChangelog: " + CHANGELOG);
	    			  newvDialog.setButton("Download", new DialogInterface.OnClickListener() {
						
	    				  @Override
	    				  public void onClick(DialogInterface dialog, int which) {
							new FetchFile().execute(ROM, URL);
	    				  }
	    			  });
	    			 newvDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							newvDialog.dismiss();
						}
	    				 
	    			 });
	    			 newvDialog.setButton3("Remind Me", new DialogInterface.OnClickListener() {   				 
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String ns = Context.NOTIFICATION_SERVICE;
							NotificationManager mNotificationManager = (NotificationManager) cx.getSystemService(ns);
							
							int icon = R.drawable.ic_stat_ic_notify_reminder;
							CharSequence tickerText = "VR Toolkit - Update Reminder";
							long when = System.currentTimeMillis();
	
							Notification notification = new Notification(icon, tickerText, when);
							
							CharSequence contentTitle = "VR Toolkit";
							CharSequence contentText = "New update for: " + ROM;
							Intent notificationIntent = new Intent(TabDisplay.mContext, ROMTab.class);
							PendingIntent contentIntent = PendingIntent.getActivity(cx, 0, notificationIntent, 0);
	
							notification.setLatestEventInfo(cx, contentTitle, contentText, contentIntent);
							
							final int NOTIFY = 1;
							
							mNotificationManager.notify(NOTIFY, notification);
						}
					});
	    			 newvDialog.show();
	    		  }
	    		  
	    	  } catch (Exception e){
	    		  e.printStackTrace();
					AlertDialog alertDialog = new AlertDialog.Builder(TabDisplay.mContext).create();
					alertDialog.setTitle("Device not found!");
					alertDialog.setMessage("Couldn't find your device/ROM on our servers! If you think we did something wrong, feel free to abuse us on IRC.");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					 
					       //here you can add functions
					      }
					     
					});
					alertDialog.show();
	    	 }
	    }
	}

}
