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

import com.jieehd.villain.toolkit.utils.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ROMTab extends PreferenceActivity {
	
	private static final String KEY_BUILD_VERSION = "rom_version_pref";
	private static final String KEY_TEST = "test_pref";	
    public final static String URL = "http://dl.dropbox.com/u/44265003/update.json";
    public final static String device = Utils.getROMVersion();
    public static MenuItem refresh;
    public static Dialog dialog;
    
    JSONObject json;
    TextView tv_display;
    /** Called when the activity is first created. */
    @Override
    @SuppressWarnings({ "deprecation", "unused" })
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.rom);
        
        setStringSummary(KEY_BUILD_VERSION, device);
        dialog = new Dialog(this);	    
		dialog.setTitle("Loading..");
		dialog.setContentView(R.layout.spinner_dialog);
		Spinner spin = (Spinner) findViewById(R.id.spinner);
        
        Preference test = (Preference) findPreference(KEY_TEST);
        test.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				// TODO Auto-generated method stub
					dialog.show();
					new Read().execute(device);
				return false;
			}
        	
        });
        
    }
    

    
    @SuppressWarnings("deprecation")
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
      
      public JSONObject getQuote() throws ClientProtocolException, IOException, JSONException{
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
        }else{
          return null;
        }
      }
      
      public class Read extends AsyncTask<String, Integer, Display> {
      @Override
      protected Display doInBackground(String... params) {
      	final String device = params[0];
        // TODO Auto-generated method stub
        try {
      	  	json = getQuote();
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
  	            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
  	            toast.show();
  	     }
  	 }
      
      @Override
      @SuppressWarnings("deprecation")
      public void onPostExecute(final Display result) {
    	  try {
    		  dialog.dismiss();
    		  if (device.equals(result.mRom)) {
    			  new ToastMessageTask().execute("No new version!");
    		  } else {
    			  new ToastMessageTask().execute("New version available!");
    		  }
    		  
    	  } catch (Exception e){
    		  e.printStackTrace();
				AlertDialog alertDialog = new AlertDialog.Builder(ROMTab.this).create();
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
