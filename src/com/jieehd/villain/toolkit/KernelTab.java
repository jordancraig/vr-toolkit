package com.jieehd.villain.toolkit;

import com.jieehd.villain.toolkit.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class KernelTab extends PreferenceActivity {


    /** Called when the activity is first created. */
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.kernel);
        
        final String kernelVersion = System.getProperty("os.version");
        
        Preference kernel_ver = (Preference) findPreference("kernel_version");
        kernel_ver.setSummary(kernelVersion);
        
        Preference kernel_is_custom = (Preference) findPreference("kernel_custom");
        
        if (kernelVersion.toLowerCase().contains("ninphetamin3")) {
        	kernel_is_custom.setSummary("VillainROM supported kernel!");
        	kernel_is_custom.setSelectable(false);
        	
        } else {
        	kernel_is_custom.setSummary("Not a VillainROM supported kernel." + "\n" + "What does this mean?");
        	kernel_is_custom.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference) {
					// TODO Auto-generated method stub
					AlertDialog alertDialog = new AlertDialog.Builder(KernelTab.this).create();
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
}